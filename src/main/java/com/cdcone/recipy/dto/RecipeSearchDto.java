package com.cdcone.recipy.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RecipeSearchDto {
    private String search;
    private String author;
    private Set<String> tags;    
    private int page = 0;
}
