package com.cdcone.recipy.dto;


import lombok.Data;

@Data
public class RecipeDtoList {
    private String recipeName;
    private String description;
    private int recipeViews;
    private String author;
    private int authorFollower;


    public RecipeDtoList(String recipeName, String description, int recipeViews, String author) {
        this.recipeName = recipeName;
        this.description = description;
        this.recipeViews = recipeViews;
        this.author = author;
        authorFollower = -1;
    }

}
