package learn.recipes.domain;

import learn.recipes.data.CategoryRepository;
import learn.recipes.models.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> findAll() {
        return repository.findAll();
    }

    public Category findById(int categoryId) {
        return repository.findById(categoryId);
    }

    public Result<Category> add(Category category) {
        Result<Category> result = validate(category);
        if (!result.isSuccess()) {
            return result;
        }
        if (category.getId() != 0) {
            result.addMessage("category id cannot be set for `add` operation.");
            return result;
        }

        result.setPayload(repository.add(category));

        return result;
    }

    public Result<Category> update(Category category) {
        Result<Category> result = validate(category);
        if (!result.isSuccess()) {
            return result;
        }
        if (category.getId() <= 0) {
            result.addMessage("category id is required");
            return result;
        }
        if (!repository.update(category)) {
            String msg = String.format("could not update category id: %s", category.getId());
            result.addMessage(msg);
        }

        return result;
    }

    public Result<Void> deleteById(int categoryId) {
        Result<Void> result = new Result<>();
        if (!repository.canDeleteById(categoryId)) {
            String msg = String.format("could not delete category id: %s", categoryId);
            result.addMessage(msg);
            return result;
        }
        if (!repository.deleteById(categoryId)) {
            String msg = String.format("could not find category id: %s.", categoryId);
            result.addMessage(msg);
        }

        return result;
    }

    private Result<Category> validate(Category category) {
        Result<Category> result = new Result<>();
        if (category == null) {
            result.addMessage("category cannot be null.");
            return result;
        }
        if (category.getLabel() == null || category.getLabel().isBlank()) {
            result.addMessage("category label is required.");
            return result;
        }
        if (repository.findAll().stream()
                .anyMatch(c -> isDuplicate(category, c))) {
            result.addMessage("category label must be unique.");
        }

        return result;
    }

    private boolean isDuplicate(Category category, Category existing) {
        return category.getId() != existing.getId()
                && category.getLabel().equalsIgnoreCase(existing.getLabel());
    }
}
