package com.cdcone.recipy.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.cdcone.recipy.dto.PhotoDto;
import com.cdcone.recipy.entity.UserEntity;
import com.cdcone.recipy.repository.RoleDao;
import com.cdcone.recipy.repository.UserDao;
import com.cdcone.recipy.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserController.class, UserService.class, BCryptPasswordEncoder.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @MockBean
    private RoleDao roleDao;

    @Autowired
    private UserController userController;

    @MockBean
    private UserDao userDao;

    /**
     * Method under test: {@link UserController#getAllUsers(Integer)}
     */
    @Test
    void testGetAllUsers() throws Exception {
        when(this.userDao.findAllPaged((org.springframework.data.domain.Pageable) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/user");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("page", String.valueOf(1));
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"status\":\"OK\",\"message\":\"SUCCESS\",\"payload\":[]}"));
    }

    /**
     * Method under test: {@link UserController#getProfilePhoto(String)}
     */
    @Test
    void testGetProfilePhoto() throws Exception {
        when(this.userDao.getProfilePhoto((String) any())).thenReturn(new PhotoDto("Type", null));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/user/{username}/photo",
                "janedoe");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link UserController#getProfilePhoto(String)}
     */
    @Test
    void testGetProfilePhoto2() throws Exception {
        when(this.userDao.getProfilePhoto((String) any())).thenReturn(null);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/user/{username}/photo",
                "janedoe");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link UserController#getAllUsers(Integer)}
     */
    @Test
    void testGetAllUsers2() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFullName("Dr Jane Doe");
        userEntity.setId(123L);
        userEntity.setPassword("iloveyou");
        userEntity.setProfilePhoto("AAAAAAAA".getBytes("UTF-8"));
        userEntity.setRecipes(new HashSet<>());
        userEntity.setRoles(new HashSet<>());
        userEntity.setUsername("janedoe");

        ArrayList<UserEntity> userEntityList = new ArrayList<>();
        userEntityList.add(userEntity);
        when(this.userDao.findAllPaged((org.springframework.data.domain.Pageable) any())).thenReturn(userEntityList);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/user");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("page", String.valueOf(1));
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":\"OK\",\"message\":\"SUCCESS\",\"payload\":[{\"id\":123,\"email\":\"jane.doe@example.org\",\"username\":"
                                        + "\"janedoe\",\"fullName\":\"Dr Jane Doe\"}]}"));
    }

    /**
     * Method under test: {@link UserController#getAllUsers(Integer)}
     */
    @Test
    void testGetAllUsers3() throws Exception {
        when(this.userDao.findAllPaged((org.springframework.data.domain.Pageable) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/user");
        getResult.contentType("https://example.org/example");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("page", String.valueOf(1));
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"status\":\"OK\",\"message\":\"SUCCESS\",\"payload\":[]}"));
    }

    /**
     * Method under test: {@link UserController#getByUsername(String)}
     */
    @Test
    void testGetByUsername() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFullName("Dr Jane Doe");
        userEntity.setId(123L);
        userEntity.setPassword("iloveyou");
        userEntity.setProfilePhoto("AAAAAAAA".getBytes("UTF-8"));
        userEntity.setRecipes(new HashSet<>());
        userEntity.setRoles(new HashSet<>());
        userEntity.setUsername("janedoe");
        Optional<UserEntity> ofResult = Optional.of(userEntity);
        when(this.userDao.findByUsername((String) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/user/{username}", "janedoe");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":\"OK\",\"message\":\"Success\",\"payload\":{\"id\":123,\"email\":\"jane.doe@example.org\",\"username\":"
                                        + "\"janedoe\",\"fullName\":\"Dr Jane Doe\"}}"));
    }

    /**
     * Method under test: {@link UserController#getByUsername(String)}
     */
    @Test
    void testGetByUsername2() throws Exception {
        when(this.userDao.findByUsername((String) any())).thenReturn(Optional.empty());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/user/{username}", "janedoe");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"status\":\"NOT_FOUND\",\"message\":\"User not found.\",\"payload\":null}"));
    }

    /**
     * Method under test: {@link UserController#getByUsername(String)}
     */
    @Test
    void testGetByUsername3() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFullName("Dr Jane Doe");
        userEntity.setId(123L);
        userEntity.setPassword("iloveyou");
        userEntity.setProfilePhoto("AAAAAAAA".getBytes("UTF-8"));
        userEntity.setRecipes(new HashSet<>());
        userEntity.setRoles(new HashSet<>());
        userEntity.setUsername("janedoe");
        Optional<UserEntity> ofResult = Optional.of(userEntity);
        when(this.userDao.findByUsername((String) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/user/{username}", "janedoe");
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":\"OK\",\"message\":\"Success\",\"payload\":{\"id\":123,\"email\":\"jane.doe@example.org\",\"username\":"
                                        + "\"janedoe\",\"fullName\":\"Dr Jane Doe\"}}"));
    }

    /**
     * Method under test: {@link UserController#saveProfilePhoto(String, org.springframework.web.multipart.MultipartFile)}
     */
    @Test
    void testSaveProfilePhoto() throws Exception {
        MockHttpServletRequestBuilder putResult = MockMvcRequestBuilders.put("/api/v1/user/{username}/photo", "janedoe");
        MockHttpServletRequestBuilder requestBuilder = putResult.param("photo", String.valueOf((Object) null));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(415));
    }
}

