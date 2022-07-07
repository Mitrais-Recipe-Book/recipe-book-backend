package com.cdcone.recipy.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;

import com.cdcone.recipy.dtoAccess.RecipeFavoriteResponseDto;
import com.cdcone.recipy.dtoAccess.RecipeReactionSummaryDto;
import com.cdcone.recipy.dtoRequest.RecipeFavoriteRequestDto;
import com.cdcone.recipy.dtoRequest.RecipeReactionRequestDto;
import com.cdcone.recipy.entity.RecipeFavoriteEntity;
import com.cdcone.recipy.entity.RecipeReactionEntity;
import com.cdcone.recipy.response.CommonResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.cdcone.recipy.dtoAccess.RecipeDtoList;
import com.cdcone.recipy.dtoRequest.RecipeDtoAdd;
import com.cdcone.recipy.dtoRequest.RecipeSearchDto;
import com.cdcone.recipy.entity.RecipeEntity;
import com.cdcone.recipy.entity.UserEntity;
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

    @Test
    void addViewer() {
        when(RECIPE_SERVICE.addViewer(1L))
                .thenReturn("success");

        assertEquals(HttpStatus.OK,
                recipeController.addViewer(1L).getStatusCode());
    }

    @Test
    void failAddViewer() {
        when(RECIPE_SERVICE.addViewer(1L))
                .thenReturn("failed");

        assertNotEquals(HttpStatus.OK,
                recipeController.addViewer(1L).getStatusCode());
    }

    @Test
    @SuppressWarnings("unchecked")
    void getPopularRecipes() {
        Set<RecipeDtoList> mockResult = (Set<RecipeDtoList>) mock(Set.class);

        when(RECIPE_SERVICE.getPopularRecipes(1))
                .thenReturn(Pair.of("success", mockResult));

        assertEquals(HttpStatus.OK,
                recipeController.getPopularRecipes(1).getStatusCode());
    }

    @Test
    @SuppressWarnings("unchecked")
    void failedGetPopularRecipes() {
        Set<RecipeDtoList> mockResult = (Set<RecipeDtoList>) mock(Set.class);

        when(RECIPE_SERVICE.getPopularRecipes(1))
                .thenReturn(Pair.of("failed", mockResult));

        assertNotEquals(HttpStatus.OK,
                recipeController.getPopularRecipes(1).getStatusCode());
    }

    @Test
    @SuppressWarnings("unchecked")
    void getDiscoverRecipes() {
        Set<RecipeDtoList> mockResult = (Set<RecipeDtoList>) mock(Set.class);

        when(RECIPE_SERVICE.getDiscoverRecipes(1))
                .thenReturn(Pair.of("success", mockResult));

        assertEquals(HttpStatus.OK,
                recipeController.getDiscoverRecipes(1).getStatusCode());
    }

    @Test
    @SuppressWarnings("unchecked")
    void failedGetDiscoverRecipes() {
        Set<RecipeDtoList> mockResult = (Set<RecipeDtoList>) mock(Set.class);

        when(RECIPE_SERVICE.getDiscoverRecipes(1))
                .thenReturn(Pair.of("failed", mockResult));

        assertNotEquals(HttpStatus.OK,
                recipeController.getDiscoverRecipes(1).getStatusCode());
    }

    @Test
    void deleteRecipe() {
        RecipeDtoList mockResult = mock(RecipeDtoList.class);

        when(RECIPE_SERVICE.deleteRecipe(1L))
                .thenReturn(Pair.of("success", mockResult));

        assertEquals(HttpStatus.OK,
                recipeController.deleteRecipe(1L).getStatusCode());
    }

    @Test
    void failedDeleteRecipe() {
        RecipeDtoList mockResult = mock(RecipeDtoList.class);

        when(RECIPE_SERVICE.deleteRecipe(1L))
                .thenReturn(Pair.of("failed", mockResult));

        assertNotEquals(HttpStatus.OK,
                recipeController.deleteRecipe(1L).getStatusCode());
    }

    @Test
    void getById() {
        UserEntity mockUser = mock(UserEntity.class);

        when(RECIPE_SERVICE.getById(1L))
                .thenReturn(Pair.of("succes", RECIPE_ENTITY));
        when(RECIPE_ENTITY.getUser())
                .thenReturn(mockUser);
        when(mockUser.getFullName()).thenReturn("fullname");
        when(mockUser.getUsername()).thenReturn("username");

        assertEquals(HttpStatus.OK,
                recipeController.getById(1L).getStatusCode());
    }

    @Test
    void failGetById() {
        UserEntity mockUser = mock(UserEntity.class);

        when(RECIPE_SERVICE.getById(1L))
                .thenReturn(Pair.of("failed", RECIPE_ENTITY));
        when(RECIPE_ENTITY.getUser())
                .thenReturn(mockUser);
        when(mockUser.getFullName()).thenReturn("fullname");
        when(mockUser.getUsername()).thenReturn("username");

        assertNotEquals(HttpStatus.OK,
                recipeController.getById(1L).getStatusCode());
    }

    @Test
    void successGetRecipeReaction() {
        when(RECIPE_SERVICE.getRecipeReaction(1L, "user1")).thenReturn(
                Pair.of("success: data retrieved", mock(RecipeReactionSummaryDto.class)));

        ResponseEntity<CommonResponse> result = recipeController.getRecipeReaction(1L, "user1");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("success: data retrieved", result.getBody().getMessage());
    }

    @Test
    void failGetRecipeReaction() {
        when(RECIPE_SERVICE.getRecipeReaction(1L, "user1")).thenReturn(
                Pair.of("failed: data not found", mock(RecipeReactionSummaryDto.class)));

        ResponseEntity<CommonResponse> result = recipeController.getRecipeReaction(1L, "user1");

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("failed: data not found", result.getBody().getMessage());
    }

    @Test
    void successSaveRecipeReaction() {
        RecipeEntity recipe = new RecipeEntity();
        recipe.setId(1L);
        recipe.setTitle("Recipe 1");

        UserEntity user = new UserEntity();
        user.setId(10L);
        user.setUsername("user1");

        RecipeReactionRequestDto requestDto = new RecipeReactionRequestDto();
        requestDto.setUsername("user1");
        requestDto.setReaction("LIKED");

        RecipeReactionEntity saveEntity = new RecipeReactionEntity(
                user,
                recipe,
                RecipeReactionEntity.Reaction.valueOf(requestDto.getReaction()),
                LocalDateTime.now());

        when(RECIPE_SERVICE.saveRecipeReaction(1L, requestDto)).thenReturn(
                Pair.of("success: data saved", saveEntity));

        ResponseEntity<CommonResponse> result = recipeController.saveRecipeReaction(1L, requestDto);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("success: data saved", result.getBody().getMessage());
    }

    @Test
    void failSaveRecipeReaction() {
        RecipeReactionRequestDto requestDto = new RecipeReactionRequestDto();
        requestDto.setUsername("user1");
        requestDto.setReaction("LIKED");

        when(RECIPE_SERVICE.saveRecipeReaction(1L, requestDto)).thenReturn(
                Pair.of("failed: data not found", mock(RecipeReactionEntity.class)));

        ResponseEntity<CommonResponse> result = recipeController.saveRecipeReaction(1L, requestDto);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("failed: data not found", result.getBody().getMessage());
    }

    @Test
    void successDeleteRecipeReaction() {
        RecipeEntity recipe = new RecipeEntity();
        recipe.setId(1L);
        recipe.setTitle("Recipe 1");

        UserEntity user = new UserEntity();
        user.setId(10L);
        user.setUsername("user1");

        RecipeReactionRequestDto requestDto = new RecipeReactionRequestDto();
        requestDto.setUsername("user1");
        requestDto.setReaction("LIKED");

        RecipeReactionEntity saveEntity = new RecipeReactionEntity(
                user,
                recipe,
                RecipeReactionEntity.Reaction.valueOf(requestDto.getReaction()),
                LocalDateTime.now());

        when(RECIPE_SERVICE.deleteRecipeReaction(1L, requestDto)).thenReturn(
                Pair.of("success: data deleted", saveEntity));

        ResponseEntity<CommonResponse> result = recipeController.deleteRecipeReaction(1L, requestDto);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("success: data deleted", result.getBody().getMessage());
    }

    @Test
    void failDeleteRecipeReaction() {
        RecipeReactionRequestDto requestDto = new RecipeReactionRequestDto();
        requestDto.setUsername("user1");
        requestDto.setReaction("LIKED");

        when(RECIPE_SERVICE.deleteRecipeReaction(1L, requestDto)).thenReturn(
                Pair.of("failed: data not found", mock(RecipeReactionEntity.class)));

        ResponseEntity<CommonResponse> result = recipeController.deleteRecipeReaction(1L, requestDto);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("failed: data not found", result.getBody().getMessage());
    }

    @Test
    void successGetRecipeFavorite() {

        RecipeFavoriteResponseDto responseDto = new RecipeFavoriteResponseDto(10L, 1L, LocalDateTime.now(), true);

        when(RECIPE_SERVICE.getRecipeFavorite(1L, "user1")).thenReturn(Pair.of("success: data retrieved", responseDto));

        ResponseEntity<CommonResponse> result = recipeController.getRecipeFavorite(1L, "user1");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("success: data retrieved", result.getBody().getMessage());
    }

    @Test
    void successSaveRecipeFavorite() {
        RecipeFavoriteResponseDto responseDto = new RecipeFavoriteResponseDto(10L, 1L, LocalDateTime.now(), true);

        RecipeFavoriteRequestDto requestDto = new RecipeFavoriteRequestDto("user1");

        when(RECIPE_SERVICE.saveRecipeFavorite(1L, new RecipeFavoriteRequestDto("user1"))).thenReturn(Pair.of("success: data saved", responseDto));

        ResponseEntity<CommonResponse> result = recipeController.saveRecipeFavorite(1L, requestDto);



        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("success: data saved", result.getBody().getMessage());
    }

    @Test
    void successDeleteRecipeFavorite() {
        RecipeFavoriteResponseDto responseDto = new RecipeFavoriteResponseDto(10L, 1L, LocalDateTime.now(), false);

        RecipeFavoriteRequestDto requestDto = new RecipeFavoriteRequestDto("user1");

        when(RECIPE_SERVICE.deleteRecipeFavorite(1L, new RecipeFavoriteRequestDto("user1"))).thenReturn(Pair.of("success: data deleted", responseDto));

        ResponseEntity<CommonResponse> result = recipeController.deleteRecipeFavorite(1L, requestDto);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("success: data deleted", result.getBody().getMessage());
    }
}