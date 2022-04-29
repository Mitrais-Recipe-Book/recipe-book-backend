package com.cdcone.recipy.service.recipe;

import java.util.List;

import com.cdcone.recipy.dto.RecipeDtoList;


public class RecipeListService extends RecipeService {
    public List<RecipeDtoList> getPublishedRecipe(int pageSize, int offset){
        RecipeDtoList dto = new RecipeDtoList(
            null, 
            null, 
            null, 
            0, 
            null, 
            null, 
            -1
        );

        return null;
    }    
}
