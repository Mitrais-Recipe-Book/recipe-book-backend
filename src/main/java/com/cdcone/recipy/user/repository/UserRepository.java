package com.cdcone.recipy.user.repository;

import com.cdcone.recipy.user.dto.repository.FollowerDto;
import com.cdcone.recipy.dto.response.PhotoResponseDto;
import com.cdcone.recipy.user.dto.repository.UserProfile;
import com.cdcone.recipy.user.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    @Query("SELECT new com.cdcone.recipy.dto.response.PhotoResponseDto" +
            "(u.profilePhotoType, u.profilePhoto) FROM UserEntity u " +
            "WHERE u.username = :username")
    PhotoResponseDto getProfilePhoto(@PathParam("username") String username);

    @Query("SELECT u FROM UserEntity u")
    Page<UserEntity> findAllPaged(Pageable pageable);

    @Query("SELECT NEW com.cdcone.recipy.user.dto.repository.UserProfile " +
            "(u.id, u.username, u.fullName, 0, 0) " +
            "FROM UserEntity u WHERE u.username = :username")
    Optional<UserProfile> findDetailByUsername(@Param("username") String username);

    @Query(value = "SELECT u.id AS id, u.username AS username, " +
            "u.full_name AS fullName FROM users_follows uf " +
            "JOIN users u ON uf.user_id = u.id " +
            "WHERE uf.creator_id = :user_id", nativeQuery = true)
    List<FollowerDto> getFollowersById(@Param("user_id") Long id);

    @Query(value = "SELECT COUNT(uf.creator_id) from users_follows uf " +
            "WHERE uf.creator_id = :id", nativeQuery = true)
    Long getFollowerCountById(@Param("id") Long id);

    @Query(value = "SELECT EXISTS(SELECT uf FROM users_follows uf " +
            "WHERE uf.creator_id = :creator_id AND uf.user_id = :user_id)", nativeQuery = true)
    Boolean isFollowing(@Param("creator_id") Long creatorId, @Param("user_id") Long userId);

    @Query(value = "SELECT u FROM UserEntity u " +
            "LEFT JOIN u.roles r " +
            "WHERE r.name LIKE CONCAT('%',:rolename,'%')")
    List<UserEntity> getUsersWithRole(@PathParam("rolename") String rolename);
}
