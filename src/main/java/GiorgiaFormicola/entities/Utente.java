package GiorgiaFormicola.entities;

import GiorgiaFormicola.enums.TipoDiUtente;
import GiorgiaFormicola.exceptions.CodiceFiscaleNonValidoException;
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
    public Utente() {
    }

    public Utente(TipoDiUtente tipo, String codiceFiscale) {
        if (codiceFiscale == null || codiceFiscale.length() != 16 || !codiceFiscale.toUpperCase().matches("^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$"))
            throw new CodiceFiscaleNonValidoException(codiceFiscale);
        else {
            this.tipo = tipo;
            this.codiceFiscale = codiceFiscale.toUpperCase();
        }

    }

    // getter e setter
    public UUID getId() {
        return id;
    }

    public TipoDiUtente getTipo() {
        return tipo;
    }

    public void setTipo(TipoDiUtente tipo) {
        this.tipo = tipo;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public Tessera getTessera() {
        return tessera;
    }

    public void setTessera(Tessera tessera) {
        this.tessera = tessera;
    }

    @Override
    public String toString() {
        return "Utente [ID=" + id + ", Tipo=" + tipo + ", CF=" + codiceFiscale + "]";
    }
}
