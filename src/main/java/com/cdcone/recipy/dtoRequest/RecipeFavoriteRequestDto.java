package com.cdcone.recipy.dtoRequest;

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
