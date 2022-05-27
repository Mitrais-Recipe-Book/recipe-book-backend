package com.cdcone.recipy.dtoAccess;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FollowingListDto {
    private Long id;
    private String username;
    private String fullName;
}
