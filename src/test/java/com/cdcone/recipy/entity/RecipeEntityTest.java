package com.cdcone.recipy.entity;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class RecipeEntityTest {
    /**
     * Method under test: {@link RecipeEntity#RecipeEntity(UserEntity, String, String, LocalDate, String, String, String, int, boolean, Byte[])}
     */
    @Test
    void testConstructor() throws UnsupportedEncodingException {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFullName("Dr Jane Doe");
        userEntity.setId(123L);
        userEntity.setPassword("iloveyou");
        userEntity.setProfilePhoto("AAAAAAAA".getBytes("UTF-8"));
        HashSet<RecipeEntity> recipeEntitySet = new HashSet<>();
        userEntity.setRecipes(recipeEntitySet);
        HashSet<RoleEntity> roleEntitySet = new HashSet<>();
        userEntity.setRoles(roleEntitySet);
        userEntity.setUsername("janedoe");
        LocalDate ofEpochDayResult = LocalDate.ofEpochDay(1L);
        Byte[] byteArray = new Byte[]{'A'};
        RecipeEntity actualRecipeEntity = new RecipeEntity(userEntity, "Dr", "Overview", ofEpochDayResult, "Ingredients",
                "Not all who wander are lost", "https://example.org/example", 1, true, byteArray);

        Byte[] bannerImage = actualRecipeEntity.getBannerImage();
        assertEquals(1, bannerImage.length);
        assertArrayEquals(new Byte[]{'A'}, bannerImage);
        //assertEquals('A', bannerImage[0]);
        assertTrue(actualRecipeEntity.isDraft());
        assertEquals(1, actualRecipeEntity.getViews());
        assertEquals("https://example.org/example", actualRecipeEntity.getVideoURL());
        UserEntity user = actualRecipeEntity.getUser();
        assertSame(userEntity, user);
        assertEquals("Dr", actualRecipeEntity.getTitle());
        assertEquals("Not all who wander are lost", actualRecipeEntity.getContent());
        assertNull(actualRecipeEntity.getId());
        assertEquals("Ingredients", actualRecipeEntity.getIngredients());
        LocalDate dateCreated = actualRecipeEntity.getDateCreated();
        assertSame(ofEpochDayResult, dateCreated);
        assertEquals("1970-01-02", dateCreated.toString());
        assertEquals("Overview", actualRecipeEntity.getOverview());
        assertNull(actualRecipeEntity.getTags());
        assertEquals(123L, user.getId().longValue());
        assertEquals("Dr Jane Doe", user.getFullName());
        assertEquals("jane.doe@example.org", user.getEmail());
        assertEquals(8, user.getProfilePhoto().length);
        assertNull(user.getProfilePhotoType());
        Set<RoleEntity> roles = user.getRoles();
        assertSame(roleEntitySet, roles);
        assertTrue(roles.isEmpty());
        assertEquals("janedoe", user.getUsername());
        assertEquals("iloveyou", user.getPassword());
        Set<RecipeEntity> recipes = user.getRecipes();
        assertSame(recipeEntitySet, recipes);
        assertTrue(recipes.isEmpty());
        assertSame(user, userEntity);
        assertSame(bannerImage, byteArray);
    }
}

