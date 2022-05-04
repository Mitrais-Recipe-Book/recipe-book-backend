package com.cdcone.recipy.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cdcone.recipy.entity.UserEntity;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

class UserDtoTest {
    /**
     * Method under test: {@link UserDto#toDto(UserEntity)}
     */
    @Test
    void testToDto() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFullName("Dr Jane Doe");
        userEntity.setId(123L);
        userEntity.setPassword("iloveyou");
        userEntity.setProfilePhoto(new Byte[]{'A'});
        userEntity.setRecipes(new HashSet<>());
        userEntity.setRoles(new HashSet<>());
        userEntity.setUsername("janedoe");
        UserDto actualToDtoResult = UserDto.toDto(userEntity);
        assertEquals("jane.doe@example.org", actualToDtoResult.getEmail());
        assertEquals("janedoe", actualToDtoResult.getUsername());
        assertEquals("Dr Jane Doe", actualToDtoResult.getFullName());
    }
}

