package GiorgiaFormicola.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SERVIZIO")
public class Servizio extends OperativitàMezzo {
    //COSTRUTTORI
    protected Servizio() {
    }

    public Servizio(MezzoDiTrasporto mezzo) {
        super(mezzo);
    }

    //TO STRING
    @Override
    public String toString() {
        return "Servizio {" +
                super.toString() +
                "}";
    }
}
