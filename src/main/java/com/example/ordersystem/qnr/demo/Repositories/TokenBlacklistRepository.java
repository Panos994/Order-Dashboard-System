package com.example.ordersystem.qnr.demo.Repositories;

import com.example.ordersystem.qnr.demo.Entities.TokenBlackList;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenBlacklistRepository extends JpaRepository<TokenBlackList, Long> {
    Optional<TokenBlackList> findByToken(String token);

    @Modifying
    @Transactional
    @Query("DELETE FROM TokenBlackList t WHERE t.expiresAt < CURRENT_TIMESTAMP")
    void deleteExpiredTokens();
}
