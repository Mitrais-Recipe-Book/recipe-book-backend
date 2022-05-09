package com.cdcone.recipy.controller;

import com.cdcone.recipy.dto.PaginatedDto;
import com.cdcone.recipy.dto.PhotoDto;
import com.cdcone.recipy.dto.RecipeDtoList;
import com.cdcone.recipy.dto.UserDto;
import com.cdcone.recipy.entity.UserEntity;
import com.cdcone.recipy.response.CommonResponse;
import com.cdcone.recipy.service.RecipeService;
import com.cdcone.recipy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        try {
            Optional<UserEntity> byUsername = userService.findByUsername(username);
            HttpStatus status = HttpStatus.NOT_FOUND;
            UserDto userDto = null;
            String msg = "User not found.";
            if (byUsername.isPresent()) {
                status = HttpStatus.OK;
                userDto = UserDto.toDto(byUsername.get());
                msg = "success: data retrieved";
            }
            return ResponseEntity.status(status).body(new CommonResponse(msg, userDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CommonResponse(e.getCause().toString()));
        }
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
        return ResponseEntity.status(status).body(new CommonResponse( savedPhoto.getSecond()));
    }

    @GetMapping
    public ResponseEntity<CommonResponse> getAllUsers(@RequestParam(defaultValue = "0") Integer page) {
        Page<UserDto> allUsers = userService.getAllUsers(page);
        return ResponseEntity.ok(new CommonResponse(new PaginatedDto<>(allUsers.getContent(), allUsers.getNumber(), allUsers.getTotalPages())));
    }

    @GetMapping("{username}/recipes")
    public ResponseEntity<CommonResponse> getRecipesById(@PathVariable(name = "username") String username, @RequestParam(defaultValue = "0") int page) {
        PaginatedDto<RecipeDtoList> byUserId = recipeService.getByUsername(username, page);
        return ResponseEntity.ok(new CommonResponse(byUserId));
    }
}