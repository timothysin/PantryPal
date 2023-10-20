package learn.recipes.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Recipe {
    private int id;
    // should not be null or blank
    private String name;
    // should be greater than equal to 1
    private String ingredients;

    private String instructions;

    private String stat;

    private int user_id;

    private int category_id;

    public Recipe(){

    }



    public Recipe(int id, String name, String ingredients, String instructions, String stat, int user_id, int category_id) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.stat = stat;
        this.user_id = user_id;
        this.category_id = category_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    public String getStat() { return stat; }

    public void setStat(String stat) { this.stat = stat; }
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recipe recipe = (Recipe) o;

        if (id != recipe.id) return false;
        if (user_id != recipe.user_id) return false;
        if (category_id != recipe.category_id) return false;
        if (!Objects.equals(name, recipe.name)) return false;
        if (!Objects.equals(ingredients, recipe.ingredients)) return false;
        return Objects.equals(instructions, recipe.instructions);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (ingredients != null ? ingredients.hashCode() : 0);
        result = 31 * result + (instructions != null ? instructions.hashCode() : 0);
        result = 31 * result + user_id;
        result = 31 * result + category_id;
        return result;
    }
}
