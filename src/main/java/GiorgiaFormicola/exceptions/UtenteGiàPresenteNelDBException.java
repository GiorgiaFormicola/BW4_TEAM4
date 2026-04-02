package GiorgiaFormicola.exceptions;

public class UtenteGiàPresenteNelDBException extends RuntimeException {
    public UtenteGiàPresenteNelDBException() {
        super("Impossibile effettuare la registrazione, codice fiscale già associato ad un altro utente");
    }
}
