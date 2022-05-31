package com.cdcone.recipy.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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

    private Pair<Set<TagEntity>, String> findMultipleTags(Set<Integer> idTags) {
        Set<TagEntity> tagEntities = new HashSet<TagEntity>();
        for (Integer id : idTags) {
            Pair<TagEntity, String> tag = tagService.getById(id);

            if (tag.getFirst().getName() == null) {
                return Pair.of(new HashSet<>(), tag.getSecond());
            }

            tagEntities.add(tag.getFirst());
        }
        return Pair.of(tagEntities, "success: data retrieved");
    }

    public Pair<RecipeEntity, String> add(RecipeDtoAdd dto) {
        Pair<Set<TagEntity>, String> tagEntities = findMultipleTags(dto.getTagIds());

        if (tagEntities.getSecond().charAt(0) != 's') {
            return Pair.of(new RecipeEntity(), tagEntities.getSecond());
        }

        try {
            RecipeEntity recipe = new RecipeEntity(
                    userService.getById(dto.getUserId()),
                    tagEntities.getFirst(),
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

    public String edit(Long recipeId, RecipeDtoAdd dto) {
        Pair<Set<TagEntity>, String> tagEntities = findMultipleTags(dto.getTagIds());

        if (tagEntities.getSecond().charAt(0) != 's') {
            return tagEntities.getSecond();
        }

        try {
            Pair<RecipeEntity, String> recipe = getById(recipeId);

            if (recipe.getSecond().charAt(0) != 's') {
                return recipe.getSecond();
            }

            recipe.getFirst().setTitle(dto.getTitle());
            recipe.getFirst().setTags(tagEntities.getFirst());
            recipe.getFirst().setOverview(dto.getOverview());
            recipe.getFirst().setIngredients(dto.getIngredients());
            recipe.getFirst().setContent(dto.getContent());
            recipe.getFirst().setVideoURL(dto.getVideoURL());
            recipe.getFirst().setDraft(dto.isDraft());

            recipeRepository.save(recipe.getFirst());

            return "success: data updated";
        } catch (Exception e) {
            if (e instanceof DataIntegrityViolationException) {
                return "failed: cannot save duplicate";
            }

            return "failed: unknown error, contact backend team";
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

            result.getContent().forEach(i -> i.setAuthorFollower(
                    userService.getFollowerCountById(
                            getById(
                                    i.getId()).getFirst().getUser().getId())));

            return Pair.of(result, "success: data retrieved");

        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                return Pair.of(new PageImpl<>(new ArrayList<>()), "failed: page index must not be less than zero");
            }

            return Pair.of(new PageImpl<>(new ArrayList<>()), "failed: unknown error, contact backend team");
        }
    }

    public Pair<RecipeEntity, String> getRecipeImage(Long recipeId) {
        Pair<RecipeEntity, String> entity = getById(recipeId);

        if (entity.getSecond().charAt(0) != 's') {
            return entity;
        }

        if (entity.getFirst().getBannerImage() == null) {
            return Pair.of(new RecipeEntity(),
                    "failed: cannot find image with recipe id " + recipeId);
        }

        return Pair.of(entity.getFirst(), "success: image found");
    }

    public Pair<Set<RecipeDtoList>, String> getPopularRecipes(int limit) {
        Set<RecipeDtoList> list = recipeRepository.getPopularRecipes()
                .stream()
                .limit(limit)
                .map(i -> {
                    i.setAuthorFollower(
                            userService.getFollowerCountById(
                                    getById(
                                            i.getId()).getFirst().getUser().getId()));
                    return i;
                })
                .collect(Collectors.toSet());

        return Pair.of(list, "success: data retrieved");
    }

    public Set<RecipeDtoList> getDiscoverRecipes(int limit) {
        Set<RecipeDtoList> list = recipeRepository.getPopularRecipes()
                .stream()
                .limit(limit)
                .map(i -> {
                    i.setAuthorFollower(
                            userService.getFollowerCountById(
                                    getById(
                                            i.getId()).getFirst().getUser().getId()));
                    return i;
                })
                .collect(Collectors.toSet());

        return list;
    }

    public Pair<RecipeEntity, String> getById(Long recipeId) {
        RecipeEntity result = recipeRepository.findById(recipeId).orElse(null);

        if (result == null) {
            return Pair.of(new RecipeEntity(), "failed: cannot find recipe with id " + recipeId);
        }

        return Pair.of(result, "success: data retrieved");
    }

    public String addViewer(Long recipeId) {
        Pair<RecipeEntity, String> recipe = getById(recipeId);

        if (recipe.getSecond().charAt(0) != 's'){
            return recipe.getSecond();
        }

        int views = recipe.getFirst().getViews();
        recipe.getFirst().setViews(views++);

        recipeRepository.save(recipe.getFirst());
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
        Pair<RecipeEntity, String> recipe = getById(recipeId);

        if (recipe.getSecond().charAt(0) != 's') {
            return recipe.getSecond();
        }

        try {
            recipe.getFirst().setBannerImage(photo.getBytes());
            recipe.getFirst().setBannerImageType(photo.getContentType());
            recipeRepository.save(recipe.getFirst());

            return "success: image updated";
        } catch (Exception e) {
            if (e instanceof IOException) {
                return "failed: image not updated";
            }

            return "failed: unknown error, contact backend team";
        }
    }

    public RecipeDtoList deleteRecipe(long recipeId) {
        Optional<RecipeEntity> byId = recipeRepository.findById(recipeId);
        RecipeDtoList deleted = null;
        if (byId.isPresent()) {
            RecipeEntity toBeDeleted = byId.get();
            recipeRepository.delete(toBeDeleted);
            Long followerCount = userService.getFollowerCountById(toBeDeleted.getId());
            deleted = new RecipeDtoList(
                    toBeDeleted.getId(),
                    toBeDeleted.getTitle(),
                    toBeDeleted.getOverview(),
                    toBeDeleted.getViews(),
                    toBeDeleted.getUser().getFullName());
            deleted.setAuthorFollower(followerCount);
        }
        return deleted;
    }
}
