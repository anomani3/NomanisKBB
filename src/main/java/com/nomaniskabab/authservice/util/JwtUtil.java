//package com.nomaniskabab.authservice.util;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.security.Key;
//import java.util.Date;
//
//@Component
//public class JwtUtil {
//    private final Key key;
//    private final long expirationMs;
//
//    public JwtUtil(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") long expirationMs) {
//        this.key = Keys.hmacShaKeyFor(secret.getBytes());
//        this.expirationMs = expirationMs;
//    }
//
//    public String generateToken(String subject) {
//        Date now = new Date();
//        return Jwts.builder()
//                .setSubject(subject)
//                .setIssuedAt(now)
//                .setExpiration(new Date(now.getTime() + expirationMs))
//                .signWith(key)
//                .compact();
//    }
//
//    public Jws<Claims> validateToken(String token) {
//        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
//    }
//}
