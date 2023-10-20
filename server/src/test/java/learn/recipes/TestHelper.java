package learn.recipes;

import learn.recipes.domain.Result;
import learn.recipes.models.AppUser;
import learn.recipes.models.Recipe;
import learn.recipes.models.Category;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class TestHelper {

    public static final int VALID_ID = 5;

    public static Recipe makeRecipe(int id) {
        Recipe recipe = new Recipe();
        recipe.setId(id);
        recipe.setName(String.format("Recipe %s", id));
        recipe.setIngredients("Ingredient 1, Ingredient 2");
        recipe.setInstructions(String.format("Instructions for Recipe %s", id));
        recipe.setStat("status");
        recipe.setUser_id(1);
        recipe.setCategory_id(1);
        return recipe;
    }

    public static Category makeCategory(int id) {
        Category category = new Category();
        category.setId(id);
        category.setLabel(String.format("Category #%s", id));
        return category;
    }

    public static AppUser makeAppUser(int id) {
        return new AppUser(
                id,
                String.format("appuser%s@app.com", id),
                String.format("password_hash_%s", id),
                true,
                List.of(String.format("TEST_ROLE_%s", id))
        );
    }

    public static <T> Result<T> makeResult(String message, T payload) {
        Result<T> result = new Result<>();
        if (message != null) {
            result.addMessage(message);
        }
        result.setPayload(payload);
        return result;
    }
}
