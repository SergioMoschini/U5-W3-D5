package org.example.u5w3d5compito.services;
import org.example.u5w3d5compito.entities.Utente;
import org.example.u5w3d5compito.exceptions.NonAutorizzatoException;
import org.example.u5w3d5compito.security.TokenTools;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final TokenTools tokenTools;
    private final UtentiService utentiService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(TokenTools tokenTools, UtentiService utentiService, PasswordEncoder passwordEncoder) {
        this.tokenTools = tokenTools;
        this.utentiService = utentiService;
        this.passwordEncoder = passwordEncoder;
    }

    public String verificaCredenzialiEGeneraToken(LoginDTO body){

        Utente utente = this.utentiService.findByEmail(body.email());

        if (passwordEncoder.matches(body.password(), utente.getPassword())){
            String accessToken = this.tokenTools.generateToken(utente);
            return accessToken;
        } else {
            throw new NonAutorizzatoException("Password non valida");
        }

    }
}
