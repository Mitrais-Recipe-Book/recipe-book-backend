package com.cdcone.recipy.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.HashSet;
import java.util.Set;

import com.cdcone.recipy.dtoAccess.RecipeDtoList;
import com.cdcone.recipy.dtoRequest.RecipeDtoAdd;
import com.cdcone.recipy.dtoRequest.RecipeSearchDto;
import com.cdcone.recipy.entity.RecipeEntity;
import com.cdcone.recipy.repository.RecipeRepository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RecipeServiceTest {
    @Autowired
    private RecipeService recipeService;

    private static RecipeDtoAdd recipeDtoAdd;
    private static int initSize;

    @BeforeAll
    public static void setup() {
        init();
    }

    private static void init() {
        Set<Integer> tags = new HashSet<Integer>();
        tags.add(1);
        tags.add(2);

        recipeDtoAdd = new RecipeDtoAdd(1L,
                tags, "Es Dugan",
                "overview",
                "ingredients",
                "content",
                "URL",
                false);
    }

    @Test
    @Order(1)
    void add() {
        initSize = (int) recipeService.totalRecipes();
        recipeService.add(recipeDtoAdd);
        assertEquals(initSize + 1, recipeService.totalRecipes());
    }

    @Test
    @Order(2)
    void getPublishedRecipes() {
        RecipeSearchDto dto = new RecipeSearchDto("Es Dugan", "", new HashSet<>(), 0);
        Page<RecipeDtoList> result = recipeService.getPublishedRecipes(dto);
        
        assertEquals(recipeDtoAdd.getTitle(), result.getContent().get(0).getRecipeName());
    }
}
