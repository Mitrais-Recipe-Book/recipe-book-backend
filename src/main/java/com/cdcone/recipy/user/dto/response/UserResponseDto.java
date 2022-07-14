package com.cdcone.recipy.user.dto.response;

import com.cdcone.recipy.user.entity.RoleEntity;
import com.cdcone.recipy.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class UserResponseDto {

    private long id;
    private String email;
    private String username;
    private String fullName;
    private Set<String> roles;

    public static UserResponseDto toDto(UserEntity userEntity) {
        return new UserResponseDto(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getUsername(),
                userEntity.getFullName(),
                userEntity.getRoles()
                        .stream().map(RoleEntity::getName)
                        .collect(Collectors.toSet())
        );
    }
}
