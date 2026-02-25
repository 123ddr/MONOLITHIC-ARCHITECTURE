package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Controller;


import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.PaymentCreateRequest;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.PaymentResponse;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Response.Response;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Service.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final IPaymentService paymentService;

    @Autowired
    public PaymentController(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Create payment (CUSTOMER)
     */
    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Response> create(@RequestBody PaymentCreateRequest request) {
        try {
            PaymentResponse payment = paymentService.createPayment(request);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Response.success(HttpStatus.CREATED, payment));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.error(HttpStatus.INTERNAL_SERVER_ERROR, "PAYMENT_CREATION_FAILED : " + e.getMessage()));
        }
    }

    /**
     * Get my payments (CUSTOMER)
     */
    @GetMapping("/me")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Response> myPayments() {
        return ResponseEntity.ok(
                Response.success(HttpStatus.OK, paymentService.getMyPayments()));
    }

    /**
     * Get all payments (ADMIN)
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> allPayments() {
        return ResponseEntity.ok(
                Response.success(HttpStatus.OK, paymentService.getAllPayments()));
    }

    /**
     * Get payment by id (ADMIN)
     */
    @GetMapping("/{paymentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> getById(@PathVariable Long paymentId) {
        return ResponseEntity.ok(
                Response.success(HttpStatus.OK, paymentService.getPaymentById(paymentId)));
    }
}
