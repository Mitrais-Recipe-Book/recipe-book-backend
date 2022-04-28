package com.cdcone.recipy.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "recipes")
public class RecipeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name ="title", nullable = false, unique = true)
    private String title;

    @Column(name="overview", columnDefinition = "TEXT")
    private String overview;

    @Column(name="date_created", nullable = false)
    private LocalDate dateCreated;

    @Column(name="ingredients", columnDefinition = "TEXT")
    private String ingredients;

    @Column(name="content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "video_url", columnDefinition = "TEXT")
    private String videoURL;

    @Column(name="views", nullable = false)
    private int views;

    @Column(name="is_draft", nullable = false)
    private boolean isDraft;

    @Lob
    private Byte[] bannerImage;
}
