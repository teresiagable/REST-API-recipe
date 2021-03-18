package se.lexicon.almgru.assignmentjpaj33;

import com.github.javafaker.Faker;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import se.lexicon.almgru.assignmentjpaj33.entity.*;

import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestDataGenerator {
    private final Faker faker;
    private final TestEntityManager em;

    public TestDataGenerator(long seed, TestEntityManager em) {
        this.faker = new Faker(new Random(seed));
        this.em = em;
    }

    /* Special handling for ingredients since they must have unique names. If a generated ingredient name already
       exists in the database, fetch it instead of trying to insert it, since inserting causes an exception. */
    public Ingredient ingredient() {
        String ingredientName = ingredientName();
        Ingredient ingredient;

        try {
            ingredient = em.getEntityManager()
                    .createQuery("SELECT i FROM Ingredient i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", ingredientName)
                    .getSingleResult();
        } catch (NoResultException ex) {
            ingredient = new Ingredient(ingredientName);
            em.persist(ingredient);
        }

        return ingredient;
    }

    public String ingredientName() {
        return faker.food().ingredient();
    }

    public Recipe recipeWithName(String name) {
        Recipe recipe = new Recipe(name, recipeInstruction());

        recipe.setIngredients(recipeIngredients(recipe));
        recipe.setCategories(recipeCategories(recipe));

        return recipe;
    }

    public RecipeInstruction recipeInstruction() {
        return new RecipeInstruction(faker.lorem().paragraph());
    }

    public Measurement measurement() {
        return Measurement.values()[faker.random().nextInt(Measurement.values().length)];
    }

    public List<RecipeIngredient> recipeIngredients(Recipe recipe) {
        return IntStream.range(1, faker.random().nextInt(3, 10))
                .mapToObj(i -> new RecipeIngredient(faker.random().nextDouble(), measurement(), ingredient(), recipe))
                .collect(Collectors.toList());
    }

    public String recipeCategoryName() {
        return faker.lorem().word();
    }

    public List<RecipeCategory> recipeCategories(Recipe recipe) {
        return IntStream.range(1, faker.random().nextInt(1, 3))
                .mapToObj(i -> new RecipeCategory(recipeCategoryName(), Collections.singletonList(recipe)))
                .collect(Collectors.toList());
    }
}