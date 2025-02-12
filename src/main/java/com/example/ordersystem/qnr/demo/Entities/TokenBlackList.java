package com.example.ordersystem.qnr.demo.Entities;


import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "blacklisted_tokens")
public class TokenBlackList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    public TokenBlackList() {}

    public TokenBlackList(String token, LocalDateTime expiresAt) {
        this.token = token;
        this.expiresAt = expiresAt;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }
}


