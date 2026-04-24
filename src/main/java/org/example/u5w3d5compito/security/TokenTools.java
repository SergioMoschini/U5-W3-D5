package org.example.u5w3d5compito.security;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.example.u5w3d5compito.entities.Utente;
import org.example.u5w3d5compito.exceptions.NonAutorizzatoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.UUID;

@Component
public class TokenTools {
    @Value("${jwt.secret}")
    private String secret;
    public String generateToken(Utente utente){
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7) )
                .subject(String.valueOf(utente.getUtenteId()))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }
    public void verifyToken(String token){
        try{
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
        } catch (Exception ex){
            throw new NonAutorizzatoException("Unvalid Token");
        }
    }
    public UUID getIdByToken(String token){
        try{
            return  UUID.fromString(Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject());
        } catch (Exception ex){
            throw new NonAutorizzatoException("Invalid Token");
        }
    }
}