package learn.recipes.data;

import learn.recipes.models.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static learn.recipes.TestHelper.makeCategory;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CategoryJdbcTemplateRepositoryTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    CategoryJdbcTemplateRepository repository;

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("call set_known_good_state();");
    }

    @Test
    void shouldFindAll() {
        List<Category> categories = repository.findAll();

        assertTrue(categories.size() >= 6);
        assertTrue(categories.stream()
                .anyMatch(c -> c.equals(makeCategory(1))));
    }

    @Test
    void shouldFindCategory1() {
        Category category = repository.findById(1);
        assertNotNull(category);
        var expected = makeCategory(1);

        assertEquals(expected, category);
    }

    @Test
    void shouldNotFindMissingCategory() {
        Category category = repository.findById(100000);
        assertNull(category);
    }

    @Test
    void shouldAddCategory8() {
        Category category = makeCategory(8);
        category.setId(0);

        Category actual = repository.add(category);

        assertEquals(8, actual.getId());
    }

    @Test
    void shouldUpdateCategory2() {
        Category category = makeCategory(2);

        category.setLabel("Updated Label");

        assertTrue(repository.update(category));

        assertEquals(category, repository.findById(2));
    }

    @Test
    void shouldNotUpdateMissingCategory() {
        Category category = makeCategory(100000);
        category.setLabel("Updated Label");

        assertFalse(repository.update(category));
    }

    @Test
    void shouldDeleteCategory5() {
        assertTrue(repository.deleteById(5));
        assertFalse(repository.deleteById(5));
    }
}
