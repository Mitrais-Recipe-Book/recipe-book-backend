package com.cdcone.recipy.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cdcone.recipy.entity.RecipeEntity;
import com.cdcone.recipy.entity.UserEntity;
import com.cdcone.recipy.repository.RecipeRepository;
import com.cdcone.recipy.repository.RoleDao;
import com.cdcone.recipy.repository.UserDao;
import com.cdcone.recipy.service.RecipeService;
import com.cdcone.recipy.service.UserService;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import com.cdcone.recipy.dto.RecipeDtoAdd;
import com.cdcone.recipy.dto.RecipeDtoList;
import com.cdcone.recipy.dto.RecipeSearchDto;
import com.cdcone.recipy.response.CommonResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RecipeControllerTest {
    @MockBean
    private RecipeRepository recipeRepository;

    @MockBean
    private RoleDao roleDao;

    @MockBean
    private UserDao userDao;

    @Autowired
    private RecipeController recipeController;

    private static RecipeDtoAdd recipeDtoAdd;

    @BeforeAll
    public static void init() {
        recipeDtoAdd = new RecipeDtoAdd(
                1L,
                "title",
                "overview",
                "ingredients",
                "content",
                "videoURL",
                true,
                null);
    }

    @Test
    @Order(1)
    public void addData() {
        CommonResponse response = recipeController.add(recipeDtoAdd);
        Assertions.assertEquals(HttpStatus.OK, response.getStatus());
    }

    /**
     * Method under test: {@link RecipeController#add(RecipeDtoAdd)}
     */
    @Test
    void testAdd() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   org.springframework.http.converter.HttpMessageConversionException: Type definition error: [simple type, class com.cdcone.recipy.dto.RecipeDtoAdd]; nested exception is com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Cannot construct instance of `com.cdcone.recipy.dto.RecipeDtoAdd` (no Creators, like default constructor, exist): cannot deserialize from Object value (no delegate- or property-based Creator)
        //    at [Source: (org.springframework.util.StreamUtils$NonClosingInputStream); line: 1, column: 2]
        //       at org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter.readJavaType(AbstractJackson2HttpMessageConverter.java:388)
        //       at org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter.read(AbstractJackson2HttpMessageConverter.java:343)
        //       at org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodArgumentResolver.readWithMessageConverters(AbstractMessageConverterMethodArgumentResolver.java:185)
        //       at org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor.readWithMessageConverters(RequestResponseBodyMethodProcessor.java:160)
        //       at org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor.resolveArgument(RequestResponseBodyMethodProcessor.java:133)
        //       at org.springframework.web.method.support.HandlerMethodArgumentResolverComposite.resolveArgument(HandlerMethodArgumentResolverComposite.java:122)
        //       at org.springframework.web.method.support.InvocableHandlerMethod.getMethodArgumentValues(InvocableHandlerMethod.java:179)
        //       at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:146)
        //       at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)
        //       at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:895)
        //       at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:808)
        //       at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)
        //       at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1067)
        //       at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:963)
        //       at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006)
        //       at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:909)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:681)
        //       at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883)
        //       at org.springframework.test.web.servlet.TestDispatcherServlet.service(TestDispatcherServlet.java:72)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:764)
        //       at org.springframework.mock.web.MockFilterChain$ServletFilterProxy.doFilter(MockFilterChain.java:167)
        //       at org.springframework.mock.web.MockFilterChain.doFilter(MockFilterChain.java:134)
        //       at org.springframework.test.web.servlet.MockMvc.perform(MockMvc.java:199)
        //   In order to prevent add(RecipeDtoAdd)
        //   from throwing HttpMessageConversionException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   add(RecipeDtoAdd).
        //   See https://diff.blue/R013 to resolve this issue.

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFullName("Dr Jane Doe");
        userEntity.setId(123L);
        userEntity.setPassword("iloveyou");
        userEntity.setProfilePhoto(new Byte[]{'A'});
        userEntity.setRecipes(new HashSet<>());
        userEntity.setRoles(new HashSet<>());
        userEntity.setUsername("janedoe");

        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setBannerImage(new Byte[]{'A'});
        recipeEntity.setContent("Not all who wander are lost");
        recipeEntity.setDateCreated(LocalDate.ofEpochDay(1L));
        recipeEntity.setDraft(true);
        recipeEntity.setId(123L);
        recipeEntity.setIngredients("Ingredients");
        recipeEntity.setOverview("Overview");
        recipeEntity.setTitle("Dr");
        recipeEntity.setUser(userEntity);
        recipeEntity.setVideoURL("https://example.org/example");
        recipeEntity.setViews(1);
        RecipeRepository recipeRepository = mock(RecipeRepository.class);
        when(recipeRepository.save((RecipeEntity) any())).thenReturn(recipeEntity);

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setEmail("jane.doe@example.org");
        userEntity1.setFullName("Dr Jane Doe");
        userEntity1.setId(123L);
        userEntity1.setPassword("iloveyou");
        userEntity1.setProfilePhoto(new Byte[]{'A'});
        userEntity1.setRecipes(new HashSet<>());
        userEntity1.setRoles(new HashSet<>());
        userEntity1.setUsername("janedoe");
        UserDao userDao = mock(UserDao.class);
        when(userDao.getById((Long) any())).thenReturn(userEntity1);
        RoleDao roleDao = mock(RoleDao.class);
        RecipeController recipeController = new RecipeController(
                new RecipeService(recipeRepository, new UserService(userDao, roleDao, new BCryptPasswordEncoder())));
        CommonResponse actualAddResult = recipeController.add(new RecipeDtoAdd(123L, "Dr", "Overview", "Ingredients",
                "Not all who wander are lost", "https://example.org/example", true, new Byte[]{'A'}));
        assertEquals("SUCCESS", actualAddResult.getMessage());
        assertEquals(HttpStatus.OK, actualAddResult.getStatus());
        assertNull(actualAddResult.getPayload());
        verify(recipeRepository).save((RecipeEntity) any());
        verify(userDao).getById((Long) any());
    }

    /**
     * Method under test: {@link RecipeController#add(RecipeDtoAdd)}
     */
    @Test
    void testAdd2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   org.springframework.http.converter.HttpMessageConversionException: Type definition error: [simple type, class com.cdcone.recipy.dto.RecipeDtoAdd]; nested exception is com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Cannot construct instance of `com.cdcone.recipy.dto.RecipeDtoAdd` (no Creators, like default constructor, exist): cannot deserialize from Object value (no delegate- or property-based Creator)
        //    at [Source: (org.springframework.util.StreamUtils$NonClosingInputStream); line: 1, column: 2]
        //       at org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter.readJavaType(AbstractJackson2HttpMessageConverter.java:388)
        //       at org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter.read(AbstractJackson2HttpMessageConverter.java:343)
        //       at org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodArgumentResolver.readWithMessageConverters(AbstractMessageConverterMethodArgumentResolver.java:185)
        //       at org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor.readWithMessageConverters(RequestResponseBodyMethodProcessor.java:160)
        //       at org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor.resolveArgument(RequestResponseBodyMethodProcessor.java:133)
        //       at org.springframework.web.method.support.HandlerMethodArgumentResolverComposite.resolveArgument(HandlerMethodArgumentResolverComposite.java:122)
        //       at org.springframework.web.method.support.InvocableHandlerMethod.getMethodArgumentValues(InvocableHandlerMethod.java:179)
        //       at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:146)
        //       at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)
        //       at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:895)
        //       at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:808)
        //       at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)
        //       at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1067)
        //       at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:963)
        //       at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006)
        //       at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:909)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:681)
        //       at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883)
        //       at org.springframework.test.web.servlet.TestDispatcherServlet.service(TestDispatcherServlet.java:72)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:764)
        //       at org.springframework.mock.web.MockFilterChain$ServletFilterProxy.doFilter(MockFilterChain.java:167)
        //       at org.springframework.mock.web.MockFilterChain.doFilter(MockFilterChain.java:134)
        //       at org.springframework.test.web.servlet.MockMvc.perform(MockMvc.java:199)
        //   In order to prevent add(RecipeDtoAdd)
        //   from throwing HttpMessageConversionException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   add(RecipeDtoAdd).
        //   See https://diff.blue/R013 to resolve this issue.

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFullName("Dr Jane Doe");
        userEntity.setId(123L);
        userEntity.setPassword("iloveyou");
        userEntity.setProfilePhoto(new Byte[]{'A'});
        userEntity.setRecipes(new HashSet<>());
        userEntity.setRoles(new HashSet<>());
        userEntity.setUsername("janedoe");

        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setBannerImage(new Byte[]{'A'});
        recipeEntity.setContent("Not all who wander are lost");
        recipeEntity.setDateCreated(LocalDate.ofEpochDay(1L));
        recipeEntity.setDraft(true);
        recipeEntity.setId(123L);
        recipeEntity.setIngredients("Ingredients");
        recipeEntity.setOverview("Overview");
        recipeEntity.setTitle("Dr");
        recipeEntity.setUser(userEntity);
        recipeEntity.setVideoURL("https://example.org/example");
        recipeEntity.setViews(1);
        RecipeRepository recipeRepository = mock(RecipeRepository.class);
        when(recipeRepository.save((RecipeEntity) any())).thenReturn(recipeEntity);
        RecipeController recipeController = new RecipeController(new RecipeService(recipeRepository, null));
        CommonResponse actualAddResult = recipeController.add(new RecipeDtoAdd(123L, "Dr", "Overview", "Ingredients",
                "Not all who wander are lost", "https://example.org/example", true, new Byte[]{'A'}));
        assertEquals("Cannot invoke \"com.cdcone.recipy.service.UserService.getById(long)\" because \"this.userService\""
                + " is null", actualAddResult.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, actualAddResult.getStatus());
        assertNull(actualAddResult.getPayload());
    }

    /**
     * Method under test: {@link RecipeController#add(RecipeDtoAdd)}
     */
    @Test
    void testAdd3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   org.springframework.http.converter.HttpMessageConversionException: Type definition error: [simple type, class com.cdcone.recipy.dto.RecipeDtoAdd]; nested exception is com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Cannot construct instance of `com.cdcone.recipy.dto.RecipeDtoAdd` (no Creators, like default constructor, exist): cannot deserialize from Object value (no delegate- or property-based Creator)
        //    at [Source: (org.springframework.util.StreamUtils$NonClosingInputStream); line: 1, column: 2]
        //       at org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter.readJavaType(AbstractJackson2HttpMessageConverter.java:388)
        //       at org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter.read(AbstractJackson2HttpMessageConverter.java:343)
        //       at org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodArgumentResolver.readWithMessageConverters(AbstractMessageConverterMethodArgumentResolver.java:185)
        //       at org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor.readWithMessageConverters(RequestResponseBodyMethodProcessor.java:160)
        //       at org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor.resolveArgument(RequestResponseBodyMethodProcessor.java:133)
        //       at org.springframework.web.method.support.HandlerMethodArgumentResolverComposite.resolveArgument(HandlerMethodArgumentResolverComposite.java:122)
        //       at org.springframework.web.method.support.InvocableHandlerMethod.getMethodArgumentValues(InvocableHandlerMethod.java:179)
        //       at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:146)
        //       at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)
        //       at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:895)
        //       at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:808)
        //       at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)
        //       at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1067)
        //       at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:963)
        //       at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006)
        //       at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:909)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:681)
        //       at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883)
        //       at org.springframework.test.web.servlet.TestDispatcherServlet.service(TestDispatcherServlet.java:72)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:764)
        //       at org.springframework.mock.web.MockFilterChain$ServletFilterProxy.doFilter(MockFilterChain.java:167)
        //       at org.springframework.mock.web.MockFilterChain.doFilter(MockFilterChain.java:134)
        //       at org.springframework.test.web.servlet.MockMvc.perform(MockMvc.java:199)
        //   In order to prevent add(RecipeDtoAdd)
        //   from throwing HttpMessageConversionException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   add(RecipeDtoAdd).
        //   See https://diff.blue/R013 to resolve this issue.

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFullName("Dr Jane Doe");
        userEntity.setId(123L);
        userEntity.setPassword("iloveyou");
        userEntity.setProfilePhoto(new Byte[]{'A'});
        userEntity.setRecipes(new HashSet<>());
        userEntity.setRoles(new HashSet<>());
        userEntity.setUsername("janedoe");

        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setBannerImage(new Byte[]{'A'});
        recipeEntity.setContent("Not all who wander are lost");
        recipeEntity.setDateCreated(LocalDate.ofEpochDay(1L));
        recipeEntity.setDraft(true);
        recipeEntity.setId(123L);
        recipeEntity.setIngredients("Ingredients");
        recipeEntity.setOverview("Overview");
        recipeEntity.setTitle("Dr");
        recipeEntity.setUser(userEntity);
        recipeEntity.setVideoURL("https://example.org/example");
        recipeEntity.setViews(1);
        RecipeRepository recipeRepository = mock(RecipeRepository.class);
        when(recipeRepository.save((RecipeEntity) any())).thenReturn(recipeEntity);

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setEmail("jane.doe@example.org");
        userEntity1.setFullName("Dr Jane Doe");
        userEntity1.setId(123L);
        userEntity1.setPassword("iloveyou");
        userEntity1.setProfilePhoto(new Byte[]{'A'});
        userEntity1.setRecipes(new HashSet<>());
        userEntity1.setRoles(new HashSet<>());
        userEntity1.setUsername("janedoe");
        UserService userService = mock(UserService.class);
        when(userService.getById(anyLong())).thenReturn(userEntity1);
        RecipeController recipeController = new RecipeController(new RecipeService(recipeRepository, userService));
        CommonResponse actualAddResult = recipeController.add(new RecipeDtoAdd(123L, "Dr", "Overview", "Ingredients",
                "Not all who wander are lost", "https://example.org/example", true, new Byte[]{'A'}));
        assertEquals("SUCCESS", actualAddResult.getMessage());
        assertEquals(HttpStatus.OK, actualAddResult.getStatus());
        assertNull(actualAddResult.getPayload());
        verify(recipeRepository).save((RecipeEntity) any());
        verify(userService).getById(anyLong());
    }

    /**
     * Method under test: {@link RecipeController#add(RecipeDtoAdd)}
     */
    @Test
    void testAdd4() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   org.springframework.http.converter.HttpMessageConversionException: Type definition error: [simple type, class com.cdcone.recipy.dto.RecipeDtoAdd]; nested exception is com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Cannot construct instance of `com.cdcone.recipy.dto.RecipeDtoAdd` (no Creators, like default constructor, exist): cannot deserialize from Object value (no delegate- or property-based Creator)
        //    at [Source: (org.springframework.util.StreamUtils$NonClosingInputStream); line: 1, column: 2]
        //       at org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter.readJavaType(AbstractJackson2HttpMessageConverter.java:388)
        //       at org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter.read(AbstractJackson2HttpMessageConverter.java:343)
        //       at org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodArgumentResolver.readWithMessageConverters(AbstractMessageConverterMethodArgumentResolver.java:185)
        //       at org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor.readWithMessageConverters(RequestResponseBodyMethodProcessor.java:160)
        //       at org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor.resolveArgument(RequestResponseBodyMethodProcessor.java:133)
        //       at org.springframework.web.method.support.HandlerMethodArgumentResolverComposite.resolveArgument(HandlerMethodArgumentResolverComposite.java:122)
        //       at org.springframework.web.method.support.InvocableHandlerMethod.getMethodArgumentValues(InvocableHandlerMethod.java:179)
        //       at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:146)
        //       at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)
        //       at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:895)
        //       at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:808)
        //       at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)
        //       at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1067)
        //       at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:963)
        //       at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006)
        //       at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:909)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:681)
        //       at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883)
        //       at org.springframework.test.web.servlet.TestDispatcherServlet.service(TestDispatcherServlet.java:72)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:764)
        //       at org.springframework.mock.web.MockFilterChain$ServletFilterProxy.doFilter(MockFilterChain.java:167)
        //       at org.springframework.mock.web.MockFilterChain.doFilter(MockFilterChain.java:134)
        //       at org.springframework.test.web.servlet.MockMvc.perform(MockMvc.java:199)
        //   In order to prevent add(RecipeDtoAdd)
        //   from throwing HttpMessageConversionException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   add(RecipeDtoAdd).
        //   See https://diff.blue/R013 to resolve this issue.

        RecipeController recipeController = new RecipeController(null);
        CommonResponse actualAddResult = recipeController.add(new RecipeDtoAdd(123L, "Dr", "Overview", "Ingredients",
                "Not all who wander are lost", "https://example.org/example", true, new Byte[]{'A'}));
        assertEquals(
                "Cannot invoke \"com.cdcone.recipy.service.RecipeService.add(com.cdcone.recipy.dto.RecipeDtoAdd)\" because"
                        + " \"this.recipeService\" is null",
                actualAddResult.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, actualAddResult.getStatus());
        assertNull(actualAddResult.getPayload());
    }

    /**
     * Method under test: {@link RecipeController#add(RecipeDtoAdd)}
     */
    @Test
    void testAdd5() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   org.springframework.http.converter.HttpMessageConversionException: Type definition error: [simple type, class com.cdcone.recipy.dto.RecipeDtoAdd]; nested exception is com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Cannot construct instance of `com.cdcone.recipy.dto.RecipeDtoAdd` (no Creators, like default constructor, exist): cannot deserialize from Object value (no delegate- or property-based Creator)
        //    at [Source: (org.springframework.util.StreamUtils$NonClosingInputStream); line: 1, column: 2]
        //       at org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter.readJavaType(AbstractJackson2HttpMessageConverter.java:388)
        //       at org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter.read(AbstractJackson2HttpMessageConverter.java:343)
        //       at org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodArgumentResolver.readWithMessageConverters(AbstractMessageConverterMethodArgumentResolver.java:185)
        //       at org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor.readWithMessageConverters(RequestResponseBodyMethodProcessor.java:160)
        //       at org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor.resolveArgument(RequestResponseBodyMethodProcessor.java:133)
        //       at org.springframework.web.method.support.HandlerMethodArgumentResolverComposite.resolveArgument(HandlerMethodArgumentResolverComposite.java:122)
        //       at org.springframework.web.method.support.InvocableHandlerMethod.getMethodArgumentValues(InvocableHandlerMethod.java:179)
        //       at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:146)
        //       at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)
        //       at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:895)
        //       at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:808)
        //       at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)
        //       at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1067)
        //       at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:963)
        //       at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006)
        //       at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:909)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:681)
        //       at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883)
        //       at org.springframework.test.web.servlet.TestDispatcherServlet.service(TestDispatcherServlet.java:72)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:764)
        //       at org.springframework.mock.web.MockFilterChain$ServletFilterProxy.doFilter(MockFilterChain.java:167)
        //       at org.springframework.mock.web.MockFilterChain.doFilter(MockFilterChain.java:134)
        //       at org.springframework.test.web.servlet.MockMvc.perform(MockMvc.java:199)
        //   In order to prevent add(RecipeDtoAdd)
        //   from throwing HttpMessageConversionException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   add(RecipeDtoAdd).
        //   See https://diff.blue/R013 to resolve this issue.

        RecipeService recipeService = mock(RecipeService.class);
        doNothing().when(recipeService).add((RecipeDtoAdd) any());
        RecipeController recipeController = new RecipeController(recipeService);
        CommonResponse actualAddResult = recipeController.add(new RecipeDtoAdd(123L, "Dr", "Overview", "Ingredients",
                "Not all who wander are lost", "https://example.org/example", true, new Byte[]{'A'}));
        assertEquals("SUCCESS", actualAddResult.getMessage());
        assertEquals(HttpStatus.OK, actualAddResult.getStatus());
        assertNull(actualAddResult.getPayload());
        verify(recipeService).add((RecipeDtoAdd) any());
    }

    /**
     * Method under test: {@link RecipeController#add(RecipeDtoAdd)}
     */
    @Test
    void testAdd6() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   org.springframework.http.converter.HttpMessageConversionException: Type definition error: [simple type, class com.cdcone.recipy.dto.RecipeDtoAdd]; nested exception is com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Cannot construct instance of `com.cdcone.recipy.dto.RecipeDtoAdd` (no Creators, like default constructor, exist): cannot deserialize from Object value (no delegate- or property-based Creator)
        //    at [Source: (org.springframework.util.StreamUtils$NonClosingInputStream); line: 1, column: 2]
        //       at org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter.readJavaType(AbstractJackson2HttpMessageConverter.java:388)
        //       at org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter.read(AbstractJackson2HttpMessageConverter.java:343)
        //       at org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodArgumentResolver.readWithMessageConverters(AbstractMessageConverterMethodArgumentResolver.java:185)
        //       at org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor.readWithMessageConverters(RequestResponseBodyMethodProcessor.java:160)
        //       at org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor.resolveArgument(RequestResponseBodyMethodProcessor.java:133)
        //       at org.springframework.web.method.support.HandlerMethodArgumentResolverComposite.resolveArgument(HandlerMethodArgumentResolverComposite.java:122)
        //       at org.springframework.web.method.support.InvocableHandlerMethod.getMethodArgumentValues(InvocableHandlerMethod.java:179)
        //       at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:146)
        //       at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)
        //       at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:895)
        //       at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:808)
        //       at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)
        //       at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1067)
        //       at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:963)
        //       at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006)
        //       at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:909)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:681)
        //       at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883)
        //       at org.springframework.test.web.servlet.TestDispatcherServlet.service(TestDispatcherServlet.java:72)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:764)
        //       at org.springframework.mock.web.MockFilterChain$ServletFilterProxy.doFilter(MockFilterChain.java:167)
        //       at org.springframework.mock.web.MockFilterChain.doFilter(MockFilterChain.java:134)
        //       at org.springframework.test.web.servlet.MockMvc.perform(MockMvc.java:199)
        //   In order to prevent add(RecipeDtoAdd)
        //   from throwing HttpMessageConversionException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   add(RecipeDtoAdd).
        //   See https://diff.blue/R013 to resolve this issue.

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFullName("Dr Jane Doe");
        userEntity.setId(123L);
        userEntity.setPassword("iloveyou");
        userEntity.setProfilePhoto(new Byte[]{'A'});
        userEntity.setRecipes(new HashSet<>());
        userEntity.setRoles(new HashSet<>());
        userEntity.setUsername("janedoe");

        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setBannerImage(new Byte[]{'A'});
        recipeEntity.setContent("Not all who wander are lost");
        recipeEntity.setDateCreated(LocalDate.ofEpochDay(1L));
        recipeEntity.setDraft(true);
        recipeEntity.setId(123L);
        recipeEntity.setIngredients("Ingredients");
        recipeEntity.setOverview("Overview");
        recipeEntity.setTitle("Dr");
        recipeEntity.setUser(userEntity);
        recipeEntity.setVideoURL("https://example.org/example");
        recipeEntity.setViews(1);
        RecipeRepository recipeRepository = mock(RecipeRepository.class);
        when(recipeRepository.save((RecipeEntity) any())).thenReturn(recipeEntity);

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setEmail("jane.doe@example.org");
        userEntity1.setFullName("Dr Jane Doe");
        userEntity1.setId(123L);
        userEntity1.setPassword("iloveyou");
        userEntity1.setProfilePhoto(new Byte[]{'A'});
        userEntity1.setRecipes(new HashSet<>());
        userEntity1.setRoles(new HashSet<>());
        userEntity1.setUsername("janedoe");
        UserService userService = mock(UserService.class);
        when(userService.getById(anyLong())).thenReturn(userEntity1);
        CommonResponse actualAddResult = (new RecipeController(new RecipeService(recipeRepository, userService))).add(null);
        assertEquals("Cannot invoke \"com.cdcone.recipy.dto.RecipeDtoAdd.getUserId()\" because \"dto\" is null",
                actualAddResult.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, actualAddResult.getStatus());
        assertNull(actualAddResult.getPayload());
    }

//    @Test
//    @Order(2)
//    public void cantAddData() {
//        CommonResponse response = recipeController.add(recipeDtoAdd);
//        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
//    }

//    @Test
//    @Order(2)
//    public void cantAddData() {
//        CommonResponse response = recipeController.add(recipeDtoAdd);
//        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
//    }

//    @Test
//    @Order(3)
//    public void getNewlyPublishedRecipes() {
//        Page<RecipeDtoList> result = (Page<RecipeDtoList>) recipeController
//                .getPublishedRecipe(new RecipeSearchDto(10, 0, "", null)).getPayload();
//        Assertions.assertEquals(1, result.getContent().size());
//    }

    /**
     * Method under test: {@link RecipeController#getPublishedRecipe(com.cdcone.recipy.dto.RecipeSearchDto)}
     */
    @Test
    void testGetPublishedRecipe() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/recipe/list");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.recipeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }
}