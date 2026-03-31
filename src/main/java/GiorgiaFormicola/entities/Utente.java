package GiorgiaFormicola.entities;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "utenti")
public class Utente {

    @Id
    @GeneratedValue
    private UUID id;


    private TipoUtente tipo;

    @Column(name = "codice_fiscale")
    private String codiceFiscale;

    public Utente() {
    }

    public Utente(TipoUtente tipo, String codiceFiscale) {
        this.tipo = tipo;
        this.codiceFiscale = codiceFiscale;
    }



    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }
}