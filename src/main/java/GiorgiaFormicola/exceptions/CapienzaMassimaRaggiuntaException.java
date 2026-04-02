package GiorgiaFormicola.exceptions;

public class CapienzaMassimaRaggiuntaException extends RuntimeException {
    public CapienzaMassimaRaggiuntaException() {
        super("Impossibile salire sul mezzo, capienza massima raggiunta.");
    }
}
