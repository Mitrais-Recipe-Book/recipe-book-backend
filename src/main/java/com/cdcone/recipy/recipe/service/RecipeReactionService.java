package com.cdcone.recipy.recipe.service;

import com.cdcone.recipy.recipe.dto.response.RecipeUserReactionResponseDto;
import com.cdcone.recipy.recipe.entity.RecipeReactionEntity;
import com.cdcone.recipy.recipe.entity.RecipeReactionEntity.Reaction;
import com.cdcone.recipy.recipe.repository.RecipeReactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class RecipeReactionService {
    private final RecipeReactionRepository recipeReactionRepository;

    public List<RecipeReactionEntity> getReactionByMultipleRecipeId(
            List<Long> recipeId) {
        return recipeReactionRepository
                .findByReactionAndRecipeIdIn(Reaction.LIKED, recipeId);
    }

    public List<RecipeUserReactionResponseDto> getCountByReaction(long recipeId) {
        return recipeReactionRepository.getCountByReaction(recipeId);
    }

    public RecipeReactionEntity findByRecipeIdAndUserId(Long recipeId, Long userId) {
        return recipeReactionRepository.findByRecipeIdAndUserId(recipeId, userId);
    }

    public RecipeReactionEntity save(RecipeReactionEntity entity) {
        return recipeReactionRepository.save(entity);
    }

    public void delete(RecipeReactionEntity entity) {
        recipeReactionRepository.delete(entity);
    }

    public RecipeReactionEntity findByRecipeIdAndUserIdAndReaction(long recipeId, Long userId, Reaction reaction) {
        Optional<RecipeReactionEntity> result = recipeReactionRepository.findByRecipeIdAndUserIdAndReaction(recipeId,
                userId, reaction);

        if (result.isEmpty()) {
            throw new EntityNotFoundException("recipe reaction " + reaction);
        }

        return result.get();
    }
}
