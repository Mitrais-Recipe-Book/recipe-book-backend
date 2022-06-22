package com.cdcone.recipy.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import com.cdcone.recipy.dtoAccess.*;
import com.cdcone.recipy.dtoRequest.*;
import com.cdcone.recipy.entity.CommentEntity;
import com.cdcone.recipy.entity.RecipeEntity;
import com.cdcone.recipy.entity.RecipeReactionEntity;
import com.cdcone.recipy.entity.TagEntity;
import com.cdcone.recipy.entity.UserEntity;
import com.cdcone.recipy.repository.RecipeReactionRepository;
import com.cdcone.recipy.repository.RecipeRepository;

import com.cdcone.recipy.repository.UserDao;
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
    private final UserDao userDao;
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
            if (e instanceof NullPointerException) {
                return "failed: " + e.getMessage();
            }
            e.printStackTrace();
            return "critical error: unpredicted cause, contact backend team";
        }
    }

    public Pair<String, Page<RecipeDtoList>> getPublishedRecipes(RecipeSearchDto dto) {
        if (dto.getTagId() == null || dto.getTagId().isEmpty()) {
            Pair<String, List<TagEntity>> tagResult = tagService.getAllTags();

            Set<Integer> allTags = tagResult.getSecond()
                    .stream()
                    .map(n -> n.getId())
                    .collect(Collectors.toSet());
            dto.setTagId(allTags);
        }

        try {
            Pageable pageable = PageRequest.of(dto.getPage(), 10, Sort.by("views").descending());
            Page<RecipeDtoList> result = recipeRepository.getPublishedRecipes(dto.getTitle(), dto.getAuthor(),
                    dto.getTagId(), pageable);

            result.getContent().forEach(i -> {
                UserEntity user = i.getUser();
                AuthorDto author = new AuthorDto(user.getUsername(),
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

    public Pair<String, Set<RecipeDtoList>> getPopularRecipes(int limit) {
        try {
            Set<RecipeDtoList> list = recipeRepository.getPopularRecipes()
                    .stream()
                    .limit(limit)
                    .map(i -> {
                        UserEntity user = i.getUser();
                        AuthorDto author = new AuthorDto(user.getUsername(),
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

    public Pair<String, Set<RecipeDtoList>> getDiscoverRecipes(int limit) {
        try {
            Set<RecipeDtoList> list = recipeRepository.getPopularRecipes()
                    .stream()
                    .limit(limit)
                    .map(i -> {
                        UserEntity user = i.getUser();
                        AuthorDto author = new AuthorDto(user.getUsername(),
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
        recipe.getSecond().setViews(views + 1);

        recipeRepository.save(recipe.getSecond());
        return "success: data updated";
    }

    public long totalRecipes() {
        return recipeRepository.count();
    }

    public PaginatedDto<UserRecipeDto> getByUsername(String userName, int page, boolean isDraft) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<UserRecipeDto> byUserId = recipeRepository.findByUsername(userName, isDraft, pageable);
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

    public Pair<String, RecipeDtoList> deleteRecipe(long recipeId) {
        Pair<String, RecipeEntity> recipe = getById(recipeId);

        if (recipe.getFirst().charAt(0) != 's') {
            return Pair.of(recipe.getFirst(), new RecipeDtoList());
        }

        RecipeDtoList result = new RecipeDtoList(recipe.getSecond().getId(),
                recipe.getSecond().getTitle(),
                recipe.getSecond().getOverview(),
                recipe.getSecond().getViews());

        recipeRepository.delete(recipe.getSecond());

        return Pair.of("success: data deleted", result);
    }

    public String addCommentToRecipe(Long recipeId, CommentEntity comment){        

        Pair<String, RecipeEntity> recipeEntity = getById(recipeId);

        if (recipeEntity.getFirst().charAt(0) != 's'){            
            return recipeEntity.getFirst();
        }

        if (recipeEntity.getSecond().isDraft()){
            return "failed: cannot comment on unpublished recipe";
        }

        recipeEntity.getSecond().getComments().add(comment);
        recipeRepository.save(recipeEntity.getSecond());

        return "success: comment added";
    }

    public Pair<String, RecipeReactionSummaryDto> getRecipeReaction(long recipeId, String username) {
        Optional<RecipeEntity> recipeOptional = recipeRepository.findById(recipeId);

        if(recipeOptional.isPresent()) {
            RecipeEntity recipe = recipeOptional.get();
            List<RecipeReactionDto> recipeReactionDtoList = recipeReactionRepository.getCountByReaction(recipeId);

            RecipeReactionResponseDto userReaction = null;
            if(!username.isBlank()) {
                Optional<UserEntity> userOptional = userDao.findByUsername(username);
                if(userOptional.isPresent()) {
                    RecipeReactionEntity userReactionEntity = recipeReactionRepository.findByRecipeIdAndUserId(recipe.getId(), userOptional.get().getId());
                    if(userReactionEntity!=null) {
                        userReaction = new RecipeReactionResponseDto(
                                userReactionEntity.getRecipe().getId(),
                                userReactionEntity.getUser().getId(),
                                userReactionEntity.getReaction(),
                                userReactionEntity.getTimestamp());
                    }
                }
            }

            RecipeReactionSummaryDto responseDto = new RecipeReactionSummaryDto(
                    recipe.getId(),
                    recipe.getTitle(),
                    recipeReactionDtoList,
                    userReaction
            );
            return Pair.of("success: data retrieved", responseDto);
        }
        return Pair.of("failed: data not found", new RecipeReactionSummaryDto());
    }

    public Pair<String, RecipeReactionEntity> saveRecipeReaction(long recipeId, RecipeReactionRequestDto requestDto) {
        Optional<UserEntity> userOptional = userDao.findByUsername(requestDto.getUsername());
        Optional<RecipeEntity> recipeOptional = recipeRepository.findById(recipeId);

        if(userOptional.isPresent() && recipeOptional.isPresent()) {
            RecipeReactionEntity entity = new RecipeReactionEntity(
                    userOptional.get(),
                    recipeOptional.get(),
                    requestDto.getReaction(),
                    LocalDateTime.now()
            );
            return Pair.of("success: data saved", recipeReactionRepository.save(entity));
        }
        return Pair.of("failed: data not found", new RecipeReactionEntity());
    }

    public Pair<String, RecipeReactionEntity> deleteRecipeReaction(long recipeId, RecipeReactionRequestDto requestDto) {
        Optional<UserEntity> userOptional = userDao.findByUsername(requestDto.getUsername());

        if(userOptional.isPresent()) {
            Optional<RecipeReactionEntity> reactionOptional = recipeReactionRepository.findByRecipeIdAndUserIdAndReaction(recipeId, userOptional.get().getId(), requestDto.getReaction());
            if(reactionOptional.isPresent()) {
                recipeReactionRepository.delete(reactionOptional.get());
                return Pair.of("success: data deleted", reactionOptional.get());
            }
        }
        return Pair.of("failed: data not found", new RecipeReactionEntity());
    }
}
