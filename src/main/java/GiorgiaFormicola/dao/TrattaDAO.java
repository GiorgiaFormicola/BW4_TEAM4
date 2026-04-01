package GiorgiaFormicola.dao;

import GiorgiaFormicola.entities.Tratta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.UUID;

public class TrattaDAO {
    private EntityManager em;

    public TrattaDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Tratta t) {
        EntityTransaction tdt = em.getTransaction();
        tdt.begin();
        em.persist(t);
        tdt.commit();
    }

    public long countPercorrenzeMezzoSuTratta(UUID mezzoId, UUID trattaId) {
        return em.createQuery(
                        "SELECT COUNT(tm) FROM TrattaMezzo tm WHERE tm.mezzo.id = :mId AND tm.tratta.id = :tId", Long.class)
                .setParameter("mId", mezzoId)
                .setParameter("tId", trattaId)
                .getSingleResult();
    }

    public Double getTempoMedioEffettivo(UUID trattaId) {
        return em.createQuery(
                        "SELECT AVG(HOUR(tm.percorrenza) * 60 + MINUTE(tm.percorrenza)) " +
                                "FROM TrattaMezzo tm WHERE tm.tratta.id = :tId", Double.class)
                .setParameter("tId", trattaId)
                .getSingleResult();
    }
}