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
            "(r.title, r.overview, r.views, u.fullName) " +
            "FROM RecipeEntity r JOIN r.user u " +
            "WHERE r.title LIKE %:titleName% " +
            "AND u.fullName LIKE %:authorName% ")
    public Page<RecipeDtoList> getPublishedRecipes(
            @PathParam("titleName") String titleName,
            @PathParam("authorName") String authorName,
            @PathParam("tags") Set<String> tags,
            Pageable pageable);

    @Query("SELECT new com.cdcone.recipy.dto.RecipeDtoList " +
            "(r.title, r.overview, r.views, u.fullName) " +
            "FROM RecipeEntity r JOIN r.user u " +
            "ORDER BY r.views DESC ")
    public Set<RecipeDtoList> getPopularRecipes();
}
