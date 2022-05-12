package com.cdcone.recipy.dtoRequest;

import java.util.Set;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecipeDtoAdd {
    @NotEmpty
    private Long userId;
    private Set<Integer> tagIds;
    @NotEmpty
    private String title;
    private String overview;
    private String ingredients;
    private String content;
    private String videoURL;
    private boolean isDraft;
}
