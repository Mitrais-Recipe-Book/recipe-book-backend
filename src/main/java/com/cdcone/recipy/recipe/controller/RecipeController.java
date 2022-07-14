package com.cdcone.recipy.recipe.controller;

import java.util.Set;

import com.cdcone.recipy.dto.response.PaginatedDto;

import com.cdcone.recipy.recipe.dto.response.*;
import com.cdcone.recipy.recipe.dto.request.*;

import com.cdcone.recipy.recipe.entity.RecipeEntity;
import com.cdcone.recipy.recipe.entity.RecipeReactionEntity;

import com.cdcone.recipy.dto.response.CommonResponse;
import com.cdcone.recipy.recipe.service.RecipeService;

import org.springframework.data.domain.Page;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    @PostMapping("/add")
    public ResponseEntity<CommonResponse> add(@RequestBody RecipeAddRequestDto dto) {
        Pair<String, RecipeEntity> result = recipeService.add(dto);

        if (result.getFirst().charAt(0) == 's') {
            return ResponseEntity.ok().body(new CommonResponse(result.getFirst(), result.getSecond().getId()));
        }

        return ResponseEntity.badRequest().body(new CommonResponse(result.getFirst()));
    }

    @PutMapping("{id}/edit")
    public ResponseEntity<CommonResponse> edit(@PathVariable(name = "id") Long recipeId,
            @RequestBody RecipeAddRequestDto dto) {
        String result = recipeService.edit(recipeId, dto);

        if (result.charAt(0) == 's') {
            return ResponseEntity.ok().body(new CommonResponse(result));
        }

        return ResponseEntity.badRequest().body(new CommonResponse(result));
    }

    @GetMapping("/search")
    public ResponseEntity<CommonResponse> getPublishedRecipes(RecipeSearchRequestDto dto) {
        Pair<String, Page<RecipeListResponseDto>> result = recipeService.getPublishedRecipes(dto);
        PaginatedDto<RecipeListResponseDto> paginated = new PaginatedDto<>(result.getSecond().getContent(),
                dto.getPage(),
                result.getSecond().getTotalPages(),
                result.getSecond().isLast(),
                result.getSecond().getTotalElements());

        if (result.getFirst().charAt(0) == 's') {
            return ResponseEntity.ok().body(new CommonResponse(result.getFirst(), paginated));
        }

        return ResponseEntity.badRequest().body(new CommonResponse(result.getFirst()));
    }

    @GetMapping("{id}/photo")
    public ResponseEntity<Object> getRecipeImage(@PathVariable(name = "id") Long recipeId) {
        Pair<String, RecipeEntity> result = recipeService.getRecipeImage(recipeId);

        if (result.getFirst().charAt(0) == 's') {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=" + result.getSecond().getTitle() + "/banner-photo")
                    .contentType(MediaType.valueOf(result.getSecond().getBannerImageType()))
                    .body(result.getSecond().getBannerImage());
        }

        return ResponseEntity.badRequest().body(new CommonResponse(result.getFirst()));
    }

    @PutMapping(value = "/{recipe}/photo", consumes = "multipart/form-data")
    public ResponseEntity<CommonResponse> saveRecipePhoto(@PathVariable(name = "recipe") Long recipeId,
            @RequestParam("photo") MultipartFile photo) {

        String result = recipeService.saveRecipePhoto(photo, recipeId);

        if (result.charAt(0) == 's') {
            return ResponseEntity.ok().body(new CommonResponse(result));
        }

        return ResponseEntity.badRequest().body(new CommonResponse(result));
    }

    @PutMapping("/addview")
    public ResponseEntity<CommonResponse> addViewer(Long recipeId) {
        String result = recipeService.addViewer(recipeId);

        if (result.charAt(0) == 's') {
            return ResponseEntity.ok().body(new CommonResponse(result));
        }

        return ResponseEntity.badRequest().body(new CommonResponse(result));
    }

    @GetMapping("/popular")
    public ResponseEntity<CommonResponse> getPopularRecipes(int limit) {
        Pair<String, Set<RecipeListResponseDto>> result = recipeService.getPopularRecipes(limit);

        if (result.getFirst().charAt(0) == 's') {
            return ResponseEntity.ok().body(new CommonResponse(result.getFirst(), result.getSecond()));
        }

        return ResponseEntity.badRequest().body(new CommonResponse(result.getFirst()));
    }

    @GetMapping("/discover")
    public ResponseEntity<CommonResponse> getDiscoverRecipes(int limit) {
        Pair<String, Set<RecipeListResponseDto>> result = recipeService.getDiscoverRecipes(limit);

        if (result.getFirst().charAt(0) == 's') {
            return ResponseEntity.ok().body(new CommonResponse(result.getFirst(), result.getSecond()));
        }

        return ResponseEntity.badRequest().body(new CommonResponse(result.getFirst()));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CommonResponse> deleteRecipe(@PathVariable(name = "id") long recipeId) {
        Pair<String, RecipeListResponseDto> result = recipeService.deleteRecipe(recipeId);

        if (result.getFirst().charAt(0) == 's') {
            return ResponseEntity.ok().body(new CommonResponse(result.getFirst(), result.getSecond()));
        }

        return ResponseEntity.badRequest().body(new CommonResponse(result.getFirst()));
    }

    @GetMapping("{id}")
    public ResponseEntity<CommonResponse> getById(@PathVariable(name = "id") long recipeId) {
        Pair<String, RecipeEntity> result = recipeService.getById(recipeId);

        if (result.getFirst().charAt(0) == 's') {
            RecipeDetailResponseDto dto = new RecipeDetailResponseDto(result.getSecond());
            return ResponseEntity.ok().body(new CommonResponse(result.getFirst(), dto));
        }

        return ResponseEntity.badRequest().body(new CommonResponse(result.getFirst()));
    }

    @GetMapping("{id}/reaction")
    public ResponseEntity<CommonResponse> getRecipeReaction(@PathVariable(name = "id") long recipeId,
            @RequestParam(defaultValue = "") String username) {
        Pair<String, RecipeReactionSummaryResponseDto> result = recipeService.getRecipeReaction(recipeId, username);

        if (result.getFirst().charAt(0) == 's') {
            return ResponseEntity.ok().body(new CommonResponse(result.getFirst(), result.getSecond()));
        }
        return ResponseEntity.badRequest().body(new CommonResponse(result.getFirst()));
    }

    @PostMapping("{id}/reaction")
    public ResponseEntity<CommonResponse> saveRecipeReaction(@PathVariable(name = "id") long recipeId,
            @RequestBody RecipeReactionRequestDto requestDto) {
        Pair<String, RecipeReactionEntity> result = recipeService.saveRecipeReaction(recipeId, requestDto);

        if (result.getFirst().charAt(0) == 's') {
            RecipeReactionEntity response = result.getSecond();
            RecipeReactionResponseDto responseDto = new RecipeReactionResponseDto(
                    response.getRecipe().getId(),
                    response.getUser().getId(),
                    response.getReaction().toString(),
                    response.getTimestamp());
            return ResponseEntity.ok().body(new CommonResponse(result.getFirst(), responseDto));
        }
        return ResponseEntity.badRequest().body(new CommonResponse(result.getFirst()));
    }

    @DeleteMapping("{id}/reaction")
    public ResponseEntity<CommonResponse> deleteRecipeReaction(@PathVariable(name = "id") long recipeId,
            @RequestBody RecipeReactionRequestDto requestDto) {
        Pair<String, RecipeReactionEntity> result = recipeService.deleteRecipeReaction(recipeId, requestDto);

        if (result.getFirst().charAt(0) == 's') {
            RecipeReactionEntity response = result.getSecond();
            RecipeReactionResponseDto responseDto = new RecipeReactionResponseDto(
                    response.getRecipe().getId(),
                    response.getUser().getId(),
                    response.getReaction().toString(),
                    response.getTimestamp());
            return ResponseEntity.ok().body(new CommonResponse(result.getFirst(), responseDto));
        }
        return ResponseEntity.badRequest().body(new CommonResponse(result.getFirst()));
    }

    @PostMapping("{id}/favorite")
    public ResponseEntity<CommonResponse> saveRecipeFavorite(@PathVariable(name = "id") long recipeId, @RequestBody RecipeFavoriteRequestDto requestDto) {
        Pair<String, RecipeFavoriteResponseDto> result = recipeService.saveRecipeFavorite(recipeId, requestDto);

        if (result.getFirst().charAt(0) == 's') {
            return ResponseEntity.ok().body(new CommonResponse(result.getFirst(), result.getSecond()));
        }
        return ResponseEntity.badRequest().body(new CommonResponse(result.getFirst()));
    }

    @DeleteMapping("{id}/favorite")
    public ResponseEntity<CommonResponse> deleteRecipeFavorite(@PathVariable(name = "id") long recipeId, @RequestBody RecipeFavoriteRequestDto requestDto) {
        Pair<String, RecipeFavoriteResponseDto> result = recipeService.deleteRecipeFavorite(recipeId, requestDto);

        if (result.getFirst().charAt(0) == 's') {
            return ResponseEntity.ok().body(new CommonResponse(result.getFirst(), result.getSecond()));
        }
        return ResponseEntity.badRequest().body(new CommonResponse(result.getFirst()));
    }

    @GetMapping("{id}/favorite")
    public ResponseEntity<CommonResponse> getRecipeFavorite(@PathVariable(name = "id") long recipeId, @RequestParam() String username) {
        Pair<String, RecipeFavoriteResponseDto> result = recipeService.getRecipeFavorite(recipeId, username);

        if (result.getFirst().charAt(0) == 's') {
            return ResponseEntity.ok().body(new CommonResponse(result.getFirst(), result.getSecond()));
        }
        return ResponseEntity.badRequest().body(new CommonResponse(result.getFirst()));
    }
}
