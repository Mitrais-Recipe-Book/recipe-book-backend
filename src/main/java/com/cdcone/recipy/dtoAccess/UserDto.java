package com.cdcone.recipy.dtoAccess;

import com.cdcone.recipy.entity.RoleEntity;
import com.cdcone.recipy.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class UserDto {

    private long id;
    private String email;
    private String username;
    private String fullName;
    private Set<String> roles;

    public static UserDto toDto(UserEntity userEntity) {
        return new UserDto(
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
