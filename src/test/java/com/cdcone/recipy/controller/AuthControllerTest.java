package com.cdcone.recipy.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.cdcone.recipy.dto.SignInDto;
import com.cdcone.recipy.dto.SignUpDto;
import com.cdcone.recipy.entity.RecipeEntity;
import com.cdcone.recipy.entity.RoleEntity;
import com.cdcone.recipy.entity.UserEntity;
import com.cdcone.recipy.repository.RoleDao;
import com.cdcone.recipy.repository.UserDao;
import com.cdcone.recipy.service.AuthService;
import com.cdcone.recipy.service.UserService;
import com.cdcone.recipy.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.access.intercept.RunAsImplAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {AuthController.class, UserService.class, AuthService.class,
        BCryptPasswordEncoder.class, JwtUtil.class})
@ExtendWith(SpringExtension.class)
class AuthControllerTest {
    @Autowired
    private AuthController authController;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private RoleDao roleDao;

    @MockBean
    private UserDao userDao;

    /**
     * Method under test: {@link AuthController#signIn(SignInDto)}
     */
    @Test
    void testSignIn() throws Exception {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R015 Method may be nondeterministic.
        //   Diffblue Cover tried to run the created test twice, but it first passed the
        //   assertions and then failed.
        //   See https://diff.blue/R015 to resolve this issue.

        when(this.authenticationManager.authenticate((org.springframework.security.core.Authentication) any()))
                .thenReturn(new TestingAuthenticationToken(new User("janedoe", "iloveyou", new ArrayList<>()), "Credentials"));

        SignInDto signInDto = new SignInDto();
        signInDto.setPassword("iloveyou");
        signInDto.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(signInDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/auth/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.authController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"data\":{\"access_token\":\"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYW5lZG9lIiwicm9sZXMiOltdLC"
                                        + "JleHAiOjE2NTIwNTc0ODR9.Y0Oog3DW2bfGLsXnmtAa1DyUPNz1un68oZZ3RMB8EviIRZSJFcPrzrWLK9hUDlNbyCiYQrhgpVhQ1Tezx8837g"
                                        + "\"},\"message\":\"Success\",\"timestamp\":\"07:49:24 09/05/2022\"}"));
    }

    /**
     * Method under test: {@link AuthController#signUp(SignUpDto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testSignUp() throws UnsupportedEncodingException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "org.springframework.data.util.Pair.getFirst()" because "signUpUser" is null
        //       at com.cdcone.recipy.controller.AuthController.signUp(AuthController.java:35)
        //   In order to prevent signUp(SignUpDto)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   signUp(SignUpDto).
        //   See https://diff.blue/R013 to resolve this issue.

        UserEntity userEntity = mock(UserEntity.class);
        doNothing().when(userEntity).setEmail((String) any());
        doNothing().when(userEntity).setFullName((String) any());
        doNothing().when(userEntity).setId((Long) any());
        doNothing().when(userEntity).setPassword((String) any());
        doNothing().when(userEntity).setProfilePhoto((byte[]) any());
        doNothing().when(userEntity).setRecipes((java.util.Set<RecipeEntity>) any());
        doNothing().when(userEntity).setRoles((java.util.Set<RoleEntity>) any());
        doNothing().when(userEntity).setUsername((String) any());
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFullName("Dr Jane Doe");
        userEntity.setId(123L);
        userEntity.setPassword("iloveyou");
        userEntity.setProfilePhoto("AAAAAAAA".getBytes("UTF-8"));
        userEntity.setRecipes(new HashSet<>());
        userEntity.setRoles(new HashSet<>());
        userEntity.setUsername("janedoe");
        RoleEntity roleEntity = mock(RoleEntity.class);
        doNothing().when(roleEntity).setId((Long) any());
        doNothing().when(roleEntity).setName((String) any());
        roleEntity.setId(123L);
        roleEntity.setName("Name");
        Optional.of(roleEntity);
        UserService userService = mock(UserService.class);
        when(userService.addUser((SignUpDto) any())).thenReturn(null);
        userService.addUser(new SignUpDto("jane.doe@example.org", "janedoe", "iloveyou", "Dr Jane Doe"));

        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
        AuthController authController = new AuthController(userService,
                new AuthService(authenticationManager, new JwtUtil()));
        authController.signUp(new SignUpDto("jane.doe@example.org", "janedoe", "iloveyou", "Dr Jane Doe"));
    }
}

