package GiorgiaFormicola.dao;

import GiorgiaFormicola.entities.Manutenzione;
import GiorgiaFormicola.entities.MezzoDiTrasporto;
import GiorgiaFormicola.entities.OperativitàMezzo;
import GiorgiaFormicola.entities.Servizio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

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

    public void aggiornaFineUltimaOperatività(MezzoDiTrasporto mezzo) {
        Query updateQuery = entityManager.createQuery("UPDATE OperativitàMezzo o SET o.dataFine = CURRENT_DATE WHERE o.mezzo.id = mezzo.id AND o.dataFine IS NULL ");
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        updateQuery.executeUpdate();
        transaction.commit();
        System.out.println("Data di fine " + (mezzo.isInServizio() ? "servizio" : "manutenzione") + " aggiornata");
    }

    public void iniziaPrimoServizio(MezzoDiTrasporto mezzo) {
        Servizio primoServizio = new Servizio(mezzo);
        
    }

    public void aggiungiOperatività(MezzoDiTrasporto mezzo, String tipo, String descrizione) {
        OperativitàMezzo nuovaOperatività;
        if (!tipo.equals("servizio") && !tipo.equals("manutenzione")) {
            System.out.println(("Tipo di operatività non valida"));
        } else {
            if (mezzo.isInServizio() && tipo.equals("servizio") || !mezzo.isInServizio() && tipo.equals("manutenzione")) {
                System.out.println("Il mezzo di trasporto " + mezzo.getId() + " è già in " + tipo);
            } else {
                if (mezzo.isInServizio() && tipo.equals("manutenzione")) {
                    nuovaOperatività = new Manutenzione(mezzo, descrizione);
                } else {
                    nuovaOperatività = new Servizio(mezzo);
                }
                this.save(nuovaOperatività);
            }
        }


    }
}
