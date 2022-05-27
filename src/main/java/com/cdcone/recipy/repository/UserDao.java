package com.cdcone.recipy.repository;

import com.cdcone.recipy.dtoAccess.FollowerDto;
import com.cdcone.recipy.dtoAccess.FollowingListDto;
import com.cdcone.recipy.dtoAccess.PhotoDto;
import com.cdcone.recipy.dtoAccess.UserProfile;
import com.cdcone.recipy.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

public interface UserDao extends JpaRepository<UserEntity, Long> {

        

    Optional<UserEntity> findByUsername(String username);

    @Query("SELECT new com.cdcone.recipy.dtoAccess.PhotoDto" +
            "(u.profilePhotoType, u.profilePhoto) FROM UserEntity u " +
            "WHERE u.username = :username")
    PhotoDto getProfilePhoto(@PathParam("username") String username);

    @Query("SELECT u FROM UserEntity u")
    Page<UserEntity> findAllPaged(Pageable pageable);

    @Query("SELECT NEW com.cdcone.recipy.dtoAccess.UserProfile " +
            "(u.id, u.username, u.fullName, SIZE(u.recipes), 0) " +
            "FROM UserEntity u WHERE u.username = :username")
    Optional<UserProfile> findDetailByUsername(@Param("username") String username);

    @Query(value = "SELECT u.id AS id, u.username AS username " +
            "FROM users_follows uf JOIN users u ON uf.user_id = u.id " +
            "WHERE uf.creator_id = :user_id", nativeQuery = true)
    List<FollowerDto> getFollowersById(@Param("user_id") Long id);

    @Query(value = "SELECT COUNT(uf) from users_follows uf " +
            "WHERE uf.creator_id = :id", nativeQuery = true)
    Long getFollowerCountById(@Param("id") Long id);
}
