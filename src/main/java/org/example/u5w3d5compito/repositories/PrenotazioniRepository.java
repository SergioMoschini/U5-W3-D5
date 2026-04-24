package org.example.u5w3d5compito.repositories;
import org.example.u5w3d5compito.entities.Prenotazione;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;


public interface PrenotazioniRepository extends JpaRepository <Prenotazione, UUID> {
    List<Prenotazione> findByUtenteAndEvento(UUID eventoId, UUID utenteId);
    List<Prenotazione> findAllByEvento(UUID eventoId);
    Page<Prenotazione> findAllByUtente(UUID utenteId, Pageable pageable);
}
