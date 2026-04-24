package org.example.u5w3d5compito.Controller;
import org.example.u5w3d5compito.entities.Evento;
import org.example.u5w3d5compito.entities.Utente;
import org.example.u5w3d5compito.payloads.EventoDTO;
import org.example.u5w3d5compito.services.EventiService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/eventi")
public class EventiController {

    private final EventiService eventiService;

    public EventiController(EventiService eventiService) {
        this.eventiService = eventiService;
    }
    @DeleteMapping("/{eventoId}")
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvento(@PathVariable UUID eventoId, @AuthenticationPrincipal Utente utenteVerificato){
        this.eventiService.deleteEvento(eventoId, utenteVerificato.getUtenteId());
    }


    @PutMapping("/{eventoId}")
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE')")
    public Evento putEvento(@PathVariable UUID eventoId, @RequestBody @Validated EventoDTO body, @AuthenticationPrincipal Utente utenteVerificato){
        return this.eventiService.putEvento(eventoId, body, utenteVerificato.getUtenteId());
    }


    @PostMapping
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE')")
    @ResponseStatus(HttpStatus.CREATED)
    public Evento postEvento(@RequestBody @Validated EventoDTO body, @AuthenticationPrincipal Utente utenteVerificato){
        return this.eventiService.saveEvento(body, utenteVerificato.getUtenteId());
    }

}