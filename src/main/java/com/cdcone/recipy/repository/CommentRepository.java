package com.cdcone.recipy.repository;

import javax.websocket.server.PathParam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cdcone.recipy.dtoAccess.CommentListDto;
import com.cdcone.recipy.entity.CommentEntity;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    @Query("SELECT NEW com.cdcone.recipy.dtoAccess.CommentListDto " +
            " (c.user.username, c.user.fullName, c.date, c.comment)" +
            " FROM RecipeEntity r" +
            " JOIN r.comments c"+ 
            " WHERE r.id = :recipeId" + 
            " ORDER BY c.date desc")
    public Page<CommentListDto> getComments(
            @PathParam("recipeId") Long recipeId,
            Pageable pageable);
}
