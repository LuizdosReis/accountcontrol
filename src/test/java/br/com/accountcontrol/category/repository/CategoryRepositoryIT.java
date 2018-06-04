package br.com.accountcontrol.category.repository;

import br.com.accountcontrol.category.builder.CategoryBuilder;
import br.com.accountcontrol.category.model.Category;
import br.com.accountcontrol.user.builder.UserBuilder;
import br.com.accountcontrol.user.model.User;
import br.com.accountcontrol.user.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepositoryIT {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private UserRepository userRepository;

    private Category categorySaved;
    private User userSaved;

    @Before
    public void setUp() {
        userSaved = userRepository.save(UserBuilder.USER);
        Category category = CategoryBuilder.CATEGORY;
        category.setUser(userSaved);
        categorySaved = repository.save(category);

    }

    @Test
    public void shouldReturnCategoryByIdAndUser() {
        Optional<Category> categoryOptional = repository.findByIdAndUser(categorySaved.getId(), userSaved);

        assertTrue(categoryOptional.isPresent());
    }

    @Test
    public void ShouldReturnAllCategoriesByUser() {
        Page<Category> categories = repository.findAllByUser(userSaved, PageRequest.of(0, 10));

        assertEquals(1L, categories.getTotalElements());
        assertEquals(1, categories.getTotalPages());
    }
}
