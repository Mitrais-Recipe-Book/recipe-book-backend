package com.cdcone.recipy.recipe.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cdcone.recipy.recipe.entity.RecipeFavoriteEntity;
import com.cdcone.recipy.recipe.repository.RecipeFavoriteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecipeFavoriteService {

    private final RecipeFavoriteRepository recipeFavoriteRepository;

    public RecipeFavoriteEntity findByRecipeIdAndUserId(long recipeId, Long userId) {
        Optional<RecipeFavoriteEntity> result = recipeFavoriteRepository
                .findByRecipeIdAndUserId(recipeId, userId);

        if (result.isEmpty()) {
            throw new EntityNotFoundException("recipe favorite of recipeId " + recipeId + " and userId " + userId);
        }

        return result.get();
    }

    public void delete(RecipeFavoriteEntity recipeFavorite) {
        recipeFavoriteRepository.delete(recipeFavorite);
    }

    public Page<RecipeFavoriteEntity> findByUserId(Long userId, Pageable paging) {
        return recipeFavoriteRepository.findByUserId(userId, paging);
    }

    public List<RecipeFavoriteEntity> findByUserId(Long userId) {
        return recipeFavoriteRepository.findByUserId(userId);
    }

    public RecipeFavoriteEntity save(RecipeFavoriteEntity entity) {
        return recipeFavoriteRepository.save(entity);
    }
}
