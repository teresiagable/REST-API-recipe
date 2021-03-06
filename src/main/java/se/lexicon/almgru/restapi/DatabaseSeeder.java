package se.lexicon.almgru.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import se.lexicon.almgru.restapi.data.*;
import se.lexicon.almgru.restapi.entity.*;

import java.util.*;

@Component
public class DatabaseSeeder implements ApplicationRunner {
    private final RecipeRepository recipeRepository;

    @Autowired
    public DatabaseSeeder(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public void run(ApplicationArguments args) {
        Ingredient egg = new Ingredient("egg");
        Ingredient milk = new Ingredient("milk");
        Ingredient flour = new Ingredient("wheat flour");
        Ingredient baking_powder = new Ingredient("baking powder");
        Ingredient butter = new  Ingredient("butter");
        Ingredient sugar = new  Ingredient("sugar");


        RecipeCategory cookies = new RecipeCategory("Cookies");
        RecipeCategory fried = new RecipeCategory("Fried Food");

        Collection<Recipe> recipesToAdd = new ArrayList<>();
        recipesToAdd.add(createRecipe(
                "Fiffikaka",
                "blend all and heat it up",
                cookies,
                Arrays.asList(
                        new RecipeIngredient(6, Measurement.DECILITER, milk),
                        new RecipeIngredient(3, Measurement.PIECES, egg),
                        new RecipeIngredient(2.5, Measurement.DECILITER, flour),
                        new RecipeIngredient(15, Measurement.MILLILITER, baking_powder),
                        new RecipeIngredient(5,Measurement.MILLILITER, new Ingredient("salt")),
                        new RecipeIngredient(4.5, Measurement.DECILITER,sugar),
                        new RecipeIngredient(225, Measurement.GRAM, butter),
                        new RecipeIngredient(22, Measurement.MILLILITER, new Ingredient("vanilla powder")),
                        new RecipeIngredient(1.5,Measurement.DECILITER, new Ingredient("cocoa"))
                )
        ));
        recipesToAdd.add(createRecipe(
                "Pancakes",
                "Fry it",
                fried,
                Arrays.asList(
                        new RecipeIngredient(1, Measurement.LITER, milk),
                        new RecipeIngredient(4, Measurement.PIECES, egg),
                        new RecipeIngredient(4, Measurement.DECILITER, flour)
                )
        ));

/*        recipesToAdd.add(createRecipe(
                "Magiccake",
                "Fry it over a dragons fire",
                fried,
                Arrays.asList(
                        new RecipeIngredient(100, Measurement.MILLILITER, baking_powder),
                        new RecipeIngredient(1, Measurement.PIECES, egg)
                )
        ));*/
/*        recipesToAdd.add(createRecipe(
                "Butter cookies",
                "Mix fast and throw it in the oven.",
                cookies,
                Arrays.asList(
                        new RecipeIngredient(100, Measurement.GRAM, butter),
                        new RecipeIngredient(1, Measurement.LITER, sugar),
                        new RecipeIngredient(1, Measurement.LITER, flour)
                        )
        ));*/

        recipeRepository.saveAll(recipesToAdd);
    }

    private Recipe createRecipe(String name, String instructions, RecipeCategory category, Collection<RecipeIngredient> ingredients) {
        Recipe recipe = new Recipe(name, instructions);
        category.getRecipes().add(recipe);

        for (RecipeIngredient ingredient : ingredients) {
            ingredient.setRecipe(recipe);
        }

        recipe.setCategory(category);
        recipe.setIngredients(ingredients);

        return recipe;
    }
}
