package com.QuantityMeasurementApp.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Secret key used to sign the token - must be at least 256 bits
    private static final String SECRET = 
        "QuantityMeasurementAppSecretKeyForJWT2024!!";
    
    // Token expiry time - 24 hours in milliseconds
    private static final long EXPIRATION = 86400000;

    // Generate signing key from secret
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // Generate JWT token for a username
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)           // who the token is for
                .setIssuedAt(new Date())        // when token was created
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey())      // sign with secret key
                .compact();
    }

    // Extract username from token
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Check if token is expired
    public boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }

    // Validate token - check username matches and token not expired
    public boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);
        return extractedUsername.equals(username) && !isTokenExpired(token);
    }
}
