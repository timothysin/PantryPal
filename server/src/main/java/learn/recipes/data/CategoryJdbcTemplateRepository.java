package learn.recipes.data;

import learn.recipes.data.CategoryRepository;
import learn.recipes.data.mappers.CategoryMapper;
import learn.recipes.models.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class CategoryJdbcTemplateRepository implements CategoryRepository {
    private static final String SELECT = """
            select category_id, `name`
            from category
            """;
    private final JdbcTemplate jdbcTemplate;

    public CategoryJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Category> findAll() {
        return jdbcTemplate.query(SELECT, new CategoryMapper());
    }

    @Override
    public Category findById(int categoryId) {
        return jdbcTemplate.query(SELECT + "where category_id = ?;",
                        new CategoryMapper(), categoryId).stream()
                .findFirst().orElse(null);
    }

    @Override
    public Category findByName(String name) {
        return jdbcTemplate.query(SELECT + "where `name` = ?;",
                        new CategoryMapper(), name).stream()
                .findFirst().orElse(null);
    }

    @Override
    public Category add(Category category) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("category")
                .usingColumns("`name`")
                .usingGeneratedKeyColumns("category_id");

        HashMap<String, Object> args = new HashMap<>();
        args.put("`name`", category.getLabel());

        int id = insert.executeAndReturnKey(args).intValue();
        category.setId(id);

        return category;
    }

    @Override
    public boolean update(Category category) {
        return jdbcTemplate.update("""
                update category set
                    `name` = ?
                where category_id = ?;
                """,
                category.getLabel(),
                category.getId()) > 0;
    }

    @Override
    public boolean canDeleteById(int categoryId) {
        return jdbcTemplate.queryForObject("""
                select count(*) from board_game_category
                where category_id = ?;
                """,
                Integer.class,
                categoryId) == 0;
    }

    @Override
    public boolean deleteById(int categoryId) {
        return jdbcTemplate.update("""
                delete from category
                where category_id = ?;
                """,
                categoryId) > 0;
    }
}
