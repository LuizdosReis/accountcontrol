package br.com.accountcontrol.category;

import br.com.accountcontrol.category.builder.CategoryBuilder;
import br.com.accountcontrol.category.dto.CategoryCreateDTO;
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
    public void setUp(){
        service = new CategoryServiceImpl(repository,modelMapper);
    }

    @Test
    public void saveCategory(){
        CategoryCreateDTO categorySaved = CategoryBuilder.CATEGORY_CREATE_DTO;

        Category category = service.save(categorySaved);

        assertNotNull(category.getId());
        assertEquals(categorySaved.getDescription(),category.getDescription());
        assertEquals(categorySaved.getType(),category.getType());
    }


    @Test
    public void getAll(){
        CategoryCreateDTO categorySaved = CategoryBuilder.CATEGORY_CREATE_DTO;

        service.save(categorySaved);

        PageRequest pageRequest = PageRequest.of(0, 20);

        Page<Category> categoriesPage = service.findAll(pageRequest);
        assertEquals(1,categoriesPage.getTotalElements());
        assertEquals(1,categoriesPage.getTotalPages());
        assertEquals(1,categoriesPage.getContent().size());


        Category category = categoriesPage.getContent().get(0);
        assertThat(categorySaved.getType(),is(category.getType()));
        assertNotNull((category.getId()));
        assertThat(categorySaved.getDescription(),is(category.getDescription()));

    }
}
