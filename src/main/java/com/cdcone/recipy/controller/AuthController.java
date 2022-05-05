package com.cdcone.recipy.controller;

import com.cdcone.recipy.dto.SignInDto;
import com.cdcone.recipy.dto.SignUpDto;
import com.cdcone.recipy.dto.UserDto;
import com.cdcone.recipy.entity.UserEntity;
import com.cdcone.recipy.service.AuthService;
import com.cdcone.recipy.service.UserService;
import com.cdcone.recipy.util.RecipyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

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

    @PostMapping("sign-in")
    public ResponseEntity<RecipyResponse> signIn(@RequestBody SignInDto signInDto) {
        Optional<String> auth = authService.auth(signInDto);
        if (auth.isPresent()) {
            return ResponseEntity.ok(new RecipyResponse(Map.of("access_token", auth.get()), "Success"));
        } else {
            return ResponseEntity.badRequest().body(new RecipyResponse(null, "Wrong username or password"));
        }
    }
}
