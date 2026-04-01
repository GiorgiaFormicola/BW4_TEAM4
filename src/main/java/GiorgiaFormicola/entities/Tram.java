package GiorgiaFormicola.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("TRAM")
public class Tram extends MezzoDiTrasporto {
    public Tram() {
        super(150);
    }

    //TO STRING
    @Override
    public String toString() {
        return "Tram {" +
                super.toString() +
                "}";
    }
}
