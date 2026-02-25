package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO;


import lombok.Data;

@Data
public class PaymentCreateRequest {
    private Long orderId;
    private Double amount;
}
