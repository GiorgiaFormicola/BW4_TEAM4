package GiorgiaFormicola.entities;

import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tratte")
public class Tratta {
    @Id
    @GeneratedValue
    private UUID id;

    private String partenza;
    private String capolinea;

    @Column(name = "percorrenza_prevista")
    private LocalTime percorrenzaPrevista;

    @OneToMany(mappedBy = "tratta", cascade = CascadeType.ALL)
    private List<TrattaMezzo> percorrenzeEffettive;

    //  costruttore

    public Tratta() {
    }


    public Tratta(String partenza, String capolinea, LocalTime percorrenzaPrevista) {
        this.partenza = partenza;
        this.capolinea = capolinea;
        this.percorrenzaPrevista = percorrenzaPrevista;
    }

    // getter e setter

    public UUID getId() { return id; }

    public String getPartenza() { return partenza; }
    public void setPartenza(String partenza) { this.partenza = partenza; }

    public String getCapolinea() { return capolinea; }
    public void setCapolinea(String capolinea) { this.capolinea = capolinea; }

    public LocalTime getPercorrenzaPrevista() { return percorrenzaPrevista; }
    public void setPercorrenzaPrevista(LocalTime percorrenzaPrevista) { this.percorrenzaPrevista = percorrenzaPrevista; }

    public List<TrattaMezzo> getPercorrenzeEffettive() { return percorrenzeEffettive; }
    public void setPercorrenzeEffettive(List<TrattaMezzo> percorrenzeEffettive) { this.percorrenzeEffettive = percorrenzeEffettive; }

    @Override
    public String toString() {
        return "Tratta [id=" + id + ", da=" + partenza + " a=" + capolinea + ", prevista=" + percorrenzaPrevista + "]";
    }
}

