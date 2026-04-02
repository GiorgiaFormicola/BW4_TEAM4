package GiorgiaFormicola.exceptions;

public class MezzoAssegnatoException extends RuntimeException {
    public MezzoAssegnatoException() {
        super("Impossibile assegnare la tratta al mezzo desiderato. Mezzo già assegnato ad un'altra tratta nella data specificata");
    }
}
