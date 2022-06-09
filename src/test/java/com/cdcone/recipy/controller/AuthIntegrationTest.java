package com.cdcone.recipy.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.Charset;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testFailSignIn() throws Exception {
        String signIn = "{\"username\":\"unavailable\",\"password\":\"unavailable\"}";
        mockMvc.perform(post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset())
                        .content(signIn))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Wrong username or password"))
                .andReturn();
    }

    @Test
    @Sql(scripts = {"/user.sql"})
    void testSuccessSignIn() throws Exception {
        String signIn = "{\"username\":\"user1\",\"password\":\"password\"}";
        mockMvc.perform(post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset())
                        .content(signIn))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Success"))
                .andReturn();
    }

    @Test
    void testFailSignUpIfEmptyField() throws Exception {
        String signUp = "{\"email\":\"\",\"username\":\"\",\"password\":\"\",\"fullName\":\"\"}";
        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset())
                        .content(signUp))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Please fill out all required fields."))
                .andReturn();
    }

    @Test
    void testFailSignUpIfUserAlreadyExist() throws Exception {
        String signUp = "{\"email\":\"user1@test.com\",\"username\":\"user1\",\"password\":\"password\",\"fullName\":\"test test\"}";
        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset())
                        .content(signUp))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Failed to create user. Username or email is already exists"))
                .andReturn();
    }

    @Test
    @Sql(scripts = {"/role.sql"})
    void testSuccessSignUp() throws Exception {
        String signUp = "{\"email\":\"test@test.com\",\"username\":\"test\",\"password\":\"password\",\"fullName\":\"test test\"}";
        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset())
                        .content(signUp))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Success"))
                .andReturn();
    }

    @Test
    void testFailSignUpIfPasswordLessThanEightCharacters() throws Exception {
        String signUp = "{\"email\":\"test2@test.com\",\"username\":\"test2\",\"password\":\"pass\",\"fullName\":\"test test\"}";
        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset())
                        .content(signUp))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Password must be equal or more than 8 characters"))
                .andReturn();
    }
}
