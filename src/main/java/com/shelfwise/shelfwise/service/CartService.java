package com.shelfwise.shelfwise.service;

import com.shelfwise.shelfwise.dto.AddToCartDto;
import com.shelfwise.shelfwise.dto.CartDto;
import com.shelfwise.shelfwise.entity.BookEntity;
import com.shelfwise.shelfwise.entity.CartEntity;
import com.shelfwise.shelfwise.entity.UserEntity;
import com.shelfwise.shelfwise.repository.BookRepository;
import com.shelfwise.shelfwise.repository.CartRepository;
import com.shelfwise.shelfwise.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public List<CartDto> getCartByUserId(UUID userId) {
        List<CartEntity> cartItems = cartRepository.findByUser_Uid(userId);

        return cartItems.stream().map(c -> {
            BookEntity book = c.getBook();
            return new CartDto(
                    c.getId(),
                    book.getBid(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getPrice(),
                    c.getQuantity()
            );
        }).toList();
    }

    public void addToCart(AddToCartDto dto) {
        UserEntity user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        BookEntity book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        CartEntity existing = cartRepository.findByUserAndBook(user, book);
        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + dto.getQuantity());
            cartRepository.save(existing);
        } else {
            CartEntity cartItem = new CartEntity();
            cartItem.setUser(user);
            cartItem.setBook(book);
            cartItem.setQuantity(dto.getQuantity());
            cartRepository.save(cartItem);
        }
    }

    public void removeFromCart(UUID cartId) {
        cartRepository.deleteById(cartId);
    }

    public void updateQuantity(UUID cartId, int delta) {
        System.out.println("USO SAM U UPDATE DUBOKO");
        CartEntity entity = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        int newQuantity = Math.max(1, entity.getQuantity() + delta);
        entity.setQuantity(newQuantity);
        cartRepository.save(entity);
    }

}
