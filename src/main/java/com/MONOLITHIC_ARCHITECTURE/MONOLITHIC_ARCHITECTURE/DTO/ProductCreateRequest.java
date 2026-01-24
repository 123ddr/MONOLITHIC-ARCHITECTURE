package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO;


import lombok.Data;

@Data
public class ProductCreateRequest {
    private String name;
    private Double price;
    private Integer stock;
    private Long categoryId;
}

