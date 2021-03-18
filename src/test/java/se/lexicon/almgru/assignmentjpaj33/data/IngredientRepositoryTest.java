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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class IngredientRepositoryTest {
    @TestConfiguration
    static class TestConfig {
        @Bean
        TestDataGenerator testDataGen(long seed, TestEntityManager em) {
            return new TestDataGenerator(seed, em);
        }

        @Bean
        long seed() {
            return -698526451L;
        }
    }

    @Autowired
    private IngredientRepository repo;

    @Autowired
    private TestEntityManager em;

    @Autowired
    private TestDataGenerator testDataGen;

    @Test
    @DisplayName("findByIngredientNameEquals should find existing ingredient")
    void findByIngredientNameEquals_findsExisting() {
        String name = testDataGen.ingredientName();
        Ingredient expected = new Ingredient(name);
        em.persistAndFlush(expected);

        Optional<Ingredient> actual = repo.findByIngredientNameEquals(name);

        assertTrue(actual.isPresent());
        assertEquals(expected.getIngredientName(), actual.get().getIngredientName());
    }

    @Test
    @DisplayName("findByIngredientNameEquals should be case sensitive")
    void findByIngredientNameEquals_caseSensitive() {
        String name = "TestIngredient";
        Ingredient ingredient = new Ingredient(name);
        em.persistAndFlush(ingredient);

        Optional<Ingredient> actual = repo.findByIngredientNameEquals("testingredient");
        assertFalse(actual.isPresent());
    }

    @Test
    @DisplayName("findByIngredientNameContainingIgnoreCase should find matching ingredients")
    void findByIngredientNameContainingIgnoreCase_findMatching() {
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("apple"),
                new Ingredient("pineapple"),
                new Ingredient("carrot")
        );
        ingredients.forEach(em::persist);
        em.flush();

        Collection<Ingredient> actual = repo.findByIngredientNameContainingIgnoreCase("apple");

        assertTrue(actual.contains(ingredients.get(0)));
        assertTrue(actual.contains(ingredients.get(1)));
        assertFalse(actual.contains(ingredients.get(2)));
    }

    @Test
    @DisplayName("findByIngredientNameContainsIgnoreCase should be case insensitive")
    void findByIngredientNameContainsIgnoreCase_caseInsensitive() {
        Ingredient ingredient = new Ingredient("Onion");
        em.persistAndFlush(ingredient);

        Collection<Ingredient> actual = repo.findByIngredientNameContainingIgnoreCase("onion");

        assertEquals(1, actual.size());
        assertTrue(actual.contains(ingredient));
    }
}
