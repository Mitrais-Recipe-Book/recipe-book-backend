package com.cdcone.recipy.dto;

import lombok.Value;

import java.util.List;

@Value
public class PaginatedDto<T> {

    List<T> data;
    int currentPage;
    int totalPages;
}
