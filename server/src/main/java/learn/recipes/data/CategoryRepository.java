package learn.recipes.data;

import learn.recipes.models.Category;

import java.util.List;

public interface CategoryRepository {
    List<Category> findAll();

    Category findById(int categoryId);

    Category add(Category category);

    boolean update(Category category);

    boolean canDeleteById(int categoryId);

    boolean deleteById(int categoryId);
}
