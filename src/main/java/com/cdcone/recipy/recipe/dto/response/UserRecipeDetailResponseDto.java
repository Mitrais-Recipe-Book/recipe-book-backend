package com.cdcone.recipy.recipe.dto.response;

import com.cdcone.recipy.recipe.entity.RecipeEntity;

public class UserRecipeDetailResponseDto extends RecipeDetailResponseDto {

    private boolean isDraft;
    
    public UserRecipeDetailResponseDto(RecipeEntity recipe) {
        super(recipe);
        isDraft = recipe.isDraft();
    }

    public boolean isDraft() {
        return isDraft;
    }
}
