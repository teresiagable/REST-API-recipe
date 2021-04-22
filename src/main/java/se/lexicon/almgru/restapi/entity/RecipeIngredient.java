package se.lexicon.almgru.restapi.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class RecipeIngredient {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String recipeIngredientId;

    private double amount;
    private Measurement measurement;

    @ManyToOne(
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "ingredient_id", table = "recipe_ingredient")
    private Ingredient ingredient;

    @ManyToOne(
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "recipe_id", table = "recipe_ingredient")
    private Recipe recipe;

    public RecipeIngredient(String recipeIngredientId, double amount, Measurement measurement, Ingredient ingredient,
                            Recipe recipe) {
        this.recipeIngredientId = recipeIngredientId;
        this.amount = amount;
        this.measurement = measurement;
        this.ingredient = ingredient;
        this.recipe = recipe;
    }

    public RecipeIngredient(double amount, Measurement measurement, Ingredient ingredient, Recipe recipe) {
        this(null, amount, measurement, ingredient, recipe);
    }

    public RecipeIngredient() { }

    public String getRecipeIngredientId() {
        return recipeIngredientId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeIngredient that = (RecipeIngredient) o;
        return amount == that.amount && Objects.equals(recipeIngredientId, that.recipeIngredientId) && measurement == that.measurement;
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeIngredientId, amount, measurement);
    }

    @Override
    public String toString() {
        return "RecipeIngredient{" +
                "uuid='" + recipeIngredientId + '\'' +
                ", amount=" + amount +
                ", measurement=" + measurement +
                '}';
    }
}
