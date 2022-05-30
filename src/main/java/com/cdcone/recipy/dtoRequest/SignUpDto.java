package com.cdcone.recipy.dtoRequest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpDto {

    private String email;
    private String username;
    private String password;
    private String fullName;

    public Boolean isBlank() {
        return email.isBlank() || username.isBlank() ||
                password.isBlank() || fullName.isBlank();
    }
}
