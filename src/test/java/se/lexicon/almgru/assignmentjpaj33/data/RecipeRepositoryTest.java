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
import se.lexicon.almgru.assignmentjpaj33.entity.RecipeIngredient;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
}
