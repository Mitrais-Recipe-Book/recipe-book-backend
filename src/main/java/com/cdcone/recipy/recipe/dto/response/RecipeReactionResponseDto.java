package com.cdcone.recipy.recipe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RecipeReactionResponseDto {

    private Long recipeId;
    private Long userId;
    private String reaction;
    private LocalDateTime timestamp;
}
