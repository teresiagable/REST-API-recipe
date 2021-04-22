package se.lexicon.almgru.restapi.data;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.almgru.restapi.entity.RecipeIngredient;

public interface RecipeIngredientRepository extends CrudRepository<RecipeIngredient, String> {
}
