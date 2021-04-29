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

    @ElementsNotNull
    @ElementsNotEmpty
    private final List<String> categories;

    private final int cookingTime;

    @JsonCreator
    public CreateRecipeDTO(String name, String instructions, List<RecipeIngredientDTO> ingredients,
                           List<String> categories, int cookingTime) {
        this.name = name;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.categories = categories;
        this.cookingTime = cookingTime;
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

    public Integer getCookingTime() {
        return cookingTime;
    }

}
