package com.cdcone.recipy.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import com.cdcone.recipy.dtoAccess.RecipeDtoList;
import com.cdcone.recipy.dtoRequest.RecipeDtoAdd;
import com.cdcone.recipy.dtoRequest.RecipeSearchDto;
import com.cdcone.recipy.entity.RecipeEntity;
import com.cdcone.recipy.service.RecipeService;
import com.cdcone.recipy.util.ImageUtil;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RecipeControllerTest {
    private static final RecipeService RECIPE_SERVICE = mock(RecipeService.class);

    private final RecipeDtoAdd RECIPE_DTO_ADD = mock(RecipeDtoAdd.class);
    private final RecipeSearchDto RECIPE_SEARCH_DTO = mock(RecipeSearchDto.class);

    private final RecipeEntity RECIPE_ENTITY = mock(RecipeEntity.class);

    private static RecipeController recipeController;
    private static byte[] RANDOM_IMAGE;

    @BeforeAll
    static void init() throws IOException {
        recipeController = new RecipeController(RECIPE_SERVICE);
        RANDOM_IMAGE = ImageUtil.randomImage();
    }

    @Test
    void add() {
        when(RECIPE_SERVICE.add(RECIPE_DTO_ADD))
                .thenReturn(Pair.of("success", RECIPE_ENTITY));

        assertEquals(HttpStatus.OK,
                recipeController.add(RECIPE_DTO_ADD).getStatusCode());
    }

    @Test
    void failAdd() {
        when(RECIPE_SERVICE.add(RECIPE_DTO_ADD))
                .thenReturn(Pair.of("failed", RECIPE_ENTITY));

        assertNotEquals(HttpStatus.OK,
                recipeController.add(RECIPE_DTO_ADD).getStatusCode());
    }

    @Test
    void edit() {
        when(RECIPE_SERVICE.edit(1L, RECIPE_DTO_ADD))
                .thenReturn("success");

        assertEquals(HttpStatus.OK,
                recipeController.edit(1L, RECIPE_DTO_ADD).getStatusCode());
    }

    @Test
    void failEdit() {
        when(RECIPE_SERVICE.edit(1L, RECIPE_DTO_ADD))
                .thenReturn("failed");

        assertNotEquals(HttpStatus.OK,
                recipeController.edit(1L, RECIPE_DTO_ADD).getStatusCode());
    }

    @Test
    @SuppressWarnings("unchecked")
    void getPublishedRecipes() {
        Page<RecipeDtoList> mockPage = (Page<RecipeDtoList>) mock(Page.class);

        when(RECIPE_SERVICE.getPublishedRecipes(RECIPE_SEARCH_DTO))
                .thenReturn(Pair.of("success", mockPage));

        assertEquals(HttpStatus.OK,
                recipeController.getPublishedRecipes(RECIPE_SEARCH_DTO).getStatusCode());
    }

    @Test
    @SuppressWarnings("unchecked")
    void failGetPublishedRecipes() {
        Page<RecipeDtoList> mockPage = (Page<RecipeDtoList>) mock(Page.class);

        when(RECIPE_SERVICE.getPublishedRecipes(RECIPE_SEARCH_DTO))
                .thenReturn(Pair.of("failed", mockPage));

        assertNotEquals(HttpStatus.OK,
                recipeController.getPublishedRecipes(RECIPE_SEARCH_DTO).getStatusCode());
    }

    @Test
    void getRecipeImage() {
        when(RECIPE_SERVICE.getRecipeImage(1L))
                .thenReturn(Pair.of("success", RECIPE_ENTITY));
        when(RECIPE_ENTITY.getBannerImage())
                .thenReturn(RANDOM_IMAGE);
        when(RECIPE_ENTITY.getBannerImageType()).thenReturn("image/png");

        assertEquals(HttpStatus.OK,
                recipeController.getRecipeImage(1L).getStatusCode());
    }

    @Test
    void failGetRecipeImage() {
        when(RECIPE_SERVICE.getRecipeImage(1L))
                .thenReturn(Pair.of("failed", RECIPE_ENTITY));

        assertNotEquals(HttpStatus.OK,
                recipeController.getRecipeImage(1L).getStatusCode());
    }

    @Test
    void saveRecipePhoto() {
        MultipartFile mockImage = mock(MultipartFile.class);
        when(RECIPE_SERVICE.saveRecipePhoto(mockImage, 1L))
                .thenReturn("success");

        assertEquals(HttpStatus.OK,
                recipeController.saveRecipePhoto(1L, mockImage).getStatusCode());
    }

    @Test
    void failedSaveRecipePhoto() {
        MultipartFile mockImage = mock(MultipartFile.class);
        when(RECIPE_SERVICE.saveRecipePhoto(mockImage, 1L))
                .thenReturn("failed");

        assertNotEquals(HttpStatus.OK,
                recipeController.saveRecipePhoto(1L, mockImage).getStatusCode());
    }
}