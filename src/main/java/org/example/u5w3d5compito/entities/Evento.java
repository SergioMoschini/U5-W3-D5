package org.example.u5w3d5compito.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "eventi")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Evento {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID eventoId;
    private String luogo;
    private LocalDate data;
    private String descrizione;
    private int ingressiMax;
    private String titolo;

    @ManyToOne
    private Utente organizzatore;


    public Evento(String titolo, String descrizione, LocalDate data, String luogo, int ingressiMax, Utente organizzatore) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.data = data;
        this.luogo = luogo;
        this.ingressiMax = ingressiMax;
        this.organizzatore = organizzatore;

    }
}
