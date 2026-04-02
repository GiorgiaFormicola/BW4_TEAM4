package GiorgiaFormicola.exceptions;

import GiorgiaFormicola.entities.Utente;

public class UserAlreadyEnteredException extends RuntimeException {
    public UserAlreadyEnteredException(Utente utente) {
        super("L'utente " + utente + " è gia stato inserito");
    }
}
