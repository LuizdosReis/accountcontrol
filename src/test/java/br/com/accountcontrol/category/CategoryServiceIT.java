package br.com.accountcontrol.category;

import br.com.accountcontrol.category.builder.CategoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

    private CategoryService service;

    @Before
    public void setUp(){
        service = new CategoryServiceImpl(repository);
    }

    @Test
    public void saveCategory(){
        Category categorySaved = CategoryBuilder.buildCategoryWithoutId();

        Category category = service.save(categorySaved);

        assertNotNull(categorySaved.getId());
        assertEquals(categorySaved.getDescription(),category.getDescription());
        assertEquals(categorySaved.getType(),category.getType());
    }


    @Test
    public void getAll(){
        Category categorySaved = CategoryBuilder.buildCategoryWithoutId();

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
