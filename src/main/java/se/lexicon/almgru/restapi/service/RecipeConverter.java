package se.lexicon.almgru.restapi.service;

import org.springframework.stereotype.Service;
import se.lexicon.almgru.restapi.dto.RecipeDTO;
import se.lexicon.almgru.restapi.entity.Recipe;

import java.util.stream.Collectors;

@Service
public class RecipeConverter {
    private final RecipeIngredientConverter ingredientConverter;
    private final RecipeCategoryConverter categoryConverter;

    public RecipeConverter(RecipeIngredientConverter ingredientConverter, RecipeCategoryConverter categoryConverter) {
        this.ingredientConverter = ingredientConverter;
        this.categoryConverter = categoryConverter;
    }

    public RecipeDTO recipeToDTO(Recipe recipe) {
        return new RecipeDTO(
                recipe.getRecipeId(),
                recipe.getRecipeName(),
                recipe.getInstructions(),
                recipe.getIngredients()
                        .stream()
                        .map(ingredientConverter::recipeIngredientToDTO)
                        .collect(Collectors.toList()),
                categoryConverter.recipeCategoryToDTO(recipe.getCategory()));
    }
}
