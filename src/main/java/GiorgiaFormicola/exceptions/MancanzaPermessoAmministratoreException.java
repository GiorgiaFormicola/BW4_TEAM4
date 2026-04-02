package GiorgiaFormicola.exceptions;

public class MancanzaPermessoAmministratoreException extends RuntimeException {
    public MancanzaPermessoAmministratoreException() {
        super("Impossibile accedere al portale come amministratore, assenza dei permessi necessari.");
    }
}
