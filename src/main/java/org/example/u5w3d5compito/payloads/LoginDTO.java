package org.example.u5w3d5compito.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginDTO(@NotBlank(message = "Inserisci l'email!")
                       @Email(message = "indirizzo email errato")
                       String email,
                       @NotBlank(message = "Inserisci la password!")
                       @Size(min = 8, max = 20, message = "La password deve essere compresa tra 8 e i 20 caratteri")
                       String password) {
}
