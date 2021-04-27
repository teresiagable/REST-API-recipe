package se.lexicon.almgru.restapi.service;

import org.springframework.stereotype.Service;
import se.lexicon.almgru.restapi.dto.IngredientDTO;
import se.lexicon.almgru.restapi.entity.Ingredient;

@Service
public class IngredientConverter {
    public IngredientDTO ingredientToDTO(Ingredient ingredient) {
        return new IngredientDTO(ingredient.getIngredientId(), ingredient.getIngredientName());
    }
}
