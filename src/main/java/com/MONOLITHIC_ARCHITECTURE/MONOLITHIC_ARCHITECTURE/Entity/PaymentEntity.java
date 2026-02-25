package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "payments")
@Data
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;
    private String paymentStatus;

    @OneToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;
}

