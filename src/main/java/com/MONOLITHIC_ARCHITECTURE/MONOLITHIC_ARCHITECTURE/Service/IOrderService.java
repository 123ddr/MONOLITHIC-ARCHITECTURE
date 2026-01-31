package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Service;


import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.OrderResponse;

import java.util.List;

public interface IOrderService {

    OrderResponse createOrder();                    // CUSTOMER

    List<OrderResponse> getMyOrders();              // CUSTOMER

    List<OrderResponse> getAllOrders();             // ADMIN

    OrderResponse getOrderById(Long orderId);       // ADMIN
}
