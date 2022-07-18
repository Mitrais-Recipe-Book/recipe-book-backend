package com.cdcone.recipy.recipe.dto.response;

import com.cdcone.recipy.recipe.entity.TagEntity;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class UserRecipeResponseDto {

    private final long id;
    private final String title;
    private final String overview;
    private final String authorName;
    private final int viewCount;
    private final String dateCreated;
    private Set<TagResponseDto> tags;

    public UserRecipeResponseDto(long id, String title, String overview,
                                 String authorName, int viewCount, LocalDate dateCreated) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.authorName = authorName;
        this.viewCount = viewCount;
        this.dateCreated = dateCreated.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
    }

    public UserRecipeResponseDto(long id, String title, String overview, String authorName, int viewCount, LocalDate dateCreated, Set<TagEntity> tags) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.authorName = authorName;
        this.viewCount = viewCount;
        this.dateCreated = dateCreated.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
        this.tags = tags.stream()
                .map(it -> new TagResponseDto(it.getId(), it.getName()))
                .collect(Collectors.toSet());
    }

    public void setTags(Set<TagEntity> tags) {
        this.tags = tags.stream()
                .map(it -> new TagResponseDto(it.getId(), it.getName()))
                .collect(Collectors.toSet());
    }
}
