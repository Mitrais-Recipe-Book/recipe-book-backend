package com.cdcone.recipy.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import com.cdcone.recipy.dtoAccess.PhotoDto;
import com.cdcone.recipy.dtoAccess.RecipeDtoList;
import com.cdcone.recipy.dtoAccess.UserRecipeDto;
import com.cdcone.recipy.dtoRequest.*;
import com.cdcone.recipy.entity.RecipeEntity;
import com.cdcone.recipy.entity.TagEntity;
import com.cdcone.recipy.repository.RecipeRepository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

    public Pair<RecipeEntity, String> add(RecipeDtoAdd dto) {
        Set<TagEntity> tagEntities = new HashSet<TagEntity>();

        for (Integer id : dto.getTagIds()) {
            Pair<TagEntity, String> tag = tagService.getById(id);

            if (tag.getFirst().getName() == null) {
                return Pair.of(new RecipeEntity(), tag.getSecond());
            }

            tagEntities.add(tag.getFirst());
        }

        try {
            RecipeEntity recipe = new RecipeEntity(
                    userService.getById(dto.getUserId()),
                    tagEntities,
                    dto.getTitle(),
                    dto.getTitle().toLowerCase(),
                    dto.getOverview(),
                    LocalDate.now(),
                    dto.getIngredients(),
                    dto.getContent(),
                    dto.getVideoURL(),
                    0,
                    dto.isDraft());

            recipeRepository.save(recipe);

            return Pair.of(recipe, "succees: data saved");
        } catch (Exception e) {

            if (e instanceof DataIntegrityViolationException) {
                return Pair.of(new RecipeEntity(), "failed: cannot save duplicate");
            }

            return Pair.of(new RecipeEntity(), "failed: unknown error, contact backend team");
        }
    }

    public Pair<Page<RecipeDtoList>, String> getPublishedRecipes(RecipeSearchDto dto) {
        if (dto.getTagId() == null || dto.getTagId().isEmpty()) {
            Set<Integer> allTags = tagService.getAllTags()
                    .stream()
                    .map(n -> n.getId())
                    .collect(Collectors.toSet());
            dto.setTagId(allTags);
        }

        try {
            Pageable pageable = PageRequest.of(dto.getPage(), 10, Sort.by("views").descending());
            Page<RecipeDtoList> result = recipeRepository.getPublishedRecipes(dto.getTitle(), dto.getAuthor(),
                    dto.getTagId(), pageable);
            return Pair.of(result, "success: data retrieved");

        } catch (Exception e) {
            List<RecipeDtoList> list = new ArrayList<>();

            if (e instanceof IllegalArgumentException) {
                return Pair.of(new PageImpl<>(list), "failed: page index must not be less than zero");
            }

            return Pair.of(new PageImpl<>(list), "failed: unknown error, contact backend team");
        }
    }

    public Pair<RecipeEntity, String> getRecipeImage(Long recipeId) {
        RecipeEntity entity = getById(recipeId);

        if (entity == null) {
            return Pair.of(new RecipeEntity(),
                    "failed: recipe with id " + recipeId + " not found");
        }

        if (entity.getBannerImage() == null) {
            return Pair.of(new RecipeEntity(),
                    "failed: cannot find image with recipe id " + recipeId);
        }

        return Pair.of(entity, "success: image found");
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
        return recipeRepository.findById(recipeId).orElse(null);
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
        Page<UserRecipeDto> byUserId = recipeRepository.findByUsername(userName, pageable);
        byUserId.getContent().forEach(it -> {
            Set<TagEntity> tags = tagService.getByRecipeId(it.getId());
            it.setTags(tags);
        });
        return new PaginatedDto<>(byUserId.getContent(), byUserId.getNumber(), byUserId.getTotalPages());
    }

    public Pair<Boolean, String> saveRecipePhoto(MultipartFile photo, Long recipeId) {
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
            deleted = new RecipeDtoList(
                    toBeDeleted.getId(),
                    toBeDeleted.getTitle(),
                    toBeDeleted.getOverview(),
                    toBeDeleted.getViews(),
                    toBeDeleted.getUser().getFullName());
        }
        return deleted;
    }
}
