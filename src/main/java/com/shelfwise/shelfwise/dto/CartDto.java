package com.shelfwise.shelfwise.dto;


import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private UUID id;
    private UUID userId;
    private UUID bookId;
    private String title;
    private String author;
    private double price;
    private int quantity;
}

