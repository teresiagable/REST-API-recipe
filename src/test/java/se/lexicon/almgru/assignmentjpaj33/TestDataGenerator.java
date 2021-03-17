package se.lexicon.almgru.assignmentjpaj33;

import com.github.javafaker.Faker;
import se.lexicon.almgru.assignmentjpaj33.entity.*;

import java.util.Random;

public class TestDataGenerator {
    private final Faker faker;

    public TestDataGenerator(long seed) {
        this.faker = new Faker(new Random(seed));
    }

    public Ingredient ingredient() {
        return new Ingredient(ingredientName());
    }

    public String ingredientName() {
        return faker.food().ingredient();
    }

    public Recipe recipeWithName(String name) {
        return new Recipe(name, recipeInstruction());
    }

    public RecipeInstruction recipeInstruction() {
        return new RecipeInstruction(faker.lorem().paragraph());
    }
}
