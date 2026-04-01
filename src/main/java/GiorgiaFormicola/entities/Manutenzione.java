package GiorgiaFormicola.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("MANUTENZIONE")
public class Manutenzione extends OperativitàMezzo {
    //ATTRIBUTI
    private String descrizione;

    //COSTRUTTORI
    protected Manutenzione() {
    }

    public Manutenzione(MezzoDiTrasporto mezzo, String descrizione) {
        super(mezzo);
        this.descrizione = descrizione;
    }

    //GETTERS

    public String getDescrizione() {
        return descrizione;
    }

    //TO STRING
    @Override
    public String toString() {
        return "Manutenzione {" +
                super.toString() +
                ", descrizione='" + descrizione + '\'' +
                '}';
    }
}
