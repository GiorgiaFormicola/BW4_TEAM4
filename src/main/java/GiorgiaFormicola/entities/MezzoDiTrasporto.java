package GiorgiaFormicola.entities;

import jakarta.persistence.*;

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

    //TO STRING
    @Override
    public String toString() {
        return "id=" + id +
                ", capienza=" + capienza;
    }
}
