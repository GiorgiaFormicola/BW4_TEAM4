package GiorgiaFormicola.exceptions;

public class UtenteAssociatoATessera extends RuntimeException {
    public UtenteAssociatoATessera() {
        super("Impossibile effettuare l'operazione, utente gia assegnato ad una tessera");
    }
}
