package com.cdcone.recipy.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RecipeServiceTest2 {
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

    @Test()
    @Order(2)
    void failAddAndTotalRecipes() {
        int x = 0;
        try {
            recipeService.add(recipeDtoAdd);
        } catch (DataIntegrityViolationException e) {
            x = 1;
        }
        assertEquals(1, x);
        assertNotEquals(initSize + 2, recipeService.totalRecipes());
    }

    @Test
    @Order(3)
    void getPublishedRecipes() {
        RecipeSearchDto dto = new RecipeSearchDto("Dugan", "", new HashSet<>(), 0);
        Page<RecipeDtoList> result = recipeService.getPublishedRecipes(dto);

        assertEquals(recipeDtoAdd.getTitle(), result.getContent().get(0).getRecipeName());
    }

    @Test
    @Order(4)
    void failGetPublishedRecipes() {
        RecipeSearchDto dto = new RecipeSearchDto("Java", "", new HashSet<>(), 0);
        Page<RecipeDtoList> result = recipeService.getPublishedRecipes(dto);

        assertEquals(0, result.getContent().size());
    }

    @Test
    @Order(5)
    void addViewerAndPopularRecipe() {
        recipeService.addViewer(entityTest.getId());
        recipeService.addViewer(entityTest.getId());
        RecipeDtoList entity = (RecipeDtoList) new ArrayList<>(recipeService.getPopularRecipes(1)).get(0);

        assertEquals(recipeDtoAdd.getTitle(), entity.getRecipeName());
    }

    @Test
    @Order(6)
    void failAddViewer() {
        int x = 0;
        try {
            recipeService.addViewer(50L);
        } catch (NoSuchElementException e) {
            x = 1;
        }
        assertEquals(1, x);
    }

    @Test
    @Order(7)
    void getDiscoverRecipes() {
        RecipeDtoList entity = (RecipeDtoList) new ArrayList<>(recipeService.getDiscoverRecipes(1)).get(0);

        assertEquals(recipeDtoAdd.getTitle(), entity.getRecipeName());
    }

    @Test
    @Order(9)
    void getById() {
        RecipeEntity entity = recipeService.getById(1L);
        assertEquals(1L, entity.getId());
    }

    @Test
    @Order(10)
    void failGetById() {
        int x = 0;
        try {
            RecipeEntity entity = recipeService.getById(100L);
            assertEquals(1L, entity.getId());
        } catch (NullPointerException e) {
            x = 1;
        }
        assertEquals(1, x);
    }

    @Test
    @Order(11)
    void getByUsername() {
        UserRecipeDto recipe = recipeService.getByUsername("user1", 0).getData().get(0);
        assertEquals("Es teh", recipe.getTitle());
    }

    @Test
    @Order(12)
    void failGetByUsername() {
        int x = 0;
        try {
            UserRecipeDto recipe = recipeService.getByUsername("user10", 0).getData().get(0);
            assertEquals(1, recipe.getId());
        } catch (IndexOutOfBoundsException e) {
            x = 1;
        }
        assertEquals(1, x);
    }

    @Test
    @Order(13)
    @Disabled
    // Any idea to test multipart file?
    void saveRecipePhoto() {
        MultipartFile photo = mock(MultipartFile.class);
        recipeService.saveRecipePhoto(photo, 1L);
        assertEquals(photo, recipeService.getById(1L).getBannerImage());
    }

    @Test
    @Order(14)
    @Disabled
    // Any idea to test multipart file?
    void failSaveRecipePhoto() {
        MultipartFile photo = mock(MultipartFile.class);
        recipeService.saveRecipePhoto(photo, 1L);
        assertEquals(photo, recipeService.getById(1L).getBannerImage());
    }

    @Test
    @Order(100)
    void deleteRecipe() {
        recipeService.deleteRecipe(entityTest.getId());
        assertEquals(initSize, recipeService.totalRecipes());
    }

    @Test
    @Order(101)
    void failDeleteRecipe() {
        recipeService.deleteRecipe(entityTest.getId() + 1);
        assertNotEquals(initSize - 1 , recipeService.totalRecipes());
    }
}
