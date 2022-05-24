package com.cdcone.recipy.dtoAccess;


import lombok.Data;

@Data
public class RecipeDtoList {
    private Long id;
    private String recipeName;
    private String description;
    private int recipeViews;
    private String author;
    private int authorFollower;


    public RecipeDtoList(Long id, String recipeName, String description, int recipeViews, String author) {
        this.id = id;
        this.recipeName = recipeName;
        this.description = description;
        this.recipeViews = recipeViews;
        this.author = author;
        authorFollower = -1;
    }

}
