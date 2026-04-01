package GiorgiaFormicola.dao;

import GiorgiaFormicola.entities.TrattaMezzo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class TrattaMezzoDAO {
    private EntityManager em;

    public TrattaMezzoDAO(EntityManager em) {
        this.em = em;
    }

    public void registraPercorrenza(TrattaMezzo tm) {
        EntityTransaction tdt = em.getTransaction();
        tdt.begin();
        em.persist(tm);
        tdt.commit();
    }
}