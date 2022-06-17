package com.cdcone.recipy.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.cdcone.recipy.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();
        if (path.startsWith("/api")) {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
                try {
                    String token = authorizationHeader.substring(7);
                    jwtUtil.validateJwt(token);
                    filterChain.doFilter(request, response);
                } catch (JWTVerificationException e) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    new ObjectMapper().writeValue(response.getWriter(), Map.of("error", "Unauthorized"));
                }
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.addHeader(HttpHeaders.WWW_AUTHENTICATE, "Bearer xxx");
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
