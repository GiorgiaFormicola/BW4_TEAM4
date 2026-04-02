package GiorgiaFormicola.dao;

import GiorgiaFormicola.entities.Tratta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

import java.util.UUID;

public class TrattaDAO {
    private EntityManager em;

    public TrattaDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Tratta t) {
        EntityTransaction tdt = em.getTransaction();
        try {
//            controllo se la tratta esiste già
            Tratta esistente = findByPartenzaECapolinea(t.getPartenza(), t.getCapolinea());

            if (esistente != null) {
                System.err.println("-- ATTENZIONE: La tratta da '" + t.getPartenza() +
                        "' a '" + t.getCapolinea() + "' esiste già con ID: " + esistente.getId());
                return;
            }

            tdt.begin();
            em.persist(t);
            tdt.commit();
            System.out.println("Tratta creata con successo!");

        } catch (Exception e) {
            if (tdt.isActive()) tdt.rollback();
            System.err.println("ERRORE durante il salvataggio della tratta: " + e.getMessage());
        }
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

    public Tratta findByPartenzaECapolinea(String partenza, String capolinea) {
        try {
            return em.createQuery("SELECT t FROM Tratta t WHERE LOWER(t.partenza) = LOWER(:p) AND LOWER(t.capolinea) = LOWER(:c)", Tratta.class)
                    .setParameter("p", partenza)
                    .setParameter("c", capolinea)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }



}