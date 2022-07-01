package com.cdcone.recipy.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import com.cdcone.recipy.dtoAccess.FollowerDto;
import com.cdcone.recipy.dtoAccess.FollowingListDto;
import com.cdcone.recipy.dtoAccess.PhotoDto;
import com.cdcone.recipy.dtoAccess.UserDto;
import com.cdcone.recipy.dtoAccess.UserProfile;
import com.cdcone.recipy.dtoAccess.UserRecipeDto;
import com.cdcone.recipy.dtoRequest.FollowUserDto;
import com.cdcone.recipy.dtoRequest.PaginatedDto;
import com.cdcone.recipy.service.RecipeService;
import com.cdcone.recipy.service.UserService;
import com.cdcone.recipy.util.ImageUtil;

public class UserControllerTest {

    private static final UserProfile USER_PROFILE = mock(UserProfile.class);

    private static final UserService USER_SERVICE = mock(UserService.class);
    private static final RecipeService RECIPE_SERVICE = mock(RecipeService.class);

    private static UserController userController;

    @BeforeAll
    static void init() {
        userController = new UserController(USER_SERVICE, RECIPE_SERVICE);
    }

    @Test
    void getByUsername() {
        Optional<UserProfile> mockResult = Optional.of(USER_PROFILE);

        when(USER_SERVICE.findByUsername("any"))
                .thenReturn(mockResult);

        assertEquals(HttpStatus.OK,
                userController.getByUsername("any").getStatusCode());
    }

    @Test
    void failGetByUsername() {
        Optional<UserProfile> mockResult = Optional.empty();

        when(USER_SERVICE.findByUsername("any"))
                .thenReturn(mockResult);

        assertNotEquals(HttpStatus.OK,
                userController.getByUsername("any").getStatusCode());
    }

    @Test
    void getProfilePhoto() throws IOException {
        PhotoDto mockResult = mock(PhotoDto.class);

        when(mockResult.getPhoto()).thenReturn(ImageUtil.randomImage());
        when(mockResult.getType()).thenReturn("image/png");
        when(USER_SERVICE.getUserPhoto("any")).thenReturn(mockResult);

        assertEquals(HttpStatus.OK,
                userController.getProfilePhoto("any").getStatusCode());
    }

    @Test
    void failGetProfilePhoto() {
        PhotoDto mockResult = mock(PhotoDto.class);

        when(mockResult.getPhoto()).thenReturn(null);
        when(mockResult.getType()).thenReturn(null);
        when(USER_SERVICE.getUserPhoto("any")).thenReturn(mockResult);

        assertNotEquals(HttpStatus.OK,
                userController.getProfilePhoto("any").getStatusCode());
    }

    @Test
    void saveProfilePhoto() {
        Pair<Boolean, String> mockResult = Pair.of(true, "any");
        MultipartFile mockPhoto = mock(MultipartFile.class);

        when(USER_SERVICE.saveProfilePhoto(mockPhoto, "any"))
                .thenReturn(mockResult);

        assertEquals(HttpStatus.OK,
                userController.saveProfilePhoto("any", mockPhoto).getStatusCode());
    }

    @Test
    void failSaveProfilePhoto() {
        Pair<Boolean, String> mockResult = Pair.of(false, "any");
        MultipartFile mockPhoto = mock(MultipartFile.class);

        when(USER_SERVICE.saveProfilePhoto(mockPhoto, "any"))
                .thenReturn(mockResult);

        assertNotEquals(HttpStatus.OK,
                userController.saveProfilePhoto("any", mockPhoto).getStatusCode());
    }

    @Test
    @SuppressWarnings("unchecked")
    void getAllUsers() {
        PaginatedDto<UserDto> mockResult = mock(PaginatedDto.class);

        when(USER_SERVICE.getAllUsers(0))
                .thenReturn(mockResult);

        assertEquals(HttpStatus.OK,
                userController.getAllUsers(0).getStatusCode());
    }

    @Test
    void getRecipesByUsername() {
        List<UserRecipeDto> mockUserRecipe = new ArrayList<>();
        mockUserRecipe.add(new UserRecipeDto(1,
                "title",
                "overview",
                "authorName",
                1));

        PaginatedDto<UserRecipeDto> mockResult = new PaginatedDto<>(mockUserRecipe, 0, 1, true, 1);

        when(RECIPE_SERVICE.getByUsername("any", 0, false))
                .thenReturn(mockResult);
        assertEquals(HttpStatus.OK,
                userController.getRecipesByUsername("any", 0).getStatusCode());
    }

    @Test
    void followCreator() throws Exception {
        doNothing().when(USER_SERVICE).addFollow(1L, 2L);

        assertEquals(HttpStatus.OK,
                userController.followCreator(new FollowUserDto(1L, 2L)).getStatusCode());
    }

    @Test
    void failedFollowCreator() throws Exception {
        doThrow(Exception.class).when(USER_SERVICE).addFollow(1L, 2L);

        assertNotEquals(HttpStatus.OK,
                userController.followCreator(new FollowUserDto(1L, 2L)).getStatusCode());
    }

    @Test
    void unfollowCreator() throws Exception {
        doNothing().when(USER_SERVICE).unFollow(1L, 2L);

        assertEquals(HttpStatus.OK,
                userController.unfollowCreator(new FollowUserDto(1L, 2L)).getStatusCode());
    }

    @Test
    void failedUnfollowCreator() throws Exception {
        doThrow(Exception.class).when(USER_SERVICE).unFollow(1L, 2L);

        assertNotEquals(HttpStatus.OK,
                userController.unfollowCreator(new FollowUserDto(1L, 2L)).getStatusCode());
    }

    @Test
    @SuppressWarnings("unchecked")
    void getFollowList() {
        List<FollowingListDto> mockResult = mock(List.class);

        when(USER_SERVICE.getFollowList(1L))
                .thenReturn(mockResult);

        assertEquals(HttpStatus.OK,
                userController.getFollowList(1L).getStatusCode());
    }

    @Test
    @SuppressWarnings("unchecked")
    void getFollowerList() {
        List<FollowerDto> mockResult = mock(List.class);

        when(USER_SERVICE.getFollowerList(1L))
                .thenReturn(mockResult);

        assertEquals(HttpStatus.OK,
                userController.getFollowerList(1L).getStatusCode());
    }

    @Test
    void isFollowing() {
        when(USER_SERVICE.isFollowing(1L, 2L))
                .thenReturn(true);

        assertEquals(HttpStatus.OK,
                userController.isFollowing(1L, 2L).getStatusCode());
    }

    @Test
    void successRequestCreatorRole(){

    }
}
