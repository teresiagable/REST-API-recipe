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
import se.lexicon.almgru.restapi.data.RecipeCategoryRepository;
import se.lexicon.almgru.restapi.data.RecipeRepository;
import se.lexicon.almgru.restapi.dto.CreateRecipeDTO;
import se.lexicon.almgru.restapi.entity.Recipe;
import se.lexicon.almgru.restapi.entity.RecipeCategory;
import se.lexicon.almgru.restapi.entity.RecipeInstruction;
import se.lexicon.almgru.restapi.exception.InvalidParameterCombinationException;
import se.lexicon.almgru.restapi.exception.ValidationException;
import se.lexicon.almgru.restapi.service.RecipeIngredientConverter;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RecipeController {
    private final RecipeRepository recipeRepository;
    private final RecipeCategoryRepository categoryRepository;
    private final RecipeIngredientConverter recipeIngredientConverter;

    @Autowired
    public RecipeController(RecipeRepository recipeRepository, RecipeCategoryRepository categoryRepository,
                            RecipeIngredientConverter recipeIngredientConverter) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.recipeIngredientConverter = recipeIngredientConverter;
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

    @PostMapping("/api/recipes")
    public ResponseEntity<Void> createRecipe(@Valid @RequestBody CreateRecipeDTO dto, BindingResult bind) {
        if (bind.hasErrors()) {
            throw new ValidationException(bind
                    .getFieldErrors()
                    .stream()
                    .map(fieldError -> String.format("%s - %s", fieldError.getField(), fieldError.getDefaultMessage()))
                    .collect(Collectors.joining(", "))
            );
        }

        Recipe recipe = new Recipe(dto.getName(), new RecipeInstruction(dto.getInstructions()));

        recipe.setIngredients(dto
                .getIngredients()
                .stream()
                .map(ingredientDTO -> recipeIngredientConverter.dtoToRecipeIngredient(ingredientDTO, recipe))
                .collect(Collectors.toList()));
        recipe.setCategories(dto
                .getCategories()
                .stream()
                .map(categoryName -> categoryRepository
                        .findByCategoryEqualsIgnoreCase(categoryName)
                        .orElse(new RecipeCategory(categoryName)))
                .collect(Collectors.toList()));

        recipeRepository.save(recipe);

        return ResponseEntity.ok().build();
    }

    private boolean invalidParamCombination(String nameQuery, String ingredient, List<String> categories) {
        return (nameQuery != null && ingredient != null) ||
               (nameQuery != null && categories != null) ||
               (ingredient != null && categories != null);
    }
}
