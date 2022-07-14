package com.cdcone.recipy.recipe.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cdcone.recipy.recipe.dto.response.CommentListResponseDto;
import com.cdcone.recipy.recipe.dto.request.AddCommentRequestDto;
import com.cdcone.recipy.dto.response.PaginatedDto;
import com.cdcone.recipy.dto.response.CommonResponse;
import com.cdcone.recipy.recipe.service.CommentService;
import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/recipe")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("{recipeId}/comment/add")
    public ResponseEntity<CommonResponse> addComment(@PathVariable(name = "recipeId") Long recipeId,
            @RequestBody AddCommentRequestDto dto) {
        String result = commentService.add(recipeId, dto);

        if (result.charAt(0) == 's') {
            return ResponseEntity.ok().body(new CommonResponse(result));
        }

        return ResponseEntity.badRequest().body(new CommonResponse(result));
    }

    @GetMapping("{recipeId}/comments")
    public ResponseEntity<CommonResponse> getComment(@PathVariable(name = "recipeId") Long recipeId,
            @RequestParam(defaultValue = "0") int page) {
        Pair<String, Page<CommentListResponseDto>> result = commentService.getComment(recipeId, page);
        PaginatedDto<CommentListResponseDto> paginated = new PaginatedDto<CommentListResponseDto>(result.getSecond().getContent(),
                page, result.getSecond().getTotalPages(),
                result.getSecond().isLast(),
                result.getSecond().getTotalElements());

        if (result.getFirst().charAt(0) == 's') {
            return ResponseEntity.ok().body(new CommonResponse(result.getFirst(), paginated));
        }

        return ResponseEntity.badRequest().body(new CommonResponse(result.getFirst()));
    }
}
