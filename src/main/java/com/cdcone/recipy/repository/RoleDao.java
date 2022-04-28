package com.cdcone.recipy.repository;

import com.cdcone.recipy.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleDao extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByName(String name);
}
