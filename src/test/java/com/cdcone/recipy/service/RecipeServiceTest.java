package com.cdcone.recipy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.cdcone.recipy.dtoAccess.RecipeDtoList;
import com.cdcone.recipy.dtoAccess.RecipeReactionDto;
import com.cdcone.recipy.dtoAccess.RecipeReactionResponseDto;
import com.cdcone.recipy.dtoAccess.RecipeReactionSummaryDto;
import com.cdcone.recipy.dtoRequest.RecipeDtoAdd;
import com.cdcone.recipy.dtoRequest.RecipeReactionRequestDto;
import com.cdcone.recipy.dtoRequest.RecipeSearchDto;
import com.cdcone.recipy.entity.*;
import com.cdcone.recipy.repository.RecipeReactionRepository;
import com.cdcone.recipy.repository.RecipeRepository;
import com.cdcone.recipy.repository.UserDao;
import com.cdcone.recipy.util.ImageUtil;

import io.swagger.v3.core.util.Json;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.util.Pair;

public class RecipeServiceTest {

    private RecipeService recipeService;

    private final RecipeRepository RECIPE_REPOSITORY = mock(RecipeRepository.class);
    private final RecipeReactionRepository RECIPE_REACTION_REPOSITORY = mock(RecipeReactionRepository.class);

    private final UserDao USER_DAO = mock(UserDao.class);
    private final UserService USER_SERVICE = mock(UserService.class);
    private final TagService TAG_SERVICE = mock(TagService.class);

    private final RecipeDtoAdd ADD_RECIPE_DTO = mock(RecipeDtoAdd.class);
    private final RecipeSearchDto RECIPE_SEARCH_DTO = mock(RecipeSearchDto.class);

    private final RecipeEntity RECIPE_ENTITY = mock(RecipeEntity.class);

    @BeforeEach
    public void init() {
        recipeService = new RecipeService(RECIPE_REPOSITORY,RECIPE_REACTION_REPOSITORY, USER_DAO, USER_SERVICE, TAG_SERVICE);
    }

    @Test
    void addRecipe() {
        when(ADD_RECIPE_DTO.getTitle()).thenReturn("title");
        when(ADD_RECIPE_DTO.getIngredients()).thenReturn("ingredients");

        assertEquals('s',
                recipeService.add(ADD_RECIPE_DTO).getFirst().charAt(0));
    }

    @Test
    void failedAddRecipeDuplicateData() {
        when(ADD_RECIPE_DTO.getTitle()).thenReturn("title");
        when(RECIPE_REPOSITORY.save(any())).thenThrow(DataIntegrityViolationException.class);

        assertEquals('f',
                recipeService.add(ADD_RECIPE_DTO).getFirst().charAt(0));
    }

    @Test
    void editRecipe() {
        when(ADD_RECIPE_DTO.getTitle()).thenReturn("title");
        when(RECIPE_REPOSITORY.findById(1L)).thenReturn(Optional.of(RECIPE_ENTITY));

        assertEquals('s',
                recipeService.edit(1L, ADD_RECIPE_DTO).charAt(0));
    }

    @Test
    void failedEditTagDuplicateData() {
        when(ADD_RECIPE_DTO.getTitle()).thenReturn("title");
        when(RECIPE_REPOSITORY.findById(1L)).thenReturn(Optional.of(RECIPE_ENTITY));
        when(RECIPE_REPOSITORY.save(RECIPE_ENTITY)).thenThrow(DataIntegrityViolationException.class);

        assertEquals('f',
                recipeService.edit(1L, ADD_RECIPE_DTO).charAt(0));

    }

    @Test()
    void getById() {
        when(RECIPE_REPOSITORY.findById(1L)).thenReturn(Optional.of(RECIPE_ENTITY));

        assertEquals('s',
                recipeService.getById(1L).getFirst().charAt(0));
    }

    @Test
    void failedGetById() {
        when(RECIPE_REPOSITORY.findById(1L)).thenReturn(Optional.empty());

        assertEquals('f',
                recipeService.getById(1L).getFirst().charAt(0));
    }

    @Test
    void totalRecipes() {
        when(RECIPE_REPOSITORY.count()).thenReturn(1L);

        assertEquals(1L, recipeService.totalRecipes());
    }

    @Test
    @SuppressWarnings("unchecked")
    void getPublishedRecipes() {
        List<TagEntity> mockTag = mock(List.class);
        Pair<String, List<TagEntity>> mockService = Pair.of("success:", mockTag);

        when(RECIPE_REPOSITORY.getPublishedRecipes(any(), any(), any(), any()))
                .thenReturn(mock(Page.class));
        when(TAG_SERVICE.getAllTags()).thenReturn(mockService);

        assertEquals('s',
                recipeService.getPublishedRecipes(RECIPE_SEARCH_DTO).getFirst().charAt(0));
    }

    @Test
    @SuppressWarnings("unchecked")
    void failedGetPublishedRecipesIllegalPageRequest() {
        List<TagEntity> mockTag = mock(List.class);
        Pair<String, List<TagEntity>> mockService = Pair.of("success:", mockTag);

        when(RECIPE_REPOSITORY.getPublishedRecipes(any(), any(), any(), any()))
                .thenThrow(IllegalArgumentException.class);

        when(TAG_SERVICE.getAllTags()).thenReturn(mockService);

        assertEquals('f',
                recipeService.getPublishedRecipes(RECIPE_SEARCH_DTO).getFirst().charAt(0));
    }

    @Test
    void getRecipeImage() throws IOException {
        byte[] photo = ImageUtil.randomImage();

        when(RECIPE_REPOSITORY.findById(1L))
                .thenReturn(Optional.of(RECIPE_ENTITY));

        when(RECIPE_ENTITY.getBannerImage()).thenReturn(photo);

        assertEquals('s',
                recipeService.getRecipeImage(1L).getFirst().charAt(0));
    }

    @Test
    void failedGetRecipeImage() {
        byte[] photo = null;

        when(RECIPE_REPOSITORY.findById(1L))
                .thenReturn(Optional.of(RECIPE_ENTITY));

        when(RECIPE_ENTITY.getBannerImage()).thenReturn(photo);

        assertEquals('f',
                recipeService.getRecipeImage(1L).getFirst().charAt(0));
    }

    @Test
    @Disabled
    void saveRecipePhoto() throws IOException {
        // todo -> write unit test for save recipe photo
    }

    @Test
    @SuppressWarnings("unchecked")
    void getPopularRecipes() {
        Set<RecipeDtoList> mockList = (Set<RecipeDtoList>) mock(Set.class);

        when(RECIPE_REPOSITORY.getPopularRecipes()).thenReturn(mockList);

        assertEquals('s',
                recipeService.getPopularRecipes(0).getFirst().charAt(0));
    }

    @Test
    @SuppressWarnings("unchecked")
    void failedGetPopularRecipesNegativeNumber() {
        Set<RecipeDtoList> mockList = (Set<RecipeDtoList>) mock(Set.class);

        when(RECIPE_REPOSITORY.getPopularRecipes()).thenReturn(mockList);

        assertEquals('f',
                recipeService.getPopularRecipes(-1).getFirst().charAt(0));
    }

    @Test
    @SuppressWarnings("unchecked")
    void getDiscoverRecipes() {
        Set<RecipeDtoList> mockList = (Set<RecipeDtoList>) mock(Set.class);

        when(RECIPE_REPOSITORY.getDiscoverRecipes()).thenReturn(mockList);

        assertEquals('s',
                recipeService.getDiscoverRecipes(0).getFirst().charAt(0));
    }

    @Test
    @SuppressWarnings("unchecked")
    void failedGetDiscoverRecipesNegativeNumber() {
        Set<RecipeDtoList> mockList = (Set<RecipeDtoList>) mock(Set.class);

        when(RECIPE_REPOSITORY.getDiscoverRecipes()).thenReturn(mockList);

        assertEquals('f',
                recipeService.getDiscoverRecipes(-1).getFirst().charAt(0));
    }

    @Test
    void addViewer() {
        when(RECIPE_REPOSITORY.findById(1L)).thenReturn(Optional.of(RECIPE_ENTITY));

        assertEquals('s', recipeService.addViewer(1L).charAt(0));
    }

    @Test
    void failedAddViewerRecipeNotFound() {
        when(RECIPE_REPOSITORY.findById(1L)).thenReturn(Optional.empty());

        assertEquals('f',
                recipeService.addViewer(1L).charAt(0));
    }

    @Test
    void getTotalRecipes() {
        when(RECIPE_REPOSITORY.count()).thenReturn(1L);

        assertEquals(1L, recipeService.totalRecipes());
    }

    @Test
    void deleteRecipe() {
        when(RECIPE_REPOSITORY.findById(1L)).thenReturn(Optional.of(RECIPE_ENTITY));
        when(RECIPE_ENTITY.getUser()).thenReturn(mock(UserEntity.class));

        assertEquals('s',
                recipeService.deleteRecipe(1L).getFirst().charAt(0));
    }

    @Test
    void failedDeleteRecipeNotFound() {
        when(RECIPE_REPOSITORY.findById(1L)).thenReturn(Optional.empty());

        assertEquals('f',
                recipeService.deleteRecipe(1L).getFirst().charAt(0));

    }

    @Test
    void addCommentToRecipe() {
        Optional<RecipeEntity> mockEntity = Optional.of(RECIPE_ENTITY);

        when(RECIPE_REPOSITORY.findById(1L)).thenReturn(mockEntity);
        when(RECIPE_ENTITY.isDraft()).thenReturn(false);

        assertEquals('s',
                recipeService.addCommentToRecipe(1L, new CommentEntity()).charAt(0));
    }

    @Test
    void successGetRecipeReaction() {
        RecipeEntity recipe = new RecipeEntity();
        recipe.setId(1L);
        recipe.setTitle("Recipe 1");

        List<RecipeReactionDto> recipeReaction = List.of(
                new RecipeReactionDto("LIKED", 10L),
                new RecipeReactionDto("HAPPY", 2L)
        );

        UserEntity user = new UserEntity();
        user.setId(10L);
        user.setUsername("user1");

        RecipeReactionEntity reactionEntity = new RecipeReactionEntity(
                user,
                recipe,
                "LIKED",
                LocalDateTime.now()
        );

        when(RECIPE_REPOSITORY.findById(1L)).thenReturn(Optional.of(recipe));
        when(RECIPE_REACTION_REPOSITORY.getCountByReaction(1L)).thenReturn(recipeReaction);
        when(USER_DAO.findByUsername("user1")).thenReturn(Optional.of(user));
        when(RECIPE_REACTION_REPOSITORY.findByRecipeIdAndUserId(1L, 10L)).thenReturn(reactionEntity);

        Pair<String, RecipeReactionSummaryDto> result = recipeService.getRecipeReaction(1L, "user1");

        assertEquals("success: data retrieved", result.getFirst());
        assertEquals("LIKED", result.getSecond().getUserReaction().getReaction());
        assertEquals(2, result.getSecond().getReactionList().size());

        Json.prettyPrint(result.getSecond());
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
                requestDto.getReaction(),
                LocalDateTime.now()
        );

        when(RECIPE_REPOSITORY.findById(1L)).thenReturn(Optional.of(recipe));
        when(USER_DAO.findByUsername("user1")).thenReturn(Optional.of(user));
        when(RECIPE_REACTION_REPOSITORY.save(saveEntity)).thenReturn(saveEntity);

        Pair<String, RecipeReactionEntity> result = recipeService.saveRecipeReaction(1L, requestDto);

        assertEquals("success: data saved", result.getFirst());
        assertEquals("LIKED", result.getSecond().getReaction());

        Json.prettyPrint(result.getSecond());
    }


}
