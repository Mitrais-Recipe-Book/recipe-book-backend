package com.cdcone.recipy.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import com.cdcone.recipy.dtoAccess.RecipeDtoList;
import com.cdcone.recipy.dtoAccess.UserRecipeDto;
import com.cdcone.recipy.dtoRequest.*;
import com.cdcone.recipy.entity.RecipeEntity;
import com.cdcone.recipy.entity.TagEntity;
import com.cdcone.recipy.repository.RecipeRepository;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
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

    private Pair<String, Set<TagEntity>> findMultipleTags(Set<Integer> idTags) {
        Set<TagEntity> tagEntities = new HashSet<TagEntity>();
        for (Integer id : idTags) {
            Pair<TagEntity, String> tag = tagService.getById(id);

            if (tag.getFirst().getName() == null) {
                return Pair.of(tag.getSecond(), new HashSet<>());
            }

            tagEntities.add(tag.getFirst());
        }
        return Pair.of("success: data retrieved", tagEntities);
    }

    public Pair<String, RecipeEntity> add(RecipeDtoAdd dto) {
        Pair<String, Set<TagEntity>> tagEntities = findMultipleTags(dto.getTagIds());

        if (tagEntities.getFirst().charAt(0) != 's') {
            return Pair.of(tagEntities.getFirst(), new RecipeEntity());
        }

        try {
            RecipeEntity recipe = new RecipeEntity(
                    userService.getById(dto.getUserId()),
                    tagEntities.getSecond(),
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

            return Pair.of("succees: data saved", recipe);
        } catch (Exception e) {

            if (e instanceof DataIntegrityViolationException) {
                return Pair.of("failed: cannot save duplicate", new RecipeEntity());
            }
            e.printStackTrace();
            return Pair.of("failed: unknown error, contact backend team", new RecipeEntity());
        }
    }

    public String edit(Long recipeId, RecipeDtoAdd dto) {
        Pair<String, Set<TagEntity>> tagEntities = findMultipleTags(dto.getTagIds());

        if (tagEntities.getFirst().charAt(0) != 's') {
            return tagEntities.getFirst();
        }

        try {
            Pair<String, RecipeEntity> recipe = getById(recipeId);

            if (recipe.getFirst().charAt(0) != 's') {
                return recipe.getFirst();
            }

            recipe.getSecond().setTitle(dto.getTitle());
            recipe.getSecond().setTags(tagEntities.getSecond());
            recipe.getSecond().setOverview(dto.getOverview());
            recipe.getSecond().setIngredients(dto.getIngredients());
            recipe.getSecond().setContent(dto.getContent());
            recipe.getSecond().setVideoURL(dto.getVideoURL());
            recipe.getSecond().setDraft(dto.isDraft());

            recipeRepository.save(recipe.getSecond());

            return "success: data updated";
        } catch (Exception e) {
            if (e instanceof DataIntegrityViolationException) {
                return "failed: cannot save duplicate";
            }
            e.printStackTrace();
            return "failed: unknown error, contact backend team";
        }
    }

    public Pair<String, Page<RecipeDtoList>> getPublishedRecipes(RecipeSearchDto dto) {
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

            result.getContent().forEach(i -> i.setAuthorFollower(
                    userService.getFollowerCountById(
                            getById(
                                    i.getId()).getSecond().getUser().getId())));

            return Pair.of("success: data retrieved", result);

        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                return Pair.of("failed: page index must not be less than zero", new PageImpl<>(new ArrayList<>()));
            }
            e.printStackTrace();a
            return Pair.of("failed: unknown error, contact backend team", new PageImpl<>(new ArrayList<>()));
        }
    }

    public Pair<String, RecipeEntity> getRecipeImage(Long recipeId) {
        Pair<String, RecipeEntity> entity = getById(recipeId);

        if (entity.getFirst().charAt(0) != 's') {
            return entity;
        }

        if (entity.getSecond().getBannerImage() == null) {
            return Pair.of("failed: cannot find image with recipe id " + recipeId,
                    new RecipeEntity());
        }

        return Pair.of("success: image found", entity.getSecond());
    }

    public Pair<String, Set<RecipeDtoList>> getPopularRecipes(int limit) {
        try {
            Set<RecipeDtoList> list = recipeRepository.getPopularRecipes()
                    .stream()
                    .limit(limit)
                    .map(i -> {
                        i.setAuthorFollower(
                                userService.getFollowerCountById(
                                        getById(
                                                i.getId()).getSecond().getUser().getId()));
                        return i;
                    })
                    .collect(Collectors.toSet());

            return Pair.of("success: data retrieved", list);
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                return Pair.of("failed: limit cannot negative", new HashSet<>());
            }
            e.printStackTrace();
            return Pair.of("failed: unknown error, contact backend team", new HashSet<>());
        }

    }

    public Pair<String, Set<RecipeDtoList>> getDiscoverRecipes(int limit) {
        try {
            Set<RecipeDtoList> list = recipeRepository.getPopularRecipes()
                    .stream()
                    .limit(limit)
                    .map(i -> {
                        i.setAuthorFollower(
                                userService.getFollowerCountById(
                                        getById(
                                                i.getId()).getSecond().getUser().getId()));
                        return i;
                    })
                    .collect(Collectors.toSet());

            return Pair.of("success: data retrieved", list);
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                return Pair.of("failed: limit cannot negative", new HashSet<>());
            }
            e.printStackTrace();
            return Pair.of("failed: unknown error, contact backend team", new HashSet<>());
        }
    }

    public Pair<String, RecipeEntity> getById(Long recipeId) {
        RecipeEntity result = recipeRepository.findById(recipeId).orElse(null);

        if (result == null) {
            return Pair.of("failed: cannot find recipe with id " + recipeId, new RecipeEntity());
        }

        return Pair.of("success: data retrieved", result);
    }

    public String addViewer(Long recipeId) {
        Pair<String, RecipeEntity> recipe = getById(recipeId);

        if (recipe.getFirst().charAt(0) != 's') {
            return recipe.getFirst();
        }

        int views = recipe.getSecond().getViews();
        recipe.getSecond().setViews(views++);

        recipeRepository.save(recipe.getSecond());
        return "success: data updated";
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

    public String saveRecipePhoto(MultipartFile photo, Long recipeId) {
        Pair<String, RecipeEntity> recipe = getById(recipeId);

        if (recipe.getFirst().charAt(0) != 's') {
            return recipe.getFirst();
        }

        try {
            ImageIO.read(photo.getInputStream()).toString();

            recipe.getSecond().setBannerImage(photo.getBytes());
            recipe.getSecond().setBannerImageType(photo.getContentType());
            recipeRepository.save(recipe.getSecond());

            return "success: image updated";
        } catch (Exception e) {
            if (e instanceof IOException) {
                return "failed: image not updated";
            }

            if(e instanceof NullPointerException){
                return "failed: file is not image";
            }

            if(e instanceof FileSizeLimitExceededException){
                return "failed: image size should be less than 1 megabytes";
            }
            
            e.printStackTrace();
            return "failed: unknown error, contact backend team";
        }
    }

    public Pair<String, RecipeDtoList> deleteRecipe(long recipeId) {
        Pair<String, RecipeEntity> recipe = getById(recipeId);

        if (recipe.getFirst().charAt(0) != 's') {
            return Pair.of(recipe.getFirst(), new RecipeDtoList());
        }

        RecipeDtoList result = new RecipeDtoList(recipe.getSecond().getId(),
                recipe.getSecond().getTitle(),
                recipe.getSecond().getOverview(),
                recipe.getSecond().getViews(),
                recipe.getSecond().getUser().getUsername());

        recipeRepository.delete(recipe.getSecond());

        return Pair.of("success: data deleted", result);
    }
}
