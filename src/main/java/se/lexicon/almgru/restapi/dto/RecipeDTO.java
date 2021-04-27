package se.lexicon.almgru.restapi.dto;

import java.util.Collection;

public class RecipeDTO {
    private final Integer id;
    private final String name;
    private final RecipeInstructionDTO instructions;
    private final Collection<RecipeIngredientDTO> ingredients;
    private final Collection<RecipeCategoryDTO> categories;

    public RecipeDTO(Integer id, String name, RecipeInstructionDTO instructions,
                     Collection<RecipeIngredientDTO> ingredients,
                     Collection<RecipeCategoryDTO> categories) {
        this.id = id;
        this.name = name;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.categories = categories;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public RecipeInstructionDTO getInstructions() {
        return instructions;
    }

    public Collection<RecipeIngredientDTO> getIngredients() {
        return ingredients;
    }

    public Collection<RecipeCategoryDTO> getCategories() {
        return categories;
    }
}
