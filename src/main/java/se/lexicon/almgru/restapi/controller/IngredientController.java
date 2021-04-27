package se.lexicon.almgru.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.lexicon.almgru.restapi.data.IngredientRepository;
import se.lexicon.almgru.restapi.data.RecipeRepository;
import se.lexicon.almgru.restapi.dto.CreateIngredientDTO;
import se.lexicon.almgru.restapi.dto.IngredientDTO;
import se.lexicon.almgru.restapi.entity.Ingredient;
import se.lexicon.almgru.restapi.entity.Recipe;
import se.lexicon.almgru.restapi.exception.UniquenessViolationException;
import se.lexicon.almgru.restapi.exception.ValidationException;
import se.lexicon.almgru.restapi.service.IngredientConverter;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class IngredientController {

    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;
    private final IngredientConverter converter;

    @Autowired
    public IngredientController(IngredientRepository ingredientRepository, RecipeRepository recipeRepository,
                                IngredientConverter converter) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
        this.converter = converter;
    }

    @GetMapping("/api/ingredients")
    public ResponseEntity<Collection<IngredientDTO>> getIngredients(
            @RequestParam(name = "query", required = false) String query
    ) {
        Iterable<Ingredient> ingredients = query != null ?
                        ingredientRepository.findByIngredientNameContainingIgnoreCase(query) :
                        ingredientRepository.findAll();

        return ResponseEntity.ok(StreamSupport.stream(ingredients.spliterator(), false)
                .map(converter::ingredientToDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/api/ingredients/{id}")
    public ResponseEntity<IngredientDTO> getIngredients(@PathVariable("id") Integer id) {
        return ingredientRepository
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

        if (ingredientRepository.findByIngredientNameEqualsIgnoreCase(dto.getName()).isPresent()) {
            throw new UniquenessViolationException(String.format("An ingredient with name '%s' already exists.",
                    dto.getName()));
        }

        ingredientRepository.save(new Ingredient(dto.getName()));

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/ingredients/{id}")
    public ResponseEntity<String> deleteIngredient(@PathVariable Integer id) {
        Optional<Ingredient> toDelete = ingredientRepository.findById(id);

        if (!toDelete.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Collection<Recipe> recipesContainingIngredient =
                recipeRepository.findByIngredientName(toDelete.get().getIngredientName());

        if (recipesContainingIngredient.size() > 0) {
            return ResponseEntity.unprocessableEntity().body(String.format(
                    "Cannot delete ingredient since it is in use by the following recipes: %s. Delete them and then " +
                            "try again.",
                    recipesContainingIngredient.stream().map(Recipe::getRecipeName).collect(Collectors.toList())));
        }

        ingredientRepository.delete(toDelete.get());

        return ResponseEntity.ok(String.format("Deleted ingredient with id %d.", id));
    }
}
