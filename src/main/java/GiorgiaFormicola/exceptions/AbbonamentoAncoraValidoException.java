package GiorgiaFormicola.exceptions;

public class AbbonamentoAncoraValidoException extends RuntimeException {
    public AbbonamentoAncoraValidoException() {
        super("Impossibile effettuare l'operazione, abbonamento ancora in corso di validità.");
    }
}
