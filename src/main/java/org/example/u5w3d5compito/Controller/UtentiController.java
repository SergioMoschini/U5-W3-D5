package org.example.u5w3d5compito.Controller;
import org.example.u5w3d5compito.entities.Prenotazione;
import org.example.u5w3d5compito.entities.Utente;
import org.example.u5w3d5compito.services.UtentiService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

    @RestController
    @RequestMapping("/utenti")
    public class UtentiController {

        private final UtentiService utentiService;

        public UtentiController(UtentiService utentiService) {
            this.utentiService = utentiService;
        }

        @GetMapping("/me/prenotazioni")
        public Page<Prenotazione> findPrenotazioni(@AuthenticationPrincipal Utente userVerificato,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size)
        {return this.utentiService.findPrenotazioniByUtente(userVerificato.getUtenteId(), page, size);
        }

        @DeleteMapping("/me/delete/{prenotazioneId}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void deletePrenotazione(@PathVariable UUID prenotazioneId, @AuthenticationPrincipal Utente userVerificato){
            this.utentiService.deletePrenotazione(prenotazioneId, userVerificato.getUtenteId());
        }
    }

