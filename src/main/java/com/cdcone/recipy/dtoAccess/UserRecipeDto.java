package com.cdcone.recipy.dtoAccess;

import com.cdcone.recipy.entity.TagEntity;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Getter
public class UserRecipeDto {

    private final long id;
    private final String title;
    private final String overview;
    private final String authorName;
    private final int viewCount;
    private final String dateCreated;
    private Set<TagEntity> tags;

    public UserRecipeDto(long id, String title, String overview, 
            String authorName, int viewCount, LocalDate dateCreated) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.authorName = authorName;
        this.viewCount = viewCount;
        this.dateCreated = dateCreated.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
    }

    public void setTags(Set<TagEntity> tags) {
        this.tags = tags;
    }
}
