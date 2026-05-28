package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO;


import lombok.Data;

import java.util.List;

@Data
public class OrderCreateRequest {
    // empty – order is created for current user
    private List<OrderItemCreateRequest> items;
}

