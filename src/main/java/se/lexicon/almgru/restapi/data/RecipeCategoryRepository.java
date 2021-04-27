package se.lexicon.almgru.restapi.data;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.almgru.restapi.entity.RecipeCategory;

import java.util.Optional;

public interface RecipeCategoryRepository extends CrudRepository<RecipeCategory, Integer> {
    Optional<RecipeCategory> findByCategoryEqualsIgnoreCase(String name);
}
