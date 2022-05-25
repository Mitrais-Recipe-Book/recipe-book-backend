package com.cdcone.recipy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import com.cdcone.recipy.dtoAccess.RecipeDtoList;
import com.cdcone.recipy.dtoAccess.UserRecipeDto;
import com.cdcone.recipy.dtoRequest.PaginatedDto;
import com.cdcone.recipy.dtoRequest.RecipeDtoAdd;
import com.cdcone.recipy.dtoRequest.RecipeSearchDto;
import com.cdcone.recipy.entity.RecipeEntity;
import com.cdcone.recipy.repository.RecipeRepository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.util.Pair;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RecipeServiceTest {

    private static final RecipeRepository RECIPE_REPOSITORY = mock(RecipeRepository.class);

    private static final UserService USER_SERVICE = mock(UserService.class);
    private static final TagService TAG_SERVICE = mock(TagService.class);
    private static RecipeService recipeService;

    private static final RecipeDtoAdd ADD_RECIPE_DTO = mock(RecipeDtoAdd.class);

    @BeforeAll
    public static void init() {
        recipeService = new RecipeService(RECIPE_REPOSITORY, USER_SERVICE, TAG_SERVICE);

    }

    @Test
    void addRecipes() {
        RecipeEntity mockRecipe = mock(RecipeEntity.class);
        Pair<RecipeEntity, String> result = Pair.of(mockRecipe, "succees");

        when(recipeService.add(ADD_RECIPE_DTO)).thenReturn(result);

        assertEquals(mockRecipe, recipeService.add(ADD_RECIPE_DTO));
    }

    @Test
    void failAddRecipes() {
        RecipeEntity mockRecipe = mock(RecipeEntity.class);        
        Pair<RecipeEntity, String> result = Pair.of(mockRecipe, "succees");

        when(recipeService.add(ADD_RECIPE_DTO)).thenReturn(result);
        when(RECIPE_REPOSITORY.save(any())).thenThrow(DataIntegrityViolationException.class);

        int x = 0;
        try {
            recipeService.add(ADD_RECIPE_DTO);
        } catch (DataIntegrityViolationException e) {
            x = 1;
        }
        assertEquals(1, x);
    }

    @Test
    @Order(1)
    @SuppressWarnings("unchecked")
    void getPublishedRecipes() {
        RecipeSearchDto mockSearch = mock(RecipeSearchDto.class);
        Page<RecipeDtoList> mockPage = mock(Page.class);
        Pair<Page<RecipeDtoList>, String> mockResult =Pair.of(mockPage, "succees");

        when(recipeService.getPublishedRecipes(mockSearch)).thenReturn(mockResult);

        assertEquals(mockResult, recipeService.getPublishedRecipes(mockSearch));
    }

    @Test
    @SuppressWarnings("unchecked")
    void failGetPublishedRecipes() {
        RecipeSearchDto mockSearch = mock(RecipeSearchDto.class);
        Page<RecipeDtoList> mockPage = mock(Page.class);
        Pair<Page<RecipeDtoList>, String> mockResult =Pair.of(mockPage, "succees");

        when(recipeService.getPublishedRecipes(mockSearch)).thenReturn(mockResult);
        when(RECIPE_REPOSITORY.getPublishedRecipes(any(), any(), any(), any()))
                .thenThrow(NoSuchElementException.class);

        int x = 0;
        try {
            recipeService.getPublishedRecipes(mockSearch);
        } catch (NoSuchElementException e) {
            x = 1;
        }
        assertEquals(1, x);
    }

    @Test
    void getPopularRecipes() {
        RecipeDtoList mockList = mock(RecipeDtoList.class);
        Set<RecipeDtoList> mockResult = new HashSet<>();
        mockResult.add(mockList);

        when(recipeService.getPopularRecipes(1)).thenReturn(mockResult);

        assertEquals(mockResult, recipeService.getPopularRecipes(1));
    }

    @Test
    void getDiscoverRecipes() {
        RecipeDtoList mockList = mock(RecipeDtoList.class);
        Set<RecipeDtoList> mockResult = new HashSet<>();
        mockResult.add(mockList);

        when(recipeService.getDiscoverRecipes(1)).thenReturn(mockResult);

        assertEquals(mockResult, recipeService.getDiscoverRecipes(1));
    }

    @Test()
    @Order(2)
    void getById() {
        RecipeEntity mockResult = mock(RecipeEntity.class);

        when(RECIPE_REPOSITORY.findById(1L)).thenReturn(Optional.of(mockResult));

        assertEquals(mockResult, recipeService.getById(1L));
    }

    @Test
    void failGetById() {
        when(RECIPE_REPOSITORY.findById(1L)).thenThrow(NoSuchElementException.class);

        int x = 0;
        try {
            recipeService.getById(1L);
        } catch (Exception e) {
            x = 1;
        }
        assertEquals(1, x);
    }

    @Test
    void addViewer() {
        RecipeService service = Mockito.spy(recipeService);
        Mockito.doNothing().when(service).addViewer(1L);

        service.addViewer(1L);
        verify(service).addViewer(1L);
    }

    @Test
    void failAddViewer() {
        RecipeService service = Mockito.spy(recipeService);
        Mockito.doThrow(NoSuchElementException.class).when(service).addViewer(10L);

        int x = 0;
        try {
            service.addViewer(10L);
            verify(service).addViewer(10L);
        } catch (Exception e) {
            x = 1;
        }
        assertEquals(1, x);
    }

    @Test
    void totalRecipes() {
        when(recipeService.totalRecipes()).thenReturn(1L);

        long n = recipeService.totalRecipes();
        assertEquals(1, n);
    }
}
