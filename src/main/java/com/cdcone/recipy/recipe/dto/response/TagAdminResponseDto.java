package com.cdcone.recipy.recipe.dto.response;

import lombok.RequiredArgsConstructor;
import lombok.Getter;

@Getter
@RequiredArgsConstructor
public class TagAdminResponseDto {
    private final int id;
    private final String name;
    private final Integer views;
    private final long totalRecipe;
}
