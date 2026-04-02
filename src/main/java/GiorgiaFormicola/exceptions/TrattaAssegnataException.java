package GiorgiaFormicola.exceptions;

public class TrattaAssegnataException extends RuntimeException {
    public TrattaAssegnataException() {
        super("Impossibile assegnare la tratta. Tratta già assegnata ad un altro mezzo nella data desiderata");
    }
}
