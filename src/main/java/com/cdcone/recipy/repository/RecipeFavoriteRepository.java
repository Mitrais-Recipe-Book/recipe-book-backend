package com.cdcone.recipy.repository;

import com.cdcone.recipy.entity.RecipeFavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface RecipeFavoriteRepository extends JpaRepository<RecipeFavoriteEntity, Long> {

    @Query("SELECT f FROM RecipeFavoriteEntity f WHERE f.recipe.id = :recipeId AND f.user.id = :userId")
    Optional<RecipeFavoriteEntity> findByRecipeIdAndUserId(@Param("recipeId") Long recipeId, @Param("userId") Long userId);
}
