package se.lexicon.almgru.restapi.service;

import org.springframework.stereotype.Service;
import se.lexicon.almgru.restapi.dto.RecipeInstructionDTO;
import se.lexicon.almgru.restapi.entity.RecipeInstruction;

@Service
public class RecipeInstructionConverter {
    public RecipeInstructionDTO recipeInstructionToDTO(RecipeInstruction instruction) {
        return new RecipeInstructionDTO(instruction.getRecipeInstructionId(), instruction.getInstructions());
    }
}
