package com.ripan.production.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtProvider {

    private static SecretKey secretKey = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    public static String generateToken(Authentication authentication) {
        return Jwts.builder()
                .setIssuer("ripan")
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + 86400000))
                .claim("email", authentication.getName())
                .signWith(secretKey)
                .compact();
    }

    public static String getEmailFromJwtToken(String jwt) {
        // Remove "Bearer " prefix if present and trim any surrounding whitespace
        if (jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7).trim();
        }

        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody();
        return String.valueOf(claims.get("email"));
    }
}