package com.cdcone.recipy.recipe.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.cdcone.recipy.recipe.dto.response.CommentListResponseDto;
import com.cdcone.recipy.recipe.dto.request.AddCommentRequestDto;
import com.cdcone.recipy.recipe.entity.CommentEntity;
import com.cdcone.recipy.recipe.repository.CommentRepository;
import com.cdcone.recipy.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.cdcone.recipy.user.entity.UserEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserService userService;
    private final RecipeService recipeService;

    private final CommentRepository commentRepository;

    public String add(Long recipeId, AddCommentRequestDto dto) {
        if (dto.getComment() == null || dto.getComment().isBlank() || dto.getComment().isEmpty()){
            return "failed: cannot post null comment";
        }

        Pair<String, UserEntity> userEntity = userService.getByUsername(dto.getUsername());

        if (userEntity.getFirst().charAt(0) != 's') {
            return userEntity.getFirst();
        }

        CommentEntity commentEntity = new CommentEntity(userEntity.getSecond(), LocalDateTime.now(), dto.getComment());
        commentEntity = commentRepository.save(commentEntity);

        return recipeService.addCommentToRecipe(recipeId, commentEntity);
    }

    public Pair<String, Page<CommentListResponseDto>> getComment(Long recipeId, int page) {
        try {
            Pageable pageable = PageRequest.of(page, 10);
            Page<CommentListResponseDto> result = commentRepository.getComments(recipeId,
            pageable);

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
}
