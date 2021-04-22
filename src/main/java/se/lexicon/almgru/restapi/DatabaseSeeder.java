package se.lexicon.almgru.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import se.lexicon.almgru.restapi.data.IngredientRepository;
import se.lexicon.almgru.restapi.entity.Ingredient;

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
import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseSeeder implements ApplicationRunner {
    private final IngredientRepository ingredientRepository;

    @Autowired
    public DatabaseSeeder(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public void run(ApplicationArguments args) {
        if (!ingredientRepository.findAll().iterator().hasNext()) {
            File outputFile = Paths.get("seed-data.xml").toAbsolutePath().toFile();
            Document document = null;

            try {
                if (outputFile.exists()) {
                    document = readDocument(outputFile);
                } else {
                    document = downloadIngredientsSeed(outputFile);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }

            NodeList list = document.getElementsByTagName("Livsmedel");
            List<Ingredient> ingredientsToSave = new ArrayList<>();

            for (int ingredientIndex = 0; ingredientIndex < list.getLength(); ingredientIndex++) {
                Node node = list.item(ingredientIndex);

                if (node instanceof Element) {
                    String ingredient = ((Element)node).getElementsByTagName("Namn").item(0).getTextContent();
                    ingredientsToSave.add(new Ingredient(ingredient));
                }
            }

            ingredientRepository.saveAll(ingredientsToSave);
        }
    }

    public Document downloadIngredientsSeed(File output) throws ParserConfigurationException, IOException, TransformerException, SAXException {
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
