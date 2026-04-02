package GiorgiaFormicola.exceptions;

public class TesseraUtenteNonTrovata extends RuntimeException {
    public TesseraUtenteNonTrovata() {
        super("Nessuna tessera associata all'utente trovata");
    }
}
