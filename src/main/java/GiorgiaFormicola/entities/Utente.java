package GiorgiaFormicola.entities;

import GiorgiaFormicola.enums.TipoDiUtente;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "utenti")
public class Utente {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoDiUtente tipo; // USER o ADMIN

    @Column(name = "codice_fiscale", unique = true, nullable = false)
    private String codiceFiscale;


    @OneToOne(mappedBy = "utente")
    private Tessera tessera;

    // costruttore
    public Utente() {}

    public Utente(TipoDiUtente tipo, String codiceFiscale) {
        this.tipo = tipo;
        this.codiceFiscale = codiceFiscale;
    }

    // getter e setter
    public UUID getId() { return id; }
    public TipoDiUtente getTipo() { return tipo; }
    public void setTipo(TipoDiUtente tipo) { this.tipo = tipo; }

    public String getCodiceFiscale() { return codiceFiscale; }
    public void setCodiceFiscale(String codiceFiscale) { this.codiceFiscale = codiceFiscale; }

    public Tessera getTessera() { return tessera; }
    public void setTessera(Tessera tessera) { this.tessera = tessera; }

    @Override
    public String toString() {
        return "Utente [ID=" + id + ", Tipo=" + tipo + ", CF=" + codiceFiscale + "]";
    }
}
