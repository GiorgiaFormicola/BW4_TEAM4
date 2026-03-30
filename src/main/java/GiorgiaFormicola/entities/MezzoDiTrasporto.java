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

    @Column(name = "in_servizio")
    private boolean inServizio;

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

    public boolean isInServizio() {
        return inServizio;
    }

  /*  public void setInServizio() {
        if (this.inServizio) System.out.println("Mezzo già in servizio.");
        else this.inServizio = true;
    }

    public void setInManutezione(){
        if (!this.inServizio) System.out.println("Mezzo già in manutenzione.");
        else this.inServizio = false;
    }*/

    //TO STRING
    @Override
    public String toString() {
        return "id=" + id +
                ", capienza=" + capienza +
                ", inServizio=" + inServizio;
    }
}
