package learn.recipes.domain;

import learn.recipes.data.CategoryRepository;
import learn.recipes.data.RecipeRepository;
import learn.recipes.models.Category;
import learn.recipes.models.Recipe;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository repository;

    public RecipeService(RecipeRepository repository) {
        this.repository = repository;
    }

    public List<Recipe> findAll() {
        return repository.findAll();
    }

    public List<Recipe> findByName(String name) {
        return repository.findByName(name);
    }

    public Recipe findById(int recipeId) {
        return repository.findById(recipeId);
    }

    public Result<Recipe> add(Recipe recipe) {

        Result<Recipe> result = validate(recipe);
        if (!result.isSuccess()) {
            return result;
        }

        if (recipe.getId() != 0) {
            result.addMessage("recipe id cannot be set for `add` operation.");
            return result;
        }

        if (result.isSuccess()) {
            Recipe rp = repository.add(recipe);
            result.setPayload(rp);
        }

        return result;
    }

    public Result<Recipe> update(Recipe recipe) {

        Result<Recipe> result = validate(recipe);
        if (!result.isSuccess()) {
            return result;
        }

        if (recipe.getId() <= 0) {
            result.addMessage("recipe id is required");
            return result;
        }

        boolean success = repository.update(recipe);
        if (!success) {
            result.addMessage("could not update recipe id " + recipe.getId());
        }

        return result;
    }

    public Result<Void> deleteById(int recipeId) {
        Result<Void> result = new Result<>();
        boolean success = repository.deleteById(recipeId);
        if (!success) {
            result.addMessage("could not delete recipe id " + recipeId);
        }
        return result;
    }

    private Result<Recipe> validate(Recipe recipe) {
        Result<Recipe> result = new Result<>();
        if (recipe == null) {
            result.addMessage("recipe cannot be null");
            return result;
        }

        if (recipe.getName() == null || recipe.getName().isBlank()) {
            result.addMessage("recipe name cannot be blank");
        }

        if (recipe.getIngredients() == null || recipe.getIngredients().isBlank()) {
            result.addMessage("recipe ingredients cannot be blank");
        }

        if (recipe.getInstructions() == null || recipe.getInstructions().isBlank()) {
            result.addMessage("recipe instructions cannot be blank");
        }

        if (recipe.getStat() != "saved" && recipe.getStat() != "posted") {
            result.addMessage("Status must be saved or posted");
        }

        if (recipe.getCategory_id() < 0) {
            result.addMessage("category must exist");
        }

        if (recipe.getUser_id() < 0) {
            result.addMessage("user must exist");
        }


        if (!result.isSuccess()) {
            return result;
        }

        // duplicate game
        List<Recipe> matchingNames = repository.findByName(recipe.getName());
        for (Recipe rp : matchingNames) {
            if (rp.getName().equalsIgnoreCase(recipe.getName())
                    && rp.getInstructions().equals(recipe.getInstructions())
                    && rp.getId() != recipe.getId()) {
                result.addMessage("duplicate recipe");
            }
        }

        return result;
    }


}
