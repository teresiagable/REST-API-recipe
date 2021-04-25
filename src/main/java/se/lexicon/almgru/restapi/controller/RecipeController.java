package se.lexicon.almgru.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.lexicon.almgru.restapi.data.RecipeRepository;
import se.lexicon.almgru.restapi.entity.Recipe;
import se.lexicon.almgru.restapi.exception.InvalidParameterCombinationException;

import java.util.List;

@RestController
public class RecipeController {
    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @GetMapping("/api/recipes")
    public ResponseEntity<Iterable<Recipe>> getRecipes(
            @RequestParam(name = "query", required = false) String nameQuery,
            @RequestParam(name = "ingredient", required = false) String ingredient,
            @RequestParam(name = "categories", required = false) List<String> categories) {
        if (invalidParamCombination(nameQuery, ingredient, categories)) {
            throw new InvalidParameterCombinationException(
                    "Parameters 'query', 'ingredient' and 'categories' cannot be combined. " +
                    "Please only specify one of them."
            );
        }

        if (nameQuery != null) {
            return ResponseEntity.ok(recipeRepository.findByRecipeNameContainingIgnoreCase(nameQuery));
        } else if (ingredient != null) {
            return ResponseEntity.ok(recipeRepository.findByIngredientName(ingredient));
        } else if (categories != null) {
            return ResponseEntity.ok(recipeRepository.findByCategoriesContainsAny(categories));
        } else {
            return ResponseEntity.ok(recipeRepository.findAll());
        }
    }

    @GetMapping("/api/recipes/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable int id) {
        return recipeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private boolean invalidParamCombination(String nameQuery, String ingredient, List<String> categories) {
        return (nameQuery != null && ingredient != null) ||
               (nameQuery != null && categories != null) ||
               (ingredient != null && categories != null);
    }
}
