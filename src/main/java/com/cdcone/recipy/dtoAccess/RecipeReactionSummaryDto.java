package com.cdcone.recipy.dtoAccess;

import com.cdcone.recipy.entity.RecipeReactionEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RecipeReactionSummaryDto {

    private Long recipeId;
    private String recipeTitle;
    private List<RecipeReactionDto> reactionList;
    private RecipeReactionResponseDto userReaction;

    public RecipeReactionSummaryDto(Long recipeId, String recipeTitle, List<RecipeReactionDto> reactionList, RecipeReactionResponseDto userReaction) {
        this.recipeId = recipeId;
        this.recipeTitle = recipeTitle;
        this.reactionList = reactionList;
        this.userReaction = userReaction;
    }
}
