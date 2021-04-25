package se.lexicon.almgru.restapi.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateIngredientDTO {
    @NotNull
    @Size(min = 1)
    private final String name;

    public CreateIngredientDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}