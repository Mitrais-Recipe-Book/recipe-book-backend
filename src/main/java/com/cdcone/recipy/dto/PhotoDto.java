package com.cdcone.recipy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PhotoDto {

    private String type;
    private byte[] photo;
}
