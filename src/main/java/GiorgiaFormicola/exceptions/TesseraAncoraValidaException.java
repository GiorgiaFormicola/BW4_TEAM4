package GiorgiaFormicola.exceptions;

public class TesseraAncoraValidaException extends RuntimeException {
    public TesseraAncoraValidaException() {
        super("Impossibile rinnovare la tessere, tessera ancora valida.");
    }
}
