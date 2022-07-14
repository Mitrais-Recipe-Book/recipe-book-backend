package com.cdcone.recipy.recipe.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class CommentListResponseDto {
    private String username;
    private String fullname;
    private LocalDateTime date;
    private String comment;
}
