package com.management.yuvro.jpa.repository;

import com.management.yuvro.jpa.entity.YuvroUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface YuvroUserRepository extends JpaRepository<YuvroUser, Long> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    YuvroUser findByUsername(String username);

    Optional<YuvroUser> findByEmail(String email);
}
