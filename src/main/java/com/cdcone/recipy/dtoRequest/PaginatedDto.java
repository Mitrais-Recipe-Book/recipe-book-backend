package com.cdcone.recipy.dtoRequest;

import lombok.Value;

import java.util.List;

@Value
public class PaginatedDto<T> {

    List<T> data;
    int currentPage;
    int totalPages;
}
