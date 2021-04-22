package se.lexicon.almgru.restapi.data;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.almgru.restapi.entity.RecipeInstruction;

public interface RecipeInstructionRepository extends CrudRepository<RecipeInstruction, String> {
}
