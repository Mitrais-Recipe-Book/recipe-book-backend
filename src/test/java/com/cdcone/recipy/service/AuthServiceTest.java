package com.cdcone.recipy.service;

import com.cdcone.recipy.user.dto.request.SignInRequestDto;
import com.cdcone.recipy.user.service.AuthService;
import com.cdcone.recipy.security.CustomUser;
import com.cdcone.recipy.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {
    private final AuthenticationManager
            authenticationManager = mock(AuthenticationManager.class);
    private final JwtUtil jwtUtil = mock(JwtUtil.class);
    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService(authenticationManager, jwtUtil);
    }

    @Test
    void testFailToSignIn() {
        SignInRequestDto mockSignIn = mock(SignInRequestDto.class);
        AuthenticationException authenticationException =
                mock(AuthenticationException.class);
        when(authenticationManager
                .authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(authenticationException);
        Optional<Map<String, Object>> auth = authService.auth(mockSignIn);
        assertFalse(auth.isPresent());
    }

    @Test
    void testSuccessSignIn() {
        SignInRequestDto mockSignIn = mock(SignInRequestDto.class);
        Authentication mockAuth = mock(Authentication.class);
        CustomUser mockUser = mock(CustomUser.class);
        GrantedAuthority userAuthority = mock(GrantedAuthority.class);
        when(mockUser.getId()).thenReturn(1L);
        when(mockUser.getEmail()).thenReturn("mock@mail.com");
        when(mockUser.getUsername()).thenReturn("mockuser");
        when(mockUser.getFullName()).thenReturn("mock user");
        when(mockUser.getAuthorities()).thenReturn(List.of(userAuthority));
        when(mockSignIn.getUsername()).thenReturn("mockuser");
        when(mockSignIn.getPassword()).thenReturn("password");
        when(authenticationManager
                .authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuth);
        when(mockAuth.getPrincipal())
                .thenReturn(mockUser);
        when(jwtUtil.signJwt(mockUser)).thenReturn("token_value");
        Optional<Map<String, Object>> auth = authService.auth(mockSignIn);
        assertTrue(auth.isPresent());
        Map<String, Object> authData = auth.get();
        assertEquals("token_value", authData.get("access_token"));
    }
}