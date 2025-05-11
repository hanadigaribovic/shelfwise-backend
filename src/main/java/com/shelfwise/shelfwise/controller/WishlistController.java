package com.shelfwise.shelfwise.controller;

import com.shelfwise.shelfwise.dto.WishlistDto;
import com.shelfwise.shelfwise.service.WishlistService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping
    public void addToWishlist(@RequestBody WishlistDto dto) {
        wishlistService.addToWishlist(dto.getUserId(), dto.getBookId());
    }

    @GetMapping("/{userId}")
    public List<WishlistDto> getWishlist(@PathVariable UUID userId) {
        return wishlistService.getWishlist(userId);
    }

    @DeleteMapping("/{wishlistId}")
    public void deleteWishlistItem(@PathVariable UUID wishlistId) {
        wishlistService.removeFromWishlist(wishlistId);
    }
}
