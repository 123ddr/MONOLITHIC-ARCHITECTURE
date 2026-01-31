package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Service;


import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.OrderItemCreateRequest;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.OrderItemResponse;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Entity.OrderEntity;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Entity.OrderItemEntity;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Entity.ProductEntity;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Repository.OrderItemRepo;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Repository.OrderRepo;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Repository.ProductRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements IOrderItemService {

    private final OrderItemRepo orderItemRepo;
    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;

    @Autowired
    public OrderItemServiceImpl(OrderItemRepo orderItemRepo,
                                OrderRepo orderRepo,
                                ProductRepo productRepo) {
        this.orderItemRepo = orderItemRepo;
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }

    @Override
    @Transactional
    public OrderItemResponse createOrderItem(OrderItemCreateRequest request) {

        OrderEntity order = orderRepo.findById(request.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("ORDER NOT FOUND"));

        ProductEntity product = productRepo.findById(request.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("PRODUCT NOT FOUND"));

        OrderItemEntity item = new OrderItemEntity();
        item.setOrder(order);
        item.setProduct(product);
        item.setQuantity(request.getQuantity());
        item.setPrice(product.getPrice() * request.getQuantity());

        orderItemRepo.save(item);

        // update order total
        order.setTotal(order.getTotal() + item.getPrice());
        orderRepo.save(order);

        return toResponse(item);
    }

    @Override
    public List<OrderItemResponse> getOrderItems(Long orderId) {

        return orderItemRepo.findByOrder_Id(orderId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /* ===================== MAPPER ===================== */

    private OrderItemResponse toResponse(OrderItemEntity entity) {
        OrderItemResponse dto = new OrderItemResponse();
        dto.setId(entity.getId());
        dto.setQuantity(entity.getQuantity());
        dto.setPrice(entity.getPrice());
        dto.setProductName(entity.getProduct().getName());
        return dto;
    }
}

