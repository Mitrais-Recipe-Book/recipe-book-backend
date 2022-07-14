package com.cdcone.recipy.recipe.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EditTagRequestDto {
    private int tagId;
    private String tagReplace;
} 
