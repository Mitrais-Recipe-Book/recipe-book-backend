package com.cdcone.recipy.recipe.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.persistence.EntityNotFoundException;

import com.cdcone.recipy.dto.response.PaginatedDto;
import com.cdcone.recipy.recipe.dto.response.*;
import com.cdcone.recipy.recipe.dto.request.*;
import com.cdcone.recipy.recipe.entity.*;
import com.cdcone.recipy.recipe.repository.RecipeFavoriteRepository;
import com.cdcone.recipy.user.dto.repository.UserProfile;
import com.cdcone.recipy.recipe.repository.RecipeReactionRepository;
import com.cdcone.recipy.recipe.repository.RecipeRepository;

import com.cdcone.recipy.user.entity.UserEntity;
import com.cdcone.recipy.user.service.UserService;
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
    private final RecipeReactionRepository recipeReactionRepository;
    private final RecipeFavoriteRepository recipeFavoriteRepository;
    private final RecipeViewedService recipeViewedService;
    private final UserService userService;
    private final TagService tagService;

    private Pair<String, Set<TagEntity>> findMultipleTags(Set<Integer> idTags) {
        Set<TagEntity> tagEntities = new HashSet<>();
        for (Integer id : idTags) {
            Pair<TagEntity, String> tag = tagService.getById(id);

            if (tag.getFirst().getName() == null) {
                return Pair.of(tag.getSecond(), new HashSet<>());
            }

            tagEntities.add(tag.getFirst());
        }
        return Pair.of("success: data retrieved", tagEntities);
    }

    public Pair<String, RecipeEntity> add(RecipeAddRequestDto dto) {
        Pair<String, Set<TagEntity>> tagEntities = findMultipleTags(dto.getTagIds());

        if (tagEntities.getFirst().charAt(0) != 's') {
            return Pair.of(tagEntities.getFirst(), new RecipeEntity());
        }

        if (dto.getTitle().length() > 140) {
            return Pair.of("failed: title length cannot more than 140 character", new RecipeEntity());
        }

        if (dto.getTitle().length() == 0) {
            return Pair.of("failed: title length cannot less than 1 character", new RecipeEntity());
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

            return Pair.of("success: data saved", recipe);
        } catch (Exception e) {

            if (e instanceof DataIntegrityViolationException) {
                return Pair.of("failed: cannot save duplicate", new RecipeEntity());
            }

            if (e instanceof NullPointerException) {
                e.printStackTrace();
                return Pair.of("failed: " + e.getMessage(), new RecipeEntity());
            }

            e.printStackTrace();
            return Pair.of("critical error: unpredicted cause, contact backend team", new RecipeEntity());
        }
    }

    public String edit(Long recipeId, RecipeAddRequestDto dto) {
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
            if (e instanceof NullPointerException) {
                return "failed: " + e.getMessage();
            }
            e.printStackTrace();
            return "critical error: unpredicted cause, contact backend team";
        }
    }

    public Pair<String, Page<RecipeListResponseDto>> getPublishedRecipes(RecipeSearchRequestDto dto) {
        if (dto.getTagId() == null || dto.getTagId().isEmpty()) {
            Pair<String, List<TagResponseDto>> tagResult = tagService.getAllTags();

            Set<Integer> allTags = tagResult.getSecond()
                    .stream()
                    .map(n -> n.getId())
                    .collect(Collectors.toSet());
            dto.setTagId(allTags);
        }

        try {
            Pageable pageable = PageRequest.of(dto.getPage(), 10, Sort.by("views").descending());
            Page<RecipeListResponseDto> result = recipeRepository.getPublishedRecipes(dto.getTitle(), dto.getAuthor(),
                    dto.getTagId(), pageable);

            result.getContent().forEach(i -> {
                UserEntity user = i.getUser();
                AuthorResponseDto author = new AuthorResponseDto(user.getUsername(),
                        user.getFullName(),
                        userService.getFollowerCountById(user.getId()));
                i.setAuthor(author);
            });

            return Pair.of("success: data retrieved", result);

        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                return Pair.of("failed: page index must not be less than zero", new PageImpl<>(new ArrayList<>()));
            }
            e.printStackTrace();
            return Pair.of("critical error: unpredicted cause, contact backend team",
                    new PageImpl<>(new ArrayList<>()));
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

    public Pair<String, Set<RecipeListResponseDto>> getPopularRecipes(int limit) {
        try {
            Set<RecipeListResponseDto> list = recipeRepository.getPopularRecipes()
                    .stream()
                    .limit(limit)
                    .map(i -> {
                        UserEntity user = i.getUser();
                        AuthorResponseDto author = new AuthorResponseDto(user.getUsername(),
                                user.getFullName(),
                                userService.getFollowerCountById(user.getId()));
                        i.setAuthor(author);
                        return i;
                    })
                    .collect(Collectors.toSet());

            return Pair.of("success: data retrieved", list);
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                return Pair.of("failed: limit cannot negative", new HashSet<>());
            }
            e.printStackTrace();
            return Pair.of("critical error: unpredicted cause, contact backend team", new HashSet<>());
        }

    }

    public Pair<String, Set<RecipeListResponseDto>> getDiscoverRecipes(int limit) {
        try {
            Set<RecipeListResponseDto> list = recipeRepository.getPopularRecipes()
                    .stream()
                    .limit(limit)
                    .map(i -> {
                        UserEntity user = i.getUser();
                        AuthorResponseDto author = new AuthorResponseDto(user.getUsername(),
                                user.getFullName(),
                                userService.getFollowerCountById(user.getId()));
                        i.setAuthor(author);
                        return i;
                    })
                    .collect(Collectors.toSet());

            return Pair.of("success: data retrieved", list);
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                return Pair.of("failed: limit cannot negative", new HashSet<>());
            }
            e.printStackTrace();
            return Pair.of("critical error: unpredicted cause, contact backend team", new HashSet<>());
        }
    }

    public Pair<String, RecipeEntity> getById(Long recipeId) {
        Optional<RecipeEntity> result = recipeRepository.findById(recipeId);

        if (result.isEmpty()) {
            throw new EntityNotFoundException("recipe");
        }

        return Pair.of("success: data retrieved", result.get());
    }

    public String addViewer(Long recipeId) {
        Pair<String, RecipeEntity> recipe = getById(recipeId);

        if (recipe.getFirst().charAt(0) != 's') {
            return recipe.getFirst();
        }

        int views = recipe.getSecond().getViews();
        recipe.getSecond().setViews(views + 1);

        recipeRepository.save(recipe.getSecond());
        return "success: data updated";
    }

    public long totalRecipes() {
        return recipeRepository.count();
    }

    public PaginatedDto<UserRecipeResponseDto> getByUsername(String userName, int page, boolean isDraft) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<RecipeEntity> byUserId = recipeRepository.findByUserUsernameAndIsDraftIs(userName, isDraft, pageable);
        List<UserRecipeResponseDto> recipeList = byUserId.stream()
                .map(it -> new UserRecipeResponseDto(
                        it.getId(), it.getTitle(),
                        it.getOverview(), it.getUser().getFullName(),
                        it.getViews(), it.getDateCreated(),
                        it.getTags()))
                .collect(Collectors.toList());
        return new PaginatedDto<>(recipeList,
                byUserId.getNumber(),
                byUserId.getTotalPages(),
                byUserId.isLast(),
                byUserId.getTotalElements());
    }

    public String saveRecipePhoto(MultipartFile photo, Long recipeId) {
        Pair<String, RecipeEntity> recipe = getById(recipeId);

        if (recipe.getFirst().charAt(0) != 's') {
            return recipe.getFirst();
        }

        try {
            if (ImageIO.read(photo.getInputStream()) == null) {
                throw new NullPointerException();
            }

            recipe.getSecond().setBannerImage(photo.getBytes());
            recipe.getSecond().setBannerImageType(photo.getContentType());
            recipeRepository.save(recipe.getSecond());

            return "success: image updated";
        } catch (Exception e) {
            if (e instanceof IOException) {
                return "failed: image not updated";
            }

            if (e instanceof NullPointerException) {
                return "failed: file is not image";
            }

            e.printStackTrace();
            return "critical error: unpredicted cause, contact backend team";
        }
    }

    public Pair<String, RecipeListResponseDto> deleteRecipe(long recipeId) {
        Pair<String, RecipeEntity> recipe = getById(recipeId);

        if (recipe.getFirst().charAt(0) != 's') {
            return Pair.of(recipe.getFirst(), new RecipeListResponseDto());
        }

        RecipeListResponseDto result = new RecipeListResponseDto(recipe.getSecond().getId(),
                recipe.getSecond().getTitle(),
                recipe.getSecond().getOverview(),
                recipe.getSecond().getViews());

        recipeRepository.delete(recipe.getSecond());

        return Pair.of("success: data deleted", result);
    }

    public String addCommentToRecipe(Long recipeId, CommentEntity comment) {
        Pair<String, RecipeEntity> recipeEntity = getById(recipeId);

        if (recipeEntity.getFirst().charAt(0) != 's') {
            return recipeEntity.getFirst();
        }

        if (recipeEntity.getSecond().isDraft()) {
            return "failed: cannot comment on unpublished recipe";
        }

        recipeEntity.getSecond().getComments().add(comment);
        recipeRepository.save(recipeEntity.getSecond());

        return "success: comment added";
    }

    public Pair<String, RecipeReactionSummaryResponseDto> getRecipeReaction(long recipeId, String username) {
        Optional<RecipeEntity> recipeOptional = recipeRepository.findById(recipeId);

        if (recipeOptional.isPresent()) {
            RecipeEntity recipe = recipeOptional.get();
            List<RecipeUserReactionResponseDto> recipeUserReactionResponseDtoList = recipeReactionRepository
                    .getCountByReaction(recipeId);

            RecipeReactionResponseDto userReaction = null;
            if (!username.isBlank()) {
                Optional<UserEntity> userOptional = Optional.of(userService.getByUsername(username).getSecond());
                if (userOptional.isPresent()) {
                    RecipeReactionEntity userReactionEntity = recipeReactionRepository
                            .findByRecipeIdAndUserId(recipe.getId(), userOptional.get().getId());
                    if (userReactionEntity != null) {
                        userReaction = new RecipeReactionResponseDto(
                                userReactionEntity.getRecipe().getId(),
                                userReactionEntity.getUser().getId(),
                                userReactionEntity.getReaction().toString(),
                                userReactionEntity.getTimestamp());
                    }
                }
            }

            RecipeReactionSummaryResponseDto responseDto = new RecipeReactionSummaryResponseDto(
                    recipe.getId(),
                    recipe.getTitle(),
                    recipeUserReactionResponseDtoList,
                    userReaction);
            return Pair.of("success: data retrieved", responseDto);
        }
        return Pair.of("failed: data not found", new RecipeReactionSummaryResponseDto());
    }

    public Pair<String, RecipeReactionEntity> saveRecipeReaction(long recipeId, RecipeReactionRequestDto requestDto) {
        Optional<UserEntity> userOptional = Optional
                .of(userService.getByUsername(requestDto.getUsername()).getSecond());
        Optional<RecipeEntity> recipeOptional = recipeRepository.findById(recipeId);

        try {
            if (userOptional.isPresent() && recipeOptional.isPresent()) {
                RecipeReactionEntity entity = new RecipeReactionEntity(
                        userOptional.get(),
                        recipeOptional.get(),
                        RecipeReactionEntity.Reaction.valueOf(requestDto.getReaction()),
                        LocalDateTime.now());
                return Pair.of("success: data saved", recipeReactionRepository.save(entity));
            }
        } catch (IllegalArgumentException e) {
            return Pair.of("failed: invalid reaction", new RecipeReactionEntity());
        }
        return Pair.of("failed: data not found", new RecipeReactionEntity());
    }

    public Pair<String, RecipeReactionEntity> deleteRecipeReaction(long recipeId, RecipeReactionRequestDto requestDto) {
        Optional<UserEntity> userOptional = Optional
                .of(userService.getByUsername(requestDto.getUsername()).getSecond());

        if (userOptional.isPresent()) {
            Optional<RecipeReactionEntity> reactionOptional = recipeReactionRepository
                    .findByRecipeIdAndUserIdAndReaction(recipeId, userOptional.get().getId(),
                            RecipeReactionEntity.Reaction.valueOf(requestDto.getReaction()));
            if (reactionOptional.isPresent()) {
                recipeReactionRepository.delete(reactionOptional.get());
                return Pair.of("success: data deleted", reactionOptional.get());
            }
        }
        return Pair.of("failed: data not found", new RecipeReactionEntity());
    }

    public Pair<String, RecipeFavoriteResponseDto> saveRecipeFavorite(long recipeId,
            RecipeFavoriteRequestDto requestDto) {
        Optional<UserEntity> userOptional = Optional
                .of(userService.getByUsername(requestDto.getUsername()).getSecond());
        Optional<RecipeEntity> recipeOptional = recipeRepository.findById(recipeId);

        if (userOptional.isPresent() && recipeOptional.isPresent()) {
            RecipeFavoriteEntity entity = new RecipeFavoriteEntity(
                    userOptional.get(),
                    recipeOptional.get(),
                    LocalDateTime.now());

            entity = recipeFavoriteRepository.save(entity);

            return Pair.of("success: data saved", new RecipeFavoriteResponseDto(
                    entity.getRecipe().getId(),
                    entity.getUser().getId(),
                    entity.getTimestamp(),
                    true));
        }
        return Pair.of("failed: data not found", new RecipeFavoriteResponseDto());
    }

    public Pair<String, RecipeFavoriteResponseDto> deleteRecipeFavorite(long recipeId,
            RecipeFavoriteRequestDto requestDto) {
        Optional<UserEntity> userOptional = Optional
                .of(userService.getByUsername(requestDto.getUsername()).getSecond());

        if (userOptional.isPresent()) {
            Optional<RecipeFavoriteEntity> recipeFavoriteOptional = recipeFavoriteRepository
                    .findByRecipeIdAndUserId(recipeId, userOptional.get().getId());
            if (recipeFavoriteOptional.isPresent()) {
                recipeFavoriteRepository.delete(recipeFavoriteOptional.get());
                return Pair.of("success: data deleted", new RecipeFavoriteResponseDto(
                        recipeFavoriteOptional.get().getRecipe().getId(),
                        recipeFavoriteOptional.get().getUser().getId(),
                        null,
                        false));
            }
        }
        return Pair.of("failed: data not found", new RecipeFavoriteResponseDto());
    }

    public Pair<String, RecipeFavoriteResponseDto> getRecipeFavorite(long recipeId, String username) {
        Optional<UserEntity> userOptional = Optional.of(userService.getByUsername(username).getSecond());
        Optional<RecipeEntity> recipeOptional = recipeRepository.findById(recipeId);

        if (userOptional.isPresent() && recipeOptional.isPresent()) {
            Optional<RecipeFavoriteEntity> recipeFavoriteOptional = recipeFavoriteRepository
                    .findByRecipeIdAndUserId(recipeId, userOptional.get().getId());
            if (recipeFavoriteOptional.isPresent()) {
                return Pair.of("success: data retrieved", new RecipeFavoriteResponseDto(
                        recipeFavoriteOptional.get().getRecipe().getId(),
                        recipeFavoriteOptional.get().getUser().getId(),
                        recipeFavoriteOptional.get().getTimestamp(),
                        true));
            }
            return Pair.of("success: data retrieved", new RecipeFavoriteResponseDto(
                    recipeOptional.get().getId(),
                    userOptional.get().getId(),
                    null,
                    false));
        }
        return Pair.of("failed: data not found", new RecipeFavoriteResponseDto());
    }

    public Pair<String, PaginatedDto<UserRecipeResponseDto>> getUserFavoriteRecipes(String username,
            Boolean isPaginated, Integer page, Integer size) {
        Optional<UserEntity> userOptional = Optional.of(userService.getByUsername(username).getSecond());

        if (userOptional.isEmpty()) {
            return Pair.of("failed: data not found", new PaginatedDto<>());
        }

        Pageable paging;
        Page<RecipeFavoriteEntity> resultPage;
        Long userId = userOptional.get().getId();

        if (isPaginated) {
            paging = PageRequest.of(page, size);
            resultPage = recipeFavoriteRepository.findByUserId(userId, paging);
        } else {
            List<RecipeFavoriteEntity> entityList = recipeFavoriteRepository.findByUserId(userId);
            resultPage = new PageImpl<>(entityList);
        }

        List<UserRecipeResponseDto> listDto = resultPage.getContent().stream().map(e -> {
            RecipeEntity recipe = e.getRecipe();
            return new UserRecipeResponseDto(
                    recipe.getId(),
                    recipe.getTitle(),
                    recipe.getOverview(),
                    recipe.getUser().getFullName(),
                    recipe.getViews(),
                    recipe.getDateCreated(),
                    recipe.getTags());
        }).collect(Collectors.toList());

        PaginatedDto<UserRecipeResponseDto> result = new PaginatedDto<>(
                listDto,
                resultPage.getNumber(),
                resultPage.getTotalPages(),
                resultPage.isLast(),
                resultPage.getTotalElements());
        return Pair.of("success: data retrieved", result);
    }

    public int getTotalRecipeLikeByUserId(Long id) {
        return recipeReactionRepository.getTotalRecipeLikeByUserId(id);
    }

    public PaginatedDto<RecipeListResponseDto> getRecipeViewed(String username, Boolean isPaginated, Integer page,
            Integer size) {
        Optional<UserProfile> user = userService.findByUsername(username);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("user");
        }

        Page<RecipeViewedEntity> resultPage;
        if (isPaginated) {
            resultPage = recipeViewedService.findByUserId(user.get().getId(), page, size);
        } else {
            resultPage = new PageImpl<>(recipeViewedService.findByUserId(user.get().getId()));
        }

        List<RecipeListResponseDto> listDto = resultPage.getContent().stream()
                .map(rv -> {
                    RecipeListResponseDto dto = new RecipeListResponseDto(
                            rv.getRecipe().getId(),
                            rv.getRecipe().getTitle(),
                            rv.getRecipe().getOverview(),
                            rv.getRecipe().getViews());
                    AuthorResponseDto author = new AuthorResponseDto(rv.getUser().getUsername(),
                            rv.getUser().getFullName(),
                            userService.getFollowerCountById(rv.getUser().getId()));
                    dto.setAuthor(author);
                    return dto;
                }).collect(Collectors.toList());

        return new PaginatedDto<>(
                listDto,
                resultPage.getNumber(),
                resultPage.getTotalPages(),
                resultPage.isLast(),
                resultPage.getTotalElements());
    }

    public RecipeViewedEntity saveViewedRecipe(RecipeViewedRequestDto requestDto) {
        Pair<String, UserEntity> user = userService.getByUsername(requestDto.getUsername());
        Pair<String, RecipeEntity> recipe = getById(requestDto.getRecipeId());
        return recipeViewedService.save(recipe.getSecond(), user.getSecond());
    }
}
