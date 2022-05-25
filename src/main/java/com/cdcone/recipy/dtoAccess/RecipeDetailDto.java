package com.cdcone.recipy.dtoAccess;

import com.cdcone.recipy.entity.RecipeEntity;
import com.cdcone.recipy.entity.TagEntity;
import com.cdcone.recipy.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

@Getter
@AllArgsConstructor
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

    public static RecipeDetailDto toDto(RecipeEntity recipe) {
        UserEntity author = recipe.getUser();
        return new RecipeDetailDto(
                recipe.getId(),
                recipe.getTitle(),
                recipe.getOverview(),
                recipe.getDateCreated(),
                recipe.getIngredients(),
                recipe.getContent(),
                recipe.getVideoURL(),
                recipe.getViews(),
                Map.of(
                        "id", author.getId(),
                        "username", author.getUsername(),
                        "name", author.getFullName()
                ),
                recipe.getTags()
        );
    }
}
