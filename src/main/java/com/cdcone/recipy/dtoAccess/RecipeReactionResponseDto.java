package com.cdcone.recipy.dtoAccess;

import lombok.Data;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;

@Data
public class RecipeReactionResponseDto {

    private Long recipeId;
    private Long userId;
    private String reaction;
    private LocalDateTime timestamp;

    public RecipeReactionResponseDto(Long recipeId, Long userId, String reaction, LocalDateTime timestamp) {
        this.recipeId = recipeId;
        this.userId = userId;
        this.reaction = reaction;
        this.timestamp = timestamp;
    }
}
