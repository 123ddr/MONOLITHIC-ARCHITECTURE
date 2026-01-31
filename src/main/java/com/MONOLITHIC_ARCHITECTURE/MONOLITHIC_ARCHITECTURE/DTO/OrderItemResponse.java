package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO;


import lombok.Data;

@Data
public class OrderItemResponse {
    private Long id;
    private Integer quantity;
    private Double price;
    private String productName;
}

