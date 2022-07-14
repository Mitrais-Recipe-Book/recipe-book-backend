package com.cdcone.recipy.recipe.repository;

import javax.websocket.server.PathParam;

import com.cdcone.recipy.recipe.dto.response.CommentListResponseDto;
import com.cdcone.recipy.recipe.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    @Query("SELECT NEW com.cdcone.recipy.recipe.dto.response.CommentListResponseDto " +
            " (c.user.username, c.user.fullName, c.date, c.comment)" +
            " FROM RecipeEntity r" +
            " JOIN r.comments c"+ 
            " WHERE r.id = :recipeId" + 
            " ORDER BY c.date desc")
    public Page<CommentListResponseDto> getComments(
            @PathParam("recipeId") Long recipeId,
            Pageable pageable);
}
