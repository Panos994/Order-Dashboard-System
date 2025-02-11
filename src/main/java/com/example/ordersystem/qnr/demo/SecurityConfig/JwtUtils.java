package com.example.ordersystem.qnr.demo.SecurityConfig;


import com.example.ordersystem.qnr.demo.Services.TokenBlacklistService;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Autowired
    TokenBlacklistService tokenBlacklistService;



    //generateJwtToken(): takes authentication object, then extracts the username, and creates a JWT signed with HS512 (HMAC 512) algorithm an jwtsecret and sets an expiration date.
    public String generateJwtToken(Authentication authentication) {
        System.out.println("1"); //debugging

        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        System.out.println("2");
        System.out.println(userPrincipal.getUsername());
        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

    }

    //here getUserNameFromJwtToken parses the JWT and it extracts the username.
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) throws SignatureException {

        if (tokenBlacklistService.isTokenBlacklisted(authToken)) {
            logger.error("JWT token is blacklisted."); //if token is blacklisted check
            return false;
        }


        try { //validating format and expiration of token, and also I am using try catch to catch errors of MalformedException and ExpiredJwtException
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("token {}", authToken);
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
