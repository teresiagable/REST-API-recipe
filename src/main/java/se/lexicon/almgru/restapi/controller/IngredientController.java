package se.lexicon.almgru.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.lexicon.almgru.restapi.data.IngredientRepository;
import se.lexicon.almgru.restapi.dto.CreateIngredientDTO;
import se.lexicon.almgru.restapi.dto.IngredientDTO;
import se.lexicon.almgru.restapi.entity.Ingredient;
import se.lexicon.almgru.restapi.exception.UniquenessViolationException;
import se.lexicon.almgru.restapi.exception.ValidationException;
import se.lexicon.almgru.restapi.service.IngredientConverter;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class IngredientController {

    private final IngredientRepository repository;
    private final IngredientConverter converter;

    @Autowired
    public IngredientController(IngredientRepository repository, IngredientConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @GetMapping("/api/ingredients")
    public ResponseEntity<Collection<IngredientDTO>> getIngredients(
            @RequestParam(name = "query", required = false) String query
    ) {
        Iterable<Ingredient> ingredients = query != null ?
                        repository.findByIngredientNameContainingIgnoreCase(query) :
                        repository.findAll();

        return ResponseEntity.ok(StreamSupport.stream(ingredients.spliterator(), false)
                .map(converter::ingredientToDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/api/ingredients/{id}")
    public ResponseEntity<IngredientDTO> getIngredients(@PathVariable("id") Integer id) {
        return repository
                .findById(id)
                .map(converter::ingredientToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/api/ingredients")
    public ResponseEntity<Void> createIngredient(@Valid @RequestBody CreateIngredientDTO dto, BindingResult bind) {
        if (bind.hasErrors()) {
            throw new ValidationException("Value for 'name' must not be empty.");
        }

        if (repository.findByIngredientNameEqualsIgnoreCase(dto.getName()).isPresent()) {
            throw new UniquenessViolationException(String.format("An ingredient with name '%s' already exists.",
                    dto.getName()));
        }

        repository.save(new Ingredient(dto.getName()));

        return ResponseEntity.ok().build();
    }
}
