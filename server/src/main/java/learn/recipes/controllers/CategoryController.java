package learn.recipes.controllers;

import learn.recipes.domain.CategoryService;
import learn.recipes.domain.Result;
import learn.recipes.models.Recipe;
import learn.recipes.models.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public List<Category> findAll() {
        return service.findAll();
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Category> findById(@PathVariable int id) {
//        Category category = service.findById(id);
//        if (category == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(category, HttpStatus.OK);
//    }

    @GetMapping("/{name}")
    public ResponseEntity<Category> findByName(@PathVariable String name) {
        Category category = service.findByName(name);
        if (category == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Result<Category>> add(@RequestBody Category category) {
        Result<Category> result = service.add(category);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result<Category>> update(@PathVariable int id, @RequestBody Category category) {
        if (category != null && id != category.getId()) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }

        Result<Category> result = service.update(category);
        if (result.isSuccess()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT); // SUCCESS!
        }
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result<Void>> deleteById(@PathVariable int id) {
        Result<Void> result = service.deleteById(id);
        if (result.isSuccess()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        if (result.getMessages().get(0).contains("could not find")) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
}
