package se.lexicon.almgru.assignmentjpaj33.data;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.almgru.assignmentjpaj33.entity.Recipe;

import java.util.Set;

public interface RecipeRepository extends CrudRepository<Recipe, Integer> {
    /**
     * Find all recipes which name contains the specified query, case insensitive.
     * @param query search query for recipe name.
     * @return A set containing all recipes which name contains 'query'.
     */
    Set<Recipe> findByRecipeNameContainingIgnoreCase(String query);
}
