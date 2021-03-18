package se.lexicon.almgru.assignmentjpaj33.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import se.lexicon.almgru.assignmentjpaj33.TestDataGenerator;
import se.lexicon.almgru.assignmentjpaj33.entity.Ingredient;
import se.lexicon.almgru.assignmentjpaj33.entity.Recipe;
import se.lexicon.almgru.assignmentjpaj33.entity.RecipeCategory;
import se.lexicon.almgru.assignmentjpaj33.entity.RecipeIngredient;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class RecipeRepositoryTest {
    @TestConfiguration
    static class TestConfig {
        @Bean
        TestDataGenerator testDataGen(long seed, TestEntityManager em) {
            return new TestDataGenerator(seed, em);
        }

        @Bean
        long seed() {
            return -206152678L;
        }
    }

    @Autowired
    private RecipeRepository repo;

    @Autowired
    private TestEntityManager em;

    @Autowired
    private TestDataGenerator testDataGen;

    @Test
    @DisplayName("findByRecipeNameContainingIgnoreCase should find matching recipes")
    void findByRecipeNameContainingIgnoreCase_findsMatching() {
        List<Recipe> recipes = Arrays.asList(
                testDataGen.recipeWithName("Spaghetti and Meatballs"),
                testDataGen.recipeWithName("Spaghetti Carbonara"),
                testDataGen.recipeWithName("Lasagne")
        );
        recipes.forEach(em::persist);
        em.flush();

        Collection<Recipe> actual = repo.findByRecipeNameContainingIgnoreCase("Spaghetti");

        assertTrue(actual.contains(recipes.get(0)));
        assertTrue(actual.contains(recipes.get(1)));
        assertFalse(actual.contains(recipes.get(2)));
    }

    @Test
    @DisplayName("findByRecipeNameContainingIgnoreCase should be case insensitive")
    void findByRecipeNameContainingIgnoreCase_caseInsensitive() {
        Recipe recipe = testDataGen.recipeWithName("Pancakes");
        em.persistAndFlush(recipe);

        Collection<Recipe> actual = repo.findByRecipeNameContainingIgnoreCase("pancakes");

        assertTrue(actual.contains(recipe));
    }

    @Test
    @DisplayName("findByIngredientName should find matching recipes")
    void findByIngredientName_findsMatching() {
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("Pasta"),
                new Ingredient("Egg"),
                new Ingredient("Bacon"),
                new Ingredient("Minced meat"),
                new Ingredient("Crushed tomatoes")
        );
        List<Recipe> recipes = Arrays.asList(
                testDataGen.recipeWithIngredients(ingredients.get(0), ingredients.get(1), ingredients.get(2)),
                testDataGen.recipeWithIngredients(ingredients.get(0), ingredients.get(3), ingredients.get(4)),
                testDataGen.recipeWithIngredients(ingredients.get(1), ingredients.get(2))
        );
        recipes.forEach(em::persist);
        em.flush();

        Collection<Recipe> actual = repo.findByIngredientName("Pasta");

        assertTrue(actual.contains(recipes.get(0)));
        assertTrue(actual.contains(recipes.get(1)));
        assertFalse(actual.contains(recipes.get(2)));
    }

    @Test
    @DisplayName("findByIngredientName should be case insensitive")
    void findByIngredientName_caseInsensitive() {
        Ingredient ingredient = new Ingredient("Cucumber");
        Recipe recipe = testDataGen.recipeWithIngredients(ingredient);
        em.persistAndFlush(recipe);

        Collection<Recipe> actual = repo.findByIngredientName("cucumber");

        assertTrue(actual.contains(recipe));
    }

    @Test
    @DisplayName("findByIngredientName should perform exact match")
    void findByIngredientName_exactMatch() {
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("Orange"),
                new Ingredient("Blood Orange"),
                new Ingredient("Orange Peel")
        );
        List<Recipe> recipes = Arrays.asList(
                testDataGen.recipeWithIngredients(ingredients.get(0)),
                testDataGen.recipeWithIngredients(ingredients.get(1)),
                testDataGen.recipeWithIngredients(ingredients.get(2))
        );
        recipes.forEach(em::persist);
        em.flush();

        Collection<Recipe> actual = repo.findByIngredientName("Orange");

        assertEquals(1, actual.size());
        assertTrue(actual.contains(recipes.get(0)));
    }

    @Test
    @DisplayName("findByCategory should find matching recipes")
    void findByCategory_findsMatching() {
        List<RecipeCategory> categories = Arrays.asList(
                new RecipeCategory("Mexican"),
                new RecipeCategory("Husmanskost"),
                new RecipeCategory("Thai")
        );
        List<Recipe> recipes = Arrays.asList(
                testDataGen.recipeWithCategories(categories.get(0)),
                testDataGen.recipeWithCategories(categories.get(0)),
                testDataGen.recipeWithCategories(categories.get(1)),
                testDataGen.recipeWithCategories(categories.get(1)),
                testDataGen.recipeWithCategories(categories.get(1)),
                testDataGen.recipeWithCategories(categories.get(2))
        );
        recipes.forEach(em::persist);
        em.flush();

        Collection<Recipe> actual = repo.findByCategory("Husmanskost");

        assertEquals(3, actual.size());
        assertTrue(actual.containsAll(recipes.subList(2, 5)));
    }

    @Test
    @DisplayName("findByCategory should be case insensitive")
    void findByCategory_caseInsensitive() {
        RecipeCategory category = new RecipeCategory("Mediterranean");
        Recipe recipe = testDataGen.recipeWithCategories(category);
        em.persistAndFlush(recipe);

        Collection<Recipe> actual = repo.findByCategory("mediterranean");

        assertTrue(actual.contains(recipe));
    }
}
