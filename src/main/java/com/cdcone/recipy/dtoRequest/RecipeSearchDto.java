package com.cdcone.recipy.dtoRequest;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecipeSearchDto {
    private String title;
    private String author;
    private Set<Integer> tagId;    
    private int page = 0;
}
