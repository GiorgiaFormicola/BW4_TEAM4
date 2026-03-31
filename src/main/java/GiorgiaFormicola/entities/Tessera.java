package GiorgiaFormicola.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tessere")
public class Tessera {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "numero_tessera")
    private Long numeroTessera;

    @Column(name = "data_emissione")
    private LocalDate dataEmissione;

    @Column(name = "data_scadenza")
    private LocalDate dataScadenza;

    @Column(name = "codice_fiscale")
    private String codiceFiscale;


    public Tessera() {
    }


    public Tessera(Long numeroTessera, LocalDate dataEmissione, LocalDate dataScadenza, String codiceFiscale) {
        this.numeroTessera = numeroTessera;
        this.dataEmissione = dataEmissione;
        this.dataScadenza = dataScadenza;
        this.codiceFiscale = codiceFiscale;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }
}
