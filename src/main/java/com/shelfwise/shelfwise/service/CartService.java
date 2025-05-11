package com.shelfwise.shelfwise.service;

import com.shelfwise.shelfwise.dto.AddToCartDto;
import com.shelfwise.shelfwise.dto.CartDto;
import com.shelfwise.shelfwise.entity.BookEntity;
import com.shelfwise.shelfwise.entity.CartEntity;
import com.shelfwise.shelfwise.entity.UserEntity;
import com.shelfwise.shelfwise.repositoy.BookRepository;
import com.shelfwise.shelfwise.repositoy.CartRepository;
import com.shelfwise.shelfwise.repositoy.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public CartService(BookRepository bookRepository, UserRepository userRepository, CartRepository cartRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

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

        // Provjera da li već postoji ta knjiga u korpi korisnika
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

}
