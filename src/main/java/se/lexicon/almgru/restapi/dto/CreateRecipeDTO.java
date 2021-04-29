package se.lexicon.almgru.restapi.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import se.lexicon.almgru.restapi.validation.ElementsNotEmpty;
import se.lexicon.almgru.restapi.validation.ElementsNotNull;
import se.lexicon.almgru.restapi.validation.NotEmptyUnlessNull;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class CreateRecipeDTO {
    @NotNull
    @NotEmpty
    private final String name;

    @NotNull
    @NotEmpty
    private final String instructions;

    @NotNull
    @NotEmpty
    @ElementsNotNull
    private final List<RecipeIngredientDTO> ingredients;

    @NotEmptyUnlessNull
    private final String category;

    @JsonCreator
    public CreateRecipeDTO(String name, String instructions, List<RecipeIngredientDTO> ingredients,
                           String category) {
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
