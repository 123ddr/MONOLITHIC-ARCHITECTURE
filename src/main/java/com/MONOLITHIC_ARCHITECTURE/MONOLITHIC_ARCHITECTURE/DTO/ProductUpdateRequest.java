package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO;


import lombok.Data;

@Data
public class ProductUpdateRequest {
    private String name;
    private Double price;
    private Integer stock;
    private Long categoryId;
}

