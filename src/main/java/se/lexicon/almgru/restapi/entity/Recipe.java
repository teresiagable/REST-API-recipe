package se.lexicon.almgru.restapi.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recipeId;

    private String recipeName;

    private String instructions;

    @OneToMany(
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY,
            mappedBy = "recipe"
    )
    private Collection<RecipeIngredient> ingredients;

    @ManyToOne(
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    private RecipeCategory category;

    public Recipe(int recipeId, String recipeName, String instructions, Collection<RecipeIngredient> ingredients,
                  RecipeCategory category) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.category = category;
    }

    /**
     * @param recipeName
     * @param instructions
     * @param ingredients
     * @param category
     */
    public Recipe(String recipeName, String instructions, Collection<RecipeIngredient> ingredients,
                  RecipeCategory category) {
        this(0, recipeName, instructions, ingredients, category);
    }

    /**
     * @param recipeName
     * @param instructions
     */
    public Recipe(String recipeName, String instructions) {
        this(0, recipeName, instructions, new HashSet<>(), null);
    }

    public Recipe() { }

    @PreRemove
    public void preRemove() {
        clearIngredients();
        clearCategories();
    }

    public void clearIngredients() {
        ingredients.forEach(RecipeIngredient::detachRecipe);
        ingredients.clear();
    }

    public void clearCategories() {
        category.removeRecipe(this);
        category = null;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String name) {
        this.recipeName = name;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Collection<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Collection<RecipeIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    public RecipeCategory getCategory() {
        return category;
    }

    public void setCategory(RecipeCategory category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return recipeId == recipe.recipeId && Objects.equals(recipeName, recipe.recipeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeId, recipeName);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + recipeId +
                ", name='" + recipeName + '\'' +
                '}';
    }
}
