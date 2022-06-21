package com.cdcone.recipy.dtoRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpDto {

    private String email;
    private String username;
    private String password;
    private String fullName;

    public Boolean checkBlank() {
        return email.isBlank() || username.isBlank() ||
                password.isBlank() || fullName.isBlank();
    }
}
