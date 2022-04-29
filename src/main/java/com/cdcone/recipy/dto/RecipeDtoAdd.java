package com.cdcone.recipy.dto;

import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecipeDtoAdd {
    private Long userId;
    @NotEmpty
    private String title;
    private String overview;
    private String ingredients;
    private String content;
    private String videoURL;
    private boolean isDraft;
    @Lob
    private Byte[] bannerImage;
}
