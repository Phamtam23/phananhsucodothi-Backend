package com.DATN.PhanAnhSuCoDoThi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getEmail(String token) {
        return getClaims(token).getSubject();
    }

    public String getMaTaiKhoan(String token) {
        return getClaims(token).get("maTaiKhoan", String.class);
    }

    public String getRefId(String token) {
        return getClaims(token).get("refMa", String.class);
    }

    public List<String> getQuyens(String token) {
        return getClaims(token).get("quyens", List.class);
    }

    public String generateToken(String email,
                                String maTaiKhoan,
                                List<String> quyens,
                                String refMa) {

        return Jwts.builder()
                .subject(email)
                .claim("maTaiKhoan", maTaiKhoan)
                .claim("quyens", quyens)
                .claim("refMa", refMa)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }
}