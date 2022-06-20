package com.cdcone.recipy.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserService userService;
    private final RecipeService recipeService;
}
