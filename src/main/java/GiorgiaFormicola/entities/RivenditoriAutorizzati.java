package GiorgiaFormicola.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("rivenditori_autorizzati")
public class RivenditoriAutorizzati extends PuntiEmissione{

    protected RivenditoriAutorizzati(){

    }

    public RivenditoriAutorizzati(boolean attivo){
        super(attivo);
    }

    @Override
    public String toString() {
        return "RivenditoriAutorizzati{}";
    }
}

