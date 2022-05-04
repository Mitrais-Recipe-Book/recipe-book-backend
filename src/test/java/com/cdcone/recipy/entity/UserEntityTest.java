package com.cdcone.recipy.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class UserEntityTest {
    /**
     * Method under test: {@link UserEntity#canEqual(Object)}
     */
    @Test
    void testCanEqual() {
        assertFalse((new UserEntity()).canEqual("Other"));
    }

    /**
     * Method under test: {@link UserEntity#canEqual(Object)}
     */
    @Test
    void testCanEqual2() {
        UserEntity userEntity = new UserEntity();

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setEmail("jane.doe@example.org");
        userEntity1.setFullName("Dr Jane Doe");
        userEntity1.setId(123L);
        userEntity1.setPassword("iloveyou");
        userEntity1.setProfilePhoto(new Byte[]{'A'});
        userEntity1.setRecipes(new HashSet<>());
        userEntity1.setRoles(new HashSet<>());
        userEntity1.setUsername("janedoe");
        assertTrue(userEntity.canEqual(userEntity1));
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>default or parameterless constructor of {@link UserEntity}
     *   <li>{@link UserEntity#setEmail(String)}
     *   <li>{@link UserEntity#setFullName(String)}
     *   <li>{@link UserEntity#setId(Long)}
     *   <li>{@link UserEntity#setPassword(String)}
     *   <li>{@link UserEntity#setProfilePhoto(Byte[])}
     *   <li>{@link UserEntity#setRecipes(Set)}
     *   <li>{@link UserEntity#setRoles(Set)}
     *   <li>{@link UserEntity#setUsername(String)}
     * </ul>
     */
    @Test
    void testConstructor() {
        UserEntity actualUserEntity = new UserEntity();
        actualUserEntity.setEmail("jane.doe@example.org");
        actualUserEntity.setFullName("Dr Jane Doe");
        actualUserEntity.setId(123L);
        actualUserEntity.setPassword("iloveyou");
        actualUserEntity.setProfilePhoto(new Byte[]{'A'});
        HashSet<RecipeEntity> recipeEntitySet = new HashSet<>();
        actualUserEntity.setRecipes(recipeEntitySet);
        HashSet<RoleEntity> roleEntitySet = new HashSet<>();
        actualUserEntity.setRoles(roleEntitySet);
        actualUserEntity.setUsername("janedoe");
        assertEquals("jane.doe@example.org", actualUserEntity.getEmail());
        assertEquals("Dr Jane Doe", actualUserEntity.getFullName());
        assertEquals(123L, actualUserEntity.getId().longValue());
        assertEquals("iloveyou", actualUserEntity.getPassword());
        assertSame(recipeEntitySet, actualUserEntity.getRecipes());
        assertSame(roleEntitySet, actualUserEntity.getRoles());
        assertEquals("janedoe", actualUserEntity.getUsername());
    }

    /**
     * Method under test: {@link UserEntity#equals(Object)}
     */
    @Test
    void testEquals() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFullName("Dr Jane Doe");
        userEntity.setId(123L);
        userEntity.setPassword("iloveyou");
        userEntity.setProfilePhoto(new Byte[]{'A'});
        userEntity.setRecipes(new HashSet<>());
        userEntity.setRoles(new HashSet<>());
        userEntity.setUsername("janedoe");
        assertNotEquals(userEntity, null);
    }

    /**
     * Method under test: {@link UserEntity#equals(Object)}
     */
    @Test
    void testEquals2() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFullName("Dr Jane Doe");
        userEntity.setId(123L);
        userEntity.setPassword("iloveyou");
        userEntity.setProfilePhoto(new Byte[]{'A'});
        userEntity.setRecipes(new HashSet<>());
        userEntity.setRoles(new HashSet<>());
        userEntity.setUsername("janedoe");
        assertNotEquals(userEntity, "Different type to UserEntity");
    }

    /**
     * Method under test: {@link UserEntity#equals(Object)}
     */
    @Test
    void testEquals3() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFullName("Dr Jane Doe");
        userEntity.setId(123L);
        userEntity.setPassword("iloveyou");
        userEntity.setProfilePhoto(new Byte[]{'A'});
        userEntity.setRecipes(new HashSet<>());
        userEntity.setRoles(new HashSet<>());
        userEntity.setUsername("janedoe");
        assertEquals(userEntity, userEntity);
        int expectedHashCodeResult = userEntity.hashCode();
        assertEquals(expectedHashCodeResult, userEntity.hashCode());
    }

    /**
     * Method under test: {@link UserEntity#equals(Object)}
     */
    @Test
    void testEquals4() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFullName("Dr Jane Doe");
        userEntity.setId(123L);
        userEntity.setPassword("iloveyou");
        userEntity.setProfilePhoto(new Byte[]{'A'});
        userEntity.setRecipes(new HashSet<>());
        userEntity.setRoles(new HashSet<>());
        userEntity.setUsername("janedoe");

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setEmail("jane.doe@example.org");
        userEntity1.setFullName("Dr Jane Doe");
        userEntity1.setId(123L);
        userEntity1.setPassword("iloveyou");
        userEntity1.setProfilePhoto(new Byte[]{'A'});
        userEntity1.setRecipes(new HashSet<>());
        userEntity1.setRoles(new HashSet<>());
        userEntity1.setUsername("janedoe");
        assertEquals(userEntity, userEntity1);
        int expectedHashCodeResult = userEntity.hashCode();
        assertEquals(expectedHashCodeResult, userEntity1.hashCode());
    }

    /**
     * Method under test: {@link UserEntity#equals(Object)}
     */
    @Test
    void testEquals5() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("janedoe");
        userEntity.setFullName("Dr Jane Doe");
        userEntity.setId(123L);
        userEntity.setPassword("iloveyou");
        userEntity.setProfilePhoto(new Byte[]{'A'});
        userEntity.setRecipes(new HashSet<>());
        userEntity.setRoles(new HashSet<>());
        userEntity.setUsername("janedoe");

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setEmail("jane.doe@example.org");
        userEntity1.setFullName("Dr Jane Doe");
        userEntity1.setId(123L);
        userEntity1.setPassword("iloveyou");
        userEntity1.setProfilePhoto(new Byte[]{'A'});
        userEntity1.setRecipes(new HashSet<>());
        userEntity1.setRoles(new HashSet<>());
        userEntity1.setUsername("janedoe");
        assertNotEquals(userEntity, userEntity1);
    }

    /**
     * Method under test: {@link UserEntity#equals(Object)}
     */
    @Test
    void testEquals6() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(null);
        userEntity.setFullName("Dr Jane Doe");
        userEntity.setId(123L);
        userEntity.setPassword("iloveyou");
        userEntity.setProfilePhoto(new Byte[]{'A'});
        userEntity.setRecipes(new HashSet<>());
        userEntity.setRoles(new HashSet<>());
        userEntity.setUsername("janedoe");

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setEmail("jane.doe@example.org");
        userEntity1.setFullName("Dr Jane Doe");
        userEntity1.setId(123L);
        userEntity1.setPassword("iloveyou");
        userEntity1.setProfilePhoto(new Byte[]{'A'});
        userEntity1.setRecipes(new HashSet<>());
        userEntity1.setRoles(new HashSet<>());
        userEntity1.setUsername("janedoe");
        assertNotEquals(userEntity, userEntity1);
    }

    /**
     * Method under test: {@link UserEntity#equals(Object)}
     */
    @Test
    void testEquals7() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFullName("jane.doe@example.org");
        userEntity.setId(123L);
        userEntity.setPassword("iloveyou");
        userEntity.setProfilePhoto(new Byte[]{'A'});
        userEntity.setRecipes(new HashSet<>());
        userEntity.setRoles(new HashSet<>());
        userEntity.setUsername("janedoe");

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setEmail("jane.doe@example.org");
        userEntity1.setFullName("Dr Jane Doe");
        userEntity1.setId(123L);
        userEntity1.setPassword("iloveyou");
        userEntity1.setProfilePhoto(new Byte[]{'A'});
        userEntity1.setRecipes(new HashSet<>());
        userEntity1.setRoles(new HashSet<>());
        userEntity1.setUsername("janedoe");
        assertNotEquals(userEntity, userEntity1);
    }

    /**
     * Method under test: {@link UserEntity#equals(Object)}
     */
    @Test
    void testEquals8() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFullName(null);
        userEntity.setId(123L);
        userEntity.setPassword("iloveyou");
        userEntity.setProfilePhoto(new Byte[]{'A'});
        userEntity.setRecipes(new HashSet<>());
        userEntity.setRoles(new HashSet<>());
        userEntity.setUsername("janedoe");

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setEmail("jane.doe@example.org");
        userEntity1.setFullName("Dr Jane Doe");
        userEntity1.setId(123L);
        userEntity1.setPassword("iloveyou");
        userEntity1.setProfilePhoto(new Byte[]{'A'});
        userEntity1.setRecipes(new HashSet<>());
        userEntity1.setRoles(new HashSet<>());
        userEntity1.setUsername("janedoe");
        assertNotEquals(userEntity, userEntity1);
    }

    /**
     * Method under test: {@link UserEntity#equals(Object)}
     */
    @Test
    void testEquals9() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFullName("Dr Jane Doe");
        userEntity.setId(1L);
        userEntity.setPassword("iloveyou");
        userEntity.setProfilePhoto(new Byte[]{'A'});
        userEntity.setRecipes(new HashSet<>());
        userEntity.setRoles(new HashSet<>());
        userEntity.setUsername("janedoe");

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setEmail("jane.doe@example.org");
        userEntity1.setFullName("Dr Jane Doe");
        userEntity1.setId(123L);
        userEntity1.setPassword("iloveyou");
        userEntity1.setProfilePhoto(new Byte[]{'A'});
        userEntity1.setRecipes(new HashSet<>());
        userEntity1.setRoles(new HashSet<>());
        userEntity1.setUsername("janedoe");
        assertNotEquals(userEntity, userEntity1);
    }

    /**
     * Method under test: {@link UserEntity#equals(Object)}
     */
    @Test
    void testEquals10() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFullName("Dr Jane Doe");
        userEntity.setId(null);
        userEntity.setPassword("iloveyou");
        userEntity.setProfilePhoto(new Byte[]{'A'});
        userEntity.setRecipes(new HashSet<>());
        userEntity.setRoles(new HashSet<>());
        userEntity.setUsername("janedoe");

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setEmail("jane.doe@example.org");
        userEntity1.setFullName("Dr Jane Doe");
        userEntity1.setId(123L);
        userEntity1.setPassword("iloveyou");
        userEntity1.setProfilePhoto(new Byte[]{'A'});
        userEntity1.setRecipes(new HashSet<>());
        userEntity1.setRoles(new HashSet<>());
        userEntity1.setUsername("janedoe");
        assertNotEquals(userEntity, userEntity1);
    }

    /**
     * Method under test: {@link UserEntity#equals(Object)}
     */
    @Test
    void testEquals11() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFullName("Dr Jane Doe");
        userEntity.setId(123L);
        userEntity.setPassword("jane.doe@example.org");
        userEntity.setProfilePhoto(new Byte[]{'A'});
        userEntity.setRecipes(new HashSet<>());
        userEntity.setRoles(new HashSet<>());
        userEntity.setUsername("janedoe");

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setEmail("jane.doe@example.org");
        userEntity1.setFullName("Dr Jane Doe");
        userEntity1.setId(123L);
        userEntity1.setPassword("iloveyou");
        userEntity1.setProfilePhoto(new Byte[]{'A'});
        userEntity1.setRecipes(new HashSet<>());
        userEntity1.setRoles(new HashSet<>());
        userEntity1.setUsername("janedoe");
        assertNotEquals(userEntity, userEntity1);
    }

    /**
     * Method under test: {@link UserEntity#equals(Object)}
     */
    @Test
    void testEquals12() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFullName("Dr Jane Doe");
        userEntity.setId(123L);
        userEntity.setPassword(null);
        userEntity.setProfilePhoto(new Byte[]{'A'});
        userEntity.setRecipes(new HashSet<>());
        userEntity.setRoles(new HashSet<>());
        userEntity.setUsername("janedoe");

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setEmail("jane.doe@example.org");
        userEntity1.setFullName("Dr Jane Doe");
        userEntity1.setId(123L);
        userEntity1.setPassword("iloveyou");
        userEntity1.setProfilePhoto(new Byte[]{'A'});
        userEntity1.setRecipes(new HashSet<>());
        userEntity1.setRoles(new HashSet<>());
        userEntity1.setUsername("janedoe");
        assertNotEquals(userEntity, userEntity1);
    }

    /**
     * Method under test: {@link UserEntity#equals(Object)}
     */
    @Test
    void testEquals13() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFullName("Dr Jane Doe");
        userEntity.setId(123L);
        userEntity.setPassword("iloveyou");
        userEntity.setProfilePhoto(new Byte[]{1});
        userEntity.setRecipes(new HashSet<>());
        userEntity.setRoles(new HashSet<>());
        userEntity.setUsername("janedoe");

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setEmail("jane.doe@example.org");
        userEntity1.setFullName("Dr Jane Doe");
        userEntity1.setId(123L);
        userEntity1.setPassword("iloveyou");
        userEntity1.setProfilePhoto(new Byte[]{'A'});
        userEntity1.setRecipes(new HashSet<>());
        userEntity1.setRoles(new HashSet<>());
        userEntity1.setUsername("janedoe");
        assertNotEquals(userEntity, userEntity1);
    }

    /**
     * Method under test: {@link UserEntity#equals(Object)}
     */
    @Test
    void testEquals14() {
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
        recipeEntity.setIngredients("jane.doe@example.org");
        recipeEntity.setOverview("jane.doe@example.org");
        recipeEntity.setTitle("Dr");
        recipeEntity.setUser(userEntity);
        recipeEntity.setVideoURL("https://example.org/example");
        recipeEntity.setViews(1);

        HashSet<RecipeEntity> recipeEntitySet = new HashSet<>();
        recipeEntitySet.add(recipeEntity);

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setEmail("jane.doe@example.org");
        userEntity1.setFullName("Dr Jane Doe");
        userEntity1.setId(123L);
        userEntity1.setPassword("iloveyou");
        userEntity1.setProfilePhoto(new Byte[]{'A'});
        userEntity1.setRecipes(recipeEntitySet);
        userEntity1.setRoles(new HashSet<>());
        userEntity1.setUsername("janedoe");

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setEmail("jane.doe@example.org");
        userEntity2.setFullName("Dr Jane Doe");
        userEntity2.setId(123L);
        userEntity2.setPassword("iloveyou");
        userEntity2.setProfilePhoto(new Byte[]{'A'});
        userEntity2.setRecipes(new HashSet<>());
        userEntity2.setRoles(new HashSet<>());
        userEntity2.setUsername("janedoe");
        assertNotEquals(userEntity1, userEntity2);
    }

    /**
     * Method under test: {@link UserEntity#equals(Object)}
     */
    @Test
    void testEquals15() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(123L);
        roleEntity.setName("jane.doe@example.org");

        HashSet<RoleEntity> roleEntitySet = new HashSet<>();
        roleEntitySet.add(roleEntity);

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFullName("Dr Jane Doe");
        userEntity.setId(123L);
        userEntity.setPassword("iloveyou");
        userEntity.setProfilePhoto(new Byte[]{'A'});
        userEntity.setRecipes(new HashSet<>());
        userEntity.setRoles(roleEntitySet);
        userEntity.setUsername("janedoe");

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setEmail("jane.doe@example.org");
        userEntity1.setFullName("Dr Jane Doe");
        userEntity1.setId(123L);
        userEntity1.setPassword("iloveyou");
        userEntity1.setProfilePhoto(new Byte[]{'A'});
        userEntity1.setRecipes(new HashSet<>());
        userEntity1.setRoles(new HashSet<>());
        userEntity1.setUsername("janedoe");
        assertNotEquals(userEntity, userEntity1);
    }

    /**
     * Method under test: {@link UserEntity#equals(Object)}
     */
    @Test
    void testEquals16() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFullName("Dr Jane Doe");
        userEntity.setId(123L);
        userEntity.setPassword("iloveyou");
        userEntity.setProfilePhoto(new Byte[]{'A'});
        userEntity.setRecipes(new HashSet<>());
        userEntity.setRoles(new HashSet<>());
        userEntity.setUsername("jane.doe@example.org");

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setEmail("jane.doe@example.org");
        userEntity1.setFullName("Dr Jane Doe");
        userEntity1.setId(123L);
        userEntity1.setPassword("iloveyou");
        userEntity1.setProfilePhoto(new Byte[]{'A'});
        userEntity1.setRecipes(new HashSet<>());
        userEntity1.setRoles(new HashSet<>());
        userEntity1.setUsername("janedoe");
        assertNotEquals(userEntity, userEntity1);
    }

    /**
     * Method under test: {@link UserEntity#equals(Object)}
     */
    @Test
    void testEquals17() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFullName("Dr Jane Doe");
        userEntity.setId(123L);
        userEntity.setPassword("iloveyou");
        userEntity.setProfilePhoto(new Byte[]{'A'});
        userEntity.setRecipes(new HashSet<>());
        userEntity.setRoles(new HashSet<>());
        userEntity.setUsername(null);

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setEmail("jane.doe@example.org");
        userEntity1.setFullName("Dr Jane Doe");
        userEntity1.setId(123L);
        userEntity1.setPassword("iloveyou");
        userEntity1.setProfilePhoto(new Byte[]{'A'});
        userEntity1.setRecipes(new HashSet<>());
        userEntity1.setRoles(new HashSet<>());
        userEntity1.setUsername("janedoe");
        assertNotEquals(userEntity, userEntity1);
    }
}

