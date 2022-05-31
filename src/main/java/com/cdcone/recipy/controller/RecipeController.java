package com.cdcone.recipy.controller;

import java.util.Set;

import com.cdcone.recipy.dtoAccess.RecipeDetailDto;
import com.cdcone.recipy.dtoAccess.RecipeDtoList;
import com.cdcone.recipy.dtoRequest.RecipeDtoAdd;
import com.cdcone.recipy.dtoRequest.RecipeSearchDto;
import com.cdcone.recipy.entity.RecipeEntity;
import com.cdcone.recipy.response.CommonResponse;
import com.cdcone.recipy.service.RecipeService;

import org.springframework.data.domain.Page;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
        Pair<RecipeEntity, String> result = recipeService.add(dto);

        if (result.getSecond().charAt(0) == 's') {
            return ResponseEntity.ok().body(new CommonResponse(result.getSecond(), result.getFirst().getId()));
        }

        return ResponseEntity.badRequest().body(new CommonResponse(result.getSecond()));
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
        Pair<Page<RecipeDtoList>, String> result = recipeService.getPublishedRecipes(dto);

        if (result.getSecond().charAt(0) == 's') {
            return ResponseEntity.ok().body(new CommonResponse(result.getSecond(), result.getFirst()));
        }

        return ResponseEntity.badRequest().body(new CommonResponse(result.getSecond()));
    }

    @GetMapping("{id}/photo")
    public ResponseEntity<Object> getRecipeImage(@PathVariable(name = "id") Long recipeId) {
        Pair<RecipeEntity, String> result = recipeService.getRecipeImage(recipeId);

        if (result.getSecond().charAt(0) == 's') {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=" + result.getFirst().getTitle() + "/banner-photo")
                    .contentType(MediaType.valueOf(result.getFirst().getBannerImageType()))
                    .body(result.getFirst().getBannerImage());
        }

        return ResponseEntity.badRequest().body(new CommonResponse(result.getSecond()));
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
        Pair<Set<RecipeDtoList>, String> result = recipeService.getPopularRecipes(limit);

        if (result.getSecond().charAt(0) == 's') {
            return ResponseEntity.ok().body(new CommonResponse(result.getSecond(), result.getFirst()));
        }

        return ResponseEntity.badRequest().body(new CommonResponse(result.getSecond()));
    }

    @GetMapping("/discover")
    public ResponseEntity<CommonResponse> getDiscoverRecipes(int limit) {
        try {
            Set<RecipeDtoList> result = recipeService.getDiscoverRecipes(limit);
            return ResponseEntity.ok(new CommonResponse("success: data retrieved", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CommonResponse(e.getCause().toString()));
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CommonResponse> deleteRecipe(@PathVariable(name = "id") long recipeId) {
        RecipeDtoList recipeDtoList = recipeService.deleteRecipe(recipeId);
        HttpStatus status = HttpStatus.OK;
        String msg = "success: data deleted";
        if (recipeDtoList == null) {
            status = HttpStatus.NOT_FOUND;
            msg = "error: recipe not found";
        }
        return ResponseEntity.status(status).body(new CommonResponse(msg, recipeDtoList));
    }

    @GetMapping("{id}")
    public ResponseEntity<CommonResponse> getById(@PathVariable(name = "id") long recipeId) {
        Pair<RecipeEntity, String> result = recipeService.getById(recipeId);

        RecipeDetailDto dto = new RecipeDetailDto(result.getFirst());

        if (result.getSecond().charAt(0) == 's') {
            return ResponseEntity.ok().body(new CommonResponse(result.getSecond(), dto));
        }

        return ResponseEntity.badRequest().body(new CommonResponse(result.getSecond()));
    }
}
