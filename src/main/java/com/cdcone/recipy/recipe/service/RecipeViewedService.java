package com.cdcone.recipy.recipe.service;

import com.cdcone.recipy.recipe.entity.RecipeEntity;
import com.cdcone.recipy.recipe.entity.RecipeViewedEntity;
import com.cdcone.recipy.recipe.repository.RecipeViewedRepository;
import com.cdcone.recipy.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeViewedService {

    private static final String TIMESTAMP_COLUMN = "timestamp";

    private final RecipeViewedRepository recipeViewedRepository;

    public Page<RecipeViewedEntity> findByUserId(Long userId, int page, int size) {
        return recipeViewedRepository.findByUserId(userId,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, TIMESTAMP_COLUMN)));
    }

    public List<RecipeViewedEntity> findByUserId(Long userId) {
        return recipeViewedRepository.findByUserId(userId, Sort.by(Sort.Direction.DESC, TIMESTAMP_COLUMN));
    }

    public RecipeViewedEntity save(RecipeEntity recipe, UserEntity user) {
        return recipeViewedRepository.save(new RecipeViewedEntity(user, recipe, LocalDateTime.now()));
    };

}
