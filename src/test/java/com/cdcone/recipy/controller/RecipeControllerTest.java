package com.cdcone.recipy.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;

import com.cdcone.recipy.dtoRequest.RecipeDtoAdd;
import com.cdcone.recipy.entity.RecipeEntity;
import com.cdcone.recipy.service.RecipeService;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RecipeControllerTest {
    private static final RecipeService RECIPE_SERVICE = mock(RecipeService.class);

    private final RecipeDtoAdd RECIPE_DTO_ADD = mock(RecipeDtoAdd.class);
    private final RecipeEntity RECIPE_ENTITY = mock(RecipeEntity.class);

    private static RecipeController recipeController;

    @BeforeAll
    static void init() {
        recipeController = new RecipeController(RECIPE_SERVICE);
    }

    @Test
    void add() {
        when(RECIPE_SERVICE.add(RECIPE_DTO_ADD))
                .thenReturn(Pair.of("success", RECIPE_ENTITY));

        assertEquals(HttpStatus.OK,
                recipeController.add(RECIPE_DTO_ADD).getStatusCode());
    }
}