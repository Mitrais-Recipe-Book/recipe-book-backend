package com.cdcone.recipy.dtoAccess;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {
    private String username;
    private String fullName;
    private Long authorFollower;
}
