package GiorgiaFormicola.exceptions;

import GiorgiaFormicola.entities.Utente;

public class NotFoundUserException extends RuntimeException {
    public NotFoundUserException(String codiceFiscale) {
        super("Nessun utente trovato con codice fiscale : " + codiceFiscale);
    }
}
