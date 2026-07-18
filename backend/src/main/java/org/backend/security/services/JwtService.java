package org.backend.security.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    //Set up the parameters
    @Value("${app.security.jwt-secret}")
    private String secretKey;
    @Value("${app.security.jwt-expiration}")
    private String secretException;

    //Extract any type of claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //Extract the username
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    //Verify if the token is expired
    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    //Extract the expiration
    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    //Extract the claim from the token
    private Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith((SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    //Get the sign key decoded
    private Key getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
