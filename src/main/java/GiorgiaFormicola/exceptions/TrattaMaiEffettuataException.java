package GiorgiaFormicola.exceptions;

public class TrattaMaiEffettuataException extends RuntimeException {
    public TrattaMaiEffettuataException() {
        super("Impossibile calcolare la media, la tratta non è mai stata effettuata dal alcun mezzo");
    }
}
