package learn.recipes.data;

import learn.recipes.data.mappers.CategoryMapper;
import learn.recipes.data.mappers.RecipeMapper;
import learn.recipes.models.Recipe;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
@Repository
public class RecipeJdbcTemplateRepository implements RecipeRepository{
    private static final String SELECT = """
            select recipe_id, `name`, ingredients, instructions, stat,
            app_user_id, category_id
            from recipe
            """;
    private final JdbcTemplate jdbcTemplate;

    public RecipeJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Recipe> findAll() {
        String sql = SELECT + ";";
        return jdbcTemplate.query(sql, new RecipeMapper());
    }

    @Override
    public List<Recipe> findUsersRecipes(int userId) {
        String sql = SELECT + "where app_user_id = ?;";
        return jdbcTemplate.query(sql, new RecipeMapper(), userId);
    }

    @Override
    @Transactional
    public Recipe findById(int id) {
        String sql = SELECT + "where recipe_id = ?;";
        Recipe recipe = jdbcTemplate.query(sql, new RecipeMapper(), id).stream()
                .findFirst().orElse(null);

        return recipe;
    }

    @Override
    public List<Recipe> findByName(String name) {
        String sql = SELECT + "where `name` LIKE ?;";

        return jdbcTemplate.query(sql, new RecipeMapper(), name + "%");
    }

    @Override
    @Transactional
    public Recipe add(Recipe recipe) {

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("recipe")
                .usingGeneratedKeyColumns("recipe_id");

        HashMap<String, Object> args = new HashMap<>();
        args.put("name", recipe.getName());
        args.put("ingredients", recipe.getIngredients());
        args.put("instructions", recipe.getInstructions());
        args.put("stat", recipe.getStat());
        args.put("app_user_id", recipe.getUser_id());
        args.put("category_id", recipe.getCategory_id());

        int id = insert.executeAndReturnKey(args).intValue();
        recipe.setId(id);

        return recipe;
    }

    @Override
    @Transactional
    public boolean update(Recipe recipe) {
        String sql = """
                update recipe set
                    `name` = ?,
                    ingredients = ?,
                    instructions = ?,
                    stat = ?,
                    app_user_id = ?,
                    category_id = ?
                where recipe_id = ?;
                """;

        boolean updated = jdbcTemplate.update(sql,
                recipe.getName(),
                recipe.getIngredients(),
                recipe.getInstructions(),
                recipe.getStat(),
                recipe.getUser_id(),
                recipe.getCategory_id(),
                recipe.getId()) > 0;


        return updated;
    }

    @Override
    public boolean deleteById(int id) {
        return jdbcTemplate.update("delete from recipe where recipe_id = ?;", id) > 0;
    }


}
