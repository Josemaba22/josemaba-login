package com.josemaba.security.service.auth;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${security.jwt.expiration-in-minutes}")
    private Long EXPIRATION_IN_MINUTES;

    public String generateToken(UserDetails user, Map<String, Object> extraClaims) {
        
        Date issuesAt = new Date(System.currentTimeMillis());
        Date expiration = new Date((EXPIRATION_IN_MINUTES * 60 * 1000) + issuesAt.getTime());
        String jwt = Jwts.builder()
            .header()
                .type("JWT")
                .and()
            .subject(user.getUsername())
            .issuedAt(issuesAt)
            .expiration(expiration)
            .claims(extraClaims)
            
            .signWith(generateKey(), Jwts.SIG.HS256)

            .compact();
        
        return jwt;
    }

    private SecretKey generateKey() {
        byte[] passwordDecoded = Decoders.BASE64.decode(SECRET_KEY);
        System.out.println( new String(passwordDecoded));
        return Keys.hmacShaKeyFor(passwordDecoded);

        //     System.out.println(SECRET_KEY);
        //    byte[] key = SECRET_KEY.getBytes();
        //    return Keys.hmacShaKeyFor(key);
    }
    
    public String extractUsername(String jwt) {
        return extractAllClaims(jwt).getSubject();
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts.parser().verifyWith(generateKey() ).build()
            .parseSignedClaims(jwt).getPayload();
    }
}
