package com.cdcone.recipy.dto;

import javax.persistence.Lob;

import lombok.Data;

@Data
public class RecipeDtoList {
    private String recipeName;
    @Lob
    private Byte[] recipeImage;
    private String description;
    private int recipeViews;
    private String author;
    @Lob
    private Byte[] authorImage;
    private int authorFollower;


    public RecipeDtoList(String recipeName, Byte[] recipeImage, String description, int recipeViews, String author, Byte[] authorImage) {
        this.recipeName = recipeName;
        this.recipeImage = recipeImage;
        this.description = description;
        this.recipeViews = recipeViews;
        this.author = author;
        this.authorImage = authorImage;
        authorFollower = -1;
    }

}
