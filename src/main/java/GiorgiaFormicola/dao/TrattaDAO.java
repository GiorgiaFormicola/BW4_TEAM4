package GiorgiaFormicola.dao;

import GiorgiaFormicola.entities.Tratta;
import GiorgiaFormicola.exceptions.NotFoundException;
import GiorgiaFormicola.exceptions.TrattaMaiEffettuataDalMezzoException;
import GiorgiaFormicola.exceptions.TrattaMaiEffettuataException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

import java.time.LocalTime;
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
                System.err.println("\nERRORE : La tratta da '" + t.getPartenza() +
                        "' a '" + t.getCapolinea() + "' esiste già con ID: " + esistente.getId());
                return;
            }
            tdt.begin();
            em.persist(t);
            tdt.commit();
            System.out.println("\n Tratta aggiunta con successo.");

        } catch (Exception e) {
            if (tdt.isActive()) tdt.rollback();
            System.err.println("ERRORE durante il salvataggio della tratta: " + e.getMessage());
        }
    }

    public long countPercorrenzeMezzoSuTratta(String mezzoId, String trattaId) {
        return em.createQuery(
                        "SELECT COUNT(tm) FROM TrattaMezzo tm WHERE tm.mezzo.id = :mId AND tm.tratta.id = :tId", Long.class)
                .setParameter("mId", UUID.fromString(mezzoId))
                .setParameter("tId", UUID.fromString(trattaId))
                .getSingleResult();
    }

    public LocalTime getTempoMedioEffettivo(String trattaId) {
        Double mediaMinuti = em.createQuery(
                        "SELECT AVG(HOUR(tm.percorrenza) * 60 + MINUTE(tm.percorrenza)) " +
                                "FROM TrattaMezzo tm WHERE tm.tratta.id = :tId", Double.class)
                .setParameter("tId", UUID.fromString(trattaId))
                .getSingleResult();

        if (mediaMinuti == 0) throw new TrattaMaiEffettuataException();
        int ore = mediaMinuti.intValue() / 60;
        int minuti = mediaMinuti.intValue() % 60;
        int secondi = (int) ((mediaMinuti - mediaMinuti.intValue()) * 60);

        return LocalTime.of(ore, minuti, secondi);
    }

    public LocalTime getTempoMedioEffettivoInBaseAMezzo(String trattaId, String mezzoId) {
        Double mediaMinuti = em.createQuery(
                        "SELECT AVG(HOUR(tm.percorrenza) * 60 + MINUTE(tm.percorrenza)) " +
                                "FROM TrattaMezzo tm WHERE tm.tratta.id = :tId AND tm.mezzo.id = :mezzoId", Double.class)
                .setParameter("tId", UUID.fromString(trattaId))
                .setParameter("mezzoId", UUID.fromString(mezzoId))
                .getSingleResult();

        if (mediaMinuti == 0) throw new TrattaMaiEffettuataDalMezzoException();

        int ore = mediaMinuti.intValue() / 60;
        int minuti = mediaMinuti.intValue() % 60;
        int secondi = (int) ((mediaMinuti - mediaMinuti.intValue()) * 60);

        return LocalTime.of(ore, minuti, secondi);
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

    public Tratta getTrattaById(String trattaId) {
        Tratta found = em.find(Tratta.class, UUID.fromString(trattaId));
        if (found == null) {
            throw new NotFoundException(trattaId);
        } else {
            /* System.out.println("La tratta con id " + trattaId + " è stata trovata");*/
            return found;
        }
    }


}