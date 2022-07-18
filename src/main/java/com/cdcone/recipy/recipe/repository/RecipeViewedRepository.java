package com.cdcone.recipy.recipe.repository;

import com.cdcone.recipy.recipe.entity.RecipeViewedEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeViewedRepository extends JpaRepository<RecipeViewedEntity, Long> {

    Page<RecipeViewedEntity> findByUserId(Long userId, Pageable pageable);

    List<RecipeViewedEntity> findByUserId(Long userId, Sort sort);
}
