package GiorgiaFormicola.dao;

import GiorgiaFormicola.entities.OperativitàMezzo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.UUID;

public class OperativitàMezziDAO {
    private final EntityManager entityManager;

    public OperativitàMezziDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(OperativitàMezzo nuovaOperatività) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(nuovaOperatività);
        transaction.commit();
        System.out.println("Operatività del mezzo" + nuovaOperatività.getMezzo().getId() + "impostata a " + nuovaOperatività.getClass().getSimpleName().toUpperCase());
    }

    public OperativitàMezzo findById(String idOperatività) {
        OperativitàMezzo found = entityManager.find(OperativitàMezzo.class, UUID.fromString(idOperatività));
        if (found == null)
            throw new RuntimeException("Operatività non trovata");//TODO: aggiungi eccezione not found
        else return found;
    }

    public void delete(String idOperatività) {
        OperativitàMezzo found = this.findById(idOperatività);
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(found);
        transaction.commit();
        System.out.println(found.getClass().getSimpleName() + " " + idOperatività + " correttamente eliminata dallo storico.");
    }
}
