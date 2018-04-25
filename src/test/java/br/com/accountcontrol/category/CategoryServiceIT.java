package br.com.accountcontrol.category;

import br.com.accountcontrol.category.builder.CategoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
}
