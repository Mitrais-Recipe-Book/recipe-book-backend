package com.cdcone.recipy.recipe.entity.idclass;

import java.io.Serializable;
import java.util.Objects;

public class UserRecipeId implements Serializable {

    private long user;
    private long recipe;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRecipeId that = (UserRecipeId) o;
        return user == that.user && recipe == that.recipe;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, recipe);
    }
}
