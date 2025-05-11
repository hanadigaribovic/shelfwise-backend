package com.shelfwise.shelfwise.controller;

import com.shelfwise.shelfwise.dto.OrderDto;
import com.shelfwise.shelfwise.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{userId}")
    public OrderDto createOrder(@PathVariable UUID userId) {
        return orderService.createOrder(userId);
    }

    @GetMapping("/{id}")
    public OrderDto getOrder(@PathVariable UUID id) {
        return orderService.getOrder(id);
    }

    @GetMapping("/user/{userId}")
    public List<OrderDto> getOrders(@PathVariable UUID userId) {
        return orderService.getAllOrders(userId);
    }
}