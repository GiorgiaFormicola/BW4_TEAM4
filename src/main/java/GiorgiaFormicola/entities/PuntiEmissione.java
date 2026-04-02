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

    public PuntiEmissione() {
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "PuntiEmissione{" +
                "id=" + id +
                '}';
    }
}
