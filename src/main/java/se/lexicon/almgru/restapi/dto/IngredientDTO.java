package se.lexicon.almgru.restapi.dto;

public class IngredientDTO {
    private final Integer id;
    private final String name;

    public IngredientDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
