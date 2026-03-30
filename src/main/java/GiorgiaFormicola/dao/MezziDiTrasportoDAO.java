package GiorgiaFormicola.dao;

import GiorgiaFormicola.entities.Autobus;
import GiorgiaFormicola.entities.MezzoDiTrasporto;
import GiorgiaFormicola.entities.Tram;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.UUID;

public class MezziDiTrasportoDAO {
    private final EntityManager entityManager;

    public MezziDiTrasportoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(MezzoDiTrasporto nuovoMezzo) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(nuovoMezzo);
        transaction.commit();
        System.out.println(nuovoMezzo.getClass().getSimpleName() + " " + nuovoMezzo.getId() + " correttamente aggiunto al parco mezzi.");
    }

    public MezzoDiTrasporto findById(String idMezzo) {
        MezzoDiTrasporto found = entityManager.find(MezzoDiTrasporto.class, UUID.fromString(idMezzo));
        if (found == null)
            throw new RuntimeException("Mezzo di trasporto non trovato");//TODO: aggiungi eccezione not found
        else return found;
    }

    public void delete(String idMezzo) {
        MezzoDiTrasporto found = this.findById(idMezzo);
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(found);
        transaction.commit();
        System.out.println(found.getClass().getSimpleName() + " " + idMezzo + " correttamente eliminato dal parco mezzi.");
    }

    public void aggiungiMezzo(String tipo) {
        MezzoDiTrasporto nuovoMezzo;
        if (tipo.equals("autobus")) {
            nuovoMezzo = new Autobus();
        } else if (tipo.equals("tram")) {
            nuovoMezzo = new Tram();
        } else {
            throw new RuntimeException("Tipo di mezzo da aggiungere al parco non valido"); //TODO: aggiungi eccezione mezzo non valido
        }
        this.save(nuovoMezzo);
    }
}
