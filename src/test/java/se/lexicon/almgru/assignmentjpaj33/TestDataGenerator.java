package se.lexicon.almgru.assignmentjpaj33;

import com.github.javafaker.Faker;
import se.lexicon.almgru.assignmentjpaj33.entity.Ingredient;

import java.util.Random;

public class TestDataGenerator {
    private final Faker faker;

    public TestDataGenerator(long seed) {
        this.faker = new Faker(new Random(seed));
    }

    public IngredientBuilder ingredient() {
        return new IngredientBuilder().setName(ingredientName());
    }

    public String ingredientName() {
        return faker.food().ingredient();
    }

    public static class IngredientBuilder {
        private final Ingredient ingredient;

        private IngredientBuilder() {
            this.ingredient = new Ingredient();
        }

        public IngredientBuilder setName(String name) {
            ingredient.setIngredientName(name);
            return this;
        }

        public Ingredient build() {
            return ingredient;
        }
    }
}
