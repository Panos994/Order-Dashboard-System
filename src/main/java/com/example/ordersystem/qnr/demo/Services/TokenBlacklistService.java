package com.example.ordersystem.qnr.demo.Services;

import com.example.ordersystem.qnr.demo.Entities.TokenBlackList;
import com.example.ordersystem.qnr.demo.Repositories.TokenBlacklistRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class TokenBlacklistService {

    private final TokenBlacklistRepository tokenBlacklistRepository;

    @Autowired
    public TokenBlacklistService(TokenBlacklistRepository tokenBlacklistRepository) {
        this.tokenBlacklistRepository = tokenBlacklistRepository;
    }

    // Blacklist a token with an expiration time (e.g., 1 hour, I haved added for 5 mintes)
    public void blacklistToken(String token, long expirationMinutes) {
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(expirationMinutes);
        tokenBlacklistRepository.save(new TokenBlackList(token, expiresAt));
    }

    // Check if a token is blacklisted
    public boolean isTokenBlacklisted(String token) {
        Optional<TokenBlackList> blacklistedToken = tokenBlacklistRepository.findByToken(token);
        return blacklistedToken.isPresent() && blacklistedToken.get().getExpiresAt().isAfter(LocalDateTime.now());
    }

    // Removing expired tokens (run this periodically)
    @Scheduled(fixedRate = 60000) // Runs every 60 seconds
    @Transactional
    public void cleanupExpiredTokens() {
        System.out.println("Running cleanup job at: " + LocalDateTime.now());
        tokenBlacklistRepository.deleteExpiredTokens();

    }
}

