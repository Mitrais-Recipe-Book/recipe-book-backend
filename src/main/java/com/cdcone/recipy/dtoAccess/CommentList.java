package com.cdcone.recipy.dtoAccess;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentList {
    private String username;
    private String fullname;
    private LocalDateTime date;
    private String comment;
}
