package com.cdcone.recipy.user.service;

import com.cdcone.recipy.user.dto.response.UserResponseDto;
import com.cdcone.recipy.user.dto.request.SignInRequestDto;
import com.cdcone.recipy.security.CustomUser;
import com.cdcone.recipy.security.JwtUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthService(@Lazy AuthenticationManager authenticationManager, @Lazy JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public Optional<Map<String, Object>> auth(SignInRequestDto signInRequestDto) {
        String token;
        Map<String, Object> user;
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            signInRequestDto.getUsername(), signInRequestDto.getPassword()
                    ));
            CustomUser principal = (CustomUser) authentication.getPrincipal();
            token = jwtUtil.signJwt(principal);
            user = Map.of(
                    "access_token", token,
                    "user", new UserResponseDto(
                            principal.getId(),
                            principal.getEmail(),
                            principal.getUsername(),
                            principal.getFullName(),
                            principal.getAuthorities()
                                    .stream()
                                    .map(GrantedAuthority::getAuthority)
                                    .collect(Collectors.toSet())
                    )
            );
        } catch (AuthenticationException e) {
            user  = null;
        }
        return Optional.ofNullable(user);
    }
}
