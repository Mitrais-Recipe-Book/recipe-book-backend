package com.cdcone.recipy.controller;

import java.util.Set;

import com.cdcone.recipy.dto.RecipeDtoAdd;
import com.cdcone.recipy.dto.RecipeDtoList;
import com.cdcone.recipy.dto.RecipeSearchDto;
import com.cdcone.recipy.entity.RecipeEntity;
import com.cdcone.recipy.response.CommonResponse;
import com.cdcone.recipy.service.RecipeService;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    @PostMapping("/add")
    public CommonResponse add(@RequestBody RecipeDtoAdd dto) {
        try {
            recipeService.add(dto);
            return new CommonResponse(HttpStatus.OK, "SUCCESS");
        } catch (DataIntegrityViolationException e) {
            return new CommonResponse(HttpStatus.BAD_REQUEST,e.getCause().toString());
        } catch (Exception e) {
            return new CommonResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getCause().toString());
        }
    }

    @GetMapping("/search")
    public CommonResponse getPublishedRecipes(RecipeSearchDto dto) {
        try {
            Page<RecipeDtoList> result = recipeService.getPublishedRecipes(dto);
            return new CommonResponse(HttpStatus.OK, result);
        } catch (Exception e) {
            return new CommonResponse(HttpStatus.BAD_REQUEST, e.getCause().toString());
        }
    }

    @GetMapping("image/{id}")
    public CommonResponse getRecipeImage(Long recipeId) {
        try {
            RecipeEntity entity = recipeService.getById(recipeId);
            CommonResponse response = new CommonResponse(HttpStatus.OK, entity.getBannerImage());
            response.setMessage("filename:\\profile-" + entity.getTitle());
            return response;
        } catch (Exception e) {
            return new CommonResponse(HttpStatus.NOT_FOUND,e.getCause().toString());
        }
    }

    @PutMapping("/addview")
    public CommonResponse addViewer(Long recipeId) {
        try {
            recipeService.addViewer(recipeId);
            return new CommonResponse(HttpStatus.OK, "SUCCESS");
        } catch (Exception e) {
            return new CommonResponse(HttpStatus.BAD_REQUEST, e.getCause().toString());
        }
    }

    @GetMapping("/popular")
    public CommonResponse getPopularRecipes(int limit) {
        try {
            Set<RecipeDtoList> result = recipeService.getPopularRecipes(limit);
            return new CommonResponse(HttpStatus.OK, result);
        } catch (Exception e) {
            return new CommonResponse(HttpStatus.BAD_REQUEST, e.getCause().toString());
        }
    }
}
