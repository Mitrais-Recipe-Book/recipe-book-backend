package com.cdcone.recipy.controller;


import java.util.Set;

import com.cdcone.recipy.dto.RecipeDtoAdd;
import com.cdcone.recipy.dto.RecipeDtoList;
import com.cdcone.recipy.dto.RecipeSearchDto;
import com.cdcone.recipy.response.CommonResponse;
import com.cdcone.recipy.service.RecipeService;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
        } catch (Exception e) {
            return new CommonResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return new CommonResponse(HttpStatus.OK, "SUCCESS");
    }

    @GetMapping("/list")
    public CommonResponse getPublishedRecipes(@RequestBody RecipeSearchDto dto) {
        try {
            Page<RecipeDtoList> result = recipeService.getPublishedRecipes(dto.getPage(), dto.getSize(), dto.getAuthor());
            return new CommonResponse(HttpStatus.OK, result);
        } catch (Exception e) {
            return new CommonResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/popular")
    public CommonResponse getPopularRecipes(@RequestBody int total){
        try{
            Set<RecipeDtoList> result = recipeService.getPopularRecipes(total);
            return new CommonResponse(HttpStatus.OK, result);
        } catch(Exception e){
            return new CommonResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
