package se.lexicon.almgru.restapi.dto;

import java.util.Collection;

public class RecipeDTO {
    private final Integer id;
    private final String name;
    private final String instructions;
    private final Collection<RecipeIngredientDTO> ingredients;
    private final RecipeCategoryDTO category;

    public RecipeDTO(Integer id, String name, String instructions,
                     Collection<RecipeIngredientDTO> ingredients,
                     RecipeCategoryDTO category) {
        this.id = id;
        this.name = name;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getInstructions() {
        return instructions;
    }

    public Collection<RecipeIngredientDTO> getIngredients() {
        return ingredients;
    }

    public RecipeCategoryDTO getCategory() {
        return category;
    }
}
