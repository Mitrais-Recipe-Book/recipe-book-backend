package com.cdcone.recipy.recipe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorResponseDto {
    private String username;
    private String fullName;
    private Long authorFollowers;
}
