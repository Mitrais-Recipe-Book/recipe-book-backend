package com.cdcone.recipy.recipe.dto.request;

import java.util.Set;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RecipeAddRequestDto {
    @NotEmpty
    private Long userId;
    private Set<Integer> tagIds;
    @NotEmpty
    private String title;
    private String overview;
    @NotEmpty
    private String ingredients;
    private String content;
    private String videoURL;
    private boolean isDraft;
}
