package com.cdcone.recipy.services;

import java.time.LocalDate;

import com.cdcone.recipy.dto.RecipeDtoAdd;
import com.cdcone.recipy.entity.RecipeEntity;
import com.cdcone.recipy.repository.RecipeRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeEntity add(RecipeDtoAdd dto){
        RecipeEntity entity = new RecipeEntity();

        entity.setTitle(dto.getTitle());
        entity.setOverview(dto.getOverview());
        entity.setDateCreated(LocalDate.now());
        entity.setIngredients(dto.getIngredients());
        entity.setContent(dto.getContent());
        entity.setVideoURL(dto.getVideoURL());
        entity.setViews(0);
        entity.setDraft(dto.isDraft());
        entity.setBannerImage(dto.getBannerImage());

        return recipeRepository.save(entity);
    }
}
