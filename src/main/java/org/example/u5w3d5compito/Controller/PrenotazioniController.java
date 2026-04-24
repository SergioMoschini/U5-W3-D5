package org.example.u5w3d5compito.Controller;
import org.example.u5w3d5compito.entities.Prenotazione;
import org.example.u5w3d5compito.entities.Utente;
import org.example.u5w3d5compito.payloads.PrenotazioneDTO;
import org.example.u5w3d5compito.services.PrenotazioniService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prenotazione")
public class PrenotazioniController {

    private final PrenotazioniService prenotazioniService;

    public PrenotazioniController(PrenotazioniService prenotazioniService) {
        this.prenotazioniService = prenotazioniService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('UTENTE', 'ORGANIZZATORE')")
    public Prenotazione postPrenotazione(@RequestBody PrenotazioneDTO body, @AuthenticationPrincipal Utente userVerificato){
        return this.prenotazioniService.postPrenotazione(body, userVerificato.getUtenteId());
    }
}