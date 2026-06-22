package com.busticketbooking.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/*
JWT Utility Class

Responsibilities:

1. Generate JWT Token
2. Validate JWT Token
3. Extract Email from Token
4. Check Expiry
*/

@Component
public class JwtUtility {

    @Value("${jwt.secret}")
    private String secret;

    /*
    Secret Key used for signing token

    IMPORTANT:
    Keep this private in real projects
    */
    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(secret));
    }

    /*
    Generate JWT Token

    Input:
    email

    Output:
    JWT Token
    */
    public String generateToken(String email) {

        Map<String, Object> claims = new HashMap<>();

        return createToken(
                claims,
                email);
    }

    /*
    Create Actual Token
    */
    private String createToken(
            Map<String, Object> claims,
            String email) {

        return Jwts.builder()

                .claims(claims)

                .subject(email)

                .issuedAt(
                        new Date(
                                System.currentTimeMillis()))

                /*
                Token valid for 1 day
                */
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 24 * 60 * 60 * 1000))

                .signWith(getSecretKey(), Jwts.SIG.HS256)

                .compact();
    }

    /*
    Validate Token

    Checks:

    1. Email matches
    2. Token not expired
    */
    public Boolean validateToken(
            String token,
            String email) {

        String extractedEmail =
                extractEmail(token);

        return extractedEmail.equals(email)
                && !isTokenExpired(token);
    }

    /*
    Extract Email from Token
    */
    public String extractEmail(
            String token) {

        return extractClaim(
                token,
                Claims::getSubject);
    }

    /*
    Check Expiry
    */
    private Boolean isTokenExpired(
            String token) {

        return extractExpiration(token)
                .before(new Date());
    }

    /*
    Extract Expiration Date
    */
    private Date extractExpiration(
            String token) {

        return extractClaim(
                token,
                Claims::getExpiration);
    }

    /*
    Generic Claim Extractor
    */
    public <T> T extractClaim(
            String token,
            Function<Claims, T> resolver) {

        Claims claims =
                extractAllClaims(token);

        return resolver.apply(claims);
    }

    /*
    Read Token Payload
    */
    private Claims extractAllClaims(
            String token) {

        return Jwts.parser()

                .verifyWith(getSecretKey())

                .build()

                .parseSignedClaims(token)

                .getPayload();
    }
}