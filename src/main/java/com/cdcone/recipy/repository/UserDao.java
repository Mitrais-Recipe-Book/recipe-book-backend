package com.cdcone.recipy.repository;

import com.cdcone.recipy.dtoAccess.PhotoDto;
import com.cdcone.recipy.dtoAccess.UserDetailDto;
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

    @Query("SELECT new com.cdcone.recipy.dtoAccess.PhotoDto" +
            "(u.profilePhotoType, u.profilePhoto) FROM UserEntity u " +
            "WHERE u.username = :username")
    PhotoDto getProfilePhoto(@PathParam("username") String username);

    @Query("SELECT u FROM UserEntity u")
    Page<UserEntity> findAllPaged(Pageable pageable);

    @Query("SELECT NEW com.cdcone.recipy.dtoAccess.UserDetailDto" +
            "(u.id, u.username, u.fullName, SIZE(u.recipes), 0, 0) FROM UserEntity u " +
            "WHERE u.username = :username")
    Optional<UserDetailDto> findDetailByUsername(@Param("username") String username);
}
