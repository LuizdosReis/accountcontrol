package br.com.accountcontrol.category;

import br.com.accountcontrol.category.builder.CategoryBuilder;
import br.com.accountcontrol.category.dto.CategoryCreateDTO;
import br.com.accountcontrol.category.dto.CategoryUpdateDTO;
import org.junit.Before;
import org.junit.Test;
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

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    private CategoryService service;

    @Before
    public void setUp() {
        service = new CategoryServiceImpl(repository, modelMapper);
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

}
