package com.shelfwise.shelfwise.service;

import com.shelfwise.shelfwise.dto.WishlistDto;
import com.shelfwise.shelfwise.entity.BookEntity;
import com.shelfwise.shelfwise.entity.UserEntity;
import com.shelfwise.shelfwise.entity.WishlistEntity;
import com.shelfwise.shelfwise.repository.BookRepository;
import com.shelfwise.shelfwise.repository.UserRepository;
import com.shelfwise.shelfwise.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public WishlistService(WishlistRepository wishlistRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.wishlistRepository = wishlistRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public void addToWishlist(UUID userId, UUID bookId) {
        UserEntity user = userRepository.findById(userId).orElseThrow();
        BookEntity book = bookRepository.findById(bookId).orElseThrow();
        WishlistEntity item = new WishlistEntity(null, user, book);
        wishlistRepository.save(item);
    }

    public List<WishlistDto> getWishlist(UUID userId) {
        return wishlistRepository.findByUser_Uid(userId).stream().map(w -> new WishlistDto(
                w.getId(),
                userId,
                w.getBook().getBid(),
                w.getBook().getTitle(),
                w.getBook().getAuthor()
        )).toList();
    }

    public void removeFromWishlist(UUID wishlistId) {
        wishlistRepository.deleteById(wishlistId);
    }
}
