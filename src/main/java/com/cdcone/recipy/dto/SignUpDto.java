package com.cdcone.recipy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpDto {

    private String email;
    private String username;
    private String password;
    private String fullName;
}
