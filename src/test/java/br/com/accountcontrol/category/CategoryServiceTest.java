package br.com.accountcontrol.category;

import br.com.accountcontrol.category.builder.CategoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {

    @Mock
    private CategoryRepository repository;

    private CategoryService service;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        service = new CategoryServiceImpl(repository);
    }

    @Test
    public void save(){
        Category categorySaved = CategoryBuilder.buildCategoryWithoutId();
        Category categoryReturned = CategoryBuilder.buildCategoryWithId();

        when(repository.save(categorySaved)).thenReturn(categoryReturned);

        Category category = service.save(categorySaved);

        assertEquals(categoryReturned.getId(),category.getId());
        assertEquals(categoryReturned.getDescription(),category.getDescription());
        assertEquals(categoryReturned.getType(),category.getType());
    }

    @Test
    public void getAll(){
        Category categoryReturned = CategoryBuilder.buildCategoryWithId();

        PageRequest pageRequest = PageRequest.of(0, 20);

        when(repository.findAll(pageRequest))
                .thenReturn(new PageImpl<>(Collections.singletonList(categoryReturned)));

        Page<Category> categoriesPage = service.findAll(pageRequest);
        assertEquals(1,categoriesPage.getTotalElements());
        assertEquals(1,categoriesPage.getTotalPages());
        assertEquals(1,categoriesPage.getContent().size());


        Category category = categoriesPage.getContent().get(0);
        assertThat(categoryReturned.getType(),is(category.getType()));
        assertThat(categoryReturned.getId(),is(category.getId()));
        assertThat(categoryReturned.getDescription(),is(category.getDescription()));

    }
}