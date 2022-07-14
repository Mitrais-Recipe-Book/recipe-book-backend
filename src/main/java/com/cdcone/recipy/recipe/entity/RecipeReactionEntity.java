package com.cdcone.recipy.recipe.entity;

import com.cdcone.recipy.recipe.entity.idclass.UserRecipeId;
import com.cdcone.recipy.user.entity.UserEntity;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "recipes_reaction")
@IdClass(UserRecipeId.class)
public class RecipeReactionEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @Id
    @ManyToOne
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    private RecipeEntity recipe;

    @Column(name = "reaction")
    @Enumerated(EnumType.STRING)
    private Reaction reaction;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    public RecipeReactionEntity(UserEntity user, RecipeEntity recipe, Reaction reaction, LocalDateTime timestamp) {
        this.user = user;
        this.recipe = recipe;
        this.reaction = reaction;
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RecipeReactionEntity that = (RecipeReactionEntity) o;
        return user != null && Objects.equals(user, that.user)
                && recipe != null && Objects.equals(recipe, that.recipe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, recipe);
    }

    public enum Reaction {
        LIKED,
        DISLIKED
    }
}
