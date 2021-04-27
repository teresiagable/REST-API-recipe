package se.lexicon.almgru.restapi.service;

import org.springframework.stereotype.Service;
import se.lexicon.almgru.restapi.dto.RecipeCategoryDTO;
import se.lexicon.almgru.restapi.entity.RecipeCategory;

@Service
public class RecipeCategoryConverter {
    public RecipeCategoryDTO recipeCategoryToDTO(RecipeCategory category) {
        return new RecipeCategoryDTO(category.getRecipeCategoryId(), category.getCategory());
    }
}
