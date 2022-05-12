package com.cdcone.recipy.controller;

import java.util.HashSet;
import java.util.Set;

import com.cdcone.recipy.dtoAccess.RecipeDtoList;
import com.cdcone.recipy.dtoRequest.RecipeDtoAdd;
import com.cdcone.recipy.dtoRequest.RecipeSearchDto;
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
import org.springframework.http.ResponseEntity;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RecipeControllerTest {
    @Autowired
    private RecipeController recipeController;

    private static RecipeDtoAdd recipeDtoAdd;

    @BeforeAll
    public static void init() {
        Set<Integer> tags = new HashSet<Integer>();
        tags.add(1);
        tags.add(2);
        recipeDtoAdd = new RecipeDtoAdd(
                1L,
                tags,
                "title",
                "overview",
                "ingredients",
                "content",
                "videoURL",
                true);
    }

    @Test
    @Order(1)
    public void addData() {
        ResponseEntity<CommonResponse> response = recipeController.add(recipeDtoAdd);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(2)
    public void cantAddData() {
        ResponseEntity<CommonResponse> response = recipeController.add(recipeDtoAdd);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(3)
    public void getNewlyPublishedRecipes() {
        Page<RecipeDtoList> result = (Page<RecipeDtoList>) recipeController
                .getPublishedRecipes(new RecipeSearchDto("", "", null, 0)).getBody().getPayload();
                
        Assertions.assertEquals(2, result.getContent().size());
    }
}