package com.cdcone.recipy.controller;

import com.cdcone.recipy.dto.UserDto;
import com.cdcone.recipy.entity.UserEntity;
import com.cdcone.recipy.response.CommonResponse;
import com.cdcone.recipy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
        if (byUsername.isPresent()) {
            status = HttpStatus.OK;
            userDto = UserDto.toDto(byUsername.get());
        }
        return ResponseEntity.status(status).body(new CommonResponse(status, userDto));
    }
}