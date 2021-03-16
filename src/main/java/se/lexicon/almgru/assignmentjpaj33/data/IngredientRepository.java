package se.lexicon.almgru.assignmentjpaj33.data;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.almgru.assignmentjpaj33.entity.Ingredient;

import java.util.Optional;

public interface IngredientRepository extends CrudRepository<Ingredient, Integer> {
    /**
     * Find an ingredient by exact name match.
     * @param name exact name of ingredient to find.
     * @return An optional containing the ingredient if an ingredient exists with 'name'.
     */
    Optional<Ingredient> findByIngredientNameEquals(String name);
}
