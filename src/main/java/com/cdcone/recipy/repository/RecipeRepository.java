package com.cdcone.recipy.repository;

import com.cdcone.recipy.entity.RecipeEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {
    
}
