package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Service;


import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Config.SecurityUtils;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.OrderCreateRequest;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.OrderItemCreateRequest;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.OrderResponse;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Entity.OrderEntity;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Entity.OrderItemEntity;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Entity.ProductEntity;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Entity.UserEntity;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Repository.OrderRepo;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Repository.ProductRepo;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {

    private final OrderRepo orderRepo;
    private final UserRepo userRepo;
    private final ProductRepo productRepo;
    //private final SecurityUtils securityUtils;

    @Autowired
    public OrderServiceImpl(OrderRepo orderRepo,
                            UserRepo userRepo,
                            SecurityUtils securityUtils, ProductRepo productRepo) {
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
        //this.securityUtils = securityUtils;
        this.productRepo = productRepo;
    }

    @Override
    @Transactional
    public OrderResponse createOrder(OrderCreateRequest request) {

        String username = SecurityUtils.getCurrentUsername();

        UserEntity user = userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("USER NOT FOUND"));

        OrderEntity order = new OrderEntity();
        order.setUser(user);
        order.setStatus("CREATED");

        double total = 0.0;

        for (OrderItemCreateRequest itemReq : request.getItems()) {

            ProductEntity product = productRepo.findById(itemReq.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("PRODUCT NOT FOUND"));

            double price = product.getPrice();
            double lineTotal = price * itemReq.getQuantity();

            total += lineTotal;

            OrderItemEntity item = new OrderItemEntity();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemReq.getQuantity());
            item.setPrice(price);

            order.getItems().add(item);
        }

        order.setTotal(total);

        orderRepo.save(order);

        return toResponse(order);
    }

    @Override
    public List<OrderResponse> getMyOrders() {

        String username = SecurityUtils.getCurrentUsername();

        return orderRepo.findByUser_Username(username)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<OrderResponse> getAllOrders() {

        return orderRepo.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {

        OrderEntity order = orderRepo.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("ORDER NOT FOUND"));

        return toResponse(order);
    }

    /* ===================== MAPPER ===================== */

    private OrderResponse toResponse(OrderEntity entity) {
        OrderResponse dto = new OrderResponse();
        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setTotal(entity.getTotal());
        return dto;
    }
}

