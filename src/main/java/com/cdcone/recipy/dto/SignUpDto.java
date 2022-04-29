package com.cdcone.recipy.dto;

import lombok.Data;

@Data
public class SignUpDto {

    private String email;
    private String username;
    private String password;
    private String fullName;
}
