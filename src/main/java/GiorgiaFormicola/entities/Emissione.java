package GiorgiaFormicola.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "emissioni")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Emissione {
    //ATTRIBUTI
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "data_emissione")
    private LocalDate dataEmissione;

    @ManyToOne
    @JoinColumn(name = "id_punto_emissione")
    private PuntiEmissione puntiEmissione;

    //COSTRUTTORI
    protected Emissione() {
    }

    public Emissione(PuntiEmissione puntiEmissione) {
        this.dataEmissione = LocalDate.now();
        this.puntiEmissione = puntiEmissione;
    }

    //GETTERS
    public UUID getId() {
        return id;
    }

    public LocalDate getDataEmissione() {
        return dataEmissione;
    }

    public void setDataEmissione(LocalDate dataEmissione) {
        this.dataEmissione = dataEmissione;
    }

    //SETTERS

    public PuntiEmissione getPuntiEmissione() {
        return puntiEmissione;
    }

    //TO STRING
    @Override
    public String toString() {
        return "id=" + id +
                ", dataEmissione=" + dataEmissione +
                ", puntoEmissione=" + puntiEmissione;
    }
}
