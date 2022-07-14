package com.cdcone.recipy.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FollowUserRequestDto {
    long userId;
    long creatorId;
}
