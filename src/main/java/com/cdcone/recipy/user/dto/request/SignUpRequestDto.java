package com.cdcone.recipy.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpRequestDto {

    private String email;
    private String username;
    private String password;
    private String fullName;

    public Boolean checkBlank() {
        return email.isBlank() || username.isBlank() ||
                password.isBlank() || fullName.isBlank();
    }
}
