package learn.recipes.data.mappers;

import com.mysql.cj.protocol.Resultset;
import learn.recipes.models.Recipe;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RecipeMapper implements RowMapper<Recipe> {

    @Override
    public Recipe mapRow(ResultSet rs, int rowNum) throws SQLException {
        Recipe recipe = new Recipe();
        recipe.setId(rs.getInt("recipe_id"));
        recipe.setName(rs.getString("name"));
        recipe.setIngredients(rs.getString("ingredients"));
        recipe.setInstructions(rs.getString("instructions"));
        recipe.setStat(rs.getString("stat"));
        recipe.setUser_id(rs.getInt("app_user_id"));
        recipe.setCategory_id(rs.getInt("category_id"));
        return recipe;
    }
}
