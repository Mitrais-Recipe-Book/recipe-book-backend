package com.cdcone.recipy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecipeDtoAdd {
    private String title;
    private String overview;
    private String ingredients;
    private String content;
    private String videoURL;
    private boolean isDraft;
    private Byte[] bannerImage;
}
