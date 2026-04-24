package org.example.u5w3d5compito.services;


import org.example.u5w3d5compito.entities.Prenotazione;
import org.example.u5w3d5compito.entities.Role;
import org.example.u5w3d5compito.entities.Utente;
import org.example.u5w3d5compito.exceptions.BadRequestException;
import org.example.u5w3d5compito.exceptions.NonTrovatoException;
import org.example.u5w3d5compito.payloads.UtenteDTO;
import org.example.u5w3d5compito.repositories.PrenotazioniRepository;
import org.example.u5w3d5compito.repositories.UtentiRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class UtentiService {
    private final UtentiRepository utentiRepository;
    private final PasswordEncoder passwordEncoder;
    private final PrenotazioniRepository prenotazioniRepository;
    public UtentiService(UtentiRepository utentiRepository, PasswordEncoder passwordEncoder, PrenotazioniRepository prenotazioniRepository) {
        this.utentiRepository = utentiRepository;
        this.passwordEncoder = passwordEncoder;
        this.prenotazioniRepository = prenotazioniRepository;
    }
    public Utente saveUtente(UtenteDTO body){
        if (body.dataNascita().isAfter(LocalDate.now())) throw new BadRequestException("La data di nascita deve essere precedente alla data di oggi");
        if (this.utentiRepository.existsByEmail(body.email())) throw new BadRequestException("Esiste già un account con questa email");
        Role ruolo;
        if (body.ruolo().equals("utente")){
            ruolo = Role.UTENTE;
        } else if (body.ruolo().equals("organizzatore")) {
            ruolo = Role.ORGANIZZATORE;
        } else {
            throw new BadRequestException("Scegli tra organizzatore oppure utente");
        }
        Utente nuovoUtente = new Utente(body.nome(), body.cognome(), body.dataNascita(), body.email(), passwordEncoder.encode(body.password()), ruolo);
        this.utentiRepository.save(nuovoUtente);
        System.out.println("User salvato");
        return nuovoUtente;
    }
    public Utente findById (UUID utenteId){
        return this.utentiRepository.findById(utenteId).orElseThrow(() -> new NonTrovatoException(utenteId));
    }
    public Utente findByEmail(String email){
        return this.utentiRepository.findByEmail(email).orElseThrow(() -> new BadRequestException("Questa email non esiste nel Database"));
    }
    public Page<Prenotazione> findPrenotazioniByUtente(UUID utenteId, int page, int size){
        if (size > 100) size = 100;
        if (size < 0) size = 0;
        if (page > 30) page = 30;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by("dataPrenotazione"));
        return this.prenotazioniRepository.findAllByUtente(utenteId, pageable);
    }
    public Prenotazione findPrenotazioneById(UUID prenotazioneId){
        return this.prenotazioniRepository.findById(prenotazioneId).orElseThrow(() -> new NonTrovatoException(prenotazioneId));
    }
    public void deletePrenotazione(UUID prenotazioneId, UUID utenteId){
        Prenotazione prenotazione = this.findPrenotazioneById(prenotazioneId);
        Utente utente = this.findById(utenteId);
        if(!utente.getUtenteId().equals(prenotazione.getUtente().getUtenteId()))
            throw new BadRequestException("Puoi eliminare solamente le prenotazioni fatte da te");
        this.prenotazioniRepository.delete(prenotazione);
        System.out.println("Prenotazione eliminata");
    }
}