package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO;


import lombok.Data;

@Data
public class PaymentResponse {
    private Long id;
    private Double amount;
    private String paymentStatus;
    private Long orderId;
}
