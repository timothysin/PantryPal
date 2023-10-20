package learn.recipes.domain;

import learn.recipes.data.CategoryRepository;
import learn.recipes.models.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp() {
        List<Category> categories = List.of(
                new Category(1, "Category1"),
                new Category(2, "Category2")
        );
        when(categoryRepository.findAll()).thenReturn(categories);

        Category category = new Category(1, "Category1");
        when(categoryRepository.findById(1)).thenReturn(category);

        Category newCategory = new Category(3, "NewCategory");
        when(categoryRepository.add(newCategory)).thenReturn(new Category(3, "NewCategory"));

        Category existingCategory = new Category(1, "Category1");
        when(categoryRepository.update(existingCategory)).thenReturn(true);

        when(categoryRepository.canDeleteById(1)).thenReturn(true);
        when(categoryRepository.deleteById(1)).thenReturn(true);

        List<Category> categoriesWithDuplicateLabel = List.of(
                new Category(1, "Category1"),
                new Category(2, "Category2")
        );
        when(categoryRepository.findAll()).thenReturn(categoriesWithDuplicateLabel);
    }

    @Test
    public void testFindAllCategories() {
        List<Category> result = categoryService.findAll();

        assertEquals(2, result.size());
        assertEquals("Category1", result.get(0).getLabel());
        assertEquals("Category2", result.get(1).getLabel());
    }

    @Test
    public void testFindCategoryById() {
        Category result = categoryService.findById(1);

        assertEquals(1, result.getId());
        assertEquals("Category1", result.getLabel());
    }

    @Test
    public void testAddCategory() {
        Category newCategory = new Category(0, "NewCategory");
        Result<Category> result = categoryService.add(newCategory);

        assertTrue(result.isSuccess());
    }

    @Test
    public void testUpdateCategory() {
        Category existingCategory = new Category(1, "Category1");
        Result<Category> result = categoryService.update(existingCategory);

        assertTrue(result.isSuccess());
    }

    @Test
    public void testDeleteCategoryById() {
        Result<Void> result = categoryService.deleteById(1);
        assertTrue(result.isSuccess());
    }

    @Test
    public void testAddCategoryWithDuplicateLabel() {
        Category newCategory = new Category(0, "Category1");
        Result<Category> result = categoryService.add(newCategory);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("category label must be unique."));
    }
}
