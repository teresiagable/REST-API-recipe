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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class IngredientRepositoryTest {
    @TestConfiguration
    static class TestConfig {
        @Bean
        TestDataGenerator testDataGen(long seed) {
            return new TestDataGenerator(seed);
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
        Ingredient expected = testDataGen.ingredient().setName(name).build();
        em.persistAndFlush(expected);

        Optional<Ingredient> actual = repo.findByIngredientNameEquals(name);

        assertTrue(actual.isPresent());
        assertEquals(expected.getIngredientName(), actual.get().getIngredientName());
    }

    @Test
    @DisplayName("findByIngredientNameEquals should be case sensitive")
    void findByIngredientNameEquals_caseSensitive() {
        String name = "TestIngredient";
        Ingredient ingredient = testDataGen.ingredient().setName(name).build();
        em.persistAndFlush(ingredient);

        Optional<Ingredient> actual = repo.findByIngredientNameEquals("testingredient");
        assertFalse(actual.isPresent());
    }
}
