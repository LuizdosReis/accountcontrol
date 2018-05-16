package br.com.accountcontrol.category.api.v1;

import br.com.accountcontrol.api.AbstractRestControllerTest;
import br.com.accountcontrol.category.Category;
import br.com.accountcontrol.category.CategoryService;
import br.com.accountcontrol.category.builder.CategoryBuilder;
import br.com.accountcontrol.category.dto.CategoryCreateDTO;
import br.com.accountcontrol.category.dto.CategoryUpdateDTO;
import br.com.accountcontrol.exception.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@WithMockUser
public class CategoryEndpointTest extends AbstractRestControllerTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private CategoryService service;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(springSecurity())
                .build();

    }

    @Test
    public void saveCategoryShouldReturnStatusCodeCreated() throws Exception {
        CategoryCreateDTO categoryCreate = CategoryBuilder.CATEGORY_CREATE_DTO;
        Category categoryReturned = CategoryBuilder.CATEGORY;

        when(service.save(categoryCreate)).thenReturn(categoryReturned);

        mockMvc.perform(post(CategoryEndpoint.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(categoryCreate))
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description", is(categoryReturned.getDescription())))
                .andExpect(jsonPath("$.type", is(categoryReturned.getType().name())))
                .andExpect(jsonPath("$.id", is(categoryReturned.getId().intValue())));
    }

    @Test
    public void saveInvalidCategoryShouldReturnStatusCodeBadRequest() throws Exception {
        mockMvc.perform(post(CategoryEndpoint.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(CategoryCreateDTO.builder().build()))
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", equalTo("Field Validation Errors")))
                .andExpect(jsonPath("$.detail", equalTo("Field Validation Errors")))
                .andExpect(jsonPath("$.developerMessage",
                        equalTo("org.springframework.web.bind.MethodArgumentNotValidException")))
                .andExpect(jsonPath("$.date", notNullValue()))
                .andExpect(jsonPath("$.fieldErrors", hasSize(2)))
                .andExpect(jsonPath("$.fieldErrors[*].field", containsInAnyOrder(
                        "description", "type")))
                .andExpect(jsonPath("$.fieldErrors[*].message", containsInAnyOrder(
                        "The description not be empty", "The Type not be empty")))
                .andExpect(jsonPath("$.fieldErrors[*].code", containsInAnyOrder(
                        "NotEmpty", "NotNull")));
    }

    @Test
    public void findAllCategoryShouldReturnStatusCodeOk() throws Exception {

        Category categoryReturned = CategoryBuilder.CATEGORY;

        when(service.findAll(PageRequest.of(0, 20)))
                .thenReturn(new PageImpl<>(Collections.singletonList(categoryReturned)));

        mockMvc.perform(get(CategoryEndpoint.BASE_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.totalElements", is(1)))
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].description", is("Car")))
                .andExpect(jsonPath("$.content[0].type", is("OUT")));
    }

    @Test
    public void updateCategoryShouldReturnStatusCodeOk() throws Exception {

        CategoryUpdateDTO categoryUpdate = CategoryBuilder.CATEGORY_UPDATE_DTO;
        Category category = CategoryBuilder.CATEGORY;

        when(service.update(categoryUpdate)).thenReturn(category);

        mockMvc.perform(put(CategoryEndpoint.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(categoryUpdate))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(category.getId().intValue())))
                .andExpect(jsonPath("$.description", is("Car")))
                .andExpect(jsonPath("$.type", is("OUT")));
    }

    @Test
    public void updateInvalidCategoryShouldReturnStatusCodeBadRequest() throws Exception {
        mockMvc.perform(put(CategoryEndpoint.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(CategoryUpdateDTO.builder().build()))
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", equalTo("Field Validation Errors")))
                .andExpect(jsonPath("$.detail", equalTo("Field Validation Errors")))
                .andExpect(jsonPath("$.developerMessage",
                        equalTo("org.springframework.web.bind.MethodArgumentNotValidException")))
                .andExpect(jsonPath("$.date", notNullValue()))
                .andExpect(jsonPath("$.fieldErrors", hasSize(2)))
                .andExpect(jsonPath("$.fieldErrors[*].field", containsInAnyOrder(
                        "description", "id")))
                .andExpect(jsonPath("$.fieldErrors[*].message", containsInAnyOrder(
                        "The description not be empty", "the id not be empty")))
                .andExpect(jsonPath("$.fieldErrors[*].code", containsInAnyOrder(
                        "NotEmpty", "NotNull")));
    }

    @Test
    public void findByIdCategoryShouldReturnStatusCodeOk() throws Exception {

        Category category = CategoryBuilder.CATEGORY;

        when(service.findById(category.getId())).thenReturn(category);

        mockMvc.perform(get(CategoryEndpoint.BASE_URL + "/" + category.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(category.getId().intValue())))
                .andExpect(jsonPath("$.description", is("Car")))
                .andExpect(jsonPath("$.type", is("OUT")));
    }

    @Test
    public void findByIdCategoryShouldReturnStatusCodeNotFound() throws Exception {

        when(service.findById(1L)).thenThrow(new ResourceNotFoundException("Category Not Found"));

        mockMvc.perform(get(CategoryEndpoint.BASE_URL + "/1"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.date").exists())
                .andExpect(jsonPath("$.title").value("Resource not found"))
                .andExpect(jsonPath("$.detail").value("Category Not Found"))
                .andExpect(jsonPath("$.developerMessage")
                        .value("br.com.accountcontrol.exception.ResourceNotFoundException"));
    }
}