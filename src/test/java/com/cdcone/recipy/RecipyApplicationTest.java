package com.cdcone.recipy;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cdcone.recipy.entity.RecipeEntity;
import com.cdcone.recipy.entity.RoleEntity;
import com.cdcone.recipy.entity.UserEntity;
import com.cdcone.recipy.repository.RecipeRepository;
import com.cdcone.recipy.repository.RoleDao;
import com.cdcone.recipy.repository.UserDao;
import com.cdcone.recipy.service.RecipeService;
import com.cdcone.recipy.service.UserService;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class RecipyApplicationTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link RecipyApplication#run(RecipeService, UserService, RoleDao)}
     *   <li>{@link CommandLineRunner#run(String[])}
     * </ul>
     */
    @Test
    void testRun() throws Exception {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R026 Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   javax.persistence.PersistenceException: [PersistenceUnit: default] Unable to build Hibernate SessionFactory; nested exception is org.hibernate.exception.GenericJDBCException: Unable to open JDBC Connection for DDL execution
        //       at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.buildNativeEntityManagerFactory(AbstractEntityManagerFactoryBean.java:421)
        //       at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.afterPropertiesSet(AbstractEntityManagerFactoryBean.java:396)
        //       at org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.afterPropertiesSet(LocalContainerEntityManagerFactoryBean.java:341)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.lambda$invokeInitMethods$5(AbstractAutowireCapableBeanFactory.java:1854)
        //       at java.security.AccessController.doPrivileged(AccessController.java:712)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1853)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1800)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1154)
        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:908)
        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583)
        //       at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:740)
        //       at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:415)
        //       at org.springframework.boot.SpringApplication.run(SpringApplication.java:303)
        //       at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:144)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:99)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:124)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:124)
        //   org.hibernate.exception.GenericJDBCException: Unable to open JDBC Connection for DDL execution
        //       at org.hibernate.exception.internal.StandardSQLExceptionConverter.convert(StandardSQLExceptionConverter.java:42)
        //       at org.hibernate.engine.jdbc.spi.SqlExceptionHelper.convert(SqlExceptionHelper.java:113)
        //       at org.hibernate.engine.jdbc.spi.SqlExceptionHelper.convert(SqlExceptionHelper.java:99)
        //       at org.hibernate.resource.transaction.backend.jdbc.internal.DdlTransactionIsolatorNonJtaImpl.getIsolatedConnection(DdlTransactionIsolatorNonJtaImpl.java:71)
        //       at org.hibernate.tool.schema.internal.exec.GenerationTargetToDatabase.jdbcStatement(GenerationTargetToDatabase.java:77)
        //       at org.hibernate.tool.schema.internal.exec.GenerationTargetToDatabase.accept(GenerationTargetToDatabase.java:53)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.applySqlString(SchemaDropperImpl.java:387)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.applySqlStrings(SchemaDropperImpl.java:371)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.applyConstraintDropping(SchemaDropperImpl.java:341)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.dropFromMetadata(SchemaDropperImpl.java:235)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.performDrop(SchemaDropperImpl.java:156)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.doDrop(SchemaDropperImpl.java:128)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.doDrop(SchemaDropperImpl.java:114)
        //       at org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator.performDatabaseAction(SchemaManagementToolCoordinator.java:157)
        //       at org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator.process(SchemaManagementToolCoordinator.java:85)
        //       at org.hibernate.internal.SessionFactoryImpl.<init>(SessionFactoryImpl.java:335)
        //       at org.hibernate.boot.internal.SessionFactoryBuilderImpl.build(SessionFactoryBuilderImpl.java:471)
        //       at org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl.build(EntityManagerFactoryBuilderImpl.java:1498)
        //       at org.springframework.orm.jpa.vendor.SpringHibernateJpaPersistenceProvider.createContainerEntityManagerFactory(SpringHibernateJpaPersistenceProvider.java:58)
        //       at org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.createNativeEntityManagerFactory(LocalContainerEntityManagerFactoryBean.java:365)
        //       at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.buildNativeEntityManagerFactory(AbstractEntityManagerFactoryBean.java:409)
        //       at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.afterPropertiesSet(AbstractEntityManagerFactoryBean.java:396)
        //       at org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.afterPropertiesSet(LocalContainerEntityManagerFactoryBean.java:341)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.lambda$invokeInitMethods$5(AbstractAutowireCapableBeanFactory.java:1854)
        //       at java.security.AccessController.doPrivileged(AccessController.java:712)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1853)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1800)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1154)
        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:908)
        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583)
        //       at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:740)
        //       at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:415)
        //       at org.springframework.boot.SpringApplication.run(SpringApplication.java:303)
        //       at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:144)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:99)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:124)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:124)
        //   org.postgresql.util.PSQLException: FATAL: password authentication failed for user "postgres"
        //       at org.postgresql.core.v3.ConnectionFactoryImpl.doAuthentication(ConnectionFactoryImpl.java:646)
        //       at org.postgresql.core.v3.ConnectionFactoryImpl.tryConnect(ConnectionFactoryImpl.java:180)
        //       at org.postgresql.core.v3.ConnectionFactoryImpl.openConnectionImpl(ConnectionFactoryImpl.java:235)
        //       at org.postgresql.core.ConnectionFactory.openConnection(ConnectionFactory.java:49)
        //       at org.postgresql.jdbc.PgConnection.<init>(PgConnection.java:223)
        //       at org.postgresql.Driver.makeConnection(Driver.java:402)
        //       at org.postgresql.Driver.connect(Driver.java:261)
        //       at com.zaxxer.hikari.util.DriverDataSource.getConnection(DriverDataSource.java:138)
        //       at com.zaxxer.hikari.pool.PoolBase.newConnection(PoolBase.java:364)
        //       at com.zaxxer.hikari.pool.PoolBase.newPoolEntry(PoolBase.java:206)
        //       at com.zaxxer.hikari.pool.HikariPool.createPoolEntry(HikariPool.java:476)
        //       at com.zaxxer.hikari.pool.HikariPool.checkFailFast(HikariPool.java:561)
        //       at com.zaxxer.hikari.pool.HikariPool.<init>(HikariPool.java:115)
        //       at com.zaxxer.hikari.HikariDataSource.getConnection(HikariDataSource.java:112)
        //       at org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl.getConnection(DatasourceConnectionProviderImpl.java:122)
        //       at org.hibernate.engine.jdbc.env.internal.JdbcEnvironmentInitiator$ConnectionProviderJdbcConnectionAccess.obtainConnection(JdbcEnvironmentInitiator.java:181)
        //       at org.hibernate.resource.transaction.backend.jdbc.internal.DdlTransactionIsolatorNonJtaImpl.getIsolatedConnection(DdlTransactionIsolatorNonJtaImpl.java:44)
        //       at org.hibernate.tool.schema.internal.exec.GenerationTargetToDatabase.jdbcStatement(GenerationTargetToDatabase.java:77)
        //       at org.hibernate.tool.schema.internal.exec.GenerationTargetToDatabase.accept(GenerationTargetToDatabase.java:53)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.applySqlString(SchemaDropperImpl.java:387)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.applySqlStrings(SchemaDropperImpl.java:371)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.applyConstraintDropping(SchemaDropperImpl.java:341)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.dropFromMetadata(SchemaDropperImpl.java:235)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.performDrop(SchemaDropperImpl.java:156)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.doDrop(SchemaDropperImpl.java:128)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.doDrop(SchemaDropperImpl.java:114)
        //       at org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator.performDatabaseAction(SchemaManagementToolCoordinator.java:157)
        //       at org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator.process(SchemaManagementToolCoordinator.java:85)
        //       at org.hibernate.internal.SessionFactoryImpl.<init>(SessionFactoryImpl.java:335)
        //       at org.hibernate.boot.internal.SessionFactoryBuilderImpl.build(SessionFactoryBuilderImpl.java:471)
        //       at org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl.build(EntityManagerFactoryBuilderImpl.java:1498)
        //       at org.springframework.orm.jpa.vendor.SpringHibernateJpaPersistenceProvider.createContainerEntityManagerFactory(SpringHibernateJpaPersistenceProvider.java:58)
        //       at org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.createNativeEntityManagerFactory(LocalContainerEntityManagerFactoryBean.java:365)
        //       at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.buildNativeEntityManagerFactory(AbstractEntityManagerFactoryBean.java:409)
        //       at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.afterPropertiesSet(AbstractEntityManagerFactoryBean.java:396)
        //       at org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.afterPropertiesSet(LocalContainerEntityManagerFactoryBean.java:341)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.lambda$invokeInitMethods$5(AbstractAutowireCapableBeanFactory.java:1854)
        //       at java.security.AccessController.doPrivileged(AccessController.java:712)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1853)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1800)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1154)
        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:908)
        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583)
        //       at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:740)
        //       at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:415)
        //       at org.springframework.boot.SpringApplication.run(SpringApplication.java:303)
        //       at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:144)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:99)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:124)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:124)
        //   See https://diff.blue/R026 to resolve this issue.

        RecipyApplication recipyApplication = new RecipyApplication();

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFullName("Dr Jane Doe");
        userEntity.setId(123L);
        userEntity.setPassword("iloveyou");
        //userEntity.setProfilePhoto(new Byte[]{'A'});
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
        //userEntity1.setProfilePhoto(new Byte[]{'A'});
        userEntity1.setRecipes(new HashSet<>());
        userEntity1.setRoles(new HashSet<>());
        userEntity1.setUsername("janedoe");
        UserDao userDao = mock(UserDao.class);
        when(userDao.getById((Long) any())).thenReturn(userEntity1);
        RoleDao roleDao = mock(RoleDao.class);
        RecipeService recipeService = new RecipeService(recipeRepository,
                new UserService(userDao, roleDao, new BCryptPasswordEncoder()));

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setEmail("jane.doe@example.org");
        userEntity2.setFullName("Dr Jane Doe");
        userEntity2.setId(123L);
        userEntity2.setPassword("iloveyou");
        //userEntity2.setProfilePhoto(new Byte[]{'A'});
        userEntity2.setRecipes(new HashSet<>());
        userEntity2.setRoles(new HashSet<>());
        userEntity2.setUsername("janedoe");
        UserDao userDao1 = mock(UserDao.class);
        when(userDao1.save((UserEntity) any())).thenReturn(userEntity2);

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(123L);
        roleEntity.setName("Name");
        RoleDao roleDao1 = mock(RoleDao.class);
        when(roleDao1.findByName((String) any())).thenReturn(Optional.of(roleEntity));
        UserService userService = new UserService(userDao1, roleDao1, new BCryptPasswordEncoder());

        RoleEntity roleEntity1 = new RoleEntity();
        roleEntity1.setId(123L);
        roleEntity1.setName("Name");
        RoleDao roleDao2 = mock(RoleDao.class);
        when(roleDao2.save((RoleEntity) any())).thenReturn(roleEntity1);
        recipyApplication.run(recipeService, userService, roleDao2).run("foo");
        verify(recipeRepository).save((RecipeEntity) any());
        verify(userDao).getById((Long) any());
        verify(userDao1).save((UserEntity) any());
        verify(roleDao1).findByName((String) any());
        verify(roleDao2).save((RoleEntity) any());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link RecipyApplication#run(RecipeService, UserService, RoleDao)}
     *   <li>{@link CommandLineRunner#run(String[])}
     * </ul>
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testRun2() throws Exception {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R026 Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   javax.persistence.PersistenceException: [PersistenceUnit: default] Unable to build Hibernate SessionFactory; nested exception is org.hibernate.exception.GenericJDBCException: Unable to open JDBC Connection for DDL execution
        //       at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.buildNativeEntityManagerFactory(AbstractEntityManagerFactoryBean.java:421)
        //       at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.afterPropertiesSet(AbstractEntityManagerFactoryBean.java:396)
        //       at org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.afterPropertiesSet(LocalContainerEntityManagerFactoryBean.java:341)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.lambda$invokeInitMethods$5(AbstractAutowireCapableBeanFactory.java:1854)
        //       at java.security.AccessController.doPrivileged(AccessController.java:712)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1853)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1800)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1154)
        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:908)
        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583)
        //       at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:740)
        //       at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:415)
        //       at org.springframework.boot.SpringApplication.run(SpringApplication.java:303)
        //       at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:144)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:99)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:124)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:124)
        //   org.hibernate.exception.GenericJDBCException: Unable to open JDBC Connection for DDL execution
        //       at org.hibernate.exception.internal.StandardSQLExceptionConverter.convert(StandardSQLExceptionConverter.java:42)
        //       at org.hibernate.engine.jdbc.spi.SqlExceptionHelper.convert(SqlExceptionHelper.java:113)
        //       at org.hibernate.engine.jdbc.spi.SqlExceptionHelper.convert(SqlExceptionHelper.java:99)
        //       at org.hibernate.resource.transaction.backend.jdbc.internal.DdlTransactionIsolatorNonJtaImpl.getIsolatedConnection(DdlTransactionIsolatorNonJtaImpl.java:71)
        //       at org.hibernate.tool.schema.internal.exec.GenerationTargetToDatabase.jdbcStatement(GenerationTargetToDatabase.java:77)
        //       at org.hibernate.tool.schema.internal.exec.GenerationTargetToDatabase.accept(GenerationTargetToDatabase.java:53)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.applySqlString(SchemaDropperImpl.java:387)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.applySqlStrings(SchemaDropperImpl.java:371)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.applyConstraintDropping(SchemaDropperImpl.java:341)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.dropFromMetadata(SchemaDropperImpl.java:235)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.performDrop(SchemaDropperImpl.java:156)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.doDrop(SchemaDropperImpl.java:128)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.doDrop(SchemaDropperImpl.java:114)
        //       at org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator.performDatabaseAction(SchemaManagementToolCoordinator.java:157)
        //       at org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator.process(SchemaManagementToolCoordinator.java:85)
        //       at org.hibernate.internal.SessionFactoryImpl.<init>(SessionFactoryImpl.java:335)
        //       at org.hibernate.boot.internal.SessionFactoryBuilderImpl.build(SessionFactoryBuilderImpl.java:471)
        //       at org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl.build(EntityManagerFactoryBuilderImpl.java:1498)
        //       at org.springframework.orm.jpa.vendor.SpringHibernateJpaPersistenceProvider.createContainerEntityManagerFactory(SpringHibernateJpaPersistenceProvider.java:58)
        //       at org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.createNativeEntityManagerFactory(LocalContainerEntityManagerFactoryBean.java:365)
        //       at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.buildNativeEntityManagerFactory(AbstractEntityManagerFactoryBean.java:409)
        //       at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.afterPropertiesSet(AbstractEntityManagerFactoryBean.java:396)
        //       at org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.afterPropertiesSet(LocalContainerEntityManagerFactoryBean.java:341)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.lambda$invokeInitMethods$5(AbstractAutowireCapableBeanFactory.java:1854)
        //       at java.security.AccessController.doPrivileged(AccessController.java:712)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1853)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1800)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1154)
        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:908)
        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583)
        //       at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:740)
        //       at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:415)
        //       at org.springframework.boot.SpringApplication.run(SpringApplication.java:303)
        //       at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:144)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:99)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:124)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:124)
        //   org.postgresql.util.PSQLException: FATAL: password authentication failed for user "postgres"
        //       at org.postgresql.core.v3.ConnectionFactoryImpl.doAuthentication(ConnectionFactoryImpl.java:646)
        //       at org.postgresql.core.v3.ConnectionFactoryImpl.tryConnect(ConnectionFactoryImpl.java:180)
        //       at org.postgresql.core.v3.ConnectionFactoryImpl.openConnectionImpl(ConnectionFactoryImpl.java:235)
        //       at org.postgresql.core.ConnectionFactory.openConnection(ConnectionFactory.java:49)
        //       at org.postgresql.jdbc.PgConnection.<init>(PgConnection.java:223)
        //       at org.postgresql.Driver.makeConnection(Driver.java:402)
        //       at org.postgresql.Driver.connect(Driver.java:261)
        //       at com.zaxxer.hikari.util.DriverDataSource.getConnection(DriverDataSource.java:138)
        //       at com.zaxxer.hikari.pool.PoolBase.newConnection(PoolBase.java:364)
        //       at com.zaxxer.hikari.pool.PoolBase.newPoolEntry(PoolBase.java:206)
        //       at com.zaxxer.hikari.pool.HikariPool.createPoolEntry(HikariPool.java:476)
        //       at com.zaxxer.hikari.pool.HikariPool.checkFailFast(HikariPool.java:561)
        //       at com.zaxxer.hikari.pool.HikariPool.<init>(HikariPool.java:115)
        //       at com.zaxxer.hikari.HikariDataSource.getConnection(HikariDataSource.java:112)
        //       at org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl.getConnection(DatasourceConnectionProviderImpl.java:122)
        //       at org.hibernate.engine.jdbc.env.internal.JdbcEnvironmentInitiator$ConnectionProviderJdbcConnectionAccess.obtainConnection(JdbcEnvironmentInitiator.java:181)
        //       at org.hibernate.resource.transaction.backend.jdbc.internal.DdlTransactionIsolatorNonJtaImpl.getIsolatedConnection(DdlTransactionIsolatorNonJtaImpl.java:44)
        //       at org.hibernate.tool.schema.internal.exec.GenerationTargetToDatabase.jdbcStatement(GenerationTargetToDatabase.java:77)
        //       at org.hibernate.tool.schema.internal.exec.GenerationTargetToDatabase.accept(GenerationTargetToDatabase.java:53)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.applySqlString(SchemaDropperImpl.java:387)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.applySqlStrings(SchemaDropperImpl.java:371)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.applyConstraintDropping(SchemaDropperImpl.java:341)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.dropFromMetadata(SchemaDropperImpl.java:235)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.performDrop(SchemaDropperImpl.java:156)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.doDrop(SchemaDropperImpl.java:128)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.doDrop(SchemaDropperImpl.java:114)
        //       at org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator.performDatabaseAction(SchemaManagementToolCoordinator.java:157)
        //       at org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator.process(SchemaManagementToolCoordinator.java:85)
        //       at org.hibernate.internal.SessionFactoryImpl.<init>(SessionFactoryImpl.java:335)
        //       at org.hibernate.boot.internal.SessionFactoryBuilderImpl.build(SessionFactoryBuilderImpl.java:471)
        //       at org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl.build(EntityManagerFactoryBuilderImpl.java:1498)
        //       at org.springframework.orm.jpa.vendor.SpringHibernateJpaPersistenceProvider.createContainerEntityManagerFactory(SpringHibernateJpaPersistenceProvider.java:58)
        //       at org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.createNativeEntityManagerFactory(LocalContainerEntityManagerFactoryBean.java:365)
        //       at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.buildNativeEntityManagerFactory(AbstractEntityManagerFactoryBean.java:409)
        //       at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.afterPropertiesSet(AbstractEntityManagerFactoryBean.java:396)
        //       at org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.afterPropertiesSet(LocalContainerEntityManagerFactoryBean.java:341)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.lambda$invokeInitMethods$5(AbstractAutowireCapableBeanFactory.java:1854)
        //       at java.security.AccessController.doPrivileged(AccessController.java:712)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1853)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1800)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1154)
        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:908)
        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583)
        //       at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:740)
        //       at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:415)
        //       at org.springframework.boot.SpringApplication.run(SpringApplication.java:303)
        //       at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:144)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:99)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:124)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:124)
        //   See https://diff.blue/R026 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.cdcone.recipy.service.UserService.getById(long)" because "this.userService" is null
        //       at com.cdcone.recipy.service.RecipeService.add(RecipeService.java:29)
        //       at com.cdcone.recipy.RecipyApplication.lambda$run$0(RecipyApplication.java:49)
        //   In order to prevent run(RecipeService, UserService, RoleDao)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   run(RecipeService, UserService, RoleDao).
        //   See https://diff.blue/R013 to resolve this issue.

        RecipyApplication recipyApplication = new RecipyApplication();

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFullName("Dr Jane Doe");
        userEntity.setId(123L);
        userEntity.setPassword("iloveyou");
        //userEntity.setProfilePhoto(new Byte[]{'A'});
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
        RecipeService recipeService = new RecipeService(recipeRepository, null);

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setEmail("jane.doe@example.org");
        userEntity1.setFullName("Dr Jane Doe");
        userEntity1.setId(123L);
        userEntity1.setPassword("iloveyou");
        //userEntity1.setProfilePhoto(new Byte[]{'A'});
        userEntity1.setRecipes(new HashSet<>());
        userEntity1.setRoles(new HashSet<>());
        userEntity1.setUsername("janedoe");
        UserDao userDao = mock(UserDao.class);
        when(userDao.save((UserEntity) any())).thenReturn(userEntity1);

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(123L);
        roleEntity.setName("Name");
        RoleDao roleDao = mock(RoleDao.class);
        when(roleDao.findByName((String) any())).thenReturn(Optional.of(roleEntity));
        UserService userService = new UserService(userDao, roleDao, new BCryptPasswordEncoder());

        RoleEntity roleEntity1 = new RoleEntity();
        roleEntity1.setId(123L);
        roleEntity1.setName("Name");
        RoleDao roleDao1 = mock(RoleDao.class);
        when(roleDao1.save((RoleEntity) any())).thenReturn(roleEntity1);
        recipyApplication.run(recipeService, userService, roleDao1).run("foo");
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link RecipyApplication#run(RecipeService, UserService, RoleDao)}
     *   <li>{@link CommandLineRunner#run(String[])}
     * </ul>
     */
    @Test
    void testRun3() throws Exception {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R026 Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   javax.persistence.PersistenceException: [PersistenceUnit: default] Unable to build Hibernate SessionFactory; nested exception is org.hibernate.exception.GenericJDBCException: Unable to open JDBC Connection for DDL execution
        //       at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.buildNativeEntityManagerFactory(AbstractEntityManagerFactoryBean.java:421)
        //       at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.afterPropertiesSet(AbstractEntityManagerFactoryBean.java:396)
        //       at org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.afterPropertiesSet(LocalContainerEntityManagerFactoryBean.java:341)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.lambda$invokeInitMethods$5(AbstractAutowireCapableBeanFactory.java:1854)
        //       at java.security.AccessController.doPrivileged(AccessController.java:712)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1853)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1800)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1154)
        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:908)
        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583)
        //       at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:740)
        //       at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:415)
        //       at org.springframework.boot.SpringApplication.run(SpringApplication.java:303)
        //       at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:144)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:99)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:124)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:124)
        //   org.hibernate.exception.GenericJDBCException: Unable to open JDBC Connection for DDL execution
        //       at org.hibernate.exception.internal.StandardSQLExceptionConverter.convert(StandardSQLExceptionConverter.java:42)
        //       at org.hibernate.engine.jdbc.spi.SqlExceptionHelper.convert(SqlExceptionHelper.java:113)
        //       at org.hibernate.engine.jdbc.spi.SqlExceptionHelper.convert(SqlExceptionHelper.java:99)
        //       at org.hibernate.resource.transaction.backend.jdbc.internal.DdlTransactionIsolatorNonJtaImpl.getIsolatedConnection(DdlTransactionIsolatorNonJtaImpl.java:71)
        //       at org.hibernate.tool.schema.internal.exec.GenerationTargetToDatabase.jdbcStatement(GenerationTargetToDatabase.java:77)
        //       at org.hibernate.tool.schema.internal.exec.GenerationTargetToDatabase.accept(GenerationTargetToDatabase.java:53)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.applySqlString(SchemaDropperImpl.java:387)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.applySqlStrings(SchemaDropperImpl.java:371)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.applyConstraintDropping(SchemaDropperImpl.java:341)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.dropFromMetadata(SchemaDropperImpl.java:235)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.performDrop(SchemaDropperImpl.java:156)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.doDrop(SchemaDropperImpl.java:128)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.doDrop(SchemaDropperImpl.java:114)
        //       at org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator.performDatabaseAction(SchemaManagementToolCoordinator.java:157)
        //       at org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator.process(SchemaManagementToolCoordinator.java:85)
        //       at org.hibernate.internal.SessionFactoryImpl.<init>(SessionFactoryImpl.java:335)
        //       at org.hibernate.boot.internal.SessionFactoryBuilderImpl.build(SessionFactoryBuilderImpl.java:471)
        //       at org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl.build(EntityManagerFactoryBuilderImpl.java:1498)
        //       at org.springframework.orm.jpa.vendor.SpringHibernateJpaPersistenceProvider.createContainerEntityManagerFactory(SpringHibernateJpaPersistenceProvider.java:58)
        //       at org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.createNativeEntityManagerFactory(LocalContainerEntityManagerFactoryBean.java:365)
        //       at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.buildNativeEntityManagerFactory(AbstractEntityManagerFactoryBean.java:409)
        //       at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.afterPropertiesSet(AbstractEntityManagerFactoryBean.java:396)
        //       at org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.afterPropertiesSet(LocalContainerEntityManagerFactoryBean.java:341)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.lambda$invokeInitMethods$5(AbstractAutowireCapableBeanFactory.java:1854)
        //       at java.security.AccessController.doPrivileged(AccessController.java:712)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1853)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1800)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1154)
        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:908)
        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583)
        //       at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:740)
        //       at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:415)
        //       at org.springframework.boot.SpringApplication.run(SpringApplication.java:303)
        //       at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:144)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:99)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:124)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:124)
        //   org.postgresql.util.PSQLException: FATAL: password authentication failed for user "postgres"
        //       at org.postgresql.core.v3.ConnectionFactoryImpl.doAuthentication(ConnectionFactoryImpl.java:646)
        //       at org.postgresql.core.v3.ConnectionFactoryImpl.tryConnect(ConnectionFactoryImpl.java:180)
        //       at org.postgresql.core.v3.ConnectionFactoryImpl.openConnectionImpl(ConnectionFactoryImpl.java:235)
        //       at org.postgresql.core.ConnectionFactory.openConnection(ConnectionFactory.java:49)
        //       at org.postgresql.jdbc.PgConnection.<init>(PgConnection.java:223)
        //       at org.postgresql.Driver.makeConnection(Driver.java:402)
        //       at org.postgresql.Driver.connect(Driver.java:261)
        //       at com.zaxxer.hikari.util.DriverDataSource.getConnection(DriverDataSource.java:138)
        //       at com.zaxxer.hikari.pool.PoolBase.newConnection(PoolBase.java:364)
        //       at com.zaxxer.hikari.pool.PoolBase.newPoolEntry(PoolBase.java:206)
        //       at com.zaxxer.hikari.pool.HikariPool.createPoolEntry(HikariPool.java:476)
        //       at com.zaxxer.hikari.pool.HikariPool.checkFailFast(HikariPool.java:561)
        //       at com.zaxxer.hikari.pool.HikariPool.<init>(HikariPool.java:115)
        //       at com.zaxxer.hikari.HikariDataSource.getConnection(HikariDataSource.java:112)
        //       at org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl.getConnection(DatasourceConnectionProviderImpl.java:122)
        //       at org.hibernate.engine.jdbc.env.internal.JdbcEnvironmentInitiator$ConnectionProviderJdbcConnectionAccess.obtainConnection(JdbcEnvironmentInitiator.java:181)
        //       at org.hibernate.resource.transaction.backend.jdbc.internal.DdlTransactionIsolatorNonJtaImpl.getIsolatedConnection(DdlTransactionIsolatorNonJtaImpl.java:44)
        //       at org.hibernate.tool.schema.internal.exec.GenerationTargetToDatabase.jdbcStatement(GenerationTargetToDatabase.java:77)
        //       at org.hibernate.tool.schema.internal.exec.GenerationTargetToDatabase.accept(GenerationTargetToDatabase.java:53)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.applySqlString(SchemaDropperImpl.java:387)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.applySqlStrings(SchemaDropperImpl.java:371)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.applyConstraintDropping(SchemaDropperImpl.java:341)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.dropFromMetadata(SchemaDropperImpl.java:235)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.performDrop(SchemaDropperImpl.java:156)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.doDrop(SchemaDropperImpl.java:128)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.doDrop(SchemaDropperImpl.java:114)
        //       at org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator.performDatabaseAction(SchemaManagementToolCoordinator.java:157)
        //       at org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator.process(SchemaManagementToolCoordinator.java:85)
        //       at org.hibernate.internal.SessionFactoryImpl.<init>(SessionFactoryImpl.java:335)
        //       at org.hibernate.boot.internal.SessionFactoryBuilderImpl.build(SessionFactoryBuilderImpl.java:471)
        //       at org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl.build(EntityManagerFactoryBuilderImpl.java:1498)
        //       at org.springframework.orm.jpa.vendor.SpringHibernateJpaPersistenceProvider.createContainerEntityManagerFactory(SpringHibernateJpaPersistenceProvider.java:58)
        //       at org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.createNativeEntityManagerFactory(LocalContainerEntityManagerFactoryBean.java:365)
        //       at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.buildNativeEntityManagerFactory(AbstractEntityManagerFactoryBean.java:409)
        //       at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.afterPropertiesSet(AbstractEntityManagerFactoryBean.java:396)
        //       at org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.afterPropertiesSet(LocalContainerEntityManagerFactoryBean.java:341)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.lambda$invokeInitMethods$5(AbstractAutowireCapableBeanFactory.java:1854)
        //       at java.security.AccessController.doPrivileged(AccessController.java:712)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1853)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1800)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1154)
        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:908)
        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583)
        //       at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:740)
        //       at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:415)
        //       at org.springframework.boot.SpringApplication.run(SpringApplication.java:303)
        //       at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:144)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:99)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:124)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:124)
        //   See https://diff.blue/R026 to resolve this issue.

        RecipyApplication recipyApplication = new RecipyApplication();

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFullName("Dr Jane Doe");
        userEntity.setId(123L);
        userEntity.setPassword("iloveyou");
        //userEntity.setProfilePhoto(new Byte[]{'A'});
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
        //userEntity1.setProfilePhoto(new Byte[]{'A'});
        userEntity1.setRecipes(new HashSet<>());
        userEntity1.setRoles(new HashSet<>());
        userEntity1.setUsername("janedoe");
        UserDao userDao = mock(UserDao.class);
        when(userDao.getById((Long) any())).thenReturn(userEntity1);
        RoleDao roleDao = mock(RoleDao.class);
        RecipeService recipeService = new RecipeService(recipeRepository,
                new UserService(userDao, roleDao, new BCryptPasswordEncoder()));

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setEmail("jane.doe@example.org");
        userEntity2.setFullName("Dr Jane Doe");
        userEntity2.setId(123L);
        userEntity2.setPassword("iloveyou");
        //userEntity2.setProfilePhoto(new Byte[]{'A'});
        userEntity2.setRecipes(new HashSet<>());
        userEntity2.setRoles(new HashSet<>());
        userEntity2.setUsername("janedoe");
        UserDao userDao1 = mock(UserDao.class);
        when(userDao1.save((UserEntity) any())).thenReturn(userEntity2);

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(123L);
        roleEntity.setName("Name");
        RoleDao roleDao1 = mock(RoleDao.class);
        when(roleDao1.findByName((String) any())).thenReturn(Optional.of(roleEntity));
        UserService userService = new UserService(userDao1, roleDao1, null);

        RoleEntity roleEntity1 = new RoleEntity();
        roleEntity1.setId(123L);
        roleEntity1.setName("Name");
        RoleDao roleDao2 = mock(RoleDao.class);
        when(roleDao2.save((RoleEntity) any())).thenReturn(roleEntity1);
        recipyApplication.run(recipeService, userService, roleDao2).run("foo");
        verify(recipeRepository).save((RecipeEntity) any());
        verify(userDao).getById((Long) any());
        verify(roleDao1).findByName((String) any());
        verify(roleDao2).save((RoleEntity) any());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link RecipyApplication#run(RecipeService, UserService, RoleDao)}
     *   <li>{@link CommandLineRunner#run(String[])}
     * </ul>
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testRun4() throws Exception {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R026 Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   javax.persistence.PersistenceException: [PersistenceUnit: default] Unable to build Hibernate SessionFactory; nested exception is org.hibernate.exception.GenericJDBCException: Unable to open JDBC Connection for DDL execution
        //       at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.buildNativeEntityManagerFactory(AbstractEntityManagerFactoryBean.java:421)
        //       at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.afterPropertiesSet(AbstractEntityManagerFactoryBean.java:396)
        //       at org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.afterPropertiesSet(LocalContainerEntityManagerFactoryBean.java:341)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.lambda$invokeInitMethods$5(AbstractAutowireCapableBeanFactory.java:1854)
        //       at java.security.AccessController.doPrivileged(AccessController.java:712)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1853)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1800)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1154)
        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:908)
        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583)
        //       at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:740)
        //       at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:415)
        //       at org.springframework.boot.SpringApplication.run(SpringApplication.java:303)
        //       at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:144)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:99)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:124)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:124)
        //   org.hibernate.exception.GenericJDBCException: Unable to open JDBC Connection for DDL execution
        //       at org.hibernate.exception.internal.StandardSQLExceptionConverter.convert(StandardSQLExceptionConverter.java:42)
        //       at org.hibernate.engine.jdbc.spi.SqlExceptionHelper.convert(SqlExceptionHelper.java:113)
        //       at org.hibernate.engine.jdbc.spi.SqlExceptionHelper.convert(SqlExceptionHelper.java:99)
        //       at org.hibernate.resource.transaction.backend.jdbc.internal.DdlTransactionIsolatorNonJtaImpl.getIsolatedConnection(DdlTransactionIsolatorNonJtaImpl.java:71)
        //       at org.hibernate.tool.schema.internal.exec.GenerationTargetToDatabase.jdbcStatement(GenerationTargetToDatabase.java:77)
        //       at org.hibernate.tool.schema.internal.exec.GenerationTargetToDatabase.accept(GenerationTargetToDatabase.java:53)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.applySqlString(SchemaDropperImpl.java:387)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.applySqlStrings(SchemaDropperImpl.java:371)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.applyConstraintDropping(SchemaDropperImpl.java:341)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.dropFromMetadata(SchemaDropperImpl.java:235)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.performDrop(SchemaDropperImpl.java:156)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.doDrop(SchemaDropperImpl.java:128)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.doDrop(SchemaDropperImpl.java:114)
        //       at org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator.performDatabaseAction(SchemaManagementToolCoordinator.java:157)
        //       at org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator.process(SchemaManagementToolCoordinator.java:85)
        //       at org.hibernate.internal.SessionFactoryImpl.<init>(SessionFactoryImpl.java:335)
        //       at org.hibernate.boot.internal.SessionFactoryBuilderImpl.build(SessionFactoryBuilderImpl.java:471)
        //       at org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl.build(EntityManagerFactoryBuilderImpl.java:1498)
        //       at org.springframework.orm.jpa.vendor.SpringHibernateJpaPersistenceProvider.createContainerEntityManagerFactory(SpringHibernateJpaPersistenceProvider.java:58)
        //       at org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.createNativeEntityManagerFactory(LocalContainerEntityManagerFactoryBean.java:365)
        //       at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.buildNativeEntityManagerFactory(AbstractEntityManagerFactoryBean.java:409)
        //       at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.afterPropertiesSet(AbstractEntityManagerFactoryBean.java:396)
        //       at org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.afterPropertiesSet(LocalContainerEntityManagerFactoryBean.java:341)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.lambda$invokeInitMethods$5(AbstractAutowireCapableBeanFactory.java:1854)
        //       at java.security.AccessController.doPrivileged(AccessController.java:712)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1853)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1800)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1154)
        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:908)
        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583)
        //       at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:740)
        //       at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:415)
        //       at org.springframework.boot.SpringApplication.run(SpringApplication.java:303)
        //       at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:144)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:99)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:124)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:124)
        //   org.postgresql.util.PSQLException: FATAL: password authentication failed for user "postgres"
        //       at org.postgresql.core.v3.ConnectionFactoryImpl.doAuthentication(ConnectionFactoryImpl.java:646)
        //       at org.postgresql.core.v3.ConnectionFactoryImpl.tryConnect(ConnectionFactoryImpl.java:180)
        //       at org.postgresql.core.v3.ConnectionFactoryImpl.openConnectionImpl(ConnectionFactoryImpl.java:235)
        //       at org.postgresql.core.ConnectionFactory.openConnection(ConnectionFactory.java:49)
        //       at org.postgresql.jdbc.PgConnection.<init>(PgConnection.java:223)
        //       at org.postgresql.Driver.makeConnection(Driver.java:402)
        //       at org.postgresql.Driver.connect(Driver.java:261)
        //       at com.zaxxer.hikari.util.DriverDataSource.getConnection(DriverDataSource.java:138)
        //       at com.zaxxer.hikari.pool.PoolBase.newConnection(PoolBase.java:364)
        //       at com.zaxxer.hikari.pool.PoolBase.newPoolEntry(PoolBase.java:206)
        //       at com.zaxxer.hikari.pool.HikariPool.createPoolEntry(HikariPool.java:476)
        //       at com.zaxxer.hikari.pool.HikariPool.checkFailFast(HikariPool.java:561)
        //       at com.zaxxer.hikari.pool.HikariPool.<init>(HikariPool.java:115)
        //       at com.zaxxer.hikari.HikariDataSource.getConnection(HikariDataSource.java:112)
        //       at org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl.getConnection(DatasourceConnectionProviderImpl.java:122)
        //       at org.hibernate.engine.jdbc.env.internal.JdbcEnvironmentInitiator$ConnectionProviderJdbcConnectionAccess.obtainConnection(JdbcEnvironmentInitiator.java:181)
        //       at org.hibernate.resource.transaction.backend.jdbc.internal.DdlTransactionIsolatorNonJtaImpl.getIsolatedConnection(DdlTransactionIsolatorNonJtaImpl.java:44)
        //       at org.hibernate.tool.schema.internal.exec.GenerationTargetToDatabase.jdbcStatement(GenerationTargetToDatabase.java:77)
        //       at org.hibernate.tool.schema.internal.exec.GenerationTargetToDatabase.accept(GenerationTargetToDatabase.java:53)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.applySqlString(SchemaDropperImpl.java:387)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.applySqlStrings(SchemaDropperImpl.java:371)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.applyConstraintDropping(SchemaDropperImpl.java:341)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.dropFromMetadata(SchemaDropperImpl.java:235)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.performDrop(SchemaDropperImpl.java:156)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.doDrop(SchemaDropperImpl.java:128)
        //       at org.hibernate.tool.schema.internal.SchemaDropperImpl.doDrop(SchemaDropperImpl.java:114)
        //       at org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator.performDatabaseAction(SchemaManagementToolCoordinator.java:157)
        //       at org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator.process(SchemaManagementToolCoordinator.java:85)
        //       at org.hibernate.internal.SessionFactoryImpl.<init>(SessionFactoryImpl.java:335)
        //       at org.hibernate.boot.internal.SessionFactoryBuilderImpl.build(SessionFactoryBuilderImpl.java:471)
        //       at org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl.build(EntityManagerFactoryBuilderImpl.java:1498)
        //       at org.springframework.orm.jpa.vendor.SpringHibernateJpaPersistenceProvider.createContainerEntityManagerFactory(SpringHibernateJpaPersistenceProvider.java:58)
        //       at org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.createNativeEntityManagerFactory(LocalContainerEntityManagerFactoryBean.java:365)
        //       at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.buildNativeEntityManagerFactory(AbstractEntityManagerFactoryBean.java:409)
        //       at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.afterPropertiesSet(AbstractEntityManagerFactoryBean.java:396)
        //       at org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.afterPropertiesSet(LocalContainerEntityManagerFactoryBean.java:341)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.lambda$invokeInitMethods$5(AbstractAutowireCapableBeanFactory.java:1854)
        //       at java.security.AccessController.doPrivileged(AccessController.java:712)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1853)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1800)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1154)
        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:908)
        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583)
        //       at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:740)
        //       at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:415)
        //       at org.springframework.boot.SpringApplication.run(SpringApplication.java:303)
        //       at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:144)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:99)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:124)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:124)
        //   See https://diff.blue/R026 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.cdcone.recipy.service.UserService.addUser(com.cdcone.recipy.dto.SignUpDto)" because "userService" is null
        //       at com.cdcone.recipy.RecipyApplication.lambda$run$0(RecipyApplication.java:42)
        //   In order to prevent run(RecipeService, UserService, RoleDao)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   run(RecipeService, UserService, RoleDao).
        //   See https://diff.blue/R013 to resolve this issue.

        RecipyApplication recipyApplication = new RecipyApplication();

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFullName("Dr Jane Doe");
        userEntity.setId(123L);
        userEntity.setPassword("iloveyou");
        //userEntity.setProfilePhoto(new Byte[]{'A'});
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
        //userEntity1.setProfilePhoto(new Byte[]{'A'});
        userEntity1.setRecipes(new HashSet<>());
        userEntity1.setRoles(new HashSet<>());
        userEntity1.setUsername("janedoe");
        UserDao userDao = mock(UserDao.class);
        when(userDao.getById((Long) any())).thenReturn(userEntity1);
        RoleDao roleDao = mock(RoleDao.class);
        RecipeService recipeService = new RecipeService(recipeRepository,
                new UserService(userDao, roleDao, new BCryptPasswordEncoder()));

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(123L);
        roleEntity.setName("Name");
        RoleDao roleDao1 = mock(RoleDao.class);
        when(roleDao1.save((RoleEntity) any())).thenReturn(roleEntity);
        recipyApplication.run(recipeService, null, roleDao1).run("foo");
    }
}

