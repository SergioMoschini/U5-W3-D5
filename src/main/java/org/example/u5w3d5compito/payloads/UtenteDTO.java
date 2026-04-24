package org.example.u5w3d5compito.payloads;
import jakarta.validation.constraints.*;
import org.example.u5w3d5compito.entities.Role;
import java.time.LocalDate;

public record UtenteDTO(
        @NotBlank(message = "Come ti chiami?")
        @Size(min = 2, max = 30, message = "Il nome deve essere compreso tra i 2 e i 30 caratteri")
        String nome,

        @NotBlank(message = "Anche il cognome si")
        @Size(min = 2, max = 30, message = "Il cognome deve essere compreso tra i 2 e i 30 caratteri")
        String cognome,

        @NotNull(message = "Quando sei nato? (spero prima del signore degli anelli)")
        LocalDate dataNascita,

        @NotBlank(message = "Inserisci l'email (dimmi che non e' paperino2000@gmail.com")
        @Email(message = "Indirizzo email non valido")
        String email,

        @NotBlank(message = "Inserisci la password, guai a te se metti 12345678, poi se ti rubano qualcosa e' colpa tua")
        @Pattern(
                regexp = "^(?=.[a-z])(?=.[A-Z])(?=.[0-9])(?=.[!@#$%^&])[a-zA-Z0-9!@#$%^&]{8,20}$",
                message = "Usa almeno una minuscola, una maiuscola, un numero e un carattere speciale, si cosi non puoi mettere 12345678"
        )
        String password,

        @NotNull(message = "Dichiara il ruolo, no, imperatore della galassia non e' un ruolo")
        @Size(min = 6, max = 15, message = "Il ruolo puo' essere ADMIN, ORGANIZZATORE o UTENTE")
        Role ruolo
) {}