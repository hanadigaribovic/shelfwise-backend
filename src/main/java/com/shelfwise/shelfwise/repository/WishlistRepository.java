package com.shelfwise.shelfwise.repositoy;

import com.shelfwise.shelfwise.entity.WishlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WishlistRepository extends JpaRepository<WishlistEntity, UUID> {
    List<WishlistEntity> findByUser_Uid(UUID userId);
    void deleteById(UUID id);
}
