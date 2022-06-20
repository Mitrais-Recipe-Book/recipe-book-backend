package com.cdcone.recipy.dtoRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class AddCommentDto {
    private String username;
    private String comment;
}
