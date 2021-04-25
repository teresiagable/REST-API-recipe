package se.lexicon.almgru.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.lexicon.almgru.restapi.data.IngredientRepository;
import se.lexicon.almgru.restapi.dto.CreateIngredientDTO;
import se.lexicon.almgru.restapi.entity.Ingredient;
import se.lexicon.almgru.restapi.exception.UniquenessViolationException;
import se.lexicon.almgru.restapi.exception.ValidationException;

import javax.validation.Valid;

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

    @PostMapping("/api/ingredients")
    public ResponseEntity<Void> createIngredient(@Valid @ModelAttribute CreateIngredientDTO dto, BindingResult bind) {
        if (bind.hasErrors()) {
            throw new ValidationException("Value for 'name' must not be empty.");
        }

        if (repository.findByIngredientNameEquals(dto.getName()).isPresent()) {
            throw new UniquenessViolationException(String.format("An ingredient with name '%s' already exists.",
                    dto.getName()));
        }

        repository.save(new Ingredient(dto.getName()));

        return ResponseEntity.ok().build();
    }
}
