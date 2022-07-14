package com.cdcone.recipy.recipe.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class AddCommentRequestDto {
    private String username;
    private String comment;
}
