package GiorgiaFormicola.exceptions;

public class AbbonamentoScadutoException extends RuntimeException {
    public AbbonamentoScadutoException() {
        super("Impossibile utilizzare l'abbonamento, abbonamento scaduto.");
    }
}
