package com.cdcone.recipy.service;

import com.cdcone.recipy.dtoAccess.*;
import com.cdcone.recipy.dtoRequest.PaginatedDto;
import com.cdcone.recipy.dtoRequest.SignUpDto;
import com.cdcone.recipy.entity.RoleEntity;
import com.cdcone.recipy.entity.UserEntity;
import com.cdcone.recipy.repository.RecipeReactionRepository;
import com.cdcone.recipy.repository.RecipeRepository;
import com.cdcone.recipy.repository.RoleDao;
import com.cdcone.recipy.repository.UserDao;
import com.cdcone.recipy.util.CustomUser;
import com.cdcone.recipy.util.ImageUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private static final UserDao userRepo = mock(UserDao.class);
    private static final RoleDao roleRepo = mock(RoleDao.class);
    private static final RecipeReactionRepository reactionRepo = mock(RecipeReactionRepository.class);
    private static final RecipeRepository recipeRepo = mock(RecipeRepository.class);
    private static final RoleEntity userRole = mock(RoleEntity.class);
    private static final SignUpDto signUpDto = mock(SignUpDto.class);
    private static final UserEntity creator = mock(UserEntity.class);
    private static final UserEntity follower1 = mock(UserEntity.class);
    private static UserService userService;
    private static final RoleService ROLE_SERVICE = mock(RoleService.class);

    @BeforeAll
    public static void setUp() {
        userService = new UserService(userRepo, ROLE_SERVICE, roleRepo,
                reactionRepo, recipeRepo, new BCryptPasswordEncoder());

        when(signUpDto.getEmail()).thenReturn("test@mail.com");
        when(signUpDto.getUsername()).thenReturn("test");
        when(signUpDto.getFullName()).thenReturn("test test");
        when(follower1.getUsername()).thenReturn("follower1");
        when(follower1.getFullName()).thenReturn("Follower 1");
        when(userRole.getId()).thenReturn(1L);
        when(userRole.getName()).thenReturn("User");
    }

    @Test
    void testSuccessAddUser() {
        UserEntity mockUser = mock(UserEntity.class);
        when(signUpDto.getPassword()).thenReturn("password");
        when(roleRepo.findByName("User")).thenReturn(Optional.of(userRole));
        when(userRepo.save(any(UserEntity.class))).thenReturn(mockUser);
        Pair<Optional<UserDto>, String> addUserSuccess = userService.addUser(signUpDto);
        verify(userRepo).save(any(UserEntity.class));
        assertEquals("Success", addUserSuccess.getSecond());
    }

    @Test
    void testFailToAddUserIfPasswordLessThanSixCharacters() {
        UserEntity mockUser = mock(UserEntity.class);
        when(signUpDto.getPassword()).thenReturn("s");
        when(userRepo.save(any(UserEntity.class))).thenReturn(mockUser);
        Pair<Optional<UserDto>, String> addUserFailed = userService.addUser(signUpDto);
        assertEquals("Password must be equal or more than 8 characters", addUserFailed.getSecond());
    }

    @Test
    void testFailToAddUserIfUsernameAlreadyExists() {
        when(signUpDto.getPassword()).thenReturn("password");
        when(roleRepo.findByName("User")).thenReturn(Optional.of(userRole));
        when(userRepo.save(any())).thenThrow(DataIntegrityViolationException.class);
        Pair<Optional<UserDto>, String> addUserFailed = userService.addUser(signUpDto);
        assertEquals("Failed to create user. Username or email is already exists",
                addUserFailed.getSecond());
        reset(userRepo);
    }

    @Test
    void testFailToAddUserIfRoleUserIsNotPresent() {
        UserEntity mockUser = mock(UserEntity.class);
        when(signUpDto.getPassword()).thenReturn("password");
        when(roleRepo.findByName("User")).thenReturn(Optional.empty());
        when(userRepo.save(any(UserEntity.class))).thenReturn(mockUser);
        Pair<Optional<UserDto>, String> addUserFailed = userService.addUser(signUpDto);
        assertEquals("Role User not found", addUserFailed.getSecond());
    }

    @Test
    void testFailToSavePhotoIfUserNotFound() {
        when(userRepo.findByUsername("invalid")).thenReturn(Optional.empty());
        Pair<Boolean, String> invalid = userService
                .saveProfilePhoto(null, "invalid");
        assertEquals("User not found.", invalid.getSecond());
        assertFalse(invalid.getFirst());
    }

    @Test
    void testFailToSavePhotoIfPhotoAccessError() throws IOException {
        UserEntity mockUser = mock(UserEntity.class);
        when(userRepo.findByUsername("user")).thenReturn(Optional.of(mockUser));
        MultipartFile mockPhoto = mock(MultipartFile.class);
        when(mockPhoto.getBytes()).thenThrow(IOException.class);
        when(mockPhoto.getInputStream()).thenThrow(IOException.class);
        Pair<Boolean, String> failedSavePhoto = userService
                .saveProfilePhoto(mockPhoto, "user");
        assertFalse(failedSavePhoto.getFirst());
        assertEquals("Failed to save profile photo.",
                failedSavePhoto.getSecond());
    }

    @Test
    void testSuccessSavePhoto() throws IOException {
        byte[] randomImage = ImageUtil.randomImage();
        UserEntity mockUser = mock(UserEntity.class);
        when(userRepo.findByUsername("user")).thenReturn(Optional.of(mockUser));
        when(mockUser.getId()).thenReturn(11L);
        MultipartFile mockPhoto = mock(MultipartFile.class);
        when(mockPhoto.getBytes()).thenReturn(randomImage);
        when(mockPhoto.getContentType()).thenReturn("image/jpeg");
        when(mockPhoto.getInputStream()).thenReturn(new ByteArrayInputStream(randomImage));

        Pair<Boolean, String> saveProfilePhoto = userService
                .saveProfilePhoto(mockPhoto, "user");

        verify(userRepo).save(mockUser);
        assertTrue(saveProfilePhoto.getFirst());
        assertEquals("Success", saveProfilePhoto.getSecond());
    }

    @Test
    void testFailToAddFollowIfUserNotFound() {
        when(userRepo.findById(1L)).thenReturn(Optional.empty());
        when(userRepo.findById(2L)).thenReturn(Optional.empty());
        assertThrows(Exception.class,
                () -> userService.addFollow(1L, 2L)); // Need error message to check
    }

    @Test
    void testFailToAddFollowIfUserIdAndCreatorIdAreSame() {
        Exception failed = assertThrows(Exception.class, () -> userService.addFollow(1L, 1L));
        assertEquals("Cannot follow yourself", failed.getMessage());
    }

    @Test
    void testFailToAddFollowIfCreatorNotFound() {
        UserEntity mockUser = mock(UserEntity.class);
        when(userRepo.findById(1L)).thenReturn(Optional.of(mockUser));
        assertThrows(Exception.class,
                () -> userService.addFollow(1L, 3L)); // Need error message to check
    }

    @Test
    void testFailToAddFollowIfAlreadyFollowed() {
        UserEntity mockUser = mock(UserEntity.class);
        when(userRepo.findById(1L)).thenReturn(Optional.of(mockUser));
        when(userRepo.findById(2L)).thenReturn(Optional.of(creator));
        when(mockUser.getFollows()).thenReturn(Set.of(creator));
        Exception failed = assertThrows(Exception.class,
                () -> userService.addFollow(1L, 2L));
        assertEquals("You already follow this creator", failed.getMessage());
    }

    @Test
    void testSuccessGetAllUsers() {
        UserEntity mockUser = mock(UserEntity.class);
        when(mockUser.getId()).thenReturn(1L);
        when(mockUser.getUsername()).thenReturn("user");
        when(mockUser.getFullName()).thenReturn("User");
        when(mockUser.getEmail()).thenReturn("user@mail.com");
        when(mockUser.getPassword()).thenReturn("password");
        when(mockUser.getRoles()).thenReturn(Set.of(userRole));
        Pageable pageable = PageRequest.of(0, 10);
        Page<UserEntity> mockResult = new PageImpl<>(List.of(mockUser));
        when(userRepo.findAllPaged(pageable)).thenReturn(mockResult);

        PaginatedDto<UserDto> allUsers = userService.getAllUsers(0);
        assertEquals(1, allUsers.getTotalPages());
        assertEquals(1, allUsers.getData().size());
        assertEquals("user", allUsers.getData().get(0).getUsername());
    }

    @Test
    void testSuccessGetFollowList() {
        UserEntity mockUser = mock(UserEntity.class);
        when(userRepo.findById(1L)).thenReturn(Optional.of(mockUser));
        when(mockUser.getFollows()).thenReturn(Set.of(follower1));

        List<FollowingListDto> followList = userService.getFollowList(1L);

        assertEquals(1, followList.size());
        assertEquals("follower1", followList.get(0).getUsername());
    }

    @Test
    void testFailToLoadCustomUserWithWrongUsername() {
        assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername("invalid"));
    }

    @Test
    void testSuccessLoadCustomUser() {
        UserEntity mockUser = mock(UserEntity.class);
        when(userRepo.findByUsername("user")).thenReturn(Optional.of(mockUser));
        when(mockUser.getPassword()).thenReturn("password");
        when(mockUser.getUsername()).thenReturn("user");
        when(mockUser.getFullName()).thenReturn("Mocked User");
        when(mockUser.getEmail()).thenReturn("mock@mail.com");
        when(mockUser.getRoles()).thenReturn(Set.of(userRole));
        CustomUser customUser = userService.loadUserByUsername("user");
        assertEquals("user", customUser.getUsername());
    }

    @Test
    void testSuccessGetUserById() {
        UserEntity mockUser = mock(UserEntity.class);
        when(mockUser.getId()).thenReturn(1L);
        when(mockUser.getUsername()).thenReturn("user");
        when(userRepo.getById(1L)).thenReturn(mockUser);
        UserEntity userTest = userService.getById(1L);
        assertEquals("user", userTest.getUsername());
    }

    @Test
    void testSuccessFindUserByUsername() {
        UserProfile mockUser = mock(UserProfile.class);
        when(mockUser.getId()).thenReturn(1L);
        when(mockUser.getUsername()).thenReturn("mockuser");
        when(userRepo.findDetailByUsername("mockuser")).thenReturn(Optional.of(mockUser));
        when(reactionRepo.getTotalRecipeLikeByUserId(1L)).thenReturn(100);

        Optional<UserProfile> findMockUser = userService.findByUsername("mockuser");
        assertTrue(findMockUser.isPresent());
        assertEquals("mockuser", findMockUser.get().getUsername());
        verify(mockUser).setRecipeLikes(100);
    }

    @Test
    void testSuccessGetUserPhoto() {
        byte[] photo = new byte[] { 80 };
        PhotoDto mockPhoto = new PhotoDto("PNG", photo);
        when(userRepo.getProfilePhoto("user")).thenReturn(mockPhoto);
        PhotoDto userPhoto = userService.getUserPhoto("user");
        assertEquals("PNG", userPhoto.getType());
        assertEquals(photo, userPhoto.getPhoto());
    }

    @Test
    void testSuccessAddFollow() throws Exception {
        when(userRepo.findById(2L)).thenReturn(Optional.of(creator));
        when(userRepo.findById(1L)).thenReturn(Optional.of(follower1));
        when(creator.getFollows()).thenReturn(Set.of());

        userService.addFollow(1L, 2L); //

        verify(follower1).setFollows(anySet());
        verify(userRepo).save(follower1);
    }

    @Test
    void testFailUnfollowIfNotFollowing() {
        when(userRepo.findById(2L)).thenReturn(Optional.of(creator));
        when(userRepo.findById(1L)).thenReturn(Optional.of(follower1));
        when(creator.getFollows()).thenReturn(Set.of());

        Exception exception = assertThrows(Exception.class,
                () -> userService.unFollow(1L, 2L)); // exception

        assertEquals("You didn't follow this creator", exception.getMessage());
    }

    @Test
    void testSuccessUnfollow() throws Exception {
        UserEntity mockFollower = mock(UserEntity.class);
        when(userRepo.findById(2L)).thenReturn(Optional.of(creator));
        when(userRepo.findById(1L)).thenReturn(Optional.of(mockFollower));
        when(creator.getFollows()).thenReturn(Set.of(mockFollower));

        userService.addFollow(1L, 2L); //

        verify(mockFollower).setFollows(anySet());
        verify(userRepo).save(mockFollower);
    }

    @Test
    void testSuccessGetFollowers() {
        FollowerDto mockUser = mock(FollowerDto.class);
        when(mockUser.getId()).thenReturn(22L);
        when(mockUser.getUsername()).thenReturn("mockuser");
        when(userRepo.getFollowersById(22L)).thenReturn(List.of(mockUser));

        List<FollowerDto> followerList = userService.getFollowerList(22L);
        assertEquals(1, followerList.size());
        assertEquals("mockuser", followerList.get(0).getUsername());
    }

    @Test
    void successRequestCreatorRole() {
        RoleEntity mockRole = mock(RoleEntity.class);
        UserEntity mockUser = mock(UserEntity.class);
        when(ROLE_SERVICE.getByName("User")).thenReturn(Pair.of("success", mockRole));
        when(userService.getByUsername("user1")).thenReturn(Pair.of("success", mockUser));

        assertEquals('s', userService.requestCreatorRole("user1"));
    }
}