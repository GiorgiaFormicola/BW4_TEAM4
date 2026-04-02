package GiorgiaFormicola.exceptions;

public class TesseraScadutaException extends RuntimeException {
    public TesseraScadutaException() {
        super("Impossibile effettuare l'operazione richiesta, tessera scaduta.");
    }
}
