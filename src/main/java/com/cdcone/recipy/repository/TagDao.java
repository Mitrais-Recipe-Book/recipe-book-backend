package com.cdcone.recipy.repository;

import com.cdcone.recipy.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagDao extends JpaRepository<TagEntity, Integer> {

    Optional<TagEntity> findByName(String name);
}
