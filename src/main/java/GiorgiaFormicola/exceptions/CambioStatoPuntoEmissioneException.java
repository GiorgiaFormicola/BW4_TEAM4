package GiorgiaFormicola.exceptions;

public class CambioStatoPuntoEmissioneException extends RuntimeException {
    public CambioStatoPuntoEmissioneException(Boolean statoCorrente) {
        super("Impossibile cambiare stato del distributore, distributore già" + (statoCorrente ? " in servizio" : " fuori servizio"));
    }
}
