package com.cdcone.recipy.dtoAccess;

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
