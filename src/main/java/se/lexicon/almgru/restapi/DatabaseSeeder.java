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
    private final IngredientRepository ingredientRepository;
    private final RecipeCategoryRepository categoryRepository;
    private final RecipeInstructionRepository instructionRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final RecipeRepository recipeRepository;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final boolean shouldSeedDatabase;

    @Autowired
    public DatabaseSeeder(IngredientRepository ingredientRepository,
                          RecipeCategoryRepository categoryRepository,
                          RecipeInstructionRepository instructionRepository,
                          RecipeIngredientRepository recipeIngredientRepository,
                          RecipeRepository recipeRepository,
                          @Value("true") boolean shouldSeedDatabase) {
        this.ingredientRepository = ingredientRepository;
        this.shouldSeedDatabase = shouldSeedDatabase;
        this.categoryRepository = categoryRepository;
    this.instructionRepository= instructionRepository;
    this.recipeIngredientRepository = recipeIngredientRepository;
    this.recipeRepository = recipeRepository;
    }

    public void run(ApplicationArguments args) {
        if (!ingredientRepository.findAll().iterator().hasNext() && shouldSeedDatabase) {

            RecipeCategory category = new RecipeCategory("Pastries");
            categoryRepository.save(category);

            RecipeInstruction instruction = new RecipeInstruction("blend all and heat it up");
            instructionRepository.save(instruction);

            Recipe recipePancake = new Recipe("Pancakes",instruction);
            Recipe recipeFiffi = new Recipe("Fiffikaka",instruction);
            recipeRepository.saveAll(Arrays.asList(recipePancake,recipeFiffi));

            Collection<RecipeCategory> categories = Collections.singletonList(category);
            recipePancake.setCategories(categories);

            List<Ingredient> ingredientsToSave = new ArrayList<>();
            List<RecipeIngredient> recipeIngredientsToSave = new ArrayList<>();


            Ingredient milk = new Ingredient("milk");
            ingredientsToSave.add(milk);
            recipeIngredientsToSave.add(new RecipeIngredient(6,Measurement.DECILITER,milk,recipePancake));
            recipeIngredientsToSave.add(new RecipeIngredient(2,Measurement.DECILITER,milk,recipeFiffi));


            Ingredient egg = new Ingredient("egg");
            ingredientsToSave.add(egg);
            recipeIngredientsToSave.add(new RecipeIngredient(3,Measurement.PIECES,egg,recipePancake));
            recipeIngredientsToSave.add(new RecipeIngredient(3,Measurement.PIECES,egg,recipeFiffi));


            Ingredient wheat_flour = new Ingredient("wheat flour");
            ingredientsToSave.add(wheat_flour);
            recipeIngredientsToSave.add(new RecipeIngredient(2.5,Measurement.DECILITER,wheat_flour,recipePancake));
            recipeIngredientsToSave.add(new RecipeIngredient(6,Measurement.DECILITER,wheat_flour,recipeFiffi));


            Ingredient baking_powder = new Ingredient("baking powder");
            ingredientsToSave.add(baking_powder);
            recipeIngredientsToSave.add(new RecipeIngredient(15,Measurement.MILLILITER,baking_powder,recipeFiffi));

            Ingredient salt = new Ingredient("salt");
            ingredientsToSave.add(salt);
            recipeIngredientsToSave.add(new RecipeIngredient(5,Measurement.MILLILITER,salt,recipePancake));

            Ingredient sugar = new Ingredient("sugar");
            ingredientsToSave.add(sugar);
            recipeIngredientsToSave.add(new RecipeIngredient(4.5,Measurement.DECILITER,sugar,recipeFiffi));

            Ingredient butter = new Ingredient("butter");
            ingredientsToSave.add(butter);
            recipeIngredientsToSave.add(new RecipeIngredient(225,Measurement.GRAM,butter,recipeFiffi));

            Ingredient vanilla_powder = new Ingredient("vanilla powder");
            ingredientsToSave.add(vanilla_powder);
            recipeIngredientsToSave.add(new RecipeIngredient(22,Measurement.MILLILITER,vanilla_powder,recipeFiffi));

            Ingredient cocoa = new Ingredient("cocoa");
            ingredientsToSave.add(cocoa);
            recipeIngredientsToSave.add(new RecipeIngredient(1.5,Measurement.DECILITER,cocoa,recipeFiffi));

            logger.info("Inserting seed data into database.");
            ingredientRepository.saveAll(ingredientsToSave);
            recipeIngredientRepository.saveAll(recipeIngredientsToSave);
            logger.info("Seeding of ingredients database complete.");

        }
    }


    public Document downloadIngredientsSeed(File output) throws ParserConfigurationException, IOException,
            TransformerException, SAXException {
        String url = "http://www7.slv.se/apilivsmedel/LivsmedelService.svc/Livsmedel/Naringsvarde/20210422";

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new URL(url).openStream());
        doc.getDocumentElement().normalize();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        transformer.transform(new DOMSource(doc), new StreamResult(new FileWriter(output)));

        return doc;
    }

    private Document readDocument(File docFile) throws ParserConfigurationException, SAXException, IOException {
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(docFile);
        document.normalize();

        return document;
    }
}
