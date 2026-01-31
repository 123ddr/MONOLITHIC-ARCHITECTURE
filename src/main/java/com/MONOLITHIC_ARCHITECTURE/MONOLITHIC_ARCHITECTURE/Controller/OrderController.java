package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Controller;


import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.OrderResponse;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Response.Response;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final IOrderService orderService;

    @Autowired
    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Create order (CUSTOMER)
     */
    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Response> create() {
        try {
            OrderResponse order = orderService.createOrder();
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Response.success(HttpStatus.CREATED, order));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.error(HttpStatus.INTERNAL_SERVER_ERROR, "ORDER_CREATION_FAILED : " + e.getMessage()));
        }
    }

    /**
     * Get my orders (CUSTOMER)
     */
    @GetMapping("/me")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Response> myOrders() {
        return ResponseEntity.ok(
                Response.success(HttpStatus.OK, orderService.getMyOrders()));
    }

    /**
     * Get all orders (ADMIN)
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> allOrders() {
        return ResponseEntity.ok(
                Response.success(HttpStatus.OK, orderService.getAllOrders()));
    }
}

