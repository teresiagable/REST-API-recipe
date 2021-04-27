package se.lexicon.almgru.restapi.dto;

public class RecipeInstructionDTO {
    private final String id;
    private final String instructions;

    public RecipeInstructionDTO(String id, String instructions) {
        this.id = id;
        this.instructions = instructions;
    }

    public String getId() {
        return id;
    }

    public String getInstructions() {
        return instructions;
    }
}
