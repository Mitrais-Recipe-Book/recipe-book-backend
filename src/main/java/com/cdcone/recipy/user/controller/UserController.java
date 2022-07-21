package com.cdcone.recipy.user.controller;

import com.cdcone.recipy.user.dto.repository.FollowerDto;
import com.cdcone.recipy.recipe.dto.response.FollowingListResponseDto;
import com.cdcone.recipy.dto.response.PhotoResponseDto;
import com.cdcone.recipy.recipe.dto.response.UserRecipeResponseDto;
import com.cdcone.recipy.dto.response.PaginatedDto;
import com.cdcone.recipy.user.dto.repository.UserProfile;
import com.cdcone.recipy.user.dto.request.*;
import com.cdcone.recipy.user.dto.response.UserResponseDto;
import com.cdcone.recipy.user.entity.RoleEntity;
import com.cdcone.recipy.user.entity.UserEntity;
import com.cdcone.recipy.dto.response.CommonResponse;
import com.cdcone.recipy.recipe.service.RecipeService;
import com.cdcone.recipy.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;
    private final RecipeService recipeService;

    @PostMapping("sign-up")
    public ResponseEntity<CommonResponse> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        Pair<Optional<UserResponseDto>, String> signUpUser = userService.addUser(signUpRequestDto);
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        UserResponseDto userResponseDto = null;
        if (signUpUser.getFirst().isPresent()) {
            userResponseDto = signUpUser.getFirst().get();
            httpStatus = HttpStatus.OK;
        }
        return ResponseEntity.status(httpStatus).body(new CommonResponse(signUpUser.getSecond(), userResponseDto));
    }

    @PostMapping("sign-in")
    public ResponseEntity<CommonResponse> signIn(@RequestBody SignInRequestDto signInRequestDto) {
        Optional<Map<String, Object>> auth = userService.signIn(signInRequestDto);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String msg = "Wrong username or password";
        if (auth.isPresent()) {
            status = HttpStatus.OK;
            msg = "Success";
        }
        return ResponseEntity.status(status).body(new CommonResponse(msg, auth));
    }

    @GetMapping("{username}")
    public ResponseEntity<CommonResponse> getByUsername(@PathVariable String username) {
        Optional<UserProfile> byUsername = userService.findByUsername(username);
        HttpStatus status = HttpStatus.NOT_FOUND;
        UserProfile userDto = null;
        String msg = "User not found.";
        if (byUsername.isPresent()) {
            status = HttpStatus.OK;
            userDto = byUsername.get();
            msg = "success: data retrieved";
        }
        return ResponseEntity.status(status).body(new CommonResponse(msg, userDto));
    }

    @GetMapping("{username}/photo")
    public ResponseEntity<byte[]> getProfilePhoto(@PathVariable String username) {
        PhotoResponseDto userPhoto = userService.getUserPhoto(username);
        if (userPhoto != null && userPhoto.getPhoto() != null) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + username + " profile photo")
                    .contentType(MediaType.valueOf(userPhoto.getType()))
                    .body(userPhoto.getPhoto());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping(value = "{username}/photo", consumes = "multipart/form-data")
    public ResponseEntity<CommonResponse> saveProfilePhoto(@PathVariable String username,
            @RequestParam("photo") MultipartFile photo) {
        Pair<Boolean, String> savedPhoto = userService.saveProfilePhoto(photo, username);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if (savedPhoto.getFirst()) {
            status = HttpStatus.OK;
        }
        return ResponseEntity.status(status).body(new CommonResponse(savedPhoto.getSecond()));
    }

    @GetMapping
    public ResponseEntity<CommonResponse> getAllUsers(
            @RequestParam(defaultValue = "false") Boolean isPaginated,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        PaginatedDto<UserResponseDto> allUsers = userService.getAllUsers(isPaginated, page, size);
        return ResponseEntity.ok(new CommonResponse(allUsers));
    }

    @GetMapping("{username}/recipes")
    public ResponseEntity<CommonResponse> getRecipesByUsername(@PathVariable(name = "username") String username,
            @RequestParam(defaultValue = "0") int page) {
        PaginatedDto<UserRecipeResponseDto> byUserId = recipeService.getByUsername(username, page, false);
        return ResponseEntity.ok(new CommonResponse(byUserId));
    }

    @PostMapping("/follow")
    public ResponseEntity<CommonResponse> followCreator(@RequestBody FollowUserRequestDto dto) {
        try {
            userService.addFollow(dto.getUserId(), dto.getCreatorId());
            return ResponseEntity.ok(new CommonResponse("Follow succeed"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CommonResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/unfollow")
    public ResponseEntity<CommonResponse> unfollowCreator(@RequestBody FollowUserRequestDto dto) {
        try {
            userService.unFollow(dto.getUserId(), dto.getCreatorId());
            return ResponseEntity.ok(new CommonResponse("Unfollow succeed"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CommonResponse(e.getMessage()));
        }
    }

    @GetMapping("{id}/follow-list")
    public ResponseEntity<CommonResponse> getFollowList(@PathVariable(name = "id") long userId) {
        try {
            List<FollowingListResponseDto> list = userService.getFollowList(userId);
            return ResponseEntity.ok(new CommonResponse("succeed", list));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CommonResponse(e.getMessage()));
        }
    }

    @GetMapping("{id}/followers")
    public ResponseEntity<CommonResponse> getFollowerList(@PathVariable(name = "id") long userId) {
        List<FollowerDto> followerList = userService.getFollowerList(userId);
        return ResponseEntity.ok(new CommonResponse("success", followerList));
    }

    @GetMapping("{user_id}/is-following")
    public ResponseEntity<CommonResponse> isFollowing(
            @PathVariable("user_id") Long userId,
            @RequestParam("creator_id") Long creatorId) {
        Boolean isFollowing = userService.isFollowing(creatorId, userId);
        return ResponseEntity.ok(new CommonResponse("success", isFollowing));
    }

    @GetMapping("{username}/draft-recipes")
    public ResponseEntity<CommonResponse> getDraftRecipes(
            @PathVariable(name = "username") String username,
            @RequestParam(defaultValue = "0") int page) {

        PaginatedDto<UserRecipeResponseDto> result = recipeService.getByUsername(username, page, true);
        return ResponseEntity.ok(new CommonResponse(result));
    }

    @PostMapping("{username}/request-creator")
    public ResponseEntity<CommonResponse> requestCreatorRole(@PathVariable(name = "username") String username) {
        UserResponseDto dto = UserResponseDto.toDto(userService.assignRole(username, "Request"));
        return ResponseEntity.ok(new CommonResponse("success", dto));
    }

    @PutMapping("{username}/approve-creator")
    public ResponseEntity<CommonResponse> approveCreatorRole(@PathVariable(name = "username") String username) {
        userService.removeRole(username, "Request");
        UserResponseDto dto = UserResponseDto.toDto(userService.assignRole(username, "Creator"));
        return ResponseEntity.ok(new CommonResponse("success", dto));

    }

    @PostMapping("{username}/assign-{role}")
    public ResponseEntity<CommonResponse> assignRole(@PathVariable(name = "username") String username,
            @PathVariable(name = "role") String rolename) {

        UserResponseDto dto = UserResponseDto.toDto(userService.assignRole(username, rolename));
        return ResponseEntity.ok(new CommonResponse("success", dto));
    }

    @DeleteMapping("{username}/remove-{role}")
    public ResponseEntity<CommonResponse> removeRole(@PathVariable(name = "username") String username,
            @PathVariable(name = "role") String rolename) {

        UserResponseDto dto = UserResponseDto.toDto(userService.removeRole(username, rolename));
        return ResponseEntity.ok(new CommonResponse("success", dto));
    }

    @GetMapping("/role-request/")
    public ResponseEntity<CommonResponse> getAllUserWithRequestRole() {
        List<UserResponseDto> dto = userService.getUsersWithRoleRequest().stream()
                .map(i -> new UserResponseDto(i.getId(),
                        i.getEmail(),
                        i.getUsername(),
                        i.getFullName(),
                        i.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toSet())))
                .collect(Collectors.toList());

        return ResponseEntity.ok(new CommonResponse(dto));
    }

    @GetMapping("{username}/profile")
    public ResponseEntity<CommonResponse> getProfile(@PathVariable("username") String username) {
        Pair<String, UserEntity> byUsername = userService.getByUsername(username);
        HttpStatus status = HttpStatus.NOT_FOUND;
        UserResponseDto result = null;
        if (byUsername.getFirst().charAt(0) == 's') {
            status = HttpStatus.OK;
            result = UserResponseDto.toDto(byUsername.getSecond());
        }
        return ResponseEntity.status(status).body(new CommonResponse(byUsername.getFirst(), result));
    }

    @PutMapping("{username}/profile")
    public ResponseEntity<CommonResponse> updateProfile(
            @PathVariable("username") String username,
            @RequestBody UpdateUserRequestDto updateUserDto) {
        Pair<HttpStatus, Optional<UserResponseDto>> updateUser = userService.updateUser(username, updateUserDto);
        String msg = "Failed: user not found";
        UserResponseDto result = null;
        HttpStatus status = updateUser.getFirst();
        if (status.equals(HttpStatus.OK) && updateUser.getSecond().isPresent()) {
            msg = "Success: user updated";
            result = updateUser.getSecond().get();
        } else if (status.equals(HttpStatus.BAD_REQUEST)) {
            msg = "Failed to update user. Email is already exists";
        }

        return ResponseEntity.status(status).body(new CommonResponse(msg, result));
    }

    @GetMapping("{username}/favorite-recipe")
    public ResponseEntity<CommonResponse> getUserFavoriteRecipes(
            @PathVariable("username") String username,
            @RequestParam(defaultValue = "false") Boolean isPaginated,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        Pair<String, PaginatedDto<UserRecipeResponseDto>> result = recipeService.getUserFavoriteRecipes(username,
                isPaginated, page, size);

        if (result.getFirst().charAt(0) == 's') {
            return ResponseEntity.ok().body(new CommonResponse(result.getFirst(), result.getSecond()));
        }
        return ResponseEntity.badRequest().body(new CommonResponse(result.getFirst()));
    }

    @PutMapping("{username}/profile/change-password")
    public ResponseEntity<CommonResponse> changePassword(
            @PathVariable("username") String username,
            @RequestBody ChangePasswordRequestDto changePasswordRequestDto) {
        UserResponseDto userResponseDto = userService
                .changePassword(username, changePasswordRequestDto);
        return ResponseEntity.ok(new CommonResponse(userResponseDto));
    }
}
