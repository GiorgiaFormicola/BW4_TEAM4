package GiorgiaFormicola.exceptions;

public class CodiceFiscaleNonValidoException extends RuntimeException {
    public CodiceFiscaleNonValidoException(String cf) {
        super("Il formato del codice fiscale fornito non è valido. ( Codice fiscale fornito: " + cf.toUpperCase() + " )");
    }
}
