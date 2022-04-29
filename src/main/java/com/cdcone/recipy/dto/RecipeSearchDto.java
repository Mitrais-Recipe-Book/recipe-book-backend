package com.cdcone.recipy.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RecipeSearchDto {
    private int size = 15;
    private int page = 0;
    private String author = "";
    private Set<String> tags;
}
