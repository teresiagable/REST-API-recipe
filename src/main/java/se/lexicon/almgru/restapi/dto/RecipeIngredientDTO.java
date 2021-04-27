package se.lexicon.almgru.restapi.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import se.lexicon.almgru.restapi.entity.Measurement;

public class RecipeIngredientDTO {
    private final String name;
    private final Measurement measurement;
    private final Double amount;

    @JsonCreator
    public RecipeIngredientDTO(String name, Measurement measurement, Double amount) {
        this.name = name;
        this.measurement = measurement;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    public Double getAmount() {
        return amount;
    }
}
