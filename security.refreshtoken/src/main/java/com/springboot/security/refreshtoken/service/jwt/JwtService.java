package com.springboot.security.refreshtoken.service.jwt;

import com.springboot.security.refreshtoken.entity.User;
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

    @Value("${jwt.access.secret}")
    private String ACCESS_SECRET;

    @Value("${jwt.refresh.secret}")
    private String REFRESH_SECRET;

    private final Long EXPIRATION_ACCESS_TOKEN=1000*30L;
    private final Long EXPIRATION_REFRESH_TOKEN=1000*60L;

    private SecretKey getAccessSecretKey(){
        return Keys.hmacShaKeyFor(ACCESS_SECRET.getBytes(StandardCharsets.UTF_8));
    }

    private SecretKey getRefreshSecretKey(){
        return Keys.hmacShaKeyFor(REFRESH_SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(User user){
        HashMap<String,Object> map=new HashMap<>();
        map.put("name",user.getName());
        map.put("username",user.getUsername());
        map.put("role",user.getRole().name());
        return Jwts
                .builder()
                .setClaims(map)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_ACCESS_TOKEN))
                .signWith(getAccessSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(User user){
        return Jwts
                .builder()
                .setSubject(user.getId()+"")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_REFRESH_TOKEN))
                .signWith(getRefreshSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token){
        return extractClaims(token).getSubject();
    }

    private Claims extractClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getAccessSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    public Boolean validateToken(String token){
        return !extractClaims(token).getExpiration().before(new Date());
    }

    public Boolean validateRefreshToken(String token){
        return !extractClaimsForId(token).getExpiration().before(new Date());
    }

    private Claims extractClaimsForId(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getRefreshSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    public String extractId(String token){
        return extractClaimsForId(token).getSubject();
    }

}
