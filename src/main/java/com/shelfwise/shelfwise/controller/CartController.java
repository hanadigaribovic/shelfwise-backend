package com.shelfwise.shelfwise.controller;

import com.shelfwise.shelfwise.dto.AddToCartDto;
import com.shelfwise.shelfwise.dto.CartDto;
import com.shelfwise.shelfwise.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public void addToCart(@RequestBody AddToCartDto dto) {
        cartService.addToCart(dto);
    }

    @GetMapping("/{userId}")
    public List<CartDto> getCartItems(@PathVariable UUID userId) {
        return cartService.getCartByUserId(userId);
    }

    // Bonus metoda za brisanje stavke iz korpe
    @DeleteMapping("/{cartId}")
    public void removeItem(@PathVariable UUID cartId) {
        cartService.removeFromCart(cartId);
    }

    @PatchMapping("/{cartId}")
    public void updateQuantity(@PathVariable UUID cartId, @RequestBody int delta) {
        System.out.println("POZVAO SAM UPDATE JAKO");
        cartService.updateQuantity(cartId, delta);
    }

}
