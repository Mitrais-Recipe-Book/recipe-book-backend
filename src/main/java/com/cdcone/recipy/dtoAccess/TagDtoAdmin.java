package com.cdcone.recipy.dtoAccess;

import lombok.RequiredArgsConstructor;
import lombok.Getter;

@Getter
@RequiredArgsConstructor
public class TagDtoAdmin {
    private final int id;
    private final String name;
    private final Integer views;
    private final long totalRecipe;
}
