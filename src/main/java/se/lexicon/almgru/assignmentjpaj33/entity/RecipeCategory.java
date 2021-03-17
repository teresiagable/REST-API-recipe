package se.lexicon.almgru.assignmentjpaj33.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Entity
public class RecipeCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recipeCategoryId;

    @Column(unique = true)
    private String category;

    @ManyToMany(
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "recipe_recipe_category",
            joinColumns = @JoinColumn(name = "recipe_category_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    private Collection<Recipe> recipes;

    public RecipeCategory(int recipeCategoryId, String category, Collection<Recipe> recipes) {
        this.recipeCategoryId = recipeCategoryId;
        this.category = category;
        this.recipes = recipes;
    }

    public RecipeCategory(String category, Collection<Recipe> recipes) {
        this(0, category, recipes);
    }

    public RecipeCategory(String category) {
        this(0, category, new HashSet<>());
    }

    public RecipeCategory() { }

    public int getRecipeCategoryId() {
        return recipeCategoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Collection<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Collection<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeCategory that = (RecipeCategory) o;
        return recipeCategoryId == that.recipeCategoryId && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeCategoryId, category);
    }

    @Override
    public String toString() {
        return "RecipeCategory{" +
                "id=" + recipeCategoryId +
                ", category='" + category + '\'' +
                '}';
    }
}
