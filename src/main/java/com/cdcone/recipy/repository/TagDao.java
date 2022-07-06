package com.cdcone.recipy.repository;

import com.cdcone.recipy.dtoAccess.TagDtoAdmin;
import com.cdcone.recipy.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;
import java.util.Optional;

public interface TagDao extends JpaRepository<TagEntity, Integer> {

    Optional<TagEntity> findByName(String name);

    @Query(value = "SELECT t.* FROM tags t JOIN recipes_tags rt ON rt.recipe_id = :recipeId WHERE t.id = rt.tag_id", nativeQuery = true)
    Set<TagEntity> findByRecipeId(@Param("recipeId") Long recipeId);

    @Query("SELECT NEW com.cdcone.recipy.dtoAccess.TagDtoAdmin " +
            "(t.id, t.name, t.views, "+
            "(SELECT COUNT(*) FROM RecipeEntity r LEFT JOIN r.tags) " +
            ") FROM TagEntity t " +
            "ORDER BY t.name DESC")
    Set<TagDtoAdmin> findAllViewCount();
}
