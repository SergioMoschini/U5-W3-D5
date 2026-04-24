package org.example.u5w3d5compito.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Prenotazione {
    @Id
    @GeneratedValue
    @Column(name = "prenotazione_id")
    @Setter(AccessLevel.NONE)
    private UUID prenotazioneId;

    @Column(name = "data_prenotazione")
    private LocalDate dataPrenotazione;

    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    public Prenotazione(LocalDate dataPrenotazione, Utente utente, Evento evento) {
        this.dataPrenotazione = dataPrenotazione;
        this.utente = utente;
        this.evento = evento;
    }




}