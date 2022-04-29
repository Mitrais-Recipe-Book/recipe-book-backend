package com.cdcone.recipy.service;

import java.time.LocalDate;
import java.util.List;

import com.cdcone.recipy.dto.RecipeDtoAdd;
import com.cdcone.recipy.dto.RecipeDtoList;
import com.cdcone.recipy.entity.RecipeEntity;
import com.cdcone.recipy.repository.RecipeRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class RecipeService {    
    private final RecipeRepository recipeRepository;

    private final UserService userService;

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

    public Page<RecipeDtoList> getPublishedRecipes(int page, int size, String filterAuthor){
        Pageable pageable = PageRequest.of(page, size, Sort.by("views").descending());
        return recipeRepository.getPublishedRecipes(filterAuthor, pageable);
    }    

    public long totalRecipes() {
        return recipeRepository.count();
    }
}
