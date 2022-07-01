package com.cdcone.recipy.dtoAccess;

import com.cdcone.recipy.entity.RoleEntity;
import lombok.Getter;

import java.util.Set;

@Getter
public class UserProfile {

    private final long id;
    private final String username;
    private final String fullName;
    private int totalRecipes;
    private int recipeLikes;
    private Long followers;
    private Set<RoleEntity> roles;

    public UserProfile(long id, String username, String fullName, int totalRecipes, int recipeLikes) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.totalRecipes = totalRecipes;
        this.recipeLikes = recipeLikes;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    public void setFollowers(Long followers) {
        this.followers = followers;
    }
    
    public void setRecipeLikes(int recipeLikes) {
        this.recipeLikes = recipeLikes;
    }
    
    public void setTotalRecipes(int totalRecipes) {
        this.totalRecipes = totalRecipes;
    }
}
