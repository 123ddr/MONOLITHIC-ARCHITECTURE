package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO;


import lombok.Data;

@Data
public class OrderResponse {
    private Long id;
    private Double total;
    private String status;
}

