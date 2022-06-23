package com.cdcone.recipy.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;

import com.cdcone.recipy.dtoAccess.CommentListDto;
import com.cdcone.recipy.dtoRequest.AddCommentDto;
import com.cdcone.recipy.service.CommentService;

public class CommentControllerTest {

    private static final CommentService COMMENT_SERVICE = mock(CommentService.class);
    private final AddCommentDto ADD_COMMENT_DTO = mock(AddCommentDto.class);

    private static CommentController commentController;

    @BeforeAll
    static void init() {
        commentController = new CommentController(COMMENT_SERVICE);
    }

    @Test
    void addComment() {
        when(COMMENT_SERVICE.add(1L, ADD_COMMENT_DTO))
                .thenReturn("success");

        assertEquals(HttpStatus.OK,
                commentController.addComment(1L, ADD_COMMENT_DTO).getStatusCode());
    }

    @Test
    void failAddComment() {
        when(COMMENT_SERVICE.add(1L, ADD_COMMENT_DTO))
                .thenReturn("failed");

        assertEquals(HttpStatus.BAD_REQUEST,
                commentController.addComment(1L, ADD_COMMENT_DTO).getStatusCode());
    }

    @Test
    @SuppressWarnings("unchecked")
    void getComment(){
        Pair<String, Page<CommentListDto>> mockResult = Pair.of("success", mock(Page.class));

        when(COMMENT_SERVICE.getComment(1L, 0)).thenReturn(mockResult);

        assertEquals(HttpStatus.OK, commentController.getComment(1L, 0).getStatusCode());
    }

    @Test
    @SuppressWarnings("unchecked")
    void failGetComment(){
        Pair<String, Page<CommentListDto>> mockResult = Pair.of("failed", mock(Page.class));

        when(COMMENT_SERVICE.getComment(1L, 0)).thenReturn(mockResult);

        assertEquals(HttpStatus.BAD_REQUEST, commentController.getComment(1L, 0).getStatusCode());
    }
}
