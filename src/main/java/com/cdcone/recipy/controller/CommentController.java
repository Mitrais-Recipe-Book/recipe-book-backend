package com.cdcone.recipy.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdcone.recipy.dtoRequest.AddCommentDto;
import com.cdcone.recipy.response.CommonResponse;
import com.cdcone.recipy.service.CommentService;

import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/recipe")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("{recipeId}/comment/add")
    public ResponseEntity<CommonResponse> addComment(@PathVariable(name = "recipeId") Long recipeId,
            AddCommentDto dto) {
        String result = commentService.add(recipeId, dto);

        if (result.charAt(0) == 's') {
            return ResponseEntity.ok().body(new CommonResponse(result));
        }

        return ResponseEntity.badRequest().body(new CommonResponse(result));
    }
}
