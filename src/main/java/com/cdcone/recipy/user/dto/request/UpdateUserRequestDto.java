package com.cdcone.recipy.user.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateUserRequestDto {
    
    private final String fullName;
    private final String email;
}
