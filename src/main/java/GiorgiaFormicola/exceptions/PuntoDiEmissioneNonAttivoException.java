package GiorgiaFormicola.exceptions;

public class PuntoDiEmissioneNonAttivoException extends RuntimeException {
    public PuntoDiEmissioneNonAttivoException() {
        super("Impossibile effettuare l'operazione richiesta. Punto di emissione non in servizio.");
    }
}
