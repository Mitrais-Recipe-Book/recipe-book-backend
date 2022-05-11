package com.cdcone.recipy.controller;

import java.util.NoSuchElementException;
import java.util.Set;

import com.cdcone.recipy.dto.RecipeDtoAdd;
import com.cdcone.recipy.dto.RecipeDtoList;
import com.cdcone.recipy.dto.RecipeSearchDto;
import com.cdcone.recipy.entity.RecipeEntity;
import com.cdcone.recipy.response.CommonResponse;
import com.cdcone.recipy.service.RecipeService;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
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
        try {
            recipeService.add(dto);
            return ResponseEntity.ok(new CommonResponse("success: data saved"));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(new CommonResponse(e.getCause().toString()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new CommonResponse(e.getCause().toString()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<CommonResponse> getPublishedRecipes(RecipeSearchDto dto) {
        try {
            Page<RecipeDtoList> result = recipeService.getPublishedRecipes(dto);
            return ResponseEntity.ok(new CommonResponse("success: data retrieved", result));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new CommonResponse(e.getCause().toString()));
        }
    }

    @GetMapping("image/{id}")
    public ResponseEntity<CommonResponse> getRecipeImage(Long recipeId) {
        try {
            RecipeEntity entity = recipeService.getById(recipeId);
            CommonResponse response = new CommonResponse("filename:\\profile-" + entity.getTitle(),
                    entity.getBannerImage());
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest()
                    .body(new CommonResponse("failed: recipe with id: (" + recipeId + ") not found"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new CommonResponse(e.getCause().toString()));
        }
    }

    @PutMapping(value = "{recipe}/photo", consumes = "multipart/form-data")
    public ResponseEntity<CommonResponse> saveRecipePhoto(@PathVariable Long recipeId,
            @RequestParam("photo") MultipartFile photo) {
        Pair<Boolean, String> savedPhoto = recipeService.saveRecipePhoto(photo, recipeId);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if (savedPhoto.getFirst()) {
            status = HttpStatus.OK;
        }
        return ResponseEntity.status(status).body(new CommonResponse( savedPhoto.getSecond()));
    }

    @PutMapping("/addview")
    public ResponseEntity<CommonResponse> addViewer(Long recipeId) {
        try {
            recipeService.addViewer(recipeId);
            return ResponseEntity.ok(new CommonResponse("success: data updated"));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest()
                    .body(new CommonResponse("failed: recipe with id: (" + recipeId + ") not found"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new CommonResponse(e.getCause().toString()));
        }
    }

    @GetMapping("/popular")
    public ResponseEntity<CommonResponse> getPopularRecipes(int limit) {
        try {
            Set<RecipeDtoList> result = recipeService.getPopularRecipes(limit);
            return ResponseEntity.ok(new CommonResponse("success: data retrieved", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CommonResponse(e.getCause().toString()));
        }
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
}
