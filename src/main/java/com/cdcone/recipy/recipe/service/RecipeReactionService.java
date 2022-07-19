package com.cdcone.recipy.recipe.service;

import com.cdcone.recipy.recipe.entity.RecipeReactionEntity;
import com.cdcone.recipy.recipe.entity.RecipeReactionEntity.Reaction;
import com.cdcone.recipy.recipe.repository.RecipeReactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeReactionService {
    private final RecipeReactionRepository recipeReactionRepository;

    public List<RecipeReactionEntity> getReactionByMultipleRecipeId(
            List<Long> recipeId) {
        return recipeReactionRepository
                .findByReactionAndRecipeIdIn(Reaction.LIKED, recipeId);
    }
}
