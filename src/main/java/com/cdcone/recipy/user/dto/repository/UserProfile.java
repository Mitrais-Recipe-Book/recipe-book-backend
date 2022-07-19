package com.cdcone.recipy.user.dto.repository;

import com.cdcone.recipy.user.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
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
