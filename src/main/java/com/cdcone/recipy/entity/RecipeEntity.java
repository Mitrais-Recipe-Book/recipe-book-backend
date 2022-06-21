package com.cdcone.recipy.entity;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.Type;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity()
@Table(name = "recipes", uniqueConstraints = {
        @UniqueConstraint(name = "recipe_title_unique", columnNames = "title_lower_variant")
})
public class RecipeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "title_lower_variant", nullable = false)
    private String titleLowerVariant;

    @Column(name = "overview", columnDefinition = "TEXT")
    private String overview;

    @Column(name = "date_created", nullable = false)
    private LocalDate dateCreated;

    @Column(name = "ingredients", nullable = false, columnDefinition = "TEXT")
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
    @Column(name = "banner_image", columnDefinition = "BYTEA")
    @Type(type = "org.hibernate.type.BinaryType")
    @JsonIgnore
    private byte[] bannerImage;

    @Column(name = "banner_image_type")
    private String bannerImageType;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(joinColumns = @JoinColumn(name = "recipe_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<TagEntity> tags;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Set<CommentEntity> comments;

    public RecipeEntity(
            UserEntity userEntity,
            Set<TagEntity> tags,
            String title,
            String titleLowerVairant,
            String overview,
            LocalDate dateCreated,
            String ingredients,
            String content,
            String videoURL,
            int views,
            boolean isDraft) {

        if (title == null || title.isBlank()) {
            throw new NullPointerException("title cannot blank");
        }

        if (ingredients == null || ingredients.isBlank()) {
            throw new NullPointerException("ingredients cannot blank");
        }

        this.user = userEntity;
        this.tags = tags;
        this.title = title;
        this.titleLowerVariant = titleLowerVairant;
        this.overview = overview;
        this.dateCreated = dateCreated;
        this.ingredients = ingredients;
        this.content = content;
        this.videoURL = videoURL;
        this.views = views;
        this.isDraft = isDraft;
    }
}
