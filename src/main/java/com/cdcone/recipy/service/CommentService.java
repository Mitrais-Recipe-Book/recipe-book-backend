package com.cdcone.recipy.service;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.cdcone.recipy.dtoRequest.AddCommentDto;
import com.cdcone.recipy.entity.CommentEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserService userService;
    private final RecipeService recipeService;
}
