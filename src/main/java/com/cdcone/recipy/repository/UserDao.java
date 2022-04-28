package com.cdcone.recipy.repository;

import com.cdcone.recipy.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserEntity, Long> {
}
