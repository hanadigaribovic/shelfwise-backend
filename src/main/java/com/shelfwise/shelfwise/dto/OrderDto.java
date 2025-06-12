package com.shelfwise.shelfwise.dto;

import lombok.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private UUID id;
    private UUID userId;
    private double totalPrice;
    private String status;
    private String orderDate;
    private int daysLeft;
    private String eta;
}