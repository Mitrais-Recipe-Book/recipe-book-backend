package com.cdcone.recipy.repository;

import com.cdcone.recipy.dto.PhotoDto;
import com.cdcone.recipy.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

public interface UserDao extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    @Query("SELECT new com.cdcone.recipy.dto.PhotoDto" +
            "(u.profilePhotoType, u.profilePhoto) FROM UserEntity u " +
            "WHERE u.username = :username")
    PhotoDto getProfilePhoto(@PathParam("username") String username);

    @Query("SELECT u FROM UserEntity u")
    List<UserEntity> findAllPaged(Pageable pageable);
}
