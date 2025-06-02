package com.shelfwise.shelfwise.service;

import com.shelfwise.shelfwise.dto.WishlistDto;
import com.shelfwise.shelfwise.entity.BookEntity;
import com.shelfwise.shelfwise.entity.UserEntity;
import com.shelfwise.shelfwise.entity.WishlistEntity;
import com.shelfwise.shelfwise.repository.BookRepository;
import com.shelfwise.shelfwise.repository.UserRepository;
import com.shelfwise.shelfwise.repository.WishlistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WishlistServiceTest {

    @Mock
    private WishlistRepository wishlistRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private WishlistService wishlistService;

    private UUID userId, bookId, wishlistId;
    private UserEntity user;
    private BookEntity book;
    private WishlistEntity wishlistEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userId = UUID.randomUUID();
        bookId = UUID.randomUUID();
        wishlistId = UUID.randomUUID();

        user = new UserEntity();
        book = new BookEntity(bookId, "Title", "Author", "Genre", 20.0, "Description");
        wishlistEntity = new WishlistEntity(wishlistId, user, book);
    }

    @Test
    void testAddToWishlist() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        wishlistService.addToWishlist(userId, bookId);

        verify(wishlistRepository).save(any(WishlistEntity.class));
    }

    @Test
    void testGetWishlist() {
        when(wishlistRepository.findByUser_Uid(userId)).thenReturn(List.of(wishlistEntity));

        List<WishlistDto> result = wishlistService.getWishlist(userId);

        assertEquals(1, result.size());
        assertEquals(bookId, result.get(0).getBookId());
    }

    @Test
    void testRemoveFromWishlist() {
        wishlistService.removeFromWishlist(wishlistId);

        verify(wishlistRepository).deleteById(wishlistId);
    }
}
