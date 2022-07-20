package com.cdcone.recipy.service;

import com.cdcone.recipy.recipe.entity.RecipeReactionEntity;
import com.cdcone.recipy.recipe.repository.RecipeReactionRepository;
import com.cdcone.recipy.recipe.service.RecipeReactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class RecipeReactionServiceTest {

    private final RecipeReactionRepository recipeReactionRepository =
            mock(RecipeReactionRepository.class);
    private RecipeReactionService recipeReactionService;

    @BeforeEach
    void setUp() {
        recipeReactionService = new RecipeReactionService(recipeReactionRepository);
    }

    @Test
    void getReactionByMultipleRecipeId_willReturnListOfRecipeReaction_whenAvailable() {
        List<Long> reactionId = List.of(1L, 2L);
        List<RecipeReactionEntity> recipeReactionEntities =
                List.of(mock(RecipeReactionEntity.class), mock(RecipeReactionEntity.class));
        when(recipeReactionRepository.findByReactionAndRecipeIdIn(RecipeReactionEntity.Reaction.LIKED, reactionId))
                .thenReturn(recipeReactionEntities);
        List<RecipeReactionEntity> result = recipeReactionService
                .getReactionByMultipleRecipeId(reactionId);

        assertEquals(2, result.size());
    }
}
