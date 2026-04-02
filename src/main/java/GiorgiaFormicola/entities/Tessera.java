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

    @Column(name = "numero_tessera", unique = true)
    private Long numeroTessera;

    @Column(name = "data_emissione")
    private LocalDate dataEmissione;

    @Column(name = "data_scadenza")
    private LocalDate dataScadenza;

    @OneToOne
    @JoinColumn(name = "id_utente", unique = true)
    private Utente utente;

    protected Tessera() {
    }

    public Tessera(Long numeroTessera, Utente utente) {
        this.numeroTessera = numeroTessera;
        this.dataEmissione = LocalDate.now();
        this.dataScadenza = LocalDate.now().plusYears(1);
        this.utente = utente;
    }

    public Tessera(long numeroTessera) {
    }

    public Long getNumeroTessera() {
        return numeroTessera;
    }

    public LocalDate getDataEmissione() {
        return dataEmissione;
    }

    public UUID getId() {
        return id;
    }

    public Utente getUtente() {
        return utente;
    }


 /* public String getCodiceFiscale() {
        return codiceFiscale;
    }*/

 /*   public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }*/

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }



    public void setNumeroTessera(Long numeroTessera) {
        this.numeroTessera = numeroTessera;
    }

    public void setDataEmissione(LocalDate dataEmissione) {
        this.dataEmissione = dataEmissione;
    }

    public void setDataScadenza(LocalDate dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    @Override
    public String toString() {
        return "Tessera {" +
                "id=" + id +
                ", numeroTessera=" + numeroTessera +
                ", dataEmissione=" + dataEmissione +
                ", dataScadenza=" + dataScadenza +
                ", utente=" + utente +
                '}';
    }
}




