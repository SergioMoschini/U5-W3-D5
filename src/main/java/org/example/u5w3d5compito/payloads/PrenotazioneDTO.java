package org.example.u5w3d5compito.payloads;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record PrenotazioneDTO(
        @NotNull(message = "Inserisci Id evento")
        UUID eventoId
) {
}
