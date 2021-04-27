package se.lexicon.almgru.restapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.almgru.restapi.data.IngredientRepository;
import se.lexicon.almgru.restapi.dto.RecipeIngredientDTO;
import se.lexicon.almgru.restapi.entity.Ingredient;
import se.lexicon.almgru.restapi.entity.Recipe;
import se.lexicon.almgru.restapi.entity.RecipeIngredient;

@Service
public class RecipeIngredientConverter {
    private final IngredientRepository repository;

    @Autowired
    public RecipeIngredientConverter(IngredientRepository repository) {
        this.repository = repository;
    }

    public RecipeIngredient dtoToRecipeIngredient(RecipeIngredientDTO dto, Recipe recipe) {
        return new RecipeIngredient(dto.getAmount(), dto.getMeasurement(),
                repository
                        .findByIngredientNameEqualsIgnoreCase(dto.getName())
                        .orElse(new Ingredient(dto.getName())),
                recipe);
    }

    public RecipeIngredientDTO recipeIngredientToDTO(RecipeIngredient recipeIngredient) {
        return new RecipeIngredientDTO(recipeIngredient.getIngredient().getIngredientId(),
                recipeIngredient.getIngredient().getIngredientName(),
                recipeIngredient.getMeasurement(), recipeIngredient.getAmount());
    }
}
