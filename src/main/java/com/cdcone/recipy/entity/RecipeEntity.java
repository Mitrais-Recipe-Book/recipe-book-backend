package com.cdcone.recipy.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "recipes")
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "recipe_title_unique", columnNames = "title")
})
public class RecipeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "overview", columnDefinition = "TEXT")
    private String overview;

    @Column(name = "date_created", nullable = false)
    private LocalDate dateCreated;

    @Column(name = "ingredients", columnDefinition = "TEXT")
    private String ingredients;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "video_url", columnDefinition = "TEXT")
    private String videoURL;

    @Column(name = "views", nullable = false)
    private int views;

    @Column(name = "is_draft", nullable = false)
    private boolean isDraft;

    @Lob
    @Column(name = "banner_image")
    private Byte[] bannerImage;

    /**
     * @param title
     * @param overview
     * @param dateCreated
     * @param ingredients
     * @param content
     * @param videoURL
     * @param views
     * @param isDraft
     * @param bannerImage
     */
    public RecipeEntity(
            String title,
            String overview,
            LocalDate dateCreated,
            String ingredients,
            String content,
            String videoURL,
            int views,
            boolean isDraft,
            Byte[] bannerImage) {
        this.title = title;
        this.overview = overview;
        this.dateCreated = dateCreated;
        this.ingredients = ingredients;
        this.content = content;
        this.videoURL = videoURL;
        this.views = views;
        this.isDraft = isDraft;
        this.bannerImage = bannerImage;
    }
}
