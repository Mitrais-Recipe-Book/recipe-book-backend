package com.cdcone.recipy.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cdcone.recipy.dto.RecipeDtoAdd;
import com.cdcone.recipy.entity.RecipeEntity;
import com.cdcone.recipy.entity.UserEntity;
import com.cdcone.recipy.repository.RecipeRepository;
import com.cdcone.recipy.repository.RoleDao;
import com.cdcone.recipy.repository.UserDao;
import com.cdcone.recipy.response.CommonResponse;
import com.cdcone.recipy.service.RecipeService;
import com.cdcone.recipy.service.UserService;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {RecipeController.class, RecipeService.class, UserService.class,
        BCryptPasswordEncoder.class})
@ExtendWith(SpringExtension.class)
class RecipeControllerTest {
    @Autowired
    private RecipeController recipeController;

    @MockBean
    private RecipeRepository recipeRepository;

    @MockBean
    private RoleDao roleDao;

    @MockBean
    private UserDao userDao;

    /**
     * Method under test: {@link RecipeController#add(RecipeDtoAdd)}
     */
    @Test
    void testAdd() throws UnsupportedEncodingException {
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
        userEntity.setProfilePhoto("AAAAAAAA".getBytes("UTF-8"));
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
        recipeEntity.setTags(new HashSet<>());
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
        userEntity1.setProfilePhoto("AAAAAAAA".getBytes("UTF-8"));
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
    void testAdd2() throws UnsupportedEncodingException {
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
        userEntity.setProfilePhoto("AAAAAAAA".getBytes("UTF-8"));
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
        recipeEntity.setTags(new HashSet<>());
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
    void testAdd3() throws UnsupportedEncodingException {
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
        userEntity.setProfilePhoto("AAAAAAAA".getBytes("UTF-8"));
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
        recipeEntity.setTags(new HashSet<>());
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
        userEntity1.setProfilePhoto("AAAAAAAA".getBytes("UTF-8"));
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
     * Method under test: {@link RecipeController#getPopularRecipes(int)}
     */
    @Test
    void testGetPopularRecipes() throws Exception {
        when(this.recipeRepository.getPopularRecipes()).thenReturn(new HashSet<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/recipe/popular");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("limit", String.valueOf(1));
        MockMvcBuilders.standaloneSetup(this.recipeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"status\":\"OK\",\"message\":\"SUCCESS\",\"payload\":[]}"));
    }

    /**
     * Method under test: {@link RecipeController#getPopularRecipes(int)}
     */
    @Test
    void testGetPopularRecipes2() throws Exception {
        when(this.recipeRepository.getPopularRecipes()).thenReturn(new HashSet<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/recipe/popular");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("limit", String.valueOf(-1));
        MockMvcBuilders.standaloneSetup(this.recipeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"-1\",\"payload\":null}"));
    }

    /**
     * Method under test: {@link RecipeController#getPopularRecipes(int)}
     */
    @Test
    void testGetPopularRecipes3() throws Exception {
        when(this.recipeRepository.getPopularRecipes()).thenReturn(new HashSet<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/recipe/popular");
        getResult.contentType("https://example.org/example");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("limit", String.valueOf(1));
        MockMvcBuilders.standaloneSetup(this.recipeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"status\":\"OK\",\"message\":\"SUCCESS\",\"payload\":[]}"));
    }

    /**
     * Method under test: {@link RecipeController#addViewer(Long)}
     */
    @Test
    void testAddViewer() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFullName("Dr Jane Doe");
        userEntity.setId(123L);
        userEntity.setPassword("iloveyou");
        userEntity.setProfilePhoto("AAAAAAAA".getBytes("UTF-8"));
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
        recipeEntity.setTags(new HashSet<>());
        recipeEntity.setTitle("Dr");
        recipeEntity.setUser(userEntity);
        recipeEntity.setVideoURL("https://example.org/example");
        recipeEntity.setViews(1);
        Optional<RecipeEntity> ofResult = Optional.of(recipeEntity);

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setEmail("jane.doe@example.org");
        userEntity1.setFullName("Dr Jane Doe");
        userEntity1.setId(123L);
        userEntity1.setPassword("iloveyou");
        userEntity1.setProfilePhoto("AAAAAAAA".getBytes("UTF-8"));
        userEntity1.setRecipes(new HashSet<>());
        userEntity1.setRoles(new HashSet<>());
        userEntity1.setUsername("janedoe");

        RecipeEntity recipeEntity1 = new RecipeEntity();
        recipeEntity1.setBannerImage(new Byte[]{'A'});
        recipeEntity1.setContent("Not all who wander are lost");
        recipeEntity1.setDateCreated(LocalDate.ofEpochDay(1L));
        recipeEntity1.setDraft(true);
        recipeEntity1.setId(123L);
        recipeEntity1.setIngredients("Ingredients");
        recipeEntity1.setOverview("Overview");
        recipeEntity1.setTags(new HashSet<>());
        recipeEntity1.setTitle("Dr");
        recipeEntity1.setUser(userEntity1);
        recipeEntity1.setVideoURL("https://example.org/example");
        recipeEntity1.setViews(1);
        when(this.recipeRepository.save((RecipeEntity) any())).thenReturn(recipeEntity1);
        when(this.recipeRepository.findById((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder putResult = MockMvcRequestBuilders.put("/api/v1/recipe/view");
        MockHttpServletRequestBuilder requestBuilder = putResult.param("recipeId", String.valueOf(1L));
        MockMvcBuilders.standaloneSetup(this.recipeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"status\":\"OK\",\"message\":\"SUCCESS\",\"payload\":null}"));
    }

    /**
     * Method under test: {@link RecipeController#getPublishedRecipes(com.cdcone.recipy.dto.RecipeSearchDto)}
     */
    @Test
    void testGetPublishedRecipes() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/recipe/list");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.recipeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Method under test: {@link RecipeController#getRecipeImage(Long)}
     */
    @Test
    void testGetRecipeImage() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFullName("Dr Jane Doe");
        userEntity.setId(123L);
        userEntity.setPassword("iloveyou");
        userEntity.setProfilePhoto("AAAAAAAA".getBytes("UTF-8"));
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
        recipeEntity.setTags(new HashSet<>());
        recipeEntity.setTitle("Dr");
        recipeEntity.setUser(userEntity);
        recipeEntity.setVideoURL("https://example.org/example");
        recipeEntity.setViews(1);
        Optional<RecipeEntity> ofResult = Optional.of(recipeEntity);
        when(this.recipeRepository.findById((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/recipe/image/*");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("recipeId", String.valueOf(1L));
        MockMvcBuilders.standaloneSetup(this.recipeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"status\":\"OK\",\"message\":\"filename:\\\\profile-Dr\",\"payload\":[65]}"));
    }

    /**
     * Method under test: {@link RecipeController#getRecipeImage(Long)}
     */
    @Test
    void testGetRecipeImage2() throws Exception {
        when(this.recipeRepository.findById((Long) any())).thenReturn(Optional.empty());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/recipe/image/*");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("recipeId", String.valueOf(1L));
        MockMvcBuilders.standaloneSetup(this.recipeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"status\":\"NOT_FOUND\",\"message\":\"No value present\",\"payload\":null}"));
    }

    /**
     * Method under test: {@link RecipeController#getRecipeImage(Long)}
     */
    @Test
    void testGetRecipeImage3() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFullName("Dr Jane Doe");
        userEntity.setId(123L);
        userEntity.setPassword("iloveyou");
        userEntity.setProfilePhoto("AAAAAAAA".getBytes("UTF-8"));
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
        recipeEntity.setTags(new HashSet<>());
        recipeEntity.setTitle("Dr");
        recipeEntity.setUser(userEntity);
        recipeEntity.setVideoURL("https://example.org/example");
        recipeEntity.setViews(1);
        Optional<RecipeEntity> ofResult = Optional.of(recipeEntity);
        when(this.recipeRepository.findById((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/recipe/image/*");
        getResult.contentType("https://example.org/example");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("recipeId", String.valueOf(1L));
        MockMvcBuilders.standaloneSetup(this.recipeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"status\":\"OK\",\"message\":\"filename:\\\\profile-Dr\",\"payload\":[65]}"));
    }
}

