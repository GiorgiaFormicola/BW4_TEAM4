package GiorgiaFormicola.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "biglietti")
public class Biglietto extends Emissione {
    //ATTRIBUTI
    @Column(name = "data_vidimazione")
    private LocalDate dataVidimazione;

    //RELAZIONE CON MEZZO
    @ManyToOne
    @JoinColumn(name = "id_mezzo")
    private MezzoDiTrasporto mezzo;

    //COSTRUTTORI
    protected Biglietto() {
    }

    public Biglietto(PuntiEmissione puntoEmissione) {
        super(puntoEmissione);
    }

    //GETTERS

    public LocalDate getDataVidimazione() {
        return dataVidimazione;
    }

    //SETTERS
    public void setDataVidimazione(LocalDate dataVidimazione) {
        this.dataVidimazione = dataVidimazione;
    }

    public MezzoDiTrasporto getMezzo() {
        return mezzo;
    }

    //TO STRING

    @Override
    public String toString() {
        return "Biglietto {" +
                super.toString() +
                ", dataVidimazione=" + dataVidimazione +
                ", mezzo=" + mezzo +
                '}';
    }
}

