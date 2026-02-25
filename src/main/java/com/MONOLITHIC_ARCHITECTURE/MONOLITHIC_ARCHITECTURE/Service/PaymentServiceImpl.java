package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Service;


import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Config.SecurityUtils;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.PaymentCreateRequest;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.PaymentResponse;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Entity.OrderEntity;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Entity.PaymentEntity;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Repository.OrderRepo;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Repository.PaymentRepo;
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

    @Autowired
    public PaymentServiceImpl(PaymentRepo paymentRepo,
                              OrderRepo orderRepo,
                              SecurityUtils securityUtils) {
        this.paymentRepo = paymentRepo;
        this.orderRepo = orderRepo;
        this.securityUtils = securityUtils;
    }

    @Override
    @Transactional
    public PaymentResponse createPayment(PaymentCreateRequest request) throws AccessDeniedException {

        Long userId = securityUtils.getCurrentUser().getUserId();

        OrderEntity order = orderRepo.findById(request.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("ORDER NOT FOUND"));

        if (!order.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("Cannot pay for orders that are not yours");
        }

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
