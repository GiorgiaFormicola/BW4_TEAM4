package GiorgiaFormicola.exceptions;

public class UtenteGiàPresenteNelDBException extends RuntimeException {
    public UtenteGiàPresenteNelDBException(String cf) {
        super("Impossibile effettuare la registrazione, codice fiscale " + cf + " già associato ad un altro utente.");
    }
}
