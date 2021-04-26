package se.lexicon.almgru.restapi.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CreateIngredientDTO {
    @NotNull
    @NotEmpty
    private final String name;

    @JsonCreator
    public CreateIngredientDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}