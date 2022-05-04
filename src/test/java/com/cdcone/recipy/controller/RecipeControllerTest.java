package com.cdcone.recipy.controller;

import com.cdcone.recipy.dto.RecipeDtoAdd;
import com.cdcone.recipy.dto.RecipeDtoList;
import com.cdcone.recipy.dto.RecipeSearchDto;
import com.cdcone.recipy.response.CommonResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RecipeControllerTest {
    @Autowired
    private RecipeController recipeController;

    private static RecipeDtoAdd recipeDtoAdd;

    @BeforeAll
    public static void init() {
        recipeDtoAdd = new RecipeDtoAdd(
                1L,
                "title",
                "overview",
                "ingredients",
                "content",
                "videoURL",
                true,
                null);
    }

    @Test
    @Order(1)
    public void addData() {
        CommonResponse response = recipeController.add(recipeDtoAdd);
        Assertions.assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    @Order(2)
    public void cantAddData() {
        CommonResponse response = recipeController.add(recipeDtoAdd);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
    }

    @Test
    @Order(3)
    public void getNewlyPublishedRecipes() {
        Page<RecipeDtoList> result = (Page<RecipeDtoList>) recipeController
                .getPublishedRecipes(new RecipeSearchDto("", "", null, 0)).getPayload();
                
        Assertions.assertEquals(2, result.getContent().size());
    }
}