package GiorgiaFormicola.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "operatività_mezzi")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "operatività")
public abstract class OperativitàMezzo {
    //ATTRIBUTI
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "data_inizio")
    private LocalDate dataInizio;

    @Column(name = "data_fine")
    private LocalDate dataFine;

    //RELAZIONE CON MEZZO
    @ManyToOne
    @JoinColumn(name = "id_mezzo")
    private MezzoDiTrasporto mezzo;

    //COSTRUTTORI
    protected OperativitàMezzo() {
    }

    public OperativitàMezzo(MezzoDiTrasporto mezzo) {
        this.dataInizio = LocalDate.now();
        this.mezzo = mezzo;
    }

    //GETTERS
    public UUID getId() {
        return id;
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }

    //SETTERS
    public MezzoDiTrasporto getMezzo() {
        return mezzo;
    }

    //TO STRING
    @Override
    public String toString() {
        return "id=" + id +
                ", dataInizio=" + dataInizio +
                ", dataFine=" + dataFine +
                ", mezzo=" + mezzo;
    }
}
