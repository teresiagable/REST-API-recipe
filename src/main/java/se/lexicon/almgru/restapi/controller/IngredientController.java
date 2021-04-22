package se.lexicon.almgru.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import se.lexicon.almgru.restapi.data.IngredientRepository;
import se.lexicon.almgru.restapi.entity.Ingredient;

import java.util.Set;

@RestController
public class IngredientController {

    private final IngredientRepository repository;

    @Autowired
    public IngredientController(IngredientRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/ingredients")
    public ResponseEntity<Iterable<Ingredient>> getIngredients() {
        return ResponseEntity.ok(repository.findAll());
    }

    /**
     * @param nameQuery --
     * @return No results returns 200 OK with an empty list.
     */
    @GetMapping("/api/ingredients/{nameQuery}")
    public ResponseEntity<Set<Ingredient>> getIngredientsByNameContains(@PathVariable("nameQuery") String nameQuery) {
        return ResponseEntity.ok(repository.findByIngredientNameContainingIgnoreCase(nameQuery));
    }
}
