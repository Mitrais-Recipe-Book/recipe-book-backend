package com.cdcone.recipy.dtoAccess;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RecipeFavoriteResponseDto {

    private Long recipeId;
    private Long userId;
    private LocalDateTime timestamp;
    private boolean favorited = false;

    public RecipeFavoriteResponseDto(Long recipeId, Long userId, LocalDateTime timestamp, boolean favorited) {
        this.recipeId = recipeId;
        this.userId = userId;
        this.timestamp = timestamp;
        this.favorited = favorited;
    }
}
