package GiorgiaFormicola.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String id) {
        super("La risorsa con id " + id + " non è stata trovata.");
    }
}
