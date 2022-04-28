package com.cdcone.recipy.controller;

import com.cdcone.recipy.dto.RecipeDtoAdd;
import com.cdcone.recipy.response.BasicResponse;
import com.cdcone.recipy.services.RecipeService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RecipeControllerTest {
    @Autowired
    private RecipeController recipeController;

    @Test
    @Order(1)
    public void addData() {
        BasicResponse response = recipeController.add(new RecipeDtoAdd(
                "title",
                "overview",
                "ingredients",
                "content",
                "videoURL",
                true,
                null));

        Assertions.assertEquals(HttpStatus.OK, response.getStatus());
    }
}
