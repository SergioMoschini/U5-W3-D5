package org.example.u5w3d5compito.payloads;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record EventoDTO(

        @NotBlank(message = "Il titolo dev'essere inserito")
        @Size(min = 10, max = 40, message = "Il titolo dev'essere compreso tra i 10 e i 40 caratteri")
        String titolo,

        @NotBlank(message = "La descrizione dev'essere inserita")
        @Size(min = 10, max = 300, message = "La descrizione dev'essere compresa tra i 10 e i 300 caratteri")
        String descrizione,

        @Future(message = "La data dell'evento deve essere una data futura")
        LocalDate data,

        @NotBlank(message = "Il luogo deve essere inserito")
        @Size(min = 4, max = 19, message = "Il luogo deve essere compreso tra i 4 e i 19 caratteri")
        String luogo,

        @Min(value = 10, message = "Il minimo numero di partecipanti richiesto e' di 10")
        @Max(value = 150, message = "Il numero massimo di partecipanti all'evento e' di 150")
        int maxIngressi
){

}



