package br.com.accountcontrol.category;

import br.com.accountcontrol.category.builder.CategoryBuilder;
import br.com.accountcontrol.category.dto.CategoryCreateDTO;
import br.com.accountcontrol.category.dto.CategoryUpdateDTO;
import br.com.accountcontrol.exception.ResourceNotFoundException;
import br.com.accountcontrol.user.service.UserService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class CategoryServiceIT {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Autowired
    private CategoryRepository repository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserService userService;
    private CategoryService service;

    @Before
    public void setUp() {
        service = new CategoryServiceImpl(repository, userService, modelMapper);
    }

    @Test
    public void save() {
        CategoryCreateDTO categoryCreated = CategoryBuilder.CATEGORY_CREATE_DTO;

        Category categorySaved = service.save(categoryCreated);

        assertNotNull(categorySaved.getId());
        assertEquals(categoryCreated.getDescription(), categorySaved.getDescription());
        assertEquals(categoryCreated.getType(), categorySaved.getType());
    }


    @Test
    public void getAll() {
        CategoryCreateDTO category = CategoryBuilder.CATEGORY_CREATE_DTO;

        service.save(category);

        PageRequest pageRequest = PageRequest.of(0, 20);

        Page<Category> categoriesPage = service.findAll(pageRequest);
        assertEquals(1, categoriesPage.getTotalElements());
        assertEquals(1, categoriesPage.getTotalPages());
        assertEquals(1, categoriesPage.getContent().size());


        Category categorySaved = categoriesPage.getContent().get(0);
        assertThat(categorySaved.getType(), is(category.getType()));
        assertNotNull((categorySaved.getId()));
        assertThat(categorySaved.getDescription(), is(category.getDescription()));

    }

    @Test
    public void update() {
        CategoryCreateDTO category = CategoryBuilder.CATEGORY_CREATE_DTO;

        Category categorySaved = service.save(category);
        CategoryUpdateDTO categoryUpdate = CategoryBuilder.CATEGORY_UPDATE_DTO;
        categoryUpdate.setId(categorySaved.getId());
        categoryUpdate.setDescription("update Description");

        Category categoryUpdated = service.update(categoryUpdate);

        assertThat(categoryUpdated.getDescription(), is(categoryUpdate.getDescription()));
    }


    @Test
    public void updateWithCategoryNotExistentShouldReturnResourceNotFoundException() {
        thrown.expect(ResourceNotFoundException.class);

        CategoryUpdateDTO categoryUpdate = CategoryBuilder.CATEGORY_UPDATE_DTO;

        service.update(categoryUpdate);
    }

    @Test
    public void findById() {
        CategoryCreateDTO category = CategoryBuilder.CATEGORY_CREATE_DTO;

        Category categorySaved = service.save(category);

        Category categoryReturned = service.findById(categorySaved.getId());

        assertEquals(categorySaved.getId(), categoryReturned.getId());
        assertEquals(category.getDescription(), categoryReturned.getDescription());
        assertEquals(category.getType(), categoryReturned.getType());
    }

    @Test
    public void findByIdNotExistentShouldReturnResourceNotFoundException() {
        thrown.expect(ResourceNotFoundException.class);
        Long id = 1L;

        service.findById(id);
    }

}
