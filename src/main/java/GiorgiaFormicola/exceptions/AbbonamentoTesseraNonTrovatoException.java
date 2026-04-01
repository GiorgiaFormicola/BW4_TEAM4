package GiorgiaFormicola.exceptions;

public class AbbonamentoTesseraNonTrovatoException extends RuntimeException {
    public AbbonamentoTesseraNonTrovatoException() {
        super("Nessun abbonamento trovato associato alla tua tessera");
    }
}
