package com.cashinvoice.order.service;

import com.cashinvoice.order.dto.CreateOrderRequest;
import com.cashinvoice.order.exception.OrderNotFoundException;
import com.cashinvoice.order.model.Order;
import com.cashinvoice.order.repository.OrderRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public String createOrder(CreateOrderRequest req) {

        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString());
        order.setCustomerId(req.getCustomerId());
        order.setProduct(req.getProduct());
        order.setAmount(req.getAmount());
        order.setCreatedAt(LocalDateTime.now());

        repository.save(order);
        return order.getOrderId();
    }

    public Order getById(String orderId, String username, boolean isAdmin) {

        Order order = repository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);

        if (!isAdmin && !order.getCustomerId().equals(username)) {
            throw new AccessDeniedException("Forbidden");
        }

        return order;
    }


    public List<Order> listOrders(String customerId,
                                  String username,
                                  boolean isAdmin) {

        if (isAdmin) {
            return customerId == null
                    ? repository.findAll()
                    : repository.findByCustomerId(customerId);
        }

        // USER → always fetch own orders
        return repository.findByCustomerId(username);
    }

}
