package com.cdcone.recipy.service;

import com.cdcone.recipy.dtoRequest.SignInDto;
import com.cdcone.recipy.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public Optional<String> auth(SignInDto signInDto) {

        String token;
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInDto.getUsername(), signInDto.getPassword()));
            token = jwtUtil.signJwt((User) authentication.getPrincipal());
        } catch (AuthenticationException e) {
            token  = null;
        }
        return Optional.ofNullable(token);
    }
}
