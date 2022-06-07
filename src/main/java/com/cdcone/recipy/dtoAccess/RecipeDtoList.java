package com.cdcone.recipy.dtoAccess;

import java.beans.Transient;

import com.cdcone.recipy.entity.RecipeEntity;
import com.cdcone.recipy.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDtoList {
    private Long id;
    private String recipeName;
    private String description;
    private int recipeViews;
    private AuthorDto author;

    @JsonIgnore
    private UserEntity user;

    public RecipeDtoList(Long recipeId, String recipeName, String description, int recipeViews, UserEntity user) {
        this.id = recipeId;
        this.recipeName = recipeName;
        this.description = description;
        this.recipeViews = recipeViews;
        this.user = user;
    }

    public RecipeDtoList(Long recipeId, String recipeName, String description, int recipeViews) {
        this.id = recipeId;
        this.recipeName = recipeName;
        this.description = description;
        this.recipeViews = recipeViews;
    }
}
