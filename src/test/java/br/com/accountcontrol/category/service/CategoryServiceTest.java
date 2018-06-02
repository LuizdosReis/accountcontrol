package br.com.accountcontrol.category.service;

import br.com.accountcontrol.category.builder.CategoryBuilder;
import br.com.accountcontrol.category.dto.CategoryCreateDTO;
import br.com.accountcontrol.category.dto.CategoryReturnDTO;
import br.com.accountcontrol.category.dto.CategoryUpdateDTO;
import br.com.accountcontrol.category.mapper.CategoryMapper;
import br.com.accountcontrol.category.model.Category;
import br.com.accountcontrol.category.repository.CategoryRepository;
import br.com.accountcontrol.exception.ResourceNotFoundException;
import br.com.accountcontrol.user.builder.UserBuilder;
import br.com.accountcontrol.user.model.User;
import br.com.accountcontrol.user.service.UserService;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private CategoryRepository repository;

    @Mock
    private UserService userService;

    private CategoryService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        service = new CategoryServiceImpl(repository, userService, CategoryMapper.INSTANCE);
    }

    @Test
    public void save() {
        Category categoryReturned = CategoryBuilder.CATEGORY;
        CategoryCreateDTO categoryCreate = CategoryBuilder.CATEGORY_CREATE_DTO;

        when(repository.save(Mockito.any(Category.class))).thenReturn(categoryReturned);

        CategoryReturnDTO category = service.save(categoryCreate);

        assertEquals(categoryReturned.getId(), category.getId());
        assertEquals(categoryReturned.getDescription(), category.getDescription());
        assertEquals(categoryReturned.getType(), category.getType());
    }

    @Test
    public void getAll() {
        User user = UserBuilder.USER;

        PageRequest pageRequest = PageRequest.of(0, 20);

        when(userService.getCurrentUser()).thenReturn(user);
        when(repository.findAllByUser(user, pageRequest))
                .thenReturn(new PageImpl<>(Lists.newArrayList(new Category(), new Category(), new Category())));

        Page<CategoryReturnDTO> categoriesPage = service.findAll(pageRequest);
        assertEquals(3, categoriesPage.getTotalElements());
        assertEquals(1, categoriesPage.getTotalPages());
        assertEquals(3, categoriesPage.getContent().size());
    }

    @Test
    public void update() {
        CategoryUpdateDTO categoryUpdate = CategoryBuilder.CATEGORY_UPDATE_DTO;
        Category category = CategoryBuilder.CATEGORY;
        User user = UserBuilder.USER;

        when(userService.getCurrentUser()).thenReturn(user);
        when(repository.findByIdAndUser(categoryUpdate.getId(), user)).thenReturn(Optional.of(category));
        when(repository.save(category)).thenReturn(category);

        CategoryReturnDTO categoryUpdated = service.update(categoryUpdate);

        assertEquals(categoryUpdate.getId(), categoryUpdated.getId());
        assertEquals(categoryUpdate.getDescription(), categoryUpdated.getDescription());
        verify(userService, times(1)).getCurrentUser();
        verify(repository, times(1)).findByIdAndUser(categoryUpdate.getId(), user);
        verify(repository, times(1)).save(category);
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

    @Test
    public void findById() {
        Category category = CategoryBuilder.CATEGORY;
        User user = UserBuilder.USER;

        when(userService.getCurrentUser()).thenReturn(user);
        when(repository.findByIdAndUser(category.getId(), user)).thenReturn(Optional.of(category));

        CategoryReturnDTO categoryReturned = service.findById(category.getId());
        assertEquals(category.getId(), categoryReturned.getId());
        assertEquals(category.getDescription(), categoryReturned.getDescription());
        assertEquals(category.getType(), categoryReturned.getType());
    }

    @Test
    public void findByIdNotExistentShouldReturnResourceNotFoundException() {
        thrown.expect(ResourceNotFoundException.class);
        Long id = 1L;

        when(repository.findById(id))
                .thenThrow(new ResourceNotFoundException(CategoryServiceImpl.CATEGORY_NOT_FOUND));

        service.findById(id);
    }
}