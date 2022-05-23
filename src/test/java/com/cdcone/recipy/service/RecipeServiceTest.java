package com.cdcone.recipy.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.cdcone.recipy.dtoAccess.RecipeDtoList;
import com.cdcone.recipy.dtoAccess.UserRecipeDto;
import com.cdcone.recipy.dtoRequest.RecipeDtoAdd;
import com.cdcone.recipy.dtoRequest.RecipeSearchDto;
import com.cdcone.recipy.entity.RecipeEntity;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RecipeServiceTest {
    @Autowired
    private RecipeService recipeService;

    private static RecipeDtoAdd recipeDtoAdd;
    private static int initSize;
    private static RecipeEntity entityTest;

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
    void addAndTotalRecipes() {
        initSize = (int) recipeService.totalRecipes();
        entityTest = recipeService.add(recipeDtoAdd);
        assertEquals(initSize + 1, recipeService.totalRecipes());
    }

    @Test
    @Order(2)
    void getPublishedRecipes() {
        RecipeSearchDto dto = new RecipeSearchDto("Dugan", "", new HashSet<>(), 0);
        Page<RecipeDtoList> result = recipeService.getPublishedRecipes(dto);

        assertEquals(recipeDtoAdd.getTitle(), result.getContent().get(0).getRecipeName());
    }

    @Test
    @Order(3)
    void addViewerAndPopularRecipe() {
        recipeService.addViewer(entityTest.getId());
        recipeService.addViewer(entityTest.getId());
        RecipeDtoList entity = (RecipeDtoList) new ArrayList<>(recipeService.getPopularRecipes(1)).get(0);

        assertEquals(recipeDtoAdd.getTitle(), entity.getRecipeName());
    }

    @Test
    @Order(4)
    void getDiscoverRecipes() {
        RecipeDtoList entity = (RecipeDtoList) new ArrayList<>(recipeService.getDiscoverRecipes(1)).get(0);

        assertEquals(recipeDtoAdd.getTitle(), entity.getRecipeName());
    }

    @Test
    @Order(5)
    void getById() {
        RecipeEntity entity = recipeService.getById(1L);
        assertEquals(1L, entity.getId());
    }

    @Test
    @Order(6)
    void getByUsername() {
        UserRecipeDto recipe = recipeService.getByUsername("user1", 0).getData().get(0);
        assertEquals("Es teh", recipe.getTitle());
    }

    @Test
    @Order(7)
    @Disabled
    // Any idea to test multipart file?
    void saveRecipePhoto() {
        MultipartFile photo = mock(MultipartFile.class);
        recipeService.saveRecipePhoto(photo, 1L);
        assertEquals(photo, recipeService.getById(1L).getBannerImage());
    }

    @Test
    @Order(100)
    void deleteRecipe() {
        recipeService.deleteRecipe(recipeService.totalRecipes());
        assertEquals(initSize, recipeService.totalRecipes());
    }
}
