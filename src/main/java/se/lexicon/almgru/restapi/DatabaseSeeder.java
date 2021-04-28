package se.lexicon.almgru.restapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import se.lexicon.almgru.restapi.data.*;
import se.lexicon.almgru.restapi.entity.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
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

        Collection<Recipe> recipesToAdd = new ArrayList<>();
        recipesToAdd.add(createRecipe(
                "Fiffikaka",
                "blend all and heat it up",
                Collections.singletonList(
                        new RecipeCategory("Pastries")
                ),
                Arrays.asList(
                        new RecipeIngredient(6, Measurement.DECILITER, milk),
                        new RecipeIngredient(3, Measurement.PIECES, egg),
                        new RecipeIngredient(2.5, Measurement.DECILITER, flour),
                        new RecipeIngredient(15, Measurement.MILLILITER, new Ingredient("baking powder")),
                        new RecipeIngredient(5,Measurement.MILLILITER, new Ingredient("salt")),
                        new RecipeIngredient(4.5, Measurement.DECILITER, new Ingredient("sugar")),
                        new RecipeIngredient(225, Measurement.GRAM, new Ingredient("butter")),
                        new RecipeIngredient(22, Measurement.MILLILITER, new Ingredient("vanilla powder")),
                        new RecipeIngredient(1.5,Measurement.DECILITER, new Ingredient("cocoa"))
                )
        ));
        recipesToAdd.add(createRecipe(
                "Pancakes",
                "cook it",
                Arrays.asList(
                        new RecipeCategory("test"),
                        new RecipeCategory("test2")
                ),
                Arrays.asList(
                        new RecipeIngredient(1, Measurement.LITER, milk),
                        new RecipeIngredient(4, Measurement.PIECES, egg),
                        new RecipeIngredient(4, Measurement.DECILITER, flour)
                )
        ));

        recipeRepository.saveAll(recipesToAdd);
    }

    private Recipe createRecipe(String name, String instructions, Collection<RecipeCategory> categories, Collection<RecipeIngredient> ingredients) {
        Recipe recipe = new Recipe(name, new RecipeInstruction(instructions));

        for (RecipeCategory category : categories) {
            category.getRecipes().add(recipe);
        }

        for (RecipeIngredient ingredient : ingredients) {
            ingredient.setRecipe(recipe);
        }

        recipe.setCategories(categories);
        recipe.setIngredients(ingredients);

        return recipe;
    }
}
