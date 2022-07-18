package com.cdcone.recipy.recipe.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TagResponseDto {
    private final int id;
    private final String name;
}
