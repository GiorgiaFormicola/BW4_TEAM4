package GiorgiaFormicola.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "assegnazioni_tratte")
public class TrattaMezzo {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_mezzo")
    private MezzoDiTrasporto mezzo;

    @ManyToOne
    @JoinColumn(name = "id_tratta")
    private Tratta tratta;

    private LocalTime percorrenza;

    @Column(name = "data_inizio")
    private LocalDate dataInizio;

    // costruttore

    public TrattaMezzo() {
    }

    public TrattaMezzo(MezzoDiTrasporto mezzo, Tratta tratta, LocalDate dataInizio) {
        this.mezzo = mezzo;
        this.tratta = tratta;
        this.dataInizio = dataInizio;
    }

    // getter e setter

    public UUID getId() {
        return id;
    }

    public MezzoDiTrasporto getMezzo() {
        return mezzo;
    }

    public void setMezzo(MezzoDiTrasporto mezzo) {
        this.mezzo = mezzo;
    }

    public Tratta getTratta() {
        return tratta;
    }

    public void setTratta(Tratta tratta) {
        this.tratta = tratta;
    }

    public LocalTime getPercorrenza() {
        return percorrenza;
    }

    public void setPercorrenza(LocalTime percorrenza) {
        this.percorrenza = percorrenza;
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

}