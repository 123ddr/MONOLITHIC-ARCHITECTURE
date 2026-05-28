package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Service;


import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.OrderItemCreateRequest;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.OrderItemResponse;

import java.util.List;

public interface IOrderItemService {

    public OrderItemResponse createOrderItem(Long orderId, OrderItemCreateRequest request);// CUSTOMER

    List<OrderItemResponse> getOrderItems(Long orderId);               // ADMIN
}
