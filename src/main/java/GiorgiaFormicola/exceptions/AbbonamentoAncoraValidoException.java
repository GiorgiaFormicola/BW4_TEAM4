package GiorgiaFormicola.exceptions;

public class AbbonamentoAncoraValidoException extends RuntimeException {
    public AbbonamentoAncoraValidoException() {
        super("Impossibile rinnovare l'abbonamento, abbonamento ancora in corso di validità.");
    }
}
