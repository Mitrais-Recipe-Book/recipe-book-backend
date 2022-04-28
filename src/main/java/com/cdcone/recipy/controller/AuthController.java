package com.cdcone.recipy.controller;

import com.cdcone.recipy.dto.SignUpDto;
import com.cdcone.recipy.dto.UserDto;
import com.cdcone.recipy.entity.UserEntity;
import com.cdcone.recipy.service.UserService;
import com.cdcone.recipy.util.RecipyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("sign-up")
    public ResponseEntity<RecipyResponse> signUp(@RequestBody SignUpDto signUpDto) {
        Pair<Optional<UserEntity>, String> signUpUser = userService.addUser(signUpDto);
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        UserDto userDto = null;
        if (signUpUser.getFirst().isPresent()) {
            userDto = UserDto.toDto(signUpUser.getFirst().get());
            httpStatus = HttpStatus.OK;
        }
        return ResponseEntity.status(httpStatus).body(new RecipyResponse(userDto, signUpUser.getSecond()));
    }
}
