package com.cdcone.recipy.service;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import com.cdcone.recipy.dto.*;
import com.cdcone.recipy.entity.RecipeEntity;
import com.cdcone.recipy.entity.TagEntity;
import com.cdcone.recipy.repository.RecipeRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final UserService userService;
    private final TagService tagService;


    public void add(RecipeDtoAdd dto) {
        Set<TagEntity> tagEntities = new HashSet<TagEntity>();

        for (Integer id : dto.getTagIds()) {
            tagEntities.add(tagService.getById(id));
        }

        RecipeEntity recipe = new RecipeEntity(
                userService.getById(dto.getUserId()),
                tagEntities,
                dto.getTitle(),
                dto.getOverview(),
                LocalDate.now(),
                dto.getIngredients(),
                dto.getContent(),
                dto.getVideoURL(),
                0,
                dto.isDraft())
        recipeRepository.save(recipe);
    }


    public Page<RecipeDtoList> getPublishedRecipes(RecipeSearchDto dto) {
        if (dto.getTagId().isEmpty()) {
            Set<Integer> allTags = tagService.getAllTags()
                    .stream()
                    .map(n -> n.getId())
                    .collect(Collectors.toSet());
            dto.setTagId(allTags);
        }

        Pageable pageable = PageRequest.of(dto.getPage(), 10, Sort.by("views").descending());
        return recipeRepository.getPublishedRecipes(dto.getTitle(), dto.getAuthor(), dto.getTagId(), pageable);
    }


    public Set<RecipeDtoList> getPopularRecipes(int limit) {
        return recipeRepository.getPopularRecipes()
                .stream()
                .limit(limit)
                .collect(Collectors.toSet());
    }


    public Set<RecipeDtoList> getDiscoverRecipes(int limit) {
        return recipeRepository.getDiscoverRecipes()
                .stream()
                .limit(limit)
                .collect(Collectors.toSet());
    }


    public RecipeEntity getById(Long recipeId) {
        return recipeRepository.findById(recipeId).get();
    }


    public void addViewer(Long id) {
        RecipeEntity entity = recipeRepository.findById(id).get();
        entity.setViews(entity.getViews() + 1);
        recipeRepository.save(entity);
    }


    public long totalRecipes() {
        return recipeRepository.count();
    }


    public PaginatedDto<UserRecipeDto> getByUsername(String userName, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Object[]> byUserId = recipeRepository.findByUsername(userName, pageable);
        List<UserRecipeDto> result = new ArrayList<>();

        byUserId.getContent().forEach(it -> {
            BigInteger id = (BigInteger) it[0];
            Set<TagEntity> tags = tagService.getByRecipeId(id.longValue());
            result.add(new UserRecipeDto(id.longValue(), (String) it[1], (String) it[2], (String) it[3], (Integer) it[4], tags));
        });
        
        return new PaginatedDto<>(result, byUserId.getNumber(), byUserId.getTotalPages());
    }


    public Pair<Boolean, String> saveRecipePhoto(MultipartFile photo, Long recipeId){
        Optional<RecipeEntity> entity = recipeRepository.findById(recipeId);
        String msg = "User not found.";
        boolean uploadedPhoto = false;
        if (entity.isPresent()) {
            try {
                RecipeEntity recipe = entity.get();
                recipe.setBannerImage(photo.getBytes());
                recipe.setBannerImageType(photo.getContentType());
                recipeRepository.save(recipe);
                msg = "Success";
                uploadedPhoto = true;
            } catch (IOException e) {
                msg = "Failed to save profile photo.";
            }
        }
        return Pair.of(uploadedPhoto, msg);
    }


    public RecipeDtoList deleteRecipe(long recipeId) {
        Optional<RecipeEntity> byId = recipeRepository.findById(recipeId);
        RecipeDtoList deleted = null;
        if (byId.isPresent()) {
            RecipeEntity toBeDeleted = byId.get();
            recipeRepository.delete(toBeDeleted);
            deleted = new RecipeDtoList(toBeDeleted.getTitle(), toBeDeleted.getOverview(), toBeDeleted.getViews(), toBeDeleted.getUser().getFullName());
        }
        return deleted;
    }
}
