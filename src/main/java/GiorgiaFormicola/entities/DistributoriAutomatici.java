package GiorgiaFormicola.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("distributori_automatici")
public class DistributoriAutomatici extends PuntiEmissione {

    @Column(name = "attivo")
    private boolean attivo;

    protected DistributoriAutomatici() {

    }

    public DistributoriAutomatici(boolean attivo) {
        this.attivo = attivo;
    }

    public boolean isAttivo() {
        return attivo;
    }

    public void setAttivo(boolean attivo) {
        this.attivo = attivo;
    }

    @Override
    public String toString() {
        return "DistributoriAutomatici{" +
                "attivo=" + attivo +
                '}';
    }
}
