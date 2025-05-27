package com.shelfwise.shelfwise.repositoy;

import com.shelfwise.shelfwise.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    List<OrderEntity> findByUser_Uid(UUID userId);
}