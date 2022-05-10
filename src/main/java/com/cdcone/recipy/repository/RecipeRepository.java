package com.cdcone.recipy.repository;

import java.util.Set;

import javax.websocket.server.PathParam;

import com.cdcone.recipy.dto.RecipeDtoList;
import com.cdcone.recipy.entity.RecipeEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {

        // this is jpa, not sql
        @Query("SELECT new com.cdcone.recipy.dto.RecipeDtoList " +
                        "(recipe.title, recipe.overview, recipe.views, user.fullName) " +
                        "FROM RecipeEntity recipe " +
                        "JOIN recipe.user user " +
                        "LEFT JOIN recipe.tags tag " +
                        "WHERE recipe.isDraft = FALSE " +
                        "AND LOWER(recipe.title) LIKE LOWER(CONCAT('%', :titleName, '%')) " +
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
                        "WHERE recipe.isDraft = FALSE " +
                        "ORDER BY recipe.views DESC ")
        public Set<RecipeDtoList> getPopularRecipes();

        @Query("SELECT NEW com.cdcone.recipy.dto.RecipeDtoList" +
                        "(r.title, r.overview, r.views, r.user.fullName) " +
                        "FROM RecipeEntity r WHERE r.user.username = :username")
        Page<RecipeDtoList> findByUsername(@Param("username") String username, Pageable pageable);

        @Query("SELECT NEW com.cdcone.recipy.dto.RecipeDtoList " +
        "(r.title, r.overview, r.views, r.user.fullName) " +
        "FROM RecipeEntity r "+
        "WHERE r.isDraft = FALSE "+
        "ORDER BY r.id DESC")
        public Set<RecipeDtoList> getDiscoverRecipes();
}
