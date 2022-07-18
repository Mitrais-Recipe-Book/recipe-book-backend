package com.cdcone.recipy.recipe.dto.response;

import com.cdcone.recipy.recipe.entity.RecipeViewedEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class RecipeViewedResponseDto {

    private long recipeId;
    private long userId;
    private LocalDateTime viewedAt;

    public RecipeViewedResponseDto(RecipeViewedEntity entity) {
        this.recipeId = entity.getRecipe().getId();
        this.userId = entity.getUser().getId();
        this.viewedAt = entity.getTimestamp();
    }
}
