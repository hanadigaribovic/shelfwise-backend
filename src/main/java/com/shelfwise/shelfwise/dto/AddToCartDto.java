package com.shelfwise.shelfwise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddToCartDto {
    private UUID userId;
    private UUID bookId;
    private int quantity;
}
