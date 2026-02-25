package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Service;


import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.PaymentCreateRequest;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.PaymentResponse;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface IPaymentService {

    PaymentResponse createPayment(PaymentCreateRequest request) throws AccessDeniedException;  // CUSTOMER

    List<PaymentResponse> getMyPayments();                        // CUSTOMER

    List<PaymentResponse> getAllPayments();                       // ADMIN

    PaymentResponse getPaymentById(Long paymentId);               // ADMIN
}
