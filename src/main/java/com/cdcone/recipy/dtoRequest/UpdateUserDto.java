package com.cdcone.recipy.dtoRequest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateUserDto {
    
    private final String fullName;
    private final String username;
    private final String email;
}
