package com.cdcone.recipy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.util.Pair;

import com.cdcone.recipy.dtoAccess.CommentListDto;
import com.cdcone.recipy.dtoRequest.AddCommentDto;
import com.cdcone.recipy.entity.CommentEntity;
import com.cdcone.recipy.entity.UserEntity;
import com.cdcone.recipy.repository.CommentRepository;

public class CommentServiceTest {

        private CommentService commentService;

        private final UserService USER_SERVICE = mock(UserService.class);
        private final RecipeService RECIPE_SERVICE = mock(RecipeService.class);
        private final CommentRepository COMMENT_REPOSITORY = mock(CommentRepository.class);

        private final UserEntity USER_ENTITY = mock(UserEntity.class);
        private final CommentEntity COMMENT_ENTITY = mock(CommentEntity.class);

        private final AddCommentDto ADD_COMMENT_DTO = mock(AddCommentDto.class);

        @BeforeEach
        void init() {
                commentService = new CommentService(USER_SERVICE,
                                RECIPE_SERVICE,
                                COMMENT_REPOSITORY);
        }

        @Test
        void add() {
                Pair<String, UserEntity> mockResult = Pair.of("success", USER_ENTITY);

                when(ADD_COMMENT_DTO.getUsername()).thenReturn("user 1");
                when(ADD_COMMENT_DTO.getComment()).thenReturn("comment");

                when(USER_SERVICE.getByUsername(ADD_COMMENT_DTO.getUsername()))
                                .thenReturn(mockResult);

                when(COMMENT_REPOSITORY.save(any())).thenReturn(COMMENT_ENTITY);

                when(RECIPE_SERVICE.addCommentToRecipe(1L, COMMENT_ENTITY))
                                .thenReturn("success");

                assertEquals('s', commentService.add(1L, ADD_COMMENT_DTO).charAt(0));
        }

        @Test
        void failAddComment() {
                Pair<String, UserEntity> mockResult = Pair.of("failed", new UserEntity());

                when(ADD_COMMENT_DTO.getComment()).thenReturn("comment");

                when(USER_SERVICE.getByUsername(ADD_COMMENT_DTO.getUsername()))
                                .thenReturn(mockResult);

                when(COMMENT_REPOSITORY.save(any())).thenReturn(COMMENT_ENTITY);

                when(RECIPE_SERVICE.addCommentToRecipe(1L, COMMENT_ENTITY))
                                .thenReturn("success");

                assertEquals('f', commentService.add(1L, ADD_COMMENT_DTO).charAt(0));
        }

        @Test
        @SuppressWarnings("unchecked")
        void getComment() {
                Page<CommentListDto> mockResult = mock(Page.class);
                when(COMMENT_REPOSITORY.getComments(any(), any()))
                                .thenReturn(mockResult);

                assertEquals("success: data retrieved",
                                commentService.getComment(1L, 0).getFirst());
        }

        @Test
        @SuppressWarnings("unchecked")
        void failedGetComment(){
                Page<CommentListDto> mockResult = mock(Page.class);
                when(COMMENT_REPOSITORY.getComments(any(), any()))
                                .thenReturn(mockResult);

                assertEquals("failed: page index must not be less than zero",
                                commentService.getComment(1L, -1).getFirst());
        }
}
