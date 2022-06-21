package com.cdcone.recipy.dtoAccess;

import com.cdcone.recipy.entity.RecipeEntity;

public class UserRecipeDetailDto extends RecipeDetailDto {

    private boolean isDraft;
    
    public UserRecipeDetailDto(RecipeEntity recipe) {
        super(recipe);
        isDraft = recipe.isDraft();
    }

    public boolean isDraft() {
        return isDraft;
    }
}
