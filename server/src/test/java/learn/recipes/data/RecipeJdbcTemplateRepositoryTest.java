package learn.recipes.data;

import learn.recipes.models.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static learn.recipes.TestHelper.makeRecipe;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class RecipeJdbcTemplateRepositoryTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    RecipeJdbcTemplateRepository repository;

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("call set_known_good_state();");
    }

    @Test
    void shouldFindAll() {
        List<Recipe> expected = List.of(makeRecipe(1),
                makeRecipe(2), makeRecipe(3));
        List<Recipe> actual = repository.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindUser1Recipes() {
        List<Recipe> expected = List.of(makeRecipe(1), makeRecipe(2), makeRecipe(3));
        List<Recipe> actual = repository.findUsersRecipes(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindById_2() {
        Recipe expected = makeRecipe(2);
        Recipe actual = repository.findById(2);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindById_12() {
        assertNull(repository.findById(12));
    }


    @Test
    void shouldAdd() {
        Recipe arg = makeRecipe(4);
        arg.setId(0);
        Recipe expected = makeRecipe(4);
        Recipe actual = repository.add(arg);
        assertEquals(expected, actual);

        actual = repository.findById(4);
        assertEquals(expected, actual);
    }

    @Test
    void shouldUpdate() {
        Recipe recipe = makeRecipe(1);
        recipe.setCategory_id(2);
        assertTrue(repository.update(recipe));
        recipe.setId(13);
        assertFalse(repository.update(recipe));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteById(2));
        assertFalse(repository.deleteById(2));
    }
}
