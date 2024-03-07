package io.biker.management.auth.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class JwtService {
    public final String SECRET;

    public JwtService(@Value("${jwt.secret.key}") String key) {
        this.SECRET = key;
    }

    public String generateToken(String userName) {
        log.info("Running generateToken(" + userName + ") in JwtService...");
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {
        log.info("Running createToken(" + claims.toString() + ", " + userName + ") in JwtService...");
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        log.info("Running getSignKey() in JwtService...");
        log.info("Decoding Jwt secret...");
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        log.info("Running extractUsername(" + token + ") in JwtService...");
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        log.info("Running extractExpiration(" + token + ") in JwtService...");
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        log.info("Running extractClaim(" + token + ", " + claimsResolver.toString() + ") in JwtService...");
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        log.info("Running extractAllClaims(" + token + ") in JwtService...");
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        log.info("Running isTokenExpired(" + token + ") in JwtService...");
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        log.info("Running validateToken(" + token + ", " + userDetails.toString() + ") in JwtService...");
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
