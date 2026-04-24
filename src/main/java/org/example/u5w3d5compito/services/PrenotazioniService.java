package org.example.u5w3d5compito.services;
import org.example.u5w3d5compito.entities.Evento;
import org.example.u5w3d5compito.entities.Prenotazione;
import org.example.u5w3d5compito.entities.Role;
import org.example.u5w3d5compito.entities.Utente;
import org.example.u5w3d5compito.exceptions.BadRequestException;
import org.example.u5w3d5compito.payloads.PrenotazioneDTO;
import org.example.u5w3d5compito.repositories.PrenotazioniRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PrenotazioniService {
    private final PrenotazioniRepository prenotazioniRepository;
    private final EventiService eventiService;
    private final UtentiService utentiService;

    public PrenotazioniService(PrenotazioniRepository prenotazioniRepository, EventiService eventiService, UtentiService utentiService) {
        this.prenotazioniRepository = prenotazioniRepository;
        this.eventiService = eventiService;
        this.utentiService = utentiService;
    }

    public Prenotazione postPrenotazione(PrenotazioneDTO body, UUID utenteId){

        Evento evento = this.eventiService.findById(body.eventoId());
        Utente utente = this.utentiService.findById(utenteId);

        if(utente.getRuolo().equals(Role.ORGANIZZATORE)){
            if (evento.getOrganizzatore().getUtenteId().equals(utente.getUtenteId()))
                throw new BadRequestException("L'organizzatore non può prenotare eventi che organizza");
        }

        List<Prenotazione> listaPrenotazioni = this.prenotazioniRepository.findByUtenteAndEvento(evento.getEventoId(), utente.getUtenteId());
        if(!listaPrenotazioni.isEmpty()) throw new BadRequestException("Ogni utente può prenotare una sola volta lo stesso evento");
        List<Prenotazione> totPrenotazioniEvento = this.prenotazioniRepository.findAllByEvento(evento.getEventoId());
        if(totPrenotazioniEvento.size() >= evento.getIngressiMax())
            throw new BadRequestException("EVENTO AL COMPLETO");

        Prenotazione nuovaPrenotazione = new Prenotazione(LocalDate.now(), utente, evento);
        this.prenotazioniRepository.save(nuovaPrenotazione);
        System.out.println("Prenotazione salvata");

        return nuovaPrenotazione;
    }

}