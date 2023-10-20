package learn.recipes.data;

import learn.recipes.models.Recipe;

import java.util.List;

public interface RecipeRepository {
    List<Recipe> findAll();

    Recipe findById(int id);

    List<Recipe> findByName(String name);

    Recipe add(Recipe recipe);

    boolean update(Recipe recipe);

    boolean deleteById (int id);
}
