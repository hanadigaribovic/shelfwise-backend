package com.shelfwise.shelfwise.repositoy;

import com.shelfwise.shelfwise.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
}
