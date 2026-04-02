package GiorgiaFormicola.exceptions;

import GiorgiaFormicola.entities.Tessera;

public class NotFoundTesseraException extends RuntimeException {
    public NotFoundTesseraException(Tessera tessera) {
        super("La tessera " + tessera + " non è stata trovata");
    }
}
