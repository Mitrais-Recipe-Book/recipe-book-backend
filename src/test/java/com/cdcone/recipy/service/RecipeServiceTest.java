package com.cdcone.recipy.service;

import java.util.NoSuchElementException;

import com.cdcone.recipy.dto.RecipeDtoAdd;
import com.cdcone.recipy.dto.RecipeDtoList;
import com.cdcone.recipy.dto.RecipeSearchDto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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

    private static RecipeDtoAdd recipeDtoAdd;
    private static int initSize;

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
        initSize = (int) recipeService.totalRecipes();
        recipeService.add(recipeDtoAdd);
        Assertions.assertEquals(initSize + 1, recipeService.totalRecipes());
    }

    @Test
    @Order(2)
    public void cantAddDuplicateData() {
        try {
            recipeService.add(recipeDtoAdd);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
        }

        Assertions.assertEquals(initSize + 1, recipeService.totalRecipes());
    }

    @Test
    @Order(3)
    public void getNewlyPublishedRecipes() {
        RecipeDtoList result = recipeService.getPublishedRecipes(new RecipeSearchDto("", "", null, 0))
                .getContent().get((int) recipeService.totalRecipes() - 1);

        Assertions.assertEquals(initSize + 1, recipeService.totalRecipes());
        Assertions.assertEquals(recipeDtoAdd.getTitle(), result.getRecipeName());
        Assertions.assertEquals(recipeDtoAdd.getOverview(), result.getDescription());
    }

    @Test
    @Order(4)
    public void addView() {
        recipeService.addView(2L);

        RecipeDtoList result = recipeService.getPublishedRecipes(new RecipeSearchDto("", "", null, 0))
                .getContent().get((int) recipeService.totalRecipes() - 1);

        Assertions.assertEquals(1, result.getRecipeViews());
    }

    @Test
    @Order(5)
    public void cantAddView() {
        try {
            recipeService.addView(3L);
        } catch (NoSuchElementException e) {
            Assertions.assertTrue(e.getMessage().contains("No value"));
        }
    }

    @Test
    @Order(6)
    public void getPopularRecipe(){
        recipeService.addView(2L);
        RecipeDtoList result = recipeService.getPopularRecipes(1).stream().findAny().get();

        Assertions.assertEquals(recipeDtoAdd.getTitle(), result.getRecipeName());
    }
}
