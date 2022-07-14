package com.cdcone.recipy.recipe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FollowingListResponseDto {
    private Long id;
    private String username;
    private String fullName;
}
