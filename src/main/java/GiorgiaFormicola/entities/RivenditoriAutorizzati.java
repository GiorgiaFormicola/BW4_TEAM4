package GiorgiaFormicola.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("rivenditori_autorizzati")
public class RivenditoriAutorizzati extends PuntiEmissione {

    public RivenditoriAutorizzati() {
        super();
    }

    @Override
    public String toString() {
        return "RivenditoriAutorizzati{}";
    }
}

