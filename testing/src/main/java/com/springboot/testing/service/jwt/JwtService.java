package com.springboot.testing.service.jwt;

import com.springboot.testing.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtService {

    @Value("${access.secret}")
    private String ACCESS_SECRET;

    @Value("${refresh.secret}")
    private String REFRESH_SECRET;

    private final Long ACCESS_EXPIRATION=1000*60*10L;

    private final Long REFRESH_EXPIRATION=1000*60*60*24*30L;

    private SecretKey getAccessSecretKey(){
        return Keys.hmacShaKeyFor(ACCESS_SECRET.getBytes(StandardCharsets.UTF_8));
    }

    private SecretKey getRefreshSecretKey(){
        return Keys.hmacShaKeyFor(REFRESH_SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(User user){
        HashMap<String,Object> claims=new HashMap<>();
        claims.put("name",user.getName());
        claims.put("email",user.getEmail());
        claims.put("role",user.getRoles());
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ACCESS_EXPIRATION))
                .signWith(getAccessSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(User user){
        return Jwts
                .builder()
                .setSubject(user.getId()+"")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+REFRESH_EXPIRATION))
                .signWith(getRefreshSecretKey(),SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractAccessTokenClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getAccessSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Claims extractRefreshTokenClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getRefreshSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token){
        return extractAccessTokenClaims(token).getSubject();
    }

    public String extractUserId(String token){
        return extractRefreshTokenClaims(token).getSubject();
    }

    public boolean validateAccessToken(String token){
        return !extractAccessTokenClaims(token).getExpiration().before(new Date());
    }
    public boolean validateRefreshToken(String token){
        return !extractRefreshTokenClaims(token).getExpiration().before(new Date());
    }
}
