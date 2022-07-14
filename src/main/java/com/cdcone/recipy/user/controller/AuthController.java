package com.cdcone.recipy.user.controller;

import com.cdcone.recipy.user.dto.response.UserResponseDto;
import com.cdcone.recipy.user.dto.request.SignInRequestDto;
import com.cdcone.recipy.user.dto.request.SignUpRequestDto;
import com.cdcone.recipy.dto.response.CommonResponse;
import com.cdcone.recipy.user.service.AuthService;
import com.cdcone.recipy.user.service.UserService;
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
        Optional<Map<String, Object>> auth = authService.auth(signInRequestDto);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String msg = "Wrong username or password";
        if (auth.isPresent()) {
            status = HttpStatus.OK;
            msg = "Success";
        }
        return ResponseEntity.status(status).body(new CommonResponse(msg, auth));
    }
}
