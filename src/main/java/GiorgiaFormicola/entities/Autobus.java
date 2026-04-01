package GiorgiaFormicola.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;


@Entity
@DiscriminatorValue("AUTOBUS")
public class Autobus extends MezzoDiTrasporto {
    public Autobus() {
        super(90);
    }

    //TO STRING
    @Override
    public String toString() {
        return "Autobus {" +
                super.toString() +
                "}";
    }
}
