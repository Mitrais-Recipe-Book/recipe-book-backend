package com.cdcone.recipy.repository;

import java.util.Set;

import javax.websocket.server.PathParam;

import com.cdcone.recipy.dto.RecipeDtoList;
import com.cdcone.recipy.entity.RecipeEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {

    // this is jpa, not sql
    @Query("SELECT new com.cdcone.recipy.dto.RecipeDtoList " +
            "(recipe.title, recipe.overview, recipe.views, user.fullName) " +
            "FROM RecipeEntity recipe " +
            "JOIN recipe.user user " +
            "LEFT JOIN recipe.tags tag " +
            "WHERE LOWER(recipe.title) LIKE LOWER(CONCAT('%', :titleName, '%')) " +
            "AND LOWER(user.fullName) LIKE LOWER(CONCAT('%', :authorName, '%')) " +
            "AND tag.id IN :tagId ")
    public Page<RecipeDtoList> getPublishedRecipes(
            @PathParam("titleName") String titleName,
            @PathParam("authorName") String authorName,
            @PathParam("tagId") Set<Integer> tagId,
            Pageable pageable);

    @Query("SELECT new com.cdcone.recipy.dto.RecipeDtoList " +
            "(recipe.title, recipe.overview, recipe.views, user.fullName) " +
            "FROM RecipeEntity recipe JOIN recipe.user user " +
            "ORDER BY recipe.views DESC ")
    public Set<RecipeDtoList> getPopularRecipes();
}
