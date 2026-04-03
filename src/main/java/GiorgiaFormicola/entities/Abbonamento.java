package GiorgiaFormicola.entities;

import GiorgiaFormicola.enums.TipoAbbonamento;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "abbonamenti")
public class Abbonamento extends Emissione {
    //ATTRIBUTI
    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private TipoAbbonamento tipo;

    @Column(name = "data_scadenza", nullable = false)
    private LocalDate dataScadenza;

    //RELAZIONE CON TESSERA
    @ManyToOne
    @JoinColumn(name = "id_tessera")
    private Tessera tessera;

    //COSTRUTTORI
    protected Abbonamento() {
    }

    public Abbonamento(PuntiEmissione puntoEmissione, Tessera tessera, TipoAbbonamento tipo) {
        super(puntoEmissione);
        this.tipo = tipo;
        this.tessera = tessera;
        if (tipo.equals(TipoAbbonamento.MENSILE)) {
            this.dataScadenza = LocalDate.now().plusMonths(1);
        } else {
            this.dataScadenza = LocalDate.now().plusWeeks(1);
        }
    }

    //GETTERS

    public TipoAbbonamento getTipo() {
        return tipo;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(LocalDate dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    //SETTERS

    public Tessera getTessera() {
        return tessera;
    }

    //TO STRING

    @Override
    public String toString() {
        return "Abbonamento {" +
                super.toString() +
                ", tipo=" + tipo +
                ", scadenza=" + dataScadenza +
                ", tessera=" + tessera +
                '}';
    }
}
