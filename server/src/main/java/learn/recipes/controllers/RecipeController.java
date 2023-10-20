package learn.recipes.controllers;

import learn.recipes.domain.Result;
import learn.recipes.domain.RecipeService;
import learn.recipes.models.AppUser;
import learn.recipes.models.Recipe;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
    private final RecipeService service;

    public RecipeController(RecipeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Recipe> findAll(@RequestParam(required = false) String title) {
        if (title == null || title.isBlank()) {
            return service.findAll();
        }
        return service.findByName(title);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> findById(@PathVariable int id) {
        Recipe rp = service.findById(id);
        if (rp == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(rp, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Result<Recipe>> add(@AuthenticationPrincipal AppUser appUser,
                                                 @RequestBody Recipe recipe) {
        recipe.setUser_id(appUser.getId());
        Result<Recipe> result = service.add(recipe);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result<Recipe>> update(@PathVariable int id, @RequestBody Recipe recipe) {
        if (recipe != null && id != recipe.getId()) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }

        Result<Recipe> result = service.update(recipe);
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
        if (result.getMessages().get(0).contains("could not delete")) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
}
