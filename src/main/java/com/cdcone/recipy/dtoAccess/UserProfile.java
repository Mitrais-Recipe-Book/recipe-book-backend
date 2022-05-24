package com.cdcone.recipy.dtoAccess;

import com.cdcone.recipy.entity.RoleEntity;
import lombok.Getter;

import java.util.Set;

@Getter
public class UserProfile {

    private final long id;
    private final String username;
    private final String fullName;
    private final int totalRecipes;
    private final int recipeLikes;
    private final int followers;
    private Set<RoleEntity> roles;

    public UserProfile(long id, String username, String fullName, int totalRecipes, int recipeLikes, int followers) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.totalRecipes = totalRecipes;
        this.recipeLikes = recipeLikes;
        this.followers = followers;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }
}
