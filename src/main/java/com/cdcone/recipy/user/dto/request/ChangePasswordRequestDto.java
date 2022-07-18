package com.cdcone.recipy.user.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ChangePasswordRequestDto {
    private final String oldPassword;
    private final String newPassword;
    private final String confirmPassword;
}
