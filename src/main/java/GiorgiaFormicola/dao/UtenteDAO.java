package GiorgiaFormicola.dao;

import GiorgiaFormicola.entities.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.UUID;

public class UtenteDAO {
    private EntityManager em;

    public UtenteDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Utente u) {
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            em.persist(u);
            t.commit();
            System.out.println("Utente creato con successo!");
        } catch (Exception e) {
            if (t.isActive()) t.rollback();
            System.err.println("ERRORE: Impossibile creare l'utente. Questo codice fiscale già esiste.");
        }
    }

    public Utente findById(UUID id) {
        return em.find(Utente.class, id);
    }


//    recupero dell utente tramite codice fiscal

    public Utente findByCodiceFiscale(String cf) {
        try {
            return em.createQuery("SELECT u FROM Utente u WHERE u.codiceFiscale = :cf", Utente.class)
                    .setParameter("cf", cf)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}