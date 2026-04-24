package org.example.u5w3d5compito.services;
import jakarta.servlet.http.HttpServletRequest;
import org.example.u5w3d5compito.entities.Evento;
import org.example.u5w3d5compito.entities.Utente;
import org.example.u5w3d5compito.exceptions.BadRequestException;
import org.example.u5w3d5compito.exceptions.NonTrovatoException;
import org.example.u5w3d5compito.payloads.EventoDTO;
import org.example.u5w3d5compito.repositories.EventiRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class EventiService {

    private final EventiRepository eventiRepository;
    private final UtentiService utentiService;

    public EventiService(EventiRepository eventiRepository, UtentiService utentiService) {
        this.eventiRepository = eventiRepository;
        this.utentiService = utentiService;
    }
    public Evento saveEvento(EventoDTO body, UUID utenteId){
        Utente utente = this.utentiService.findById(utenteId);
        if (body.data().isBefore(LocalDate.now())) throw new BadRequestException("La data dell'evento non può corrispondere alla data odierna");
        Evento nuovoEvento = new Evento(body.titolo(), body.descrizione(), body.data(), body.luogo(), body.maxIngressi(), utente);
        this.eventiRepository.save(nuovoEvento);
        System.out.println("Evento salvato");
        return nuovoEvento;
    }

    public Evento findById(UUID eventoId){
        return this.eventiRepository.findById(eventoId).orElseThrow(() -> new NonTrovatoException(eventoId));
    }

    public Evento putEvento(UUID eventoId, EventoDTO body, UUID organizzatoreId){
        Evento evento = this.findById(eventoId);
        Utente organizzatore = this.utentiService.findById(organizzatoreId);
        if (!evento.getOrganizzatore().getUtenteId().equals(organizzatore.getUtenteId()))
            throw new BadRequestException("L'evento che stai cercando di modificare non appartiene a te");
        evento.setTitolo(body.titolo());
        evento.setDescrizione(body.descrizione());
        evento.setData(body.data());
        evento.setLuogo(body.luogo());
        evento.setIngressiMax(body.maxIngressi());
        this.eventiRepository.save(evento);

        System.out.println("Modifica effettuata");
        return evento;
    }

    public void deleteEvento(UUID eventoId, UUID organizzatoreId){
        Evento evento = this.findById(eventoId);
        Utente organizzatore = this.utentiService.findById(organizzatoreId);
        if (!evento.getOrganizzatore().getUtenteId().equals(organizzatore.getUtenteId()))
            throw new BadRequestException("L'evento che stai cercando di cancellare non appartiene a te");
        this.eventiRepository.delete(evento);
        System.out.println("Eliminato");
    }
}