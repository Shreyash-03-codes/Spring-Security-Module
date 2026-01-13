package com.springboot.security.session.service.jwt;

import com.springboot.security.session.entity.User;
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

    @Value(("${access.secret}"))
    private String ACCESS_SECRET;

    @Value(("${refresh.secret}"))
    private String REFRESH_SECRET;

    private final Long ACCESS_EXPIRATION=1000*30L;

    private final Long REFRESH_EXPIRATION=1000*60L;

    private SecretKey getAccessSecretKey(){
        return Keys.hmacShaKeyFor(ACCESS_SECRET.getBytes(StandardCharsets.UTF_8));

    }

    private SecretKey getRefreshSecretKey(){
        return Keys.hmacShaKeyFor(REFRESH_SECRET.getBytes(StandardCharsets.UTF_8));

    }

    public String generateAccessToken(User user){
        HashMap<String,Object> map=new HashMap<>();
        map.put("name",user.getName());
        map.put("id",user.getId());
        map.put("roles",user.getRoles()
                .stream()
                .map((r)->r.name())
                .toList()
        );

        return Jwts
                .builder()
                .setClaims(map)
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
                .signWith(getRefreshSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractClaimsForAccessToken(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getAccessSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Claims extractClaimsForRefreshToken(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getRefreshSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateAccessToken(String token){
        return !extractClaimsForAccessToken(token).getExpiration().before(new Date());
    }

    public boolean validateRefreshToken(String token){
        return !extractClaimsForRefreshToken(token).getExpiration().before(new Date());
    }

    public String extractUsernameFromAccessToken(String token){
        return extractClaimsForAccessToken(token).getSubject();
    }

    public String extractIdFromRefreshToken(String token){
        return extractClaimsForRefreshToken(token).getSubject();
    }

}
