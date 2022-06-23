package com.cdcone.recipy.repository;

import com.cdcone.recipy.dtoAccess.RecipeReactionDto;
import com.cdcone.recipy.entity.RecipeReactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeReactionRepository extends JpaRepository<RecipeReactionEntity, Long> {

    @Query("SELECT new com.cdcone.recipy.dtoAccess.RecipeReactionDto(CAST(reaction AS string), count(*))" +
            "FROM RecipeReactionEntity r " +
            "WHERE r.recipe.id = :recipeId GROUP BY r.reaction")
    List<RecipeReactionDto> getCountByReaction(@Param("recipeId") Long recipeId);

    @Query("SELECT r FROM RecipeReactionEntity r " +
            "WHERE r.recipe.id = :recipeId AND r.user.id = :userId")
    RecipeReactionEntity findByRecipeIdAndUserId(@Param("recipeId") Long recipeId, @Param("userId") Long userId);

    @Query("SELECT r FROM RecipeReactionEntity r " +
            "WHERE r.recipe.id = :recipeId AND r.user.id = :userId AND r.reaction = :reaction")
    Optional<RecipeReactionEntity> findByRecipeIdAndUserIdAndReaction(@Param("recipeId") Long recipeId, @Param("userId") Long userId, @Param("reaction") RecipeReactionEntity.Reaction reaction);

    @Query("SELECT COUNT(rr.reaction) FROM RecipeReactionEntity rr "
            + "JOIN rr.recipe r WHERE rr.reaction='LIKED' AND r.id=:userId")
    Integer getTotalRecipeLikeByUserId(@Param("userId") long userId);
}
