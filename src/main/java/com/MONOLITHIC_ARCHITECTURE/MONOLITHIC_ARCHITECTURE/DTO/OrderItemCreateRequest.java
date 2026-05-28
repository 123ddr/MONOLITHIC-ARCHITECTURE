package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO;


import lombok.Data;

@Data
public class OrderItemCreateRequest {
    private Long productId;
    private Integer quantity;
}

