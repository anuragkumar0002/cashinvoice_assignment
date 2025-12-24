package com.cashinvoice.order.controller;

import com.cashinvoice.order.dto.CreateOrderRequest;
import com.cashinvoice.order.dto.OrderResponse;
import com.cashinvoice.order.model.Order;
import com.cashinvoice.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(
            @Valid @RequestBody CreateOrderRequest request) {

        String orderId = service.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new OrderResponse(orderId, "CREATED"));
    }

    @GetMapping("/{orderId}")
    public Order getById(@PathVariable String orderId,
                         Authentication auth) {

        String username = auth.getName(); // ✅ principal is String
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        return service.getById(orderId, username, isAdmin);
    }


    @GetMapping
    public List<Order> listOrders(
            @RequestParam(required = false) String customerId,
            Authentication auth) {

        String username = auth.getName(); // principal is String
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        return service.listOrders(customerId, username, isAdmin);
    }

}
