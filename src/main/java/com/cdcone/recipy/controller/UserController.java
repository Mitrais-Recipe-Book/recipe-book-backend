package com.cdcone.recipy.controller;

import com.cdcone.recipy.dtoAccess.*;
import com.cdcone.recipy.dtoRequest.*;
import com.cdcone.recipy.entity.UserEntity;
import com.cdcone.recipy.response.CommonResponse;
import com.cdcone.recipy.service.RecipeService;
import com.cdcone.recipy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;
    private final RecipeService recipeService;

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
        PhotoDto userPhoto = userService.getUserPhoto(username);
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
    public ResponseEntity<CommonResponse> getAllUsers(@RequestParam(defaultValue = "0") Integer page) {
        PaginatedDto<UserDto> allUsers = userService.getAllUsers(page);
        return ResponseEntity.ok(new CommonResponse(allUsers));
    }

    @GetMapping("{username}/recipes")
    public ResponseEntity<CommonResponse> getRecipesByUsername(@PathVariable(name = "username") String username,
            @RequestParam(defaultValue = "0") int page) {
        PaginatedDto<UserRecipeDto> byUserId = recipeService.getByUsername(username, page, false);
        return ResponseEntity.ok(new CommonResponse(byUserId));
    }

    @PostMapping("/follow")
    public ResponseEntity<CommonResponse> followCreator(@RequestBody FollowUserDto dto) {
        try {
            userService.addFollow(dto.getUserId(), dto.getCreatorId());
            return ResponseEntity.ok(new CommonResponse("Follow succeed"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CommonResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/unfollow")
    public ResponseEntity<CommonResponse> unfollowCreator(@RequestBody FollowUserDto dto) {
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
            List<FollowingListDto> list = userService.getFollowList(userId);
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

        PaginatedDto<UserRecipeDto> result = recipeService.getByUsername(username, page, true);
        return ResponseEntity.ok(new CommonResponse(result));
    }

    @PostMapping("{username}/request-creator")
    public ResponseEntity<CommonResponse> requestCreatorRole(@PathVariable(name = "username") String username) {
        String result = userService.assignRole(username, "Request");

        if (result.charAt(0) == 's') {
            return ResponseEntity.ok(new CommonResponse(result));
        }

        return ResponseEntity.badRequest().body(new CommonResponse(result));
    }

    @PostMapping("{username}/assign-{role}")
    public ResponseEntity<CommonResponse> assignRole(@PathVariable(name = "username") String username,
            @PathVariable(name = "role") String rolename) {
        String result = userService.assignRole(username, rolename);

        if (result.charAt(0) == 's') {
            return ResponseEntity.ok(new CommonResponse(result));
        }

        return ResponseEntity.badRequest().body(new CommonResponse(result));
    }

    @GetMapping("{username}/profile")
    public ResponseEntity<CommonResponse> getProfile(@PathVariable("username") String username) {
        Pair<String, UserEntity> byUsername = userService.getByUsername(username);
        HttpStatus status = HttpStatus.NOT_FOUND;
        UserDto result = null;
        if (byUsername.getFirst().charAt(0) == 's') {
            status = HttpStatus.OK;
            result = UserDto.toDto(byUsername.getSecond());
        }
        return ResponseEntity.status(status).body(new CommonResponse(byUsername.getFirst(), result));
    }

    @PutMapping("{username}/profile")
    public ResponseEntity<CommonResponse> updateProfile(
            @PathVariable("username") String username,
            @RequestBody UpdateUserDto updateUserDto) {
        Pair<HttpStatus, Optional<UserDto>> updateUser = userService.updateUser(username, updateUserDto);
        String msg = "Failed: user not found";
        UserDto result = null;
        HttpStatus status = updateUser.getFirst();
        if (status.equals(HttpStatus.OK)) {
            msg = "Success: user updated";
            result = updateUser.getSecond().get();
        } else if (status.equals(HttpStatus.BAD_REQUEST)) {
            msg = "Failed to update user. Username or email is already exists";
        }

        return ResponseEntity.status(status).body(new CommonResponse(msg, result));
    }
}