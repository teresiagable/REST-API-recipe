package se.lexicon.almgru.restapi.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import se.lexicon.almgru.restapi.validation.ElementsNotNull;
import se.lexicon.almgru.restapi.validation.NotEmptyUnlessNull;

import java.util.List;

public class UpdateRecipeDTO {
    @NotEmptyUnlessNull
    private final String name;

    @NotEmptyUnlessNull
    private final String instructions;

    @NotEmptyUnlessNull
    @ElementsNotNull
    private final List<RecipeIngredientDTO> ingredients;

    @NotEmptyUnlessNull
    private final String category;

    @JsonCreator
    public UpdateRecipeDTO(String name, String instructions, List<RecipeIngredientDTO> ingredients, String category) {
        this.name = name;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getInstructions() {
        return instructions;
    }

    public List<RecipeIngredientDTO> getIngredients() {
        return ingredients;
    }

    public String getCategory() {
        return category;
    }
}
