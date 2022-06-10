package com.cdcone.recipy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.Charset;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testFailSignIn() throws Exception {
        String signIn = "{\"username\":\"unavailable\",\"password\":\"unavailable\"}";
        mockMvc.perform(post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset())
                        .content(signIn))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Wrong username or password"))
                .andReturn();
    }

    @Test
    void testSuccessSignIn() throws Exception {
        String signIn = "{\"username\":\"user1\",\"password\":\"password\"}";
        mockMvc.perform(post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset())
                        .content(signIn))
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Failed to create user. Username or email is already exists"))
                .andReturn();
    }

    @Test
    void testSuccessSignUp() throws Exception {
        String signUp = "{\"email\":\"test@test.com\",\"username\":\"test\",\"password\":\"password\",\"fullName\":\"test test\"}";
        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset())
                        .content(signUp))
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Password must be equal or more than 8 characters"))
                .andReturn();
    }

    @Test
    void testSuccessGetByUsername() throws Exception {
        String username = "user1";
        mockMvc.perform(get("/api/v1/user/" + username))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("success: data retrieved"))
                .andReturn();
    }

    @Test
    void testFailGetByUsername() throws Exception {
        String username = "unavailablexxx";
        mockMvc.perform(get("/api/v1/user/" + username))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found."))
                .andReturn();
    }
    
    @Test
    void testSuccessGetRecipeByUsername() throws Exception {
    	String username = "user1";
    	mockMvc.perform(get("/api/v1/user/" + username + "/recipes"))
    	        .andExpect(status().isOk())
    	        .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.payload.totalPages").value(1))
                .andExpect(jsonPath("$.payload.data[0].title").value("string"))
    	        .andReturn();
    }
    
    @Test
    void testSuccessGetFollowerList() throws Exception {
    	Long userId = 1L;
    	mockMvc.perform(get("/api/v1/user/" + userId + "/followers"))
    	        .andExpect(status().isOk())
    	        .andExpect(jsonPath("$.message").value("success"))
    	        .andExpect(jsonPath("$.payload.*").isArray())
    	        .andExpect(jsonPath("$.payload.length()", is(4)))
    	        .andReturn();
    }
    
    @Test
    void testSuccessGetFollowingList() throws Exception {
    	Long userId = 10L;
    	mockMvc.perform(get("/api/v1/user/" + userId + "/follow-list"))
    	        .andExpect(status().isOk())
    	        .andExpect(jsonPath("$.message").value("succeed"))
    	        .andExpect(jsonPath("$.payload.*").isArray())
    	        .andExpect(jsonPath("$.payload.length()", is(1)))
    	        .andReturn();
    }
}
