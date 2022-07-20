package com.cdcone.recipy.recipe.repository;

import java.util.Set;

import javax.websocket.server.PathParam;

import com.cdcone.recipy.recipe.dto.response.RecipeListResponseDto;
import com.cdcone.recipy.recipe.entity.RecipeEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {

        // this is jpa, not sql
        @Query("SELECT DISTINCT NEW com.cdcone.recipy.recipe.dto.response.RecipeListResponseDto " +
                        "(recipe.id, recipe.title, recipe.overview, recipe.views, user) " +
                        "FROM RecipeEntity recipe " +
                        "JOIN recipe.user user " +
                        "LEFT JOIN recipe.tags tag " +
                        "WHERE recipe.isDraft = FALSE " +
                        "AND (LOWER(user.fullName) LIKE LOWER(CONCAT('%', :authorName, '%')) " +
                        "OR LOWER(user.username) LIKE LOWER(CONCAT('%', :authorName, '%'))) " +
                        "AND LOWER(recipe.title) LIKE LOWER(CONCAT('%', :titleName, '%')) " +
                        "AND tag.id IN :tagId ")
        public Page<RecipeListResponseDto> getPublishedRecipes(
                        @PathParam("titleName") String titleName,
                        @PathParam("authorName") String authorName,
                        @PathParam("tagId") Set<Integer> tagId,
                        Pageable pageable);

        @Query("SELECT NEW com.cdcone.recipy.recipe.dto.response.RecipeListResponseDto " +
                        "(recipe.id, recipe.title, recipe.overview, recipe.views, user) " +
                        "FROM RecipeEntity recipe JOIN recipe.user user " +
                        "WHERE recipe.isDraft = FALSE " +
                        "ORDER BY recipe.views DESC ")
        public Set<RecipeListResponseDto> getPopularRecipes();

        @Query("SELECT NEW com.cdcone.recipy.recipe.dto.response.RecipeListResponseDto " +
                        "(recipe.id, recipe.title, recipe.overview, recipe.views, recipe.user) " +
                        "FROM RecipeEntity recipe " +
                        "WHERE recipe.isDraft = FALSE " +
                        "ORDER BY recipe.id DESC")
        public Set<RecipeListResponseDto> getDiscoverRecipes();        

        Page<RecipeEntity> findByUserUsernameAndIsDraftIs(String username, boolean isDraft, Pageable pageable);
}
