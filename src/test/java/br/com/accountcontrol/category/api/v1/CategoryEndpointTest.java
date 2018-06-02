package br.com.accountcontrol.category.api.v1;

import br.com.accountcontrol.api.AbstractRestControllerTest;
import br.com.accountcontrol.category.builder.CategoryBuilder;
import br.com.accountcontrol.category.dto.CategoryCreateDTO;
import br.com.accountcontrol.category.dto.CategoryReturnDTO;
import br.com.accountcontrol.category.dto.CategoryUpdateDTO;
import br.com.accountcontrol.category.service.CategoryService;
import br.com.accountcontrol.exception.ResourceNotFoundException;
import br.com.accountcontrol.handler.RestExceptionHandler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryEndpointTest extends AbstractRestControllerTest {

    @Mock
    private CategoryService service;

    @InjectMocks
    private CategoryEndpoint endpoint;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(endpoint)
                .setControllerAdvice(new RestExceptionHandler())
                .setCustomArgumentResolvers(
                        new PageableHandlerMethodArgumentResolver(
                                new SortHandlerMethodArgumentResolver()
                        )
                )
                .build();
    }

    @Test
    public void saveCategoryShouldReturnStatusCodeCreated() throws Exception {
        CategoryCreateDTO categoryCreate = CategoryBuilder.CATEGORY_CREATE_DTO;
        CategoryReturnDTO categoryReturned = CategoryBuilder.CATEGORY_RETURN_DTO;

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

        CategoryReturnDTO categoryReturned = CategoryBuilder.CATEGORY_RETURN_DTO;

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
        CategoryReturnDTO category = CategoryBuilder.CATEGORY_RETURN_DTO;

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

        CategoryReturnDTO category = CategoryBuilder.CATEGORY_RETURN_DTO;

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