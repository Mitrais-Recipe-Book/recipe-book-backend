package com.cdcone.recipy.recipe.entity;

import com.cdcone.recipy.recipe.entity.idclass.UserRecipeId;
import com.cdcone.recipy.user.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "recipes_viewed")
@IdClass(UserRecipeId.class)
public class RecipeViewedEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @Id
    @ManyToOne
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    private RecipeEntity recipe;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    public RecipeViewedEntity(UserEntity user, RecipeEntity recipe, LocalDateTime timestamp) {
        this.user = user;
        this.recipe = recipe;
        this.timestamp = timestamp;
    }
}
