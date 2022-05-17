package com.cdcone.recipy.service;

import com.cdcone.recipy.dtoAccess.UserDto;
import com.cdcone.recipy.dtoRequest.SignInDto;
import com.cdcone.recipy.util.CustomUser;
import com.cdcone.recipy.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public Optional<Map<String, Object>> auth(SignInDto signInDto) {
        String token;
        Map<String, Object> user;
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            signInDto.getUsername(),signInDto.getPassword()
                    ));
            CustomUser principal = (CustomUser) authentication.getPrincipal();
            token = jwtUtil.signJwt(principal);
            user = Map.of(
                    "access_token", token,
                    "user", new UserDto(
                            principal.getId(),
                            principal.getEmail(),
                            principal.getUsername(),
                            principal.getFullName()
                    )
            );
        } catch (AuthenticationException e) {
            user  = null;
        }
        return Optional.ofNullable(user);
    }
}
