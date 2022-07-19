package com.cdcone.recipy.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.cdcone.recipy.dto.response.PaginatedDto;
import com.cdcone.recipy.recipe.dto.response.*;
import com.cdcone.recipy.recipe.dto.request.*;
import com.cdcone.recipy.recipe.entity.*;
import com.cdcone.recipy.recipe.repository.RecipeFavoriteRepository;
import com.cdcone.recipy.recipe.repository.RecipeReactionRepository;
import com.cdcone.recipy.recipe.repository.RecipeRepository;
import com.cdcone.recipy.recipe.service.RecipeViewedService;
import com.cdcone.recipy.user.repository.UserRepository;
import com.cdcone.recipy.recipe.service.RecipeService;
import com.cdcone.recipy.recipe.service.TagService;
import com.cdcone.recipy.user.entity.UserEntity;
import com.cdcone.recipy.user.service.UserService;
import com.cdcone.recipy.util.ImageUtil;

import io.swagger.v3.core.util.Json;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;

public class RecipeServiceTest {

    private RecipeService recipeService;

    private final RecipeRepository RECIPE_REPOSITORY = mock(RecipeRepository.class);
    private final RecipeReactionRepository RECIPE_REACTION_REPOSITORY = mock(RecipeReactionRepository.class);
    private final RecipeFavoriteRepository RECIPE_FAVORITE_REPOSITORY = mock(RecipeFavoriteRepository.class);

    private final RecipeViewedService RECIPE_VIEWED_SERVICE = mock(RecipeViewedService.class);

    private final UserRepository USER_REPOSITORY = mock(UserRepository.class);
    private final UserService USER_SERVICE = mock(UserService.class);
    private final TagService TAG_SERVICE = mock(TagService.class);

    private final RecipeAddRequestDto ADD_RECIPE_DTO = mock(RecipeAddRequestDto.class);
    private final RecipeSearchRequestDto RECIPE_SEARCH_DTO = mock(RecipeSearchRequestDto.class);

    private final RecipeEntity RECIPE_ENTITY = mock(RecipeEntity.class);

    @BeforeEach
    public void init() {
        recipeService = new RecipeService(RECIPE_REPOSITORY, RECIPE_REACTION_REPOSITORY, RECIPE_FAVORITE_REPOSITORY,
                RECIPE_VIEWED_SERVICE, USER_SERVICE,
                TAG_SERVICE);
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
        when(ADD_RECIPE_DTO.getIngredients()).thenReturn("ingredients");
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
        List<TagResponseDto> mockTag = List.of();
        Pair<String, List<TagResponseDto>> mockService = Pair.of("success:", mockTag);

        when(RECIPE_REPOSITORY.getPublishedRecipes(any(), any(), any(), any()))
                .thenReturn(mock(Page.class));
        when(TAG_SERVICE.getAllTags()).thenReturn(mockService);

        assertEquals('s',
                recipeService.getPublishedRecipes(RECIPE_SEARCH_DTO).getFirst().charAt(0));
    }

    @Test
    void failedGetPublishedRecipesIllegalPageRequest() {
        List<TagResponseDto> mockTag = List.of();
        Pair<String, List<TagResponseDto>> mockService = Pair.of("success:", mockTag);

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
        Set<RecipeListResponseDto> mockList = (Set<RecipeListResponseDto>) mock(Set.class);

        when(RECIPE_REPOSITORY.getPopularRecipes()).thenReturn(mockList);

        assertEquals('s',
                recipeService.getPopularRecipes(0).getFirst().charAt(0));
    }

    @Test
    @SuppressWarnings("unchecked")
    void failedGetPopularRecipesNegativeNumber() {
        Set<RecipeListResponseDto> mockList = (Set<RecipeListResponseDto>) mock(Set.class);

        when(RECIPE_REPOSITORY.getPopularRecipes()).thenReturn(mockList);

        assertEquals('f',
                recipeService.getPopularRecipes(-1).getFirst().charAt(0));
    }

    @Test
    @SuppressWarnings("unchecked")
    void getDiscoverRecipes() {
        Set<RecipeListResponseDto> mockList = (Set<RecipeListResponseDto>) mock(Set.class);

        when(RECIPE_REPOSITORY.getDiscoverRecipes()).thenReturn(mockList);

        assertEquals('s',
                recipeService.getDiscoverRecipes(0).getFirst().charAt(0));
    }

    @Test
    @SuppressWarnings("unchecked")
    void failedGetDiscoverRecipesNegativeNumber() {
        Set<RecipeListResponseDto> mockList = (Set<RecipeListResponseDto>) mock(Set.class);

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
    void successGetRecipesByUsername() {
        UserEntity user = new UserEntity("any@mail.com", "any", "password", "any user");
        RecipeEntity recipe = new RecipeEntity();
        recipe.setTitle("recipe");
        recipe.setOverview("overview");
        recipe.setTags(Set.of());
        recipe.setViews(99);
        recipe.setId(1L);
        recipe.setDateCreated(LocalDate.now());
        recipe.setUser(user);

        Page<RecipeEntity> mockResult = new PageImpl<>(
                List.of(recipe));

        when(RECIPE_REPOSITORY.findByUserUsernameAndIsDraftIs(any(String.class), any(Boolean.class),
                any(Pageable.class)))
                .thenReturn(mockResult);

        PaginatedDto<UserRecipeResponseDto> result = recipeService.getByUsername("any", 0, false);
        assertEquals(1, result.getData().size());
        assertEquals(99, result.getData().get(0).getViewCount());
        assertEquals("any user", result.getData().get(0).getAuthorName());
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

        List<RecipeUserReactionResponseDto> recipeReaction = List.of(
                new RecipeUserReactionResponseDto("LIKED", 10L),
                new RecipeUserReactionResponseDto("HAPPY", 2L));

        UserEntity user = new UserEntity();
        user.setId(10L);
        user.setUsername("user1");

        RecipeReactionEntity reactionEntity = new RecipeReactionEntity(
                user,
                recipe,
                RecipeReactionEntity.Reaction.LIKED,
                LocalDateTime.now());

        when(RECIPE_REPOSITORY.findById(1L)).thenReturn(Optional.of(recipe));
        when(RECIPE_REACTION_REPOSITORY.getCountByReaction(1L)).thenReturn(recipeReaction);
        when(USER_REPOSITORY.findByUsername("user1")).thenReturn(Optional.of(user));
        when(RECIPE_REACTION_REPOSITORY.findByRecipeIdAndUserId(1L, 10L)).thenReturn(reactionEntity);

        Pair<String, RecipeReactionSummaryResponseDto> result = recipeService.getRecipeReaction(1L, "user1");

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
                RecipeReactionEntity.Reaction.valueOf(requestDto.getReaction()),
                LocalDateTime.now());

        when(RECIPE_REPOSITORY.findById(1L)).thenReturn(Optional.of(recipe));
        when(USER_REPOSITORY.findByUsername("user1")).thenReturn(Optional.of(user));
        when(RECIPE_REACTION_REPOSITORY.save(saveEntity)).thenReturn(saveEntity);

        Pair<String, RecipeReactionEntity> result = recipeService.saveRecipeReaction(1L, requestDto);

        assertEquals("success: data saved", result.getFirst());
        assertEquals("LIKED", result.getSecond().getReaction().toString());

        Json.prettyPrint(result.getSecond());
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

        RecipeReactionEntity reactionEntity = new RecipeReactionEntity(
                user,
                recipe,
                RecipeReactionEntity.Reaction.valueOf(requestDto.getReaction()),
                LocalDateTime.now());

        when(USER_REPOSITORY.findByUsername("user1")).thenReturn(Optional.of(user));
        when(RECIPE_REACTION_REPOSITORY.findByRecipeIdAndUserIdAndReaction(1L, 10L,
                RecipeReactionEntity.Reaction.LIKED)).thenReturn(Optional.of(reactionEntity));

        Pair<String, RecipeReactionEntity> result = recipeService.deleteRecipeReaction(1L, requestDto);

        assertEquals("success: data deleted", result.getFirst());
        assertEquals("LIKED", result.getSecond().getReaction().toString());

        Json.prettyPrint(result.getSecond());

    }

    @Test
    void failedCommentRecipeNotFound() {
        when(RECIPE_REPOSITORY.findById(1L)).thenReturn(Optional.empty());

        assertEquals('f',
                recipeService.addCommentToRecipe(1L, new CommentEntity()).charAt(0));
    }

    @Test
    void failedCommentRecipeNotPublished() {
        Optional<RecipeEntity> mockEntity = Optional.of(RECIPE_ENTITY);

        when(RECIPE_REPOSITORY.findById(1L)).thenReturn(mockEntity);
        when(RECIPE_ENTITY.isDraft()).thenReturn(true);

        assertEquals('f',
                recipeService.addCommentToRecipe(1L, new CommentEntity()).charAt(0));
    }

    @Test
    void successGetRecipeFavorite() {
        UserEntity user = new UserEntity();
        user.setId(10L);
        user.setUsername("user1");

        RecipeEntity recipe = new RecipeEntity();
        recipe.setId(1L);
        recipe.setTitle("Recipe 1");

        RecipeFavoriteEntity recipeFavorite = new RecipeFavoriteEntity(user, recipe, LocalDateTime.now());

        when(USER_REPOSITORY.findByUsername("user1")).thenReturn(Optional.of(user));
        when(RECIPE_REPOSITORY.findById(1L)).thenReturn(Optional.of(recipe));
        when(RECIPE_FAVORITE_REPOSITORY.findByRecipeIdAndUserId(recipe.getId(), user.getId()))
                .thenReturn(Optional.of(recipeFavorite));

        Pair<String, RecipeFavoriteResponseDto> result = recipeService.getRecipeFavorite(1L, "user1");

        assertEquals("success: data retrieved", result.getFirst());
        assertEquals(10L, result.getSecond().getUserId());
        assertEquals(1L, result.getSecond().getRecipeId());
        assertTrue(result.getSecond().isFavorited());

        Json.prettyPrint(result.getSecond());

    }

    @Test
    void successSaveRecipeFavorite() {
        UserEntity user = new UserEntity();
        user.setId(10L);
        user.setUsername("user1");

        RecipeEntity recipe = new RecipeEntity();
        recipe.setId(1L);
        recipe.setTitle("Recipe 1");

        RecipeFavoriteEntity recipeFavorite = new RecipeFavoriteEntity(user, recipe, LocalDateTime.now());

        when(USER_REPOSITORY.findByUsername("user1")).thenReturn(Optional.of(user));
        when(RECIPE_REPOSITORY.findById(1L)).thenReturn(Optional.of(recipe));
        when(RECIPE_FAVORITE_REPOSITORY.save(any(RecipeFavoriteEntity.class))).thenReturn(recipeFavorite);

        Pair<String, RecipeFavoriteResponseDto> result = recipeService.saveRecipeFavorite(1L,
                new RecipeFavoriteRequestDto("user1"));

        assertEquals("success: data saved", result.getFirst());
        assertEquals(10L, result.getSecond().getUserId());
        assertEquals(1L, result.getSecond().getRecipeId());
        assertTrue(result.getSecond().isFavorited());

        Json.prettyPrint(result.getSecond());
    }

    @Test
    void successDeleteRecipeFavorite() {
        UserEntity user = new UserEntity();
        user.setId(10L);
        user.setUsername("user1");

        RecipeEntity recipe = new RecipeEntity();
        recipe.setId(1L);
        recipe.setTitle("Recipe 1");

        RecipeFavoriteEntity recipeFavorite = new RecipeFavoriteEntity(user, recipe, LocalDateTime.now());

        when(USER_REPOSITORY.findByUsername("user1")).thenReturn(Optional.of(user));
        when(RECIPE_FAVORITE_REPOSITORY.findByRecipeIdAndUserId(recipe.getId(), user.getId()))
                .thenReturn(Optional.of(recipeFavorite));

        Pair<String, RecipeFavoriteResponseDto> result = recipeService.deleteRecipeFavorite(1L,
                new RecipeFavoriteRequestDto("user1"));

        assertEquals("success: data deleted", result.getFirst());
        assertEquals(10L, result.getSecond().getUserId());
        assertEquals(1L, result.getSecond().getRecipeId());
        assertFalse(result.getSecond().isFavorited());

        Json.prettyPrint(result.getSecond());
    }

    @Test
    void successGetUserFavoriteRecipes() {

        UserEntity userCreator = new UserEntity();
        userCreator.setId(20L);
        userCreator.setUsername("user2");
        userCreator.setFullName("USER TWO");

        UserEntity userFavorite = new UserEntity();
        userFavorite.setId(10L);
        userFavorite.setUsername("user1");

        RecipeEntity recipe = new RecipeEntity();
        recipe.setId(1L);
        recipe.setTitle("Recipe 1");
        recipe.setOverview("Recipe 1 Overview");
        recipe.setViews(10);
        recipe.setDateCreated(LocalDate.now());
        recipe.setTags(Set.of(new TagEntity("Lunch")));
        recipe.setUser(userCreator);

        RecipeEntity recipe2 = new RecipeEntity();
        recipe2.setId(2L);
        recipe2.setTitle("Recipe 2");
        recipe2.setOverview("Recipe 2 Overview");
        recipe2.setViews(20);
        recipe2.setDateCreated(LocalDate.now());
        recipe2.setTags(Set.of(new TagEntity("Lunch")));
        recipe2.setUser(userCreator);

        List<RecipeFavoriteEntity> recipesFavoriteAll = List.of(
                new RecipeFavoriteEntity(userFavorite, recipe, LocalDateTime.now()),
                new RecipeFavoriteEntity(userFavorite, recipe2, LocalDateTime.now()));

        when(USER_REPOSITORY.findByUsername("user1")).thenReturn(Optional.of(userFavorite));
        when(RECIPE_FAVORITE_REPOSITORY.findByUserId(userFavorite.getId(), PageRequest.of(0, 10)))
                .thenReturn(new PageImpl<>(recipesFavoriteAll));
        when(RECIPE_FAVORITE_REPOSITORY.findByUserId(userFavorite.getId())).thenReturn(recipesFavoriteAll);

        Pair<String, PaginatedDto<UserRecipeResponseDto>> result = recipeService.getUserFavoriteRecipes("user1", true,
                0, 10);

        assertEquals("success: data retrieved", result.getFirst());
        assertEquals(2, result.getSecond().getTotalItem());
        assertEquals(1, result.getSecond().getTotalPages());

        Pair<String, PaginatedDto<UserRecipeResponseDto>> result2 = recipeService.getUserFavoriteRecipes("user1", false,
                0, 0);

        assertEquals("success: data retrieved", result2.getFirst());
        assertEquals(2, result2.getSecond().getTotalItem());
        assertEquals(1, result2.getSecond().getTotalPages());
    }
}
