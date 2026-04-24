package org.example.u5w3d5compito.repositories;

import org.example.u5w3d5compito.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UtentiRepository extends JpaRepository <Utente, UUID> {
    Optional<Utente> findByEmail(String email);
}