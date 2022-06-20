package com.cdcone.recipy.dtoRequest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddCommentDto {
    private String username;
    private String comment;
}
