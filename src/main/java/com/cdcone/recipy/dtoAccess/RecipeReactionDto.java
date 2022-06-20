package com.cdcone.recipy.dtoAccess;

import lombok.Data;

@Data
public class RecipeReactionDto {

    private String reaction;
    private long totalReaction;

    public RecipeReactionDto(String reaction, long totalReaction) {
        this.reaction = reaction;
        this.totalReaction = totalReaction;
    }
}
