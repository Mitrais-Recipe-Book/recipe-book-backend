package com.cdcone.recipy.recipe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecipeUserReactionResponseDto {

    private String reaction;
    private long totalReaction;
}
