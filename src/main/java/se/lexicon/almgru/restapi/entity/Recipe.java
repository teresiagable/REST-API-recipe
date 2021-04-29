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

    private int cookingTime;

    private String recipeName;

    @OneToOne(
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    private RecipeInstruction instructions;

    @OneToMany(
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY,
            mappedBy = "recipe"
    )
    private Collection<RecipeIngredient> ingredients;

    @ManyToMany(
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "recipe_recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_category_id")
    )
    private Collection<RecipeCategory> categories;

    public Recipe(int recipeId, String recipeName, RecipeInstruction instructions, Collection<RecipeIngredient> ingredients,
                  Collection<RecipeCategory> categories, int cookingTime) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.categories = categories;
        this.cookingTime=cookingTime;
    }


    public Recipe(String recipeName, RecipeInstruction instructions, Collection<RecipeIngredient> ingredients,
                  Collection<RecipeCategory> categories, int cookingTime) {
        this(0, recipeName, instructions, ingredients, categories, cookingTime);
    }

    /**
     * @param recipeName
     * @param instructions
     */
    public Recipe(String recipeName, RecipeInstruction instructions, int cookingTime) {
        this(0, recipeName, instructions, new HashSet<>(), new HashSet<>(),cookingTime);
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
        categories.forEach(category -> category.removeRecipe(this));
        categories.clear();
    }

    public int getRecipeId() {
        return recipeId;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String name) {
        this.recipeName = name;
    }

    public RecipeInstruction getInstructions() {
        return instructions;
    }

    public void setInstructions(RecipeInstruction instructions) {
        this.instructions = instructions;
    }

    public Collection<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Collection<RecipeIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Collection<RecipeCategory> getCategories() {
        return categories;
    }

    public void setCategories(Collection<RecipeCategory> categories) {
        this.categories = categories;
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
