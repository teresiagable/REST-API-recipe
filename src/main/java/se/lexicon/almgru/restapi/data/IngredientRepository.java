package se.lexicon.almgru.restapi.data;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.almgru.restapi.entity.Ingredient;

import java.util.Optional;
import java.util.Set;

public interface IngredientRepository extends CrudRepository<Ingredient, Integer> {


    /**
     * Find an ingredient by exact name match, case insensitive.
     * @param name exact name of ingredient to find.
     * @return An optional containing the ingredient if an ingredient exists with 'name'.
     */
    Optional<Ingredient> findByIngredientNameEqualsIgnoreCase(String name);

    /**
     * Find all ingredients which name contains the specified query, case insensitive.
     * @param query search query for ingredient name.
     * @return A set containing all ingredients which name contains 'query'.
     */
    Set<Ingredient> findByIngredientNameContainingIgnoreCase(String query);
}
