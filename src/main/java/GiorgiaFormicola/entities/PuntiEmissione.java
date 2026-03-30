package GiorgiaFormicola.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "punti_emissione")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class PuntiEmissione {

    @Id
    @GeneratedValue
    @Column(name = "punti_emissione_id")
    private UUID id;

    @Column(name = "attivo")
    private boolean attivo;

    protected PuntiEmissione(){

    }

    public PuntiEmissione(boolean attivo){
        this.attivo = attivo;
    }

    public UUID getId() {
        return id;
    }

    public boolean isAttivo() {
        return attivo;
    }

    public void setAttivo(boolean attivo) {
        this.attivo = attivo;
    }

    @Override
    public String toString() {
        return "PuntiEmissione{" +
                "id=" + id +
                ", attivo=" + attivo +
                '}';
    }
}
