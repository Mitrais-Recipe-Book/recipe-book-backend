package com.cdcone.recipy.service.recipe;

import java.time.LocalDate;

import com.cdcone.recipy.dto.RecipeDtoAdd;
import com.cdcone.recipy.entity.RecipeEntity;
import com.cdcone.recipy.repository.RecipeRepository;
import com.cdcone.recipy.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserService userService;

    public void add(RecipeDtoAdd dto) {
        RecipeEntity recipe = new RecipeEntity(
                userService.getById(dto.getUserId()),
                dto.getTitle(),
                dto.getOverview(),
                LocalDate.now(),
                dto.getIngredients(),
                dto.getContent(),
                dto.getVideoURL(),
                0,
                dto.isDraft(),
                dto.getBannerImage());
        recipeRepository.save(recipe);
    }

    public long totalRecipes() {
        return recipeRepository.count();
    }
}
