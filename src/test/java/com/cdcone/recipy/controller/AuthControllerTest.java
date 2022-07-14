package com.cdcone.recipy.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Optional;

import com.cdcone.recipy.user.controller.AuthController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;

import com.cdcone.recipy.user.dto.response.UserResponseDto;
import com.cdcone.recipy.user.dto.request.SignInRequestDto;
import com.cdcone.recipy.user.dto.request.SignUpRequestDto;
import com.cdcone.recipy.user.service.AuthService;
import com.cdcone.recipy.user.service.UserService;

public class AuthControllerTest {

    private static final UserService USER_SERVICE = mock(UserService.class);
    private static final AuthService AUTH_SERVICE = mock(AuthService.class);

    private static final SignUpRequestDto SIGN_UP_DTO = mock(SignUpRequestDto.class);
    private static final SignInRequestDto SIGN_IN_DTO = mock(SignInRequestDto.class);

    private static AuthController authController;

    @BeforeAll
    static void init() {
        authController = new AuthController(USER_SERVICE, AUTH_SERVICE);
        ;
    }

    @Test
    void signUp() {
        Pair<Optional<UserResponseDto>, String> mockResult = Pair.of(Optional.of(mock(UserResponseDto.class)), "any");

        when(USER_SERVICE.addUser(SIGN_UP_DTO))
                .thenReturn(mockResult);

        assertEquals(HttpStatus.OK, authController.signUp(SIGN_UP_DTO).getStatusCode());
    }

    @Test
    void failedSignUp() {
        Pair<Optional<UserResponseDto>, String> mockResult = Pair.of(Optional.empty(), "any");

        when(USER_SERVICE.addUser(SIGN_UP_DTO))
                .thenReturn(mockResult);

        assertNotEquals(HttpStatus.OK, authController.signUp(SIGN_UP_DTO).getStatusCode());
    }

    @Test
    @SuppressWarnings("unchecked")
    void signIn() {
        Map<String, Object> mockMap = mock(Map.class);
        Optional<Map<String, Object>> mockResult = Optional.of(mockMap);

        when(AUTH_SERVICE.auth(SIGN_IN_DTO))
                .thenReturn(mockResult);

        assertEquals(HttpStatus.OK,
                authController.signIn(SIGN_IN_DTO).getStatusCode());
    }

    @Test
    void failedSignIn() {
        Optional<Map<String, Object>> mockResult = Optional.empty();

        when(AUTH_SERVICE.auth(SIGN_IN_DTO))
                .thenReturn(mockResult);

        assertNotEquals(HttpStatus.OK,
                authController.signIn(SIGN_IN_DTO).getStatusCode());
    }
}
