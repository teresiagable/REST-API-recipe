package se.lexicon.almgru.restapi.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import se.lexicon.almgru.restapi.validation.ElementsNotEmpty;
import se.lexicon.almgru.restapi.validation.ElementsNotNull;

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

    @ElementsNotEmpty
    @ElementsNotNull
    private final List<String> categories;

    @JsonCreator
    public CreateRecipeDTO(String name, String instructions, List<RecipeIngredientDTO> ingredients,
                           List<String> categories) {
        this.name = name;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.categories = categories;
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

    public List<String> getCategories() {
        return categories;
    }
}
