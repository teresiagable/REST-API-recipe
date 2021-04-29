package se.lexicon.almgru.restapi;

import com.github.javafaker.Faker;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import se.lexicon.almgru.restapi.entity.*;

import javax.persistence.NoResultException;
import java.util.Arrays;
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

    public String recipeName() {
        return faker.food().ingredient() + " and " + faker.food().ingredient();
    }

    public Recipe recipeWithName(String name) {
        Recipe recipe = new Recipe(name, recipeInstruction());

        recipe.setIngredients(recipeIngredients(recipe));
        recipe.setCategory(recipeCategory(recipe));

        return recipe;
    }

    public Recipe recipeWithIngredients(Ingredient ...ingredients) {
        Recipe recipe = new Recipe(recipeName(), recipeInstruction());

        recipe.setIngredients(recipeIngredients(recipe, Arrays.asList(ingredients)));
        recipe.setCategory(recipeCategory(recipe));

        return recipe;
    }

    public Recipe recipeWithCategories(RecipeCategory category) {
        Recipe recipe = new Recipe(recipeName(), recipeInstruction());

        recipe.setIngredients(recipeIngredients(recipe));
        recipe.setCategory(category);

        return recipe;
    }

    public String recipeInstruction() {
        return faker.lorem().paragraph();
    }

    public Measurement measurement() {
        return Measurement.values()[faker.random().nextInt(Measurement.values().length)];
    }

    public List<RecipeIngredient> recipeIngredients(Recipe recipe) {
        return IntStream.range(1, faker.random().nextInt(3, 10))
                .mapToObj(i -> new RecipeIngredient(faker.random().nextDouble(), measurement(), ingredient(), recipe))
                .collect(Collectors.toList());
    }

    public List<RecipeIngredient> recipeIngredients(Recipe recipe, List<Ingredient> ingredients) {
        return ingredients.stream()
                .map(ing -> new RecipeIngredient(faker.random().nextDouble(), measurement(), ing, recipe))
                .collect(Collectors.toList());
    }

    public String recipeCategoryName() {
        return faker.lorem().word();
    }

    public RecipeCategory recipeCategory(Recipe recipe) {
        return new RecipeCategory(recipeCategoryName(), Collections.singletonList(recipe));
    }
}
