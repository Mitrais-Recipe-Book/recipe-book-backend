package com.cdcone.recipy.recipe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeReactionSummaryResponseDto {

    private Long recipeId;
    private String recipeTitle;
    private List<RecipeUserReactionResponseDto> reactionList;
    private RecipeReactionResponseDto userReaction;

}
