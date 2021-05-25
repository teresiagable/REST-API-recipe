package se.lexicon.almgru.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.lexicon.almgru.restapi.data.RecipeCategoryRepository;
import se.lexicon.almgru.restapi.dto.RecipeCategoryDTO;
import se.lexicon.almgru.restapi.service.RecipeCategoryConverter;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@CrossOrigin
public class CategoryController {
    private final RecipeCategoryRepository repository;
    private final RecipeCategoryConverter converter;

    @Autowired
    public CategoryController(RecipeCategoryRepository repository, RecipeCategoryConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @GetMapping("/api/categories")
    public ResponseEntity<Collection<RecipeCategoryDTO>> getCategories() {
        return ResponseEntity.ok(StreamSupport
                .stream(repository.findAll().spliterator(), false)
                .map(converter::recipeCategoryToDTO)
                .collect(Collectors.toList())
        );
    }
}