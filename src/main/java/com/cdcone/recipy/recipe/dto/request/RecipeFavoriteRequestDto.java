package com.cdcone.recipy.recipe.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecipeFavoriteRequestDto {

    String username;

    public RecipeFavoriteRequestDto(String username) {
        this.username = username;
    }
}
