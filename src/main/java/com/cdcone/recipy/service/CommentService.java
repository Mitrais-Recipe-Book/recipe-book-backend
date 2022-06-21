package com.cdcone.recipy.service;

import java.time.LocalDateTime;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.cdcone.recipy.dtoRequest.AddCommentDto;
import com.cdcone.recipy.entity.CommentEntity;
import com.cdcone.recipy.entity.UserEntity;
import com.cdcone.recipy.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserService userService;
    private final RecipeService recipeService;

    private final CommentRepository commentRepository;

    public String add(Long recipeId, AddCommentDto dto) {
        Pair<String, UserEntity> userEntity = userService.getByUsername(dto.getUsername());

        if (userEntity.getFirst().charAt(0) != 's') {
            return userEntity.getFirst();
        }

        CommentEntity commentEntity = new CommentEntity(userEntity.getSecond(), LocalDateTime.now(), dto.getComment());
        commentEntity = commentRepository.save(commentEntity);

        return recipeService.addCommentToRecipe(recipeId, commentEntity);
    }
}
