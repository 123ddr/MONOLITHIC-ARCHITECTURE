package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagination {
    private int currentPage;   // Current page number
    private int pageSize;      // Number of items per page
    private long totalItems;   // Total number of items in the dataset
    private int totalPages;    // Total number of pages
}
