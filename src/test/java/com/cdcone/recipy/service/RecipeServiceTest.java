package com.cdcone.recipy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Optional;

import com.cdcone.recipy.dtoRequest.RecipeDtoAdd;
import com.cdcone.recipy.entity.RecipeEntity;
import com.cdcone.recipy.repository.RecipeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;

public class RecipeServiceTest {

    private final RecipeRepository RECIPE_REPOSITORY = mock(RecipeRepository.class);

    private final UserService USER_SERVICE = mock(UserService.class);
    private final TagService TAG_SERVICE = mock(TagService.class);
    private RecipeService recipeService;

    private final RecipeDtoAdd ADD_RECIPE_DTO = mock(RecipeDtoAdd.class);
    private final RecipeEntity RECIPE_ENTITY = mock(RecipeEntity.class);

    @BeforeEach
    public void init() {
        recipeService = new RecipeService(RECIPE_REPOSITORY, USER_SERVICE, TAG_SERVICE);
    }

    @Test
    void addRecipe() {
        when(ADD_RECIPE_DTO.getTitle()).thenReturn("title");

        assertEquals('s',
                recipeService.add(ADD_RECIPE_DTO).getSecond().charAt(0));
    }

    @Test
    void failedAddRecipeDuplicateData() {
        when(ADD_RECIPE_DTO.getTitle()).thenReturn("title");
        when(RECIPE_REPOSITORY.save(any())).thenThrow(DataIntegrityViolationException.class);

        assertEquals('f',
                recipeService.getById(1L).getSecond().charAt(0));

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
                recipeService.getById(1L).getSecond().charAt(0));
    }

    @Test
    void failedGetById() {
        when(RECIPE_REPOSITORY.findById(1L)).thenReturn(Optional.empty());

        assertEquals('f',
                recipeService.getById(1L).getSecond().charAt(0));
    }

    @Test
    void totalRecipes() {
        when(RECIPE_REPOSITORY.count()).thenReturn(1L);

        assertEquals(1L, recipeService.totalRecipes());
    }
}
