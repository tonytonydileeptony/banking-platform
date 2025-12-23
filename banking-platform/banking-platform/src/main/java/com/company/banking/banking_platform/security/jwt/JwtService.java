package com.company.banking.banking_platform.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET = "my-super-secure-secret-key-which-is-long-enough-for-HS256";

    // Single signing key
    private final Key signingKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    // Extract all claims
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Extract a specific claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    // Extract username
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Token expiration check
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    // Validate token against user
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // Generate token from username
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // Generate token from UserDetails
    public String generateToken(UserDetails userDetails) {
        return generateToken(userDetails.getUsername());
    }
}

