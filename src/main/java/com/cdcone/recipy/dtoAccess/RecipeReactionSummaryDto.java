package com.cdcone.recipy.dtoAccess;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeReactionSummaryDto {

    private Long recipeId;
    private String recipeTitle;
    private List<RecipeReactionDto> reactionList;
    private RecipeReactionResponseDto userReaction;

}
