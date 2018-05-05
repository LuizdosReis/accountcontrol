package br.com.accountcontrol.category;

import br.com.accountcontrol.category.builder.CategoryBuilder;
import br.com.accountcontrol.category.dto.CategoryCreateDTO;
import br.com.accountcontrol.category.dto.CategoryUpdateDTO;
import br.com.accountcontrol.exception.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private CategoryRepository repository;

    private CategoryService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        service = new CategoryServiceImpl(repository, new ModelMapper());
    }

    @Test
    public void save() {
        Category categoryReturned = CategoryBuilder.CATEGORY;
        Category categorySaved = CategoryBuilder.CATEGORY_WITHOUT_ID;
        CategoryCreateDTO categoryCreate = CategoryBuilder.CATEGORY_CREATE_DTO;

        when(repository.save(categorySaved)).thenReturn(categoryReturned);

        Category category = service.save(categoryCreate);

        assertEquals(categoryReturned.getId(), category.getId());
        assertEquals(categoryReturned.getDescription(), category.getDescription());
        assertEquals(categoryReturned.getType(), category.getType());
    }

    @Test
    public void getAll() {
        Category categoryReturned = CategoryBuilder.CATEGORY;

        PageRequest pageRequest = PageRequest.of(0, 20);

        when(repository.findAll(pageRequest))
                .thenReturn(new PageImpl<>(Collections.singletonList(categoryReturned)));

        Page<Category> categoriesPage = service.findAll(pageRequest);
        assertEquals(1, categoriesPage.getTotalElements());
        assertEquals(1, categoriesPage.getTotalPages());
        assertEquals(1, categoriesPage.getContent().size());


        Category category = categoriesPage.getContent().get(0);
        assertThat(categoryReturned.getType(), is(category.getType()));
        assertThat(categoryReturned.getId(), is(category.getId()));
        assertThat(categoryReturned.getDescription(), is(category.getDescription()));

    }

    @Test
    public void update() {
        CategoryUpdateDTO categoryUpdate = CategoryBuilder.CATEGORY_UPDATE_DTO;
        Category category = CategoryBuilder.CATEGORY;

        when(repository.existsById(categoryUpdate.getId())).thenReturn(true);
        when(repository.save(category)).thenReturn(category);

        Category categoryUpdated = service.update(categoryUpdate);

        assertEquals(categoryUpdate.getId(), categoryUpdated.getId());
        assertEquals(categoryUpdate.getDescription(), categoryUpdated.getDescription());
        verify(repository, times(1)).existsById(categoryUpdate.getId());
    }

    @Test
    public void updateWithCategoryNotExistentShouldReturnResourceNotFoundException() {
        thrown.expect(ResourceNotFoundException.class);

        CategoryUpdateDTO categoryUpdate = CategoryBuilder.CATEGORY_UPDATE_DTO;

        when(repository.existsById(categoryUpdate.getId()))
                .thenThrow(new ResourceNotFoundException(CategoryServiceImpl.CATEGORY_NOT_FOUND));

        service.update(categoryUpdate);
        verify(repository, times(1)).existsById(categoryUpdate.getId());
    }
}