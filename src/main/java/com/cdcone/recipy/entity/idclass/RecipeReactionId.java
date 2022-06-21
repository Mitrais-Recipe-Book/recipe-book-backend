package com.cdcone.recipy.entity.idclass;

import com.cdcone.recipy.entity.RecipeEntity;
import com.cdcone.recipy.entity.UserEntity;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

public class RecipeReactionId implements Serializable {

    private long user;
    private long recipe;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeReactionId that = (RecipeReactionId) o;
        return user == that.user && recipe == that.recipe;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, recipe);
    }
}
