package GiorgiaFormicola.entities;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("distributori_automatici")
public class DistributoriAutomatici extends PuntiEmissione{

    protected DistributoriAutomatici(){

    }

    public DistributoriAutomatici(boolean attivo){
        super(attivo);
    }

    @Override
    public String toString() {
        return "DistributoriAutomatici{}";
    }
}
