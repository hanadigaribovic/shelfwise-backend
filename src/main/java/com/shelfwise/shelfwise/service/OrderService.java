package com.shelfwise.shelfwise.service;

import com.shelfwise.shelfwise.dto.OrderDto;
import com.shelfwise.shelfwise.entity.CartEntity;
import com.shelfwise.shelfwise.entity.OrderEntity;
import com.shelfwise.shelfwise.entity.UserEntity;
import com.shelfwise.shelfwise.repositoy.CartRepository;
import com.shelfwise.shelfwise.repositoy.OrderRepository;
import com.shelfwise.shelfwise.repositoy.UserRepository;
import com.shelfwise.shelfwise.type.OrderStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    public OrderDto createOrder(UUID userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow();
        List<CartEntity> cartItems = cartRepository.findByUser_Uid(userId);

        double totalPrice = cartItems.stream()
                .mapToDouble(item -> item.getBook().getPrice() * item.getQuantity())
                .sum();

        OrderEntity order = new OrderEntity(null, user, totalPrice, OrderStatus.PENDING, LocalDateTime.now());
        orderRepository.save(order);

        // Optionally: cartRepository.deleteAll(cartItems);

        return new OrderDto(order.getId(), userId, totalPrice, order.getStatus().toString(), order.getOrderDate().toString());
    }

    public List<OrderDto> getAllOrders(UUID userId) {
        return orderRepository.findByUser_Uid(userId).stream().map(o -> new OrderDto(
                o.getId(),
                o.getUser().getUid(),
                o.getTotalPrice(),
                o.getStatus().toString(),
                o.getOrderDate().toString()
        )).toList();
    }

    public OrderDto getOrder(UUID orderId) {
        OrderEntity order = orderRepository.findById(orderId).orElseThrow();
        return new OrderDto(
                order.getId(),
                order.getUser().getUid(),
                order.getTotalPrice(),
                order.getStatus().toString(),
                order.getOrderDate().toString()
        );
    }
}
