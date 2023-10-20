package learn.recipes.domain;

import learn.recipes.data.RecipeRepository;
import learn.recipes.models.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class RecipeServiceTest {
    @Autowired
    private RecipeService recipeService;

    @MockBean
    private RecipeRepository recipeRepository;

    @BeforeEach
    public void setUp() {
        List<Recipe> recipes = List.of(
                new Recipe(1, "Recipe1", "Ingredients1", "Instructions1", "saved", 1, 1),
                new Recipe(2, "Recipe2", "Ingredients2", "Instructions2", "posted", 2, 2)
        );
        when(recipeRepository.findAll()).thenReturn(recipes);

        List<Recipe> recipesByName = List.of(
                new Recipe(1, "Recipe1", "Ingredients1", "Instructions1", "saved", 1, 1),
                new Recipe(3, "Recipe1", "Ingredients3", "Instructions3", "saved", 3, 1)
        );
        when(recipeRepository.findByName("Recipe1")).thenReturn(recipesByName);

        Recipe recipe = new Recipe(1, "Recipe1", "Ingredients1", "Instructions1", "saved", 1, 1);
        when(recipeRepository.findById(1)).thenReturn(recipe);

        Recipe newRecipe = new Recipe(0, "NewRecipe", "NewIngredients", "NewInstructions", "saved", 2, 3);
        when(recipeRepository.add(newRecipe)).thenReturn(new Recipe(3, "NewRecipe", "NewIngredients", "NewInstructions", "saved", 2, 3));

        Recipe existingRecipe = new Recipe(1, "Recipe1", "Ingredients1", "Instructions1", "saved", 1, 1);
        when(recipeRepository.update(existingRecipe)).thenReturn(true);

        when(recipeRepository.deleteById(1)).thenReturn(true);
    }

    @Test
    public void testFindAllRecipes() {
        List<Recipe> result = recipeService.findAll();

        assertEquals(2, result.size());
        assertEquals("Recipe1", result.get(0).getName());
        assertEquals("Recipe2", result.get(1).getName());
    }

    @Test
    public void testFindRecipesByName() {
        List<Recipe> result = recipeService.findByName("Recipe1");

        assertEquals(2, result.size());
        assertEquals("Recipe1", result.get(0).getName());
        assertEquals("Recipe1", result.get(1).getName());
    }

    @Test
    public void testFindRecipeById() {
        Recipe result = recipeService.findById(1);

        assertEquals(1, result.getId());
        assertEquals("Recipe1", result.getName());
    }

    @Test
    public void testAddRecipe() {
        Recipe newRecipe = new Recipe(0, "NewRecipe", "NewIngredients", "NewInstructions", "saved", 2, 3);
        Result<Recipe> result = recipeService.add(newRecipe);
        System.out.println(result.getMessages());

        assertTrue(result.isSuccess());
        assertEquals(3, result.getPayload().getId());
        assertEquals("NewRecipe", result.getPayload().getName());
    }

    @Test
    public void testUpdateRecipe() {
        Recipe existingRecipe = new Recipe(1, "Recipe1", "Ingredients1", "Instructions1", "saved", 1, 1);
        Result<Recipe> result = recipeService.update(existingRecipe);
        System.out.println(result.getMessages());

        assertTrue(result.isSuccess());
    }

    @Test
    public void testDeleteRecipeById() {
        Result<Void> result = recipeService.deleteById(1);
        assertTrue(result.isSuccess());
    }
}
