package GiorgiaFormicola.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tessere")
public class Tessera {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "numero_tessera", unique = true)
    private Long numeroTessera;

    @Column(name = "data_emissione")
    private LocalDate dataEmissione;

    @Column(name = "data_scadenza")
    private LocalDate dataScadenza;

    @Column(name = "codice_fiscale", unique = true)
    private String codiceFiscale;

//    @OneToMany(mappedBy = "tessera")
//    private List<>



    protected Tessera() {
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

    public UUID getId() {
        return id;
    }

    public Long getNumeroTessera() {
        return numeroTessera;
    }

    public LocalDate getDataEmissione() {
        return dataEmissione;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    @Override
    public String toString() {
        return "Tessera{" +
                "id=" + id +
                ", numeroTessera=" + numeroTessera +
                ", dataEmissione=" + dataEmissione +
                ", dataScadenza=" + dataScadenza +
                ", codiceFiscale='" + codiceFiscale + '\'' +
                '}';
    }
}
