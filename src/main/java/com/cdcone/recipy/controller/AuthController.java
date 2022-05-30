package com.cdcone.recipy.controller;

import com.cdcone.recipy.dtoAccess.UserDto;
import com.cdcone.recipy.dtoRequest.SignInDto;
import com.cdcone.recipy.dtoRequest.SignUpDto;
import com.cdcone.recipy.response.CommonResponse;
import com.cdcone.recipy.service.AuthService;
import com.cdcone.recipy.service.UserService;
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
    public ResponseEntity<CommonResponse> signUp(@RequestBody SignUpDto signUpDto) {
        Pair<Optional<UserDto>, String> signUpUser = userService.addUser(signUpDto);
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        UserDto userDto = null;
        if (signUpUser.getFirst().isPresent()) {
            userDto = signUpUser.getFirst().get();
            httpStatus = HttpStatus.OK;
        }
        return ResponseEntity.status(httpStatus).body(new CommonResponse(signUpUser.getSecond(), userDto));
    }

    @PostMapping("sign-in")
    public ResponseEntity<CommonResponse> signIn(@RequestBody SignInDto signInDto) {
        Optional<Map<String, Object>> auth = authService.auth(signInDto);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String msg = "Wrong username or password";
        if (auth.isPresent()) {
            status = HttpStatus.OK;
            msg = "Success";
        }
        return ResponseEntity.status(status).body(new CommonResponse(msg, auth));
    }
}
