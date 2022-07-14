package com.cdcone.recipy.recipe.dto.response;

import com.cdcone.recipy.user.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeListResponseDto {
    private Long id;
    private String recipeName;
    private String description;
    private int recipeViews;
    private AuthorResponseDto author;

    @JsonIgnore
    private UserEntity user;

    public RecipeListResponseDto(Long recipeId, String recipeName, String description, int recipeViews, UserEntity user) {
        this.id = recipeId;
        this.recipeName = recipeName;
        this.description = description;
        this.recipeViews = recipeViews;
        this.user = user;
    }

    public RecipeListResponseDto(Long recipeId, String recipeName, String description, int recipeViews) {
        this.id = recipeId;
        this.recipeName = recipeName;
        this.description = description;
        this.recipeViews = recipeViews;
    }
}
