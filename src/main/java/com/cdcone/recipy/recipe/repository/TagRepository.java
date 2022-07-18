package com.cdcone.recipy.recipe.repository;

import com.cdcone.recipy.recipe.dto.response.TagAdminResponseDto;
import com.cdcone.recipy.recipe.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;
import java.util.Optional;

public interface TagRepository extends JpaRepository<TagEntity, Integer> {

    Optional<TagEntity> findByName(String name);

    @Query("SELECT NEW com.cdcone.recipy.recipe.dto.response.TagAdminResponseDto " +
            "(t.id, t.name, t.views, COUNT(r.id))" +
            "FROM RecipeEntity r " +
            "RIGHT JOIN r.tags t " +
            "GROUP BY t.id " +
            "ORDER BY t.name ASC")
    Set<TagAdminResponseDto> findAllViewCount();

    List<TagEntity> findAllByOrderByNameAsc();
}
