package com.cdcone.recipy.dtoAccess;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecipeReactionDto {

    private String reaction;
    private long totalReaction;
}
