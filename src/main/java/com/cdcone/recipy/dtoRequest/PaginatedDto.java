package com.cdcone.recipy.dtoRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.util.List;

@Data
@AllArgsConstructor
public class PaginatedDto<T> {

    private List<T> data;
    private int currentPage;
    private int totalPages;
}
