package com.cdcone.recipy.service;

import com.cdcone.recipy.dto.SignUpDto;
import com.cdcone.recipy.entity.RoleEntity;
import com.cdcone.recipy.entity.UserEntity;
import com.cdcone.recipy.repository.RoleDao;
import com.cdcone.recipy.repository.UserDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private static final UserDao userDao = mock(UserDao.class);
    private static final RoleDao roleDao = mock(RoleDao.class);
    private static final RoleEntity userRole = mock(RoleEntity.class);
    private static UserService userService;

    @BeforeAll
    public static void setUp() {
        userService = new UserService(userDao, roleDao, new BCryptPasswordEncoder());
        when(roleDao.findByName("User")).thenReturn(Optional.of(userRole));
    }

    @Test
    void addUser() {
        SignUpDto signUpDto = mock(SignUpDto.class);
        when(signUpDto.getEmail()).thenReturn("test@mail.com");
        when(signUpDto.getUsername()).thenReturn("test");
        when(signUpDto.getPassword()).thenReturn("password");
        when(signUpDto.getFullName()).thenReturn("test test");

        // Success
        Pair<Optional<UserEntity>, String> addUserSuccess = userService.addUser(signUpDto);
        verify(userDao).save(any(UserEntity.class));
        assertEquals("Success", addUserSuccess.getSecond());

        // Password < 6 characters
        when(signUpDto.getPassword()).thenReturn("s");
        Pair<Optional<UserEntity>, String> addUserFailed = userService.addUser(signUpDto);
        assertEquals("Password must be equal or more than 6 characters", addUserFailed.getSecond());
    }
}