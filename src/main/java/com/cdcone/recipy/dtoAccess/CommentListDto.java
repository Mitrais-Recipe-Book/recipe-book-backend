package com.cdcone.recipy.dtoAccess;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class CommentListDto {
    private String username;
    private String fullname;
    private LocalDateTime date;
    private String comment;
}
