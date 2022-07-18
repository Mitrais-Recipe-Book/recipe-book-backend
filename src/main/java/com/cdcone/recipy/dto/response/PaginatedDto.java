package com.cdcone.recipy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

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

    public PaginatedDto(Page<T> pageData) {
        this.data = pageData.getContent();
        this.currentPage = pageData.getNumber();
        this.totalPages = pageData.getTotalPages();
        this.islast = pageData.isLast();
        this.totalItem = pageData.getTotalElements();
    }
}
