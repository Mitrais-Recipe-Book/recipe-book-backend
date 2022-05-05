package com.cdcone.recipy.controller;

import com.cdcone.recipy.dto.PhotoDto;
import com.cdcone.recipy.dto.UserDto;
import com.cdcone.recipy.entity.UserEntity;
import com.cdcone.recipy.response.CommonResponse;
import com.cdcone.recipy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping("{username}")
    public ResponseEntity<CommonResponse> getByUsername(@PathVariable String username) {
        Optional<UserEntity> byUsername = userService.findByUsername(username);
        HttpStatus status = HttpStatus.NOT_FOUND;
        UserDto userDto = null;
        String msg = "User not found.";
        if (byUsername.isPresent()) {
            status = HttpStatus.OK;
            userDto = UserDto.toDto(byUsername.get());
            msg = "Success";
        }
        return ResponseEntity.status(status).body(new CommonResponse(status, msg, userDto));
    }

    @GetMapping("{username}/photo")
    public ResponseEntity<byte[]> getProfilePhoto(@PathVariable String username) {
        PhotoDto userPhoto = userService.getUserPhoto(username);
        if (userPhoto != null && userPhoto.getPhoto() != null) {
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + username + " profile photo")
                    .contentType(MediaType.valueOf(userPhoto.getType()))
                    .body(userPhoto.getPhoto());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping(value = "{username}/photo", consumes = "multipart/form-data")
    public ResponseEntity<CommonResponse> saveProfilePhoto(@PathVariable String username, @RequestParam("photo") MultipartFile photo) {
        Pair<Boolean, String> savedPhoto = userService.saveProfilePhoto(photo, username);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if (savedPhoto.getFirst()) {
            status = HttpStatus.OK;
        }
        return ResponseEntity.status(status).body(new CommonResponse(status, savedPhoto.getSecond()));
    }
}