package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Service;


import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Config.SecurityUtils;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.OrderResponse;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Entity.OrderEntity;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Entity.UserEntity;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Repository.OrderRepo;
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
    //private final SecurityUtils securityUtils;

    @Autowired
    public OrderServiceImpl(OrderRepo orderRepo,
                            UserRepo userRepo,
                            SecurityUtils securityUtils) {
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
        //this.securityUtils = securityUtils;
    }

    @Override
    @Transactional
    public OrderResponse createOrder() {

        String username = SecurityUtils.getCurrentUsername();

        UserEntity user = userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("USER NOT FOUND"));

        OrderEntity order = new OrderEntity();
        order.setUser(user);
        order.setStatus("CREATED");
        order.setTotal(0.0);

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

