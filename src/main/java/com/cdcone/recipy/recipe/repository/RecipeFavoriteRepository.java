package com.cdcone.recipy.recipe.repository;

import com.cdcone.recipy.recipe.entity.RecipeFavoriteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeFavoriteRepository extends JpaRepository<RecipeFavoriteEntity, Long> {

    @Query("SELECT f FROM RecipeFavoriteEntity f WHERE f.recipe.id = :recipeId AND f.user.id = :userId")
    Optional<RecipeFavoriteEntity> findByRecipeIdAndUserId(@Param("recipeId") Long recipeId, @Param("userId") Long userId);

    @Query("SELECT f FROM RecipeFavoriteEntity f WHERE f.user.id = :userId")
    Page<RecipeFavoriteEntity> findByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT f FROM RecipeFavoriteEntity f WHERE f.user.id = :userId")
    List<RecipeFavoriteEntity> findByUserId(@Param("userId") Long userId);
}
