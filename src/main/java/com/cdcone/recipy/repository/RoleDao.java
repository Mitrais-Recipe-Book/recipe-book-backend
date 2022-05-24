package com.cdcone.recipy.repository;

import com.cdcone.recipy.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface RoleDao extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByName(String name);

    @Query(value = "SELECT r.* FROM users_roles ur JOIN roles r ON ur.role_id = r.id " +
            "WHERE ur.user_id = :user_id", nativeQuery = true)
    Set<RoleEntity> findByUserId(@Param("user_id") Long userId);
}
