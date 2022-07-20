package com.cdcone.recipy.recipe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cdcone.recipy.recipe.dto.response.RecipeUserReactionResponseDto;
import com.cdcone.recipy.recipe.entity.RecipeReactionEntity;
import com.cdcone.recipy.recipe.entity.RecipeReactionEntity.Reaction;
import com.cdcone.recipy.recipe.repository.RecipeReactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecipeReactionService {

    private final RecipeReactionRepository recipeReactionRepository;

    public int getTotalRecipeLikeByUserId(Long id) {
        return recipeReactionRepository.getTotalRecipeLikeByUserId(id);
    }

    public List<RecipeUserReactionResponseDto> getCountByReaction(Long recipeId) {
        return recipeReactionRepository.getCountByReaction(recipeId);
    }

    public RecipeReactionEntity findByRecipeIdAndUserId(Long recipeId, Long userId) {
        return recipeReactionRepository.findByRecipeIdAndUserId(recipeId, userId);
    }

    public Optional<RecipeReactionEntity> findByRecipeIdAndUserIdAndReaction(long recipeId, Long userId,
            Reaction reaction) {
        return recipeReactionRepository.findByRecipeIdAndUserIdAndReaction(recipeId, userId, reaction);
    }

    public void delete(RecipeReactionEntity recipeReactionEntity) {
        recipeReactionRepository.delete(recipeReactionEntity);
    }
    
    public RecipeReactionEntity save(RecipeReactionEntity entity) {
        return recipeReactionRepository.save(entity);
    }
}
