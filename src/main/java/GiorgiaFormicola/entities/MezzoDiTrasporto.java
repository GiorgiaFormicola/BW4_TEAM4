package GiorgiaFormicola.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "mezzi_di_trasporto")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class MezzoDiTrasporto {
    //ATTRIBUTI
    @Id
    @GeneratedValue
    private UUID id;

    private int capienza;

    //RELAZIONE CON BIGLIETTI
    @OneToMany(mappedBy = "mezzo")
    private List<Biglietto> bigliettiValidati;

    //COSTRUTTORI
    protected MezzoDiTrasporto() {
    }

    protected MezzoDiTrasporto(int capienza) {
        this.capienza = capienza;
    }

    //GETTERS
    public UUID getId() {
        return id;
    }

    public int getCapienza() {
        return capienza;
    }

    public List<Biglietto> getBigliettiValidati() {
        return bigliettiValidati;
    }

    //TO STRING
    @Override
    public String toString() {
        return "id=" + id +
                ", capienza=" + capienza;
    }
}

