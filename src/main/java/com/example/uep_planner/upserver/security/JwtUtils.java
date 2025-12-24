package com.example.uep_planner.upserver.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtUtils {

    private final SecretKey secretKey =
        Keys.hmacShaKeyFor("planner-super-secret-key-should-be-32-bytes".getBytes());

    public String generateToken(Long userId) {
        return Jwts.builder()
            .claim("userId", userId)
            .setExpiration(Date.from(Instant.now().plus(3, ChronoUnit.HOURS)))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();
    }

    public Long validateAndGetUserId(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody();

        return Long.parseLong(claims.get("userId").toString());
    }
}
