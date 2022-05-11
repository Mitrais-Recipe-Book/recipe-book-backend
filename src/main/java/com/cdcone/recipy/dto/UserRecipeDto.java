package com.cdcone.recipy.dto;

import com.cdcone.recipy.entity.TagEntity;
import lombok.Getter;

import java.util.Set;

@Getter
public class UserRecipeDto {

    private final long id;
    private final String title;
    private final String overview;
    private final String authorName;
    private final int viewCount;
    private final Set<TagEntity> tags;

    public UserRecipeDto(long id, String title, String overview, String authorName, int viewCount, Set<TagEntity> tags) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.authorName = authorName;
        this.viewCount = viewCount;
        this.tags = tags;
    }
}
