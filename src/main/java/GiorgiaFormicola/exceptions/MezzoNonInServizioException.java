package GiorgiaFormicola.exceptions;

public class MezzoNonInServizioException extends RuntimeException {
    public MezzoNonInServizioException() {
        super("Mezzo non in servizio.");
    }
}
