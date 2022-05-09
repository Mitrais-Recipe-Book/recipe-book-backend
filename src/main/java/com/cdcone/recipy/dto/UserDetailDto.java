package com.cdcone.recipy.dto;

import lombok.Value;

@Value
public class UserDetailDto {

    long id;
    String username;
    String fullName;
    int totalRecipes;
    int recipeLikes;
    int followers;


}
