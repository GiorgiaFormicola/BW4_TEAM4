package GiorgiaFormicola.dao;

import GiorgiaFormicola.entities.Tessera;
import GiorgiaFormicola.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.UUID;

public class TessereDAO {

    private final EntityManager entityManager;


    public TessereDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void saveTessera(Tessera nuovaTessera){
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        entityManager.persist(nuovaTessera);

        transaction.commit();

        System.out.println("La tessera " + nuovaTessera + " è stata salvata correttamente");
    }

    public Tessera findTesseraById(UUID tesseraId){
        Tessera found = entityManager.find(Tessera.class, tesseraId);
        if (found == null){
            throw new NotFoundException(tesseraId);
        } else {
            System.out.println("La tessera con id " + tesseraId + " è stata trovata");
            return found;
        }
    }

    public void deleteTesseraById(UUID tesseraId){
        Tessera found = this.findTesseraById(tesseraId);

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        entityManager.remove(found);

        transaction.commit();

        System.out.println("La tessera con id " + tesseraId + " è stata cancellata");
    }


}
