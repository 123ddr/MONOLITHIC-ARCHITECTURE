package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Controller;


import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.OrderItemCreateRequest;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.OrderItemResponse;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Response.Response;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Service.IOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order-items")
public class OrderItemController {

    private final IOrderItemService orderItemService;

    @Autowired
    public OrderItemController(IOrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    /**
     * Create order item (CUSTOMER)
     */
    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Response> create(@RequestBody OrderItemCreateRequest request) {
        try {
            OrderItemResponse item = orderItemService.createOrderItem(request);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Response.success(HttpStatus.CREATED, item));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.error(HttpStatus.INTERNAL_SERVER_ERROR, "ORDER_ITEM_CREATION_FAILED : " + e.getMessage()));
        }
    }

    /**
     * Get order items (ADMIN)
     */
    @GetMapping("/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> getByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(
                Response.success(HttpStatus.OK,
                        orderItemService.getOrderItems(orderId)));
    }
}
