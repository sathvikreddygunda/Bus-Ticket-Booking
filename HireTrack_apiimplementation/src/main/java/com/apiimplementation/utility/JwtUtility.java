package com.apiimplementation.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtility {

    // Read secret key from application.properties
    @Value("${jwt.secret}")
    private String key;

    // SecretKey object used for signing and validating JWT
    private SecretKey secretKey;

    // Initialize SecretKey after Spring loads properties
    @PostConstruct
    public void init() {
        secretKey = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(key)
        );
    }

    // Generate JWT token using username
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    // Create JWT token with claims, subject and expiration
    private String createToken(
            Map<String, Object> claims,
            String username) {

        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1 * 60 * 60 * 24 * 1000
                        )
                )
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    // Validate username and expiration
    public Boolean validateToken(
            String token,
            String username) {

        final String extractedUsername =
                extractUsername(token);

        return extractedUsername.equals(username)
                && !isTokenExpired(token);
    }

    // Extract username from JWT token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Check whether token is expired
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token)
                .before(new Date());
    }

    // Extract expiration date from token
    public Date extractExpiration(String token) {
        return extractClaim(
                token,
                Claims::getExpiration
        );
    }

    // Generic method to extract any claim
    public <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver) {

        final Claims claims =
                extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    // Read all claims from token after verification
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}