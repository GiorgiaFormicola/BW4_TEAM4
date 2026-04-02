package GiorgiaFormicola.exceptions;

public class TesseraGiaEsistente extends RuntimeException {
    public TesseraGiaEsistente() {
        super("Numero tessera gia esistente");
    }
}
