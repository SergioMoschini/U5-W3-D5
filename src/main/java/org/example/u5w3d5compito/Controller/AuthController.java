package org.example.u5w3d5compito.Controller;
import jakarta.validation.ValidationException;
import org.example.u5w3d5compito.entities.Utente;
import org.example.u5w3d5compito.payloads.LoginDTO;
import org.example.u5w3d5compito.payloads.LoginRespDTO;
import org.example.u5w3d5compito.payloads.UtenteDTO;
import org.example.u5w3d5compito.services.AuthService;
import org.example.u5w3d5compito.services.UtentiService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UtentiService utentiService;
    private final AuthService authService;

    public AuthController(UtentiService utentiService, AuthService authService) {
        this.utentiService = utentiService;
        this.authService = authService;
    }
    @PostMapping("/login")
    public LoginRespDTO registraUtente(@RequestBody @Validated LoginDTO body, BindingResult validationResults){
        if(validationResults.hasErrors()){
            List<String> errorsList = validationResults.getFieldErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new ValidationException(errorsList);
        }
        String accessToken = this.authService.verificaCredenzialiEGeneraToken(body);
        return new LoginRespDTO(accessToken);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Utente registraUtente(@RequestBody @Validated UtenteDTO body, BindingResult validationResults){
        if(validationResults.hasErrors()){
            List<String> errorsList = validationResults.getFieldErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new ValidationException(errorsList);
        }
        return this.utentiService.saveUtente(body);
    }


}