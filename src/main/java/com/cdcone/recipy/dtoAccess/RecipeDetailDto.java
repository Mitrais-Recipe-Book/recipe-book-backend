package com.cdcone.recipy.dtoAccess;

import com.cdcone.recipy.entity.RecipeEntity;
import com.cdcone.recipy.entity.TagEntity;
import com.cdcone.recipy.entity.UserEntity;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

@Getter
public class RecipeDetailDto {
    private Long id;
    private String title;
    private String overview;
    private LocalDate dateCreated;
    private String ingredients;
    private String content;
    private String videoURL;
    private int views;
    private Map<String, Object> author;
    private Set<TagEntity> tags;

    public RecipeDetailDto(RecipeEntity recipe) {
        UserEntity author = recipe.getUser();

        this.id = recipe.getId();
        this.title = recipe.getTitle();
        this.overview = recipe.getOverview();
        this.dateCreated = recipe.getDateCreated();
        this.ingredients = recipe.getIngredients();
        this.content = recipe.getContent();
        this.videoURL = recipe.getVideoURL();
        this.views = recipe.getViews();
        this.author = Map.of(
                "id", author.getId(),
                "username", author.getUsername(),
                "name", author.getFullName());
        this.tags = recipe.getTags();
    }
}
