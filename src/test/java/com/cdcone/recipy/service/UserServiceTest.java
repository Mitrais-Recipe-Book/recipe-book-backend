package com.cdcone.recipy.service;

import com.cdcone.recipy.dtoAccess.*;
import com.cdcone.recipy.dtoRequest.PaginatedDto;
import com.cdcone.recipy.dtoRequest.SignUpDto;
import com.cdcone.recipy.entity.RoleEntity;
import com.cdcone.recipy.entity.UserEntity;
import com.cdcone.recipy.repository.RoleDao;
import com.cdcone.recipy.repository.UserDao;
import com.cdcone.recipy.util.CustomUser;
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

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private static final UserDao userDao = mock(UserDao.class);
    private static final RoleDao roleDao = mock(RoleDao.class);
    private static final RoleEntity userRole = mock(RoleEntity.class);
    private static final SignUpDto signUpDto = mock(SignUpDto.class);
    private static final UserEntity creator = mock(UserEntity.class);
    private static final UserEntity follower1 = mock(UserEntity.class);
    private static UserService userService;

    @BeforeAll
    public static void setUp() {
        userService = new UserService(userDao, roleDao, new BCryptPasswordEncoder());

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
        when(signUpDto.getPassword()).thenReturn("password");
        when(roleDao.findByName("User")).thenReturn(Optional.of(userRole));
        Pair<Optional<UserEntity>, String> addUserSuccess = userService.addUser(signUpDto);
        verify(userDao).save(any(UserEntity.class));
        assertEquals("Success", addUserSuccess.getSecond());
    }

    @Test
    void testFailToAddUserIfPasswordLessThanSixCharacters() {
        when(signUpDto.getPassword()).thenReturn("s");
        Pair<Optional<UserEntity>, String> addUserFailed = userService.addUser(signUpDto);
        assertEquals("Password must be equal or more than 6 characters", addUserFailed.getSecond());
    }

    @Test
    void testFailToAddUserIfUsernameAlreadyExists() {
        when(signUpDto.getPassword()).thenReturn("password");
        when(roleDao.findByName("User")).thenReturn(Optional.of(userRole));
        when(userDao.save(any())).thenThrow(DataIntegrityViolationException.class);
        Pair<Optional<UserEntity>, String> addUserFailed = userService.addUser(signUpDto);
        assertEquals("Failed to create user. Username or email is already exists",
                addUserFailed.getSecond());
        reset(userDao);
    }

    @Test
    void testFailToAddUserIfRoleUserIsNotPresent() {
        when(signUpDto.getPassword()).thenReturn("password");
        when(roleDao.findByName("User")).thenReturn(Optional.empty());
        Pair<Optional<UserEntity>, String> addUserFailed = userService.addUser(signUpDto);
        assertEquals("Role not found", addUserFailed.getSecond());
    }

    @Test
    void testFailToSavePhotoIfUserNotFound() {
        when(userDao.findByUsername("invalid")).thenReturn(Optional.empty());
        Pair<Boolean, String> invalid = userService
                .saveProfilePhoto(null, "invalid");
        assertEquals("User not found.", invalid.getSecond());
        assertFalse(invalid.getFirst());
    }

    @Test
    void testFailToSavePhotoIfPhotoAccessError() throws IOException {
        UserEntity mockUser = mock(UserEntity.class);
        when(userDao.findByUsername("user")).thenReturn(Optional.of(mockUser));
        MultipartFile mockPhoto = mock(MultipartFile.class);
        when(mockPhoto.getBytes()).thenThrow(IOException.class);
        Pair<Boolean, String> failedSavePhoto = userService
                .saveProfilePhoto(mockPhoto, "user");
        assertFalse(failedSavePhoto.getFirst());
        assertEquals("Failed to save profile photo.",
                failedSavePhoto.getSecond());
    }

    @Test
    void testSuccessSavePhoto() throws IOException {
        UserEntity mockUser = mock(UserEntity.class);
        when(userDao.findByUsername("user")).thenReturn(Optional.of(mockUser));
        when(mockUser.getId()).thenReturn(11L);
        MultipartFile mockPhoto = mock(MultipartFile.class);
        when(mockPhoto.getBytes()).thenReturn(new byte[] { 10 });
        when(mockPhoto.getContentType()).thenReturn("PNG");

        Pair<Boolean, String> saveProfilePhoto = userService
                .saveProfilePhoto(mockPhoto, "user");

        verify(userDao).save(mockUser);
        assertTrue(saveProfilePhoto.getFirst());
        assertEquals("Success", saveProfilePhoto.getSecond());
    }

    @Test
    void testFailToAddFollowIfUserNotFound() {
        when(userDao.findById(1L)).thenReturn(Optional.empty());
        when(userDao.findById(2L)).thenReturn(Optional.empty());
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
        when(userDao.findById(1L)).thenReturn(Optional.of(mockUser));
        assertThrows(Exception.class,
                () -> userService.addFollow(1L, 3L)); // Need error message to check
    }

    @Test
    void testFailToAddFollowIfAlreadyFollowed() {
        UserEntity mockUser = mock(UserEntity.class);
        when(userDao.findById(1L)).thenReturn(Optional.of(mockUser));
        when(userDao.findById(2L)).thenReturn(Optional.of(creator));
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
        when(userDao.findAllPaged(pageable)).thenReturn(mockResult);

        PaginatedDto<UserDto> allUsers = userService.getAllUsers(0);
        assertEquals(1, allUsers.getTotalPages());
        assertEquals(1, allUsers.getData().size());
        assertEquals("user", allUsers.getData().get(0).getUsername());
    }

    @Test
    void testSuccessGetFollowList() {
        UserEntity mockUser = mock(UserEntity.class);
        when(userDao.findById(1L)).thenReturn(Optional.of(mockUser));
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
        when(userDao.findByUsername("user")).thenReturn(Optional.of(mockUser));
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
        when(userDao.getById(1L)).thenReturn(mockUser);
        UserEntity userTest = userService.getById(1L);
        assertEquals("user", userTest.getUsername());
    }

    @Test
    void testSuccessFindUserByUsername() {
        UserProfile mockUser = mock(UserProfile.class);
        when(userDao.findDetailByUsername("mockuser")).thenReturn(Optional.of(mockUser));
        when(mockUser.getUsername()).thenReturn("mockuser");

        Optional<UserProfile> findMockUser = userService.findByUsername("mockuser");
        assertTrue(findMockUser.isPresent());
        assertEquals("mockuser", findMockUser.get().getUsername());
    }

    @Test
    void testSuccessGetUserPhoto() {
        byte[] photo = new byte[] { 80 };
        PhotoDto mockPhoto = new PhotoDto("PNG", photo);
        when(userDao.getProfilePhoto("user")).thenReturn(mockPhoto);
        PhotoDto userPhoto = userService.getUserPhoto("user");
        assertEquals("PNG", userPhoto.getType());
        assertEquals(photo, userPhoto.getPhoto());
    }
}