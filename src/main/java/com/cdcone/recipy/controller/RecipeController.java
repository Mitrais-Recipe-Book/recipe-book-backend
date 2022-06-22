package com.cdcone.recipy.controller;

import java.util.Set;

import com.cdcone.recipy.dtoAccess.*;
import com.cdcone.recipy.dtoRequest.RecipeDtoAdd;
import com.cdcone.recipy.dtoRequest.RecipeReactionRequestDto;
import com.cdcone.recipy.dtoRequest.RecipeSearchDto;
import com.cdcone.recipy.entity.RecipeEntity;
import com.cdcone.recipy.entity.RecipeReactionEntity;
import com.cdcone.recipy.response.CommonResponse;
import com.cdcone.recipy.service.RecipeService;

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
    public ResponseEntity<CommonResponse> add(@RequestBody RecipeDtoAdd dto) {
        Pair<String, RecipeEntity> result = recipeService.add(dto);

        if (result.getFirst().charAt(0) == 's') {
            return ResponseEntity.ok().body(new CommonResponse(result.getFirst(), result.getSecond().getId()));
        }

        return ResponseEntity.badRequest().body(new CommonResponse(result.getFirst()));
    }

    @PutMapping("{id}/edit")
    public ResponseEntity<CommonResponse> edit(@PathVariable(name = "id") Long recipeId,
            @RequestBody RecipeDtoAdd dto) {
        String result = recipeService.edit(recipeId, dto);

        if (result.charAt(0) == 's') {
            return ResponseEntity.ok().body(new CommonResponse(result));
        }

        return ResponseEntity.badRequest().body(new CommonResponse(result));
    }

    @GetMapping("/search")
    public ResponseEntity<CommonResponse> getPublishedRecipes(RecipeSearchDto dto) {
        Pair<String, Page<RecipeDtoList>> result = recipeService.getPublishedRecipes(dto);

        if (result.getFirst().charAt(0) == 's') {
            return ResponseEntity.ok().body(new CommonResponse(result.getFirst(), result.getSecond()));
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
        Pair<String, Set<RecipeDtoList>> result = recipeService.getPopularRecipes(limit);

        if (result.getFirst().charAt(0) == 's') {
            return ResponseEntity.ok().body(new CommonResponse(result.getFirst(), result.getSecond()));
        }

        return ResponseEntity.badRequest().body(new CommonResponse(result.getFirst()));
    }

    @GetMapping("/discover")
    public ResponseEntity<CommonResponse> getDiscoverRecipes(int limit) {
        Pair<String, Set<RecipeDtoList>> result = recipeService.getDiscoverRecipes(limit);

        if (result.getFirst().charAt(0) == 's') {
            return ResponseEntity.ok().body(new CommonResponse(result.getFirst(), result.getSecond()));
        }

        return ResponseEntity.badRequest().body(new CommonResponse(result.getFirst()));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CommonResponse> deleteRecipe(@PathVariable(name = "id") long recipeId) {
        Pair<String, RecipeDtoList> result = recipeService.deleteRecipe(recipeId);

        if (result.getFirst().charAt(0) == 's') {
            return ResponseEntity.ok().body(new CommonResponse(result.getFirst(), result.getSecond()));
        }

        return ResponseEntity.badRequest().body(new CommonResponse(result.getFirst()));
    }

    @GetMapping("{id}")
    public ResponseEntity<CommonResponse> getById(@PathVariable(name = "id") long recipeId) {
        Pair<String, RecipeEntity> result = recipeService.getById(recipeId);

        RecipeDetailDto dto = new RecipeDetailDto(result.getSecond());

        if (result.getFirst().charAt(0) == 's') {
            return ResponseEntity.ok().body(new CommonResponse(result.getFirst(), dto));
        }

        return ResponseEntity.badRequest().body(new CommonResponse(result.getFirst()));
    }

    @GetMapping("{id}/reaction")
    public ResponseEntity<CommonResponse> getRecipeReaction(@PathVariable(name = "id") long recipeId, @RequestParam(defaultValue = "") String username) {
        Pair<String, RecipeReactionSummaryDto> result = recipeService.getRecipeReaction(recipeId, username);

        if (result.getFirst().charAt(0) == 's') {
            return ResponseEntity.ok().body(new CommonResponse(result.getFirst(), result.getSecond()));
        }
        return ResponseEntity.badRequest().body(new CommonResponse(result.getFirst()));
    }

    @PostMapping("{id}/reaction")
    public ResponseEntity<CommonResponse> saveRecipeReaction(@PathVariable(name = "id") long recipeId, @RequestBody RecipeReactionRequestDto requestDto) {
        Pair<String, RecipeReactionEntity> result = recipeService.saveRecipeReaction(recipeId, requestDto);

        if (result.getFirst().charAt(0) == 's') {
            RecipeReactionEntity response = result.getSecond();
            RecipeReactionResponseDto responseDto = new RecipeReactionResponseDto(
                    response.getRecipe().getId(),
                    response.getUser().getId(),
                    response.getReaction(),
                    response.getTimestamp()
            );
            return ResponseEntity.ok().body(new CommonResponse(result.getFirst(), responseDto));
        }
        return ResponseEntity.badRequest().body(new CommonResponse(result.getFirst()));
    }

    @DeleteMapping("{id}/reaction/")
    public ResponseEntity<CommonResponse> deleteRecipeReaction(@PathVariable(name = "id") long recipeId, @RequestBody RecipeReactionRequestDto requestDto) {
        Pair<String, RecipeReactionEntity> result = recipeService.deleteRecipeReaction(recipeId, requestDto);

        if (result.getFirst().charAt(0) == 's') {
            RecipeReactionEntity response = result.getSecond();
            RecipeReactionResponseDto responseDto = new RecipeReactionResponseDto(
                    response.getRecipe().getId(),
                    response.getUser().getId(),
                    response.getReaction(),
                    response.getTimestamp()
            );
            return ResponseEntity.ok().body(new CommonResponse(result.getFirst(), responseDto));
        }
        return ResponseEntity.badRequest().body(new CommonResponse(result.getFirst()));
    }

}
