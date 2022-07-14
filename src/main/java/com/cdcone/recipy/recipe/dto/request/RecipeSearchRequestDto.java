package com.cdcone.recipy.recipe.dto.request;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecipeSearchRequestDto {
    private String title;
    private String author;
    private Set<Integer> tagId;    
    private int page = 0;
}
