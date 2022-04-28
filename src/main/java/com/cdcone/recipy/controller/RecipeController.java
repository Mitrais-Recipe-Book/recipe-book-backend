package com.cdcone.recipy.controller;

import com.cdcone.recipy.dto.RecipeDtoAdd;
import com.cdcone.recipy.services.RecipeService;

import org.springframework.web.bind.annotation.CrossOrigin;
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
    public void add(@RequestBody RecipeDtoAdd dto){
        try{
            recipeService.add(dto);
        } catch(Exception e){
            System.out.println("ERROR: " + e);
        }
    }
}
