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

    public RecipeFavoriteResponseDto(Long recipeId, Long userId, LocalDateTime timestamp) {
        this.recipeId = recipeId;
        this.userId = userId;
        this.timestamp = timestamp;
    }
}
