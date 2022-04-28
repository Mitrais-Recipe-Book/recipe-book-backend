package com.cdcone.recipy.service;

import com.cdcone.recipy.dto.RecipeDtoAdd;
import com.cdcone.recipy.services.RecipeService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

@SpringBootTest()
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RecipeServiceTest {
    @Autowired
    private RecipeService recipeService;
    
    @Test
    @Order(1)
    public void addData() {
        long prevSize = recipeService.size();

        recipeService.add(new RecipeDtoAdd(
                "title",
                "overview",
                "ingredients",
                "content",
                "videoURL",
                true,
                null));

        Assertions.assertEquals(prevSize + 1, recipeService.size());
    }

    @Test
    @Order(2)
    public void cantAddData(){ 
        long prevSize = recipeService.size();

        try{
        recipeService.add(new RecipeDtoAdd(
                "title",
                "overview",
                "ingredients",
                "content",
                "videoURL",
                true,
                null));
        } catch(DataIntegrityViolationException e){
            e.printStackTrace();
        }        
        
        Assertions.assertEquals(prevSize, recipeService.size());
    }
}
