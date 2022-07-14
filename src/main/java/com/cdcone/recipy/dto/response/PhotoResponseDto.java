package com.cdcone.recipy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PhotoResponseDto {

    private String type;
    private byte[] photo;
}
