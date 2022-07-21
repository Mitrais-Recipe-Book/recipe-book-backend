package com.cdcone.recipy.service;

import com.cdcone.recipy.recipe.entity.RecipeEntity;
import com.cdcone.recipy.recipe.entity.RecipeReactionEntity;
import com.cdcone.recipy.recipe.service.RecipeReactionService;
import com.cdcone.recipy.user.dto.repository.FollowerDto;
import com.cdcone.recipy.recipe.dto.response.FollowingListResponseDto;
import com.cdcone.recipy.dto.response.PhotoResponseDto;
import com.cdcone.recipy.dto.response.PaginatedDto;
import com.cdcone.recipy.user.dto.request.ChangePasswordRequestDto;
import com.cdcone.recipy.user.dto.request.SignUpRequestDto;
import com.cdcone.recipy.user.dto.request.UpdateUserRequestDto;
import com.cdcone.recipy.user.dto.repository.UserProfile;
import com.cdcone.recipy.user.dto.response.UserResponseDto;
import com.cdcone.recipy.user.entity.RoleEntity;
import com.cdcone.recipy.user.entity.UserEntity;
import com.cdcone.recipy.recipe.repository.RecipeReactionRepository;
import com.cdcone.recipy.recipe.repository.RecipeRepository;
import com.cdcone.recipy.error.PasswordNotMatchException;
import com.cdcone.recipy.user.repository.RoleRepository;
import com.cdcone.recipy.user.repository.UserRepository;
import com.cdcone.recipy.user.service.RoleService;
import com.cdcone.recipy.user.service.UserService;
import com.cdcone.recipy.security.CustomUser;
import com.cdcone.recipy.util.ImageUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private static final UserRepository userRepo = mock(UserRepository.class);
    private static final RoleRepository roleRepo = mock(RoleRepository.class);
    private static final RecipeReactionRepository reactionRepo = mock(RecipeReactionRepository.class);
    private static final RecipeRepository recipeRepo = mock(RecipeRepository.class);
    private static final RoleEntity userRole = mock(RoleEntity.class);
    private static final SignUpRequestDto SIGN_UP_REQUEST_DTO = mock(SignUpRequestDto.class);
    private static final UserEntity creator = mock(UserEntity.class);
    private static final UserEntity follower1 = mock(UserEntity.class);
    private static UserService userService;
    private static final RoleService ROLE_SERVICE = mock(RoleService.class);
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final RecipeReactionService recipeReactionService = mock(RecipeReactionService.class);

    @BeforeAll
    public static void setUp() {
        userService = new UserService(userRepo, ROLE_SERVICE,
                recipeReactionService, new BCryptPasswordEncoder());

        when(SIGN_UP_REQUEST_DTO.getEmail()).thenReturn("test@mail.com");
        when(SIGN_UP_REQUEST_DTO.getUsername()).thenReturn("test");
        when(SIGN_UP_REQUEST_DTO.getFullName()).thenReturn("test test");
        when(follower1.getUsername()).thenReturn("follower1");
        when(follower1.getFullName()).thenReturn("Follower 1");
        when(userRole.getId()).thenReturn(1L);
        when(userRole.getName()).thenReturn("User");
    }

    @Test
    void testSuccessAddUser() {
        UserEntity mockUser = mock(UserEntity.class);
        when(SIGN_UP_REQUEST_DTO.getPassword()).thenReturn("password");
        when(roleRepo.findByName("User")).thenReturn(Optional.of(userRole));
        when(userRepo.save(any(UserEntity.class))).thenReturn(mockUser);
        Pair<Optional<UserResponseDto>, String> addUserSuccess = userService.addUser(SIGN_UP_REQUEST_DTO);
        verify(userRepo).save(any(UserEntity.class));
        assertEquals("Success", addUserSuccess.getSecond());
    }

    @Test
    void testFailToAddUserIfPasswordLessThanSixCharacters() {
        UserEntity mockUser = mock(UserEntity.class);
        when(SIGN_UP_REQUEST_DTO.getPassword()).thenReturn("s");
        when(userRepo.save(any(UserEntity.class))).thenReturn(mockUser);
        Pair<Optional<UserResponseDto>, String> addUserFailed = userService.addUser(SIGN_UP_REQUEST_DTO);
        assertEquals("Password must be equal or more than 8 characters", addUserFailed.getSecond());
    }

    @Test
    void testFailToAddUserIfUsernameAlreadyExists() {
        when(SIGN_UP_REQUEST_DTO.getPassword()).thenReturn("password");
        when(roleRepo.findByName("User")).thenReturn(Optional.of(userRole));
        when(userRepo.save(any())).thenThrow(DataIntegrityViolationException.class);
        Pair<Optional<UserResponseDto>, String> addUserFailed = userService.addUser(SIGN_UP_REQUEST_DTO);
        assertEquals("Failed to create user. Username or email is already exists",
                addUserFailed.getSecond());
        reset(userRepo);
    }

    @Test
    void testFailToAddUserIfRoleUserIsNotPresent() {
        UserEntity mockUser = mock(UserEntity.class);
        when(SIGN_UP_REQUEST_DTO.getPassword()).thenReturn("password");
        when(roleRepo.findByName("User")).thenReturn(Optional.empty());
        when(userRepo.save(any(UserEntity.class))).thenReturn(mockUser);
        Pair<Optional<UserResponseDto>, String> addUserFailed = userService.addUser(SIGN_UP_REQUEST_DTO);
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
        Page<UserEntity> mockResult = new PageImpl<>(List.of(mockUser));
        when(userRepo.findAllPaged(any(Pageable.class))).thenReturn(mockResult);

        PaginatedDto<UserResponseDto> allUsers = userService.getAllUsers(false, 0, 0);
        assertEquals(1, allUsers.getTotalPages());
        assertEquals(1, allUsers.getData().size());
        assertEquals("user", allUsers.getData().get(0).getUsername());
    }

    @Test
    void testSuccessGetFollowList() {
        UserEntity mockUser = mock(UserEntity.class);
        when(userRepo.findById(1L)).thenReturn(Optional.of(mockUser));
        when(mockUser.getFollows()).thenReturn(Set.of(follower1));

        List<FollowingListResponseDto> followList = userService.getFollowList(1L);

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
        UserEntity mockUser = new UserEntity("user@mail.com", "mockuser", "password", "user");
        when(userRepo.findByUsername("mockuser")).thenReturn(Optional.of(mockUser));

        RecipeEntity recipe1 = mock(RecipeEntity.class);
        when(recipe1.getId()).thenReturn(111L);
        when(recipe1.isDraft()).thenReturn(false);
        mockUser.setRecipes(List.of(recipe1));
        mockUser.setId(33L);

        List<Long> recipeId = List.of(111L);
        RecipeReactionEntity reaction1 = mock(RecipeReactionEntity.class);
        when(reaction1.getRecipe()).thenReturn(recipe1);

        List<RecipeReactionEntity> reactionList = List.of(reaction1);
        when(recipeReactionService.getReactionByMultipleRecipeId(recipeId))
                .thenReturn(reactionList);

        Optional<UserProfile> findMockUser = userService.findByUsername("mockuser");
        assertTrue(findMockUser.isPresent());
        UserProfile userProfile = findMockUser.get();
        assertEquals("mockuser", userProfile.getUsername());
        assertEquals(1, userProfile.getRecipeLikes());
    }

    @Test
    void testSuccessGetUserPhoto() {
        byte[] photo = new byte[] { 80 };
        PhotoResponseDto mockPhoto = new PhotoResponseDto("PNG", photo);
        when(userRepo.getProfilePhoto("user")).thenReturn(mockPhoto);
        PhotoResponseDto userPhoto = userService.getUserPhoto("user");
        assertEquals("PNG", userPhoto.getType());
        assertEquals(photo, userPhoto.getPhoto());
    }

    @Test
    void testSuccessAddFollow() throws Exception {
        reset(userRepo);
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
    void assignRole_willSuccess_whenUsernameAndRolenameAreFound() {
        Pair<String, RoleEntity> mockRole = Pair.of("success", mock(RoleEntity.class));
        UserEntity mockUser = mock(UserEntity.class);

        when(ROLE_SERVICE.getByName("rolename")).thenReturn(mockRole);
        when(userRepo.findByUsername("username")).thenReturn(Optional.of(mockUser));
        when(userRepo.save(mockUser)).thenReturn(mockUser);

        assertDoesNotThrow(() -> userService.removeRole("username", "rolename"));
        assertEquals(mockUser, userService.removeRole("username", "rolename"));
    }

    @Test
    void assignRole_willFail_whenRoleNotFound() {
        reset(userRepo);
        UserEntity mockUser = mock(UserEntity.class);

        when(ROLE_SERVICE.getByName("rolename")).thenThrow(EntityNotFoundException.class);
        when(userRepo.findByUsername("username")).thenReturn(Optional.of(mockUser));

        assertThrows(EntityNotFoundException.class,
                () -> userService.assignRole("username", "rolename"));
    }

    @Test
    void assignRole_willFail_whenUsernameNotFound() {
        Pair<String, RoleEntity> mockRole = Pair.of("success", mock(RoleEntity.class));

        when(ROLE_SERVICE.getByName("rolename")).thenReturn(mockRole);
        when(userRepo.findByUsername("username")).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class,
                () -> userService.assignRole("username", "rolename"));
    }

    @Test
    void testFailUpdateUserIfNotFound() {
        String username = "user1";
        when(userRepo.findByUsername(username)).thenReturn(Optional.empty());

        EntityNotFoundException result = assertThrows(EntityNotFoundException.class,
                () -> userService.updateUser(username, mock(UpdateUserRequestDto.class)));
        assertEquals(username, result.getMessage());
    }

    @Test
    void testFailUpdateUserIfAlreadyExist() {
        UpdateUserRequestDto updateUserDto = new UpdateUserRequestDto("user 123", "user1@mail.com");
        when(userRepo.findByUsername("user1")).thenReturn(Optional.of(follower1));
        when(userRepo.save(any(UserEntity.class)))
                .thenThrow(DataIntegrityViolationException.class);

        Pair<HttpStatus, Optional<UserResponseDto>> result = userService.updateUser("user1", updateUserDto);

        assertEquals(HttpStatus.BAD_REQUEST, result.getFirst());
    }

    @Test
    void testSuccessUpdateUser() {
        reset(userRepo);

        UpdateUserRequestDto updateUserDto = new UpdateUserRequestDto("user 123", "user1@mail.com");
        when(userRepo.findByUsername("user1")).thenReturn(Optional.of(follower1));

        Pair<HttpStatus, Optional<UserResponseDto>> result = userService.updateUser("user1", updateUserDto);

        verify(userRepo).save(any(UserEntity.class));
        assertEquals(HttpStatus.OK, result.getFirst());
    }

    @Test
    void removeRole_willSuccess_whenUsernameAndRolenameAreFound() {
        Pair<String, RoleEntity> mockRole = Pair.of("success", mock(RoleEntity.class));
        UserEntity mockUser = mock(UserEntity.class);

        when(ROLE_SERVICE.getByName("rolename")).thenReturn(mockRole);
        when(userRepo.findByUsername("username")).thenReturn(Optional.of(mockUser));
        when(userRepo.save(mockUser)).thenReturn(mockUser);

        assertDoesNotThrow(() -> userService.removeRole("username", "rolename"));
        assertEquals(mockUser, userService.removeRole("username", "rolename"));
    }

    @Test
    void changePassword_willThrowError_whenOldPasswordNotMatch() {
        String username = "user1";
        String oldPassword = "old_password";
        String newPassword = "new_password";
        String confirmPassword = "new_password";

        ChangePasswordRequestDto requestBody = new ChangePasswordRequestDto(oldPassword, newPassword, confirmPassword);
        UserEntity mockUser = mock(UserEntity.class);
        when(mockUser.getPassword()).thenReturn(passwordEncoder.encode("original_password"));
        when(userRepo.findByUsername(username)).thenReturn(Optional.of(mockUser));

        PasswordNotMatchException result = assertThrows(PasswordNotMatchException.class,
                () -> userService.changePassword(username, requestBody));

        assertEquals("failed: password does not match", result.getMessage());
    }

    @Test
    void changePassword_willThrowError_whenConfirmPasswordNotMatch() {
        String username = "user1";
        String oldPassword = "original_password";
        String newPassword = "new_password";
        String confirmPassword = "confirm_password";

        UserEntity mockUser = mock(UserEntity.class);
        ChangePasswordRequestDto requestBody = new ChangePasswordRequestDto(oldPassword, newPassword, confirmPassword);
        when(mockUser.getPassword()).thenReturn(passwordEncoder.encode("original_password"));
        when(userRepo.findByUsername(username)).thenReturn(Optional.of(mockUser));

        PasswordNotMatchException result = assertThrows(PasswordNotMatchException.class,
                () -> userService.changePassword(username, requestBody));

        assertEquals("failed: password does not match", result.getMessage());
    }

    @Test
    void changePassword_willReturnUserResponseDto_whenPasswordMatch() {
        String username = "user1";
        String originalPassword = passwordEncoder.encode("original_password");
        String oldPassword = "original_password";
        String newPassword = "new_password";
        String confirmPassword = "new_password";
        String email = "user1@mail.com";

        ChangePasswordRequestDto requestBody = new ChangePasswordRequestDto(oldPassword, newPassword, confirmPassword);
        UserEntity mockUser = new UserEntity(email, username, originalPassword, "user 1");
        mockUser.setId(11L);
        mockUser.setRoles(Set.of(userRole));

        when(userRepo.findByUsername(username)).thenReturn(Optional.of(mockUser));

        UserResponseDto result = userService
                .changePassword(username, requestBody);

        assertNotNull(result);
        verify(userRepo).save(mockUser);
        assertEquals("user1@mail.com", result.getEmail());
    }

    @Test
    void getUserWithRequestRole_willReturnPaginatedListOfUserEntityWithRole() {
        UserEntity mockEntity = mock(UserEntity.class);
        List<UserEntity> mockResult = List.of(mockEntity);

        when(userRepo.getUsersWithRole("Request"))
                .thenReturn(mockResult);

        assertEquals(mockResult, userService.getUsersWithRoleRequest());
    }
}