package com.cdcone.recipy.recipe.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecipeViewedRequestDto {

    private String username;
    private Long recipeId;
}
