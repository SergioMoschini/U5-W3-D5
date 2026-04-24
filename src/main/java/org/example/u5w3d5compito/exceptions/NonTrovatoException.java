package org.example.u5w3d5compito.exceptions;

import java.util.UUID;

public class NonTrovatoException extends RuntimeException {
    public NonTrovatoException (UUID id) {
        super("La risorsa con id " + id + " non è stata trovata");
    }
}
