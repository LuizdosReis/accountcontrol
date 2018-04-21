package br.com.accountcontrol.category.api.v1;

import br.com.accountcontrol.api.AbstractRestControllerTest;
import br.com.accountcontrol.category.Category;
import br.com.accountcontrol.category.CategoryService;
import br.com.accountcontrol.category.builder.CategoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryEndpointTest extends AbstractRestControllerTest{

    @Mock
    private CategoryService service;

    @InjectMocks
    private CategoryEndpoint endpoint;

    private MockMvc mockMvc;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(endpoint).build();
    }

    @Test
    public void saveCategoryShouldReturnStatusCode201() throws Exception {
        Category category = CategoryBuilder.buildCategoryWithoutId();
        Category categoryReturned = CategoryBuilder.buildCategoryWithId();

        when(service.save(category)).thenReturn(categoryReturned);

        mockMvc.perform(post(CategoryEndpoint.BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(category))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description", equalTo(categoryReturned.getDescription())))
                .andExpect(jsonPath("$.type", equalTo(categoryReturned.getType().name())))
                .andExpect(jsonPath("$.id",equalTo(categoryReturned.getId().intValue())));
    }

    @Test
    public void saveInvalidCategoryShouldReturnStatusCode201() throws Exception {
        Category category = CategoryBuilder.buildCategoryWithoutId();
        category.setDescription("");

        mockMvc.perform(post(CategoryEndpoint.BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(category))
                )
                .andExpect(status().isBadRequest());
    }


}