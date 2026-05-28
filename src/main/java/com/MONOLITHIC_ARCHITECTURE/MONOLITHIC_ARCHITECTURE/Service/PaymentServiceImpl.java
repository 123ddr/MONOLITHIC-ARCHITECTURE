package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Service;


import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Config.SecurityUtils;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.PaymentCreateRequest;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.PaymentResponse;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Entity.OrderEntity;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Entity.PaymentEntity;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Entity.UserEntity;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Repository.OrderRepo;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Repository.PaymentRepo;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
public class PaymentServiceImpl implements IPaymentService {

    private final PaymentRepo paymentRepo;
    private final OrderRepo orderRepo;
    private final SecurityUtils securityUtils;
    private final UserRepo userRepo;

    @Autowired
    public PaymentServiceImpl(PaymentRepo paymentRepo,
                              OrderRepo orderRepo,
                              SecurityUtils securityUtils, UserRepo userRepo) {
        this.paymentRepo = paymentRepo;
        this.orderRepo = orderRepo;
        this.securityUtils = securityUtils;
        this.userRepo = userRepo;
    }

    @Override
    @Transactional
    public PaymentResponse createPayment(PaymentCreateRequest request) throws AccessDeniedException {

        // 1. Get logged-in username from SecurityContext
        String username = SecurityUtils.getCurrentUsername();

        // 2. Load real user from DB (ENSURES correct ID)
        UserEntity user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("USER NOT FOUND"));

        Long userId = user.getId();

        // 3. Fetch order
        OrderEntity order = orderRepo.findById(request.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("ORDER NOT FOUND"));

        // 4. Check ownership
        if (!order.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("Cannot pay for orders that are not yours");
        }

        // 5. Create payment
        PaymentEntity payment = new PaymentEntity();
        payment.setOrder(order);
        payment.setAmount(request.getAmount());
        payment.setPaymentStatus("PAID");

        paymentRepo.save(payment);

        return toResponse(payment);
    }

    @Override
    public List<PaymentResponse> getMyPayments() {

        Long userId = securityUtils.getCurrentUser().getUserId();

        return paymentRepo.findByOrder_User_Id(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<PaymentResponse> getAllPayments() {

        return paymentRepo.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public PaymentResponse getPaymentById(Long paymentId) {

        PaymentEntity payment = paymentRepo.findById(paymentId)
                .orElseThrow(() -> new EntityNotFoundException("PAYMENT NOT FOUND"));

        return toResponse(payment);
    }

    /* ===================== MAPPER ===================== */

    private PaymentResponse toResponse(PaymentEntity entity) {
        PaymentResponse dto = new PaymentResponse();
        dto.setId(entity.getId());
        dto.setAmount(entity.getAmount());
        dto.setPaymentStatus(entity.getPaymentStatus());
        dto.setOrderId(entity.getOrder().getId());
        return dto;
    }
}
