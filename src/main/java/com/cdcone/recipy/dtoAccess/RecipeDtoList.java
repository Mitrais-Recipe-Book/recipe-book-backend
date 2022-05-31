package com.cdcone.recipy.dtoAccess;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecipeDtoList {
    private Long id;
    private String recipeName;
    private String description;
    private int recipeViews;
    private String author;
    private Long authorFollower;

    public RecipeDtoList(Long recipeId, String recipeName, String description, int recipeViews, String author) {
        this.id = recipeId;
        this.recipeName = recipeName;
        this.description = description;
        this.recipeViews = recipeViews;
        this.author = author;
    }
}
