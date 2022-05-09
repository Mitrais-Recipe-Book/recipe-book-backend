package com.cdcone.recipy.repository;

import com.cdcone.recipy.dto.PhotoDto;
import com.cdcone.recipy.dto.UserDetailDto;
import com.cdcone.recipy.dto.UserDto;
import com.cdcone.recipy.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.websocket.server.PathParam;
import java.util.Optional;

public interface UserDao extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    @Query("SELECT new com.cdcone.recipy.dto.PhotoDto" +
            "(u.profilePhotoType, u.profilePhoto) FROM UserEntity u " +
            "WHERE u.username = :username")
    PhotoDto getProfilePhoto(@PathParam("username") String username);

    @Query("SELECT NEW com.cdcone.recipy.dto.UserDto(u.id, u.email, u.username, u.fullName) FROM UserEntity u")
    Page<UserDto> findAllPaged(Pageable pageable);

    @Query("SELECT NEW com.cdcone.recipy.dto.UserDetailDto" +
            "(u.id, u.username, u.fullName, SIZE(u.recipes), 0, 0) FROM UserEntity u " +
            "WHERE u.username = :username")
    Optional<UserDetailDto> findDetailByUsername(@Param("username") String username);
}
