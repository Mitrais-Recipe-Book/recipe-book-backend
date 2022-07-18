package com.cdcone.recipy.recipe.repository;

import com.cdcone.recipy.recipe.dto.response.RecipeUserReactionResponseDto;
import com.cdcone.recipy.recipe.entity.RecipeReactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeReactionRepository extends JpaRepository<RecipeReactionEntity, Long> {

    @Query("SELECT new com.cdcone.recipy.recipe.dto.response.RecipeUserReactionResponseDto(CAST(reaction AS string), count(*))" +
            "FROM RecipeReactionEntity r " +
            "WHERE r.recipe.id = :recipeId GROUP BY r.reaction")
    List<RecipeUserReactionResponseDto> getCountByReaction(@Param("recipeId") Long recipeId);

    RecipeReactionEntity findByRecipeIdAndUserId(Long recipeId, Long userId);

    Optional<RecipeReactionEntity> findByRecipeIdAndUserIdAndReaction(Long recipeId,Long userId, RecipeReactionEntity.Reaction reaction);

    @Query("SELECT COUNT(rr.reaction) FROM RecipeReactionEntity rr "
            + "JOIN rr.recipe r WHERE rr.reaction='LIKED' AND r.id=:userId")
    Integer getTotalRecipeLikeByUserId(@Param("userId") long userId);
}
