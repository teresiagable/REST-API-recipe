package se.lexicon.almgru.assignmentjpaj33.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import se.lexicon.almgru.assignmentjpaj33.entity.Recipe;

import java.util.Set;

public interface RecipeRepository extends CrudRepository<Recipe, Integer> {
    /**
     * Find all recipes which name contains the specified query, case insensitive.
     * @param query search query for recipe name.
     * @return A set containing all recipes which name contains 'query'.
     */
    Set<Recipe> findByRecipeNameContainingIgnoreCase(String query);

    /**
     * Find all recipes that contains ingredients with the specified name. The match is exact and case insensitive.
     * @param ingredientName name of the ingredient to search recipes by.
     * @return A set containing all recipes that contain the specified ingredient.
     */
    @Query("SELECT r FROM Recipe r JOIN FETCH r.ingredients AS ri WHERE UPPER(ri.ingredient.ingredientName) = UPPER(:name)")
    Set<Recipe> findByIngredientName(@Param("name") String ingredientName);

    /**
     * Find all recipes that are categorized with the specified category.
     * @param category Name of category to search recipes by.
     * @return A set containing all recipes with the specified category.
     */
    @Query("SELECT r FROM Recipe r JOIN FETCH r.categories AS rc WHERE UPPER(rc.category) = UPPER(:category)")
    Set<Recipe> findByCategory(@Param("category") String category);
}
