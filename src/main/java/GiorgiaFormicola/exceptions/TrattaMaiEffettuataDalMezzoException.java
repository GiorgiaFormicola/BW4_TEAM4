package GiorgiaFormicola.exceptions;

public class TrattaMaiEffettuataDalMezzoException extends RuntimeException {
    public TrattaMaiEffettuataDalMezzoException() {
        super("Impossibile calcolare la media, la tratta non è mai stata effettuata dal mezzo indicato");
    }
}
