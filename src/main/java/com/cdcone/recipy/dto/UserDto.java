package com.cdcone.recipy.dto;

import com.cdcone.recipy.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {

    private String email;
    private String username;
    private String fullName;

    public static UserDto toDto(UserEntity userEntity) {
        return new UserDto(
                userEntity.getEmail(),
                userEntity.getUsername(),
                userEntity.getFullName()
        );
    }
}
