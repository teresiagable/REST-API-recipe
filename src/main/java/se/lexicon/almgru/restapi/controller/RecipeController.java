package se.lexicon.almgru.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.lexicon.almgru.restapi.data.RecipeCategoryRepository;
import se.lexicon.almgru.restapi.data.RecipeIngredientRepository;
import se.lexicon.almgru.restapi.data.RecipeInstructionRepository;
import se.lexicon.almgru.restapi.data.RecipeRepository;
import se.lexicon.almgru.restapi.dto.CreateRecipeDTO;
import se.lexicon.almgru.restapi.dto.RecipeCategoryDTO;
import se.lexicon.almgru.restapi.dto.RecipeDTO;
import se.lexicon.almgru.restapi.dto.UpdateRecipeDTO;
import se.lexicon.almgru.restapi.entity.Recipe;
import se.lexicon.almgru.restapi.entity.RecipeCategory;
import se.lexicon.almgru.restapi.entity.RecipeIngredient;
import se.lexicon.almgru.restapi.entity.RecipeInstruction;
import se.lexicon.almgru.restapi.exception.InvalidParameterCombinationException;
import se.lexicon.almgru.restapi.exception.ValidationException;
import se.lexicon.almgru.restapi.service.RecipeConverter;
import se.lexicon.almgru.restapi.service.RecipeIngredientConverter;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class RecipeController {
    private final RecipeRepository recipeRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final RecipeCategoryRepository categoryRepository;
    private final RecipeInstructionRepository instructionRepository;

    private final RecipeIngredientConverter recipeIngredientConverter;
    private final RecipeConverter recipeConverter;

    @Autowired
    public RecipeController(RecipeRepository recipeRepository, RecipeIngredientRepository recipeIngredientRepository,
                            RecipeCategoryRepository categoryRepository,
                            RecipeInstructionRepository instructionRepository,
                            RecipeIngredientConverter recipeIngredientConverter, RecipeConverter recipeConverter) {
        this.recipeRepository = recipeRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.categoryRepository = categoryRepository;
        this.instructionRepository = instructionRepository;
        this.recipeIngredientConverter = recipeIngredientConverter;
        this.recipeConverter = recipeConverter;
    }

    @GetMapping("/api/recipes")
    public ResponseEntity<Collection<RecipeDTO>> getRecipes(
            @RequestParam(name = "query", required = false) String nameQuery,
            @RequestParam(name = "ingredient", required = false) String ingredient,
            @RequestParam(name = "categories", required = false) List<String> categories) {
        if (invalidParamCombination(nameQuery, ingredient, categories)) {
            throw new InvalidParameterCombinationException(
                    "Parameters 'query', 'ingredient' and 'categories' cannot be combined. " +
                    "Please only specify one of them."
            );
        }
        Iterable<Recipe> results;

        if (nameQuery != null) {
            results = recipeRepository.findByRecipeNameContainingIgnoreCase(nameQuery);
        } else if (ingredient != null) {
            results = recipeRepository.findByIngredientName(ingredient);
        } else if (categories != null) {
            results = recipeRepository.findByCategoriesContainsAny(categories);
        } else {
            results = recipeRepository.findAll();
        }

        return ResponseEntity.ok(StreamSupport
                .stream(results.spliterator(), false)
                .map(recipeConverter::recipeToDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/api/recipes/{id}")
    public ResponseEntity<RecipeDTO> getRecipeById(@PathVariable int id) {
        return recipeRepository
                .findById(id)
                .map(recipeConverter::recipeToDTO)
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

        Recipe recipe = new Recipe(dto.getName(), new RecipeInstruction(dto.getInstructions()),dto.getCookingTime());

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

    @PatchMapping("/api/recipes/{id}")
    public ResponseEntity<String> updateRecipe(@PathVariable Integer id, @Valid @RequestBody UpdateRecipeDTO dto,
                                               BindingResult bind) {
        if (bind.hasErrors()) {
            throw new ValidationException(bind
                    .getFieldErrors()
                    .stream()
                    .map(fieldError -> String.format("%s - %s", fieldError.getField(), fieldError.getDefaultMessage()))
                    .collect(Collectors.joining(", "))
            );
        }

        Optional<Recipe> recipe = recipeRepository.findById(id);

        if (!recipe.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        if (dto.getName() != null) {
            recipe.get().setRecipeName(dto.getName());
        }

        if (dto.getInstructions() != null) {
            recipe.get().getInstructions().setInstructions(dto.getInstructions());
        }

        if (dto.getIngredients() != null) {
            recipe.get().clearIngredients();
            recipe.get().getIngredients().addAll(dto.getIngredients().stream()
                    .map(ingredientDTO -> recipeIngredientConverter.dtoToRecipeIngredient(ingredientDTO, recipe.get()))
                    .collect(Collectors.toList()));
        }

        if (dto.getCategories() != null) {
            recipe.get().clearCategories();
            recipe.get().getCategories().addAll(dto.getCategories().stream()
                    .map(categoryName -> categoryRepository
                            .findByCategoryEqualsIgnoreCase(categoryName)
                            .orElse(new RecipeCategory(categoryName)))
                    .collect(Collectors.toList()));
        }

        recipeRepository.save(recipe.get());

        return ResponseEntity.ok(String.format("Recipe with id %d updated succesfully.", id));
    }

    @DeleteMapping("/api/recipes/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable Integer id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);

        if (!recipe.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        instructionRepository.deleteById(recipe.get().getInstructions().getRecipeInstructionId());
        recipeIngredientRepository.deleteAll(recipe.get().getIngredients());
        recipeRepository.delete(recipe.get());

        return ResponseEntity.ok(String.format("Deleted recipe with id %d.", id));
    }

    private boolean invalidParamCombination(String nameQuery, String ingredient, List<String> categories) {
        return (nameQuery != null && ingredient != null) ||
               (nameQuery != null && categories != null) ||
               (ingredient != null && categories != null);
    }
}
