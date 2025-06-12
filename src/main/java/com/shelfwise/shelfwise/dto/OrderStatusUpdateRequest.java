package com.shelfwise.shelfwise.dto;

import com.shelfwise.shelfwise.type.OrderStatus;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderStatusUpdateRequest {
    private OrderStatus status;
} 