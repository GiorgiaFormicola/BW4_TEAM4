package GiorgiaFormicola.exceptions;

public class PuntoDiEmissioneNonAttivo extends RuntimeException {
    public PuntoDiEmissioneNonAttivo() {
        super("Impossibile effettuare l'operazione richiesta. Punto di emissione non in servizio.");
    }
}
