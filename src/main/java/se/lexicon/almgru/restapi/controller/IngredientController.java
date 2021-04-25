package se.lexicon.almgru.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.lexicon.almgru.restapi.data.IngredientRepository;
import se.lexicon.almgru.restapi.entity.Ingredient;

@RestController
public class IngredientController {

    private final IngredientRepository repository;

    @Autowired
    public IngredientController(IngredientRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/ingredients")
    public ResponseEntity<Iterable<Ingredient>> getIngredients(
            @RequestParam(name = "query", required = false) String query
    ) {
        return ResponseEntity.ok(
                query != null ?
                repository.findByIngredientNameContainingIgnoreCase(query) :
                repository.findAll()
        );
    }

    @GetMapping("/api/ingredients/{id}")
    public ResponseEntity<Ingredient> getIngredients(@PathVariable("id") Integer id) {
        return repository
                .findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
