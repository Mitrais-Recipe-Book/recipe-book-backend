package com.cdcone.recipy.dtoRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedDto<T> {

    private List<T> data;
    private int currentPage;
    private int totalPages;
    private boolean islast;
    private long totalItem;
}
