package com.shelfwise.shelfwise.repository;

import com.shelfwise.shelfwise.entity.BookEntity;
import com.shelfwise.shelfwise.entity.CartEntity;
import com.shelfwise.shelfwise.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CartRepository extends JpaRepository<CartEntity, UUID> {
    List<CartEntity> findByUser_Uid(UUID userId);
    CartEntity findByUserAndBook(UserEntity user, BookEntity book);
}
