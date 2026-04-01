package GiorgiaFormicola.exceptions;

public class BigliettoGiàVidimatoException extends RuntimeException {
    public BigliettoGiàVidimatoException() {
        super("Impossibile utilizzare il biglietto. Biglietto già vidimato.");
    }
}
