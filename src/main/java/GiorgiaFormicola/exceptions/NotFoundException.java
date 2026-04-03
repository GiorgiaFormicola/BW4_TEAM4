package GiorgiaFormicola.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String id) {
        super("La risorsa non è stata trovata.");
    }
}
