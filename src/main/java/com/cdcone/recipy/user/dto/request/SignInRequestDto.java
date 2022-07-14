package com.cdcone.recipy.user.dto.request;

import lombok.Data;

@Data
public class SignInRequestDto {
    private String username;
    private String password;
}
