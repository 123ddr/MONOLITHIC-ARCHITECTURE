package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Entity;


import jakarta.persistence.*;

@Entity
@Table(name = "payments")
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

