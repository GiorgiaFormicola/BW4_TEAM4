package GiorgiaFormicola.exceptions;

public class UtenteNonTrovatoException extends RuntimeException {
    public UtenteNonTrovatoException() {
        super("Impossibile accedere al portale, utente non trovato");
    }
}
