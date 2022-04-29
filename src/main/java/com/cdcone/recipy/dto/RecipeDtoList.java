package com.cdcone.recipy.dto;

import javax.persistence.Lob;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
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
}
