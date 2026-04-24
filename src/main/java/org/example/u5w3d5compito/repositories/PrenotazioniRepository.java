package org.example.u5w3d5compito.repositories;

import org.example.u5w3d5compito.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PrenotazioniRepository extends JpaRepository <Prenotazione, UUID> {
}
