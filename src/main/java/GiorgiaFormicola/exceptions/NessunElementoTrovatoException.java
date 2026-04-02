package GiorgiaFormicola.exceptions;

public class NessunElementoTrovatoException extends RuntimeException {
    public NessunElementoTrovatoException() {
        super("La ricerca effettuata non ha fornito alcun risultato");
    }
}
