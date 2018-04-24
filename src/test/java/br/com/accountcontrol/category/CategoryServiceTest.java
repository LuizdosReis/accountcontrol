package br.com.accountcontrol.category;

import br.com.accountcontrol.category.builder.CategoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
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
    public void saveCategory(){
        Category categorySaved = CategoryBuilder.buildCategoryWithoutId();
        Category categoryReturned = CategoryBuilder.buildCategoryWithId();

        when(repository.save(categorySaved)).thenReturn(categoryReturned);

        Category category = service.save(categorySaved);

        assertEquals(categoryReturned.getId(),category.getId());
        assertEquals(categoryReturned.getDescription(),category.getDescription());
        assertEquals(categoryReturned.getType(),category.getType());
    }
}