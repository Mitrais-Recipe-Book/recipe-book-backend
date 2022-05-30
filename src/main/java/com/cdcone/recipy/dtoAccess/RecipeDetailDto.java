package com.cdcone.recipy.dtoAccess;

import com.cdcone.recipy.entity.RecipeEntity;
import com.cdcone.recipy.entity.TagEntity;
import com.cdcone.recipy.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

@Getter
public class RecipeDetailDto {
    Long id;
    String title;
    String overview;
    LocalDate dateCreated;
    String ingredients;
    String content;
    String videoUrl;
    int views;
    Map<String, Object> author;
    Set<TagEntity> tags;

    public RecipeDetailDto(RecipeEntity recipe) {
        UserEntity author = recipe.getUser();

        this.id = recipe.getId();
        this.title = recipe.getTitle();
        this.overview = recipe.getOverview();
        this.dateCreated = recipe.getDateCreated();
        this.ingredients = recipe.getIngredients();
        this.content = recipe.getContent();
        this.videoUrl = recipe.getVideoURL();
        this.views = recipe.getViews();
        this.author = Map.of(
                "id", author.getId(),
                "username", author.getUsername(),
                "name", author.getFullName());
        this.tags = recipe.getTags();
    }
}
