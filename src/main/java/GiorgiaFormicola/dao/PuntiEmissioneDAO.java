package GiorgiaFormicola.dao;

import GiorgiaFormicola.entities.PuntiEmissione;
import GiorgiaFormicola.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.UUID;

public class PuntiEmissioneDAO {

    private final EntityManager entityManager;

    public PuntiEmissioneDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void savePuntoEmissione(PuntiEmissione nuovoPuntoEmissione){
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        entityManager.persist(nuovoPuntoEmissione);

        transaction.commit();

        System.out.println("Il punto emissione con id " + nuovoPuntoEmissione.getId() + " è stato creato");
    }

    public PuntiEmissione getPuntoEmissioneById(UUID puntoEmissioneId){
        PuntiEmissione found = entityManager.find(PuntiEmissione.class, puntoEmissioneId);
        if (found == null){
            throw new NotFoundException(puntoEmissioneId);
        } else {
            System.out.println("Il punto di emissione con id: " + puntoEmissioneId + " è stato trovato");
            return found;
        }
    }

    public void deletePuntoEmissioneById(UUID puntoEmissioneId){
        PuntiEmissione found = this.getPuntoEmissioneById(puntoEmissioneId);

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        entityManager.remove(found);

        transaction.commit();

        System.out.println("Il punto di emissione con id " + puntoEmissioneId + " è stato cancellato");
    }

    public void changeStatoPuntiEmissione(UUID id){
        EntityTransaction transaction = entityManager.getTransaction();

//        PuntiEmissione found = this.getPuntoEmissioneById(id);

        transaction.begin();

        entityManager.createQuery("UPDATE PuntiEmissione d SET d.attivo = NOT d.attivo WHERE d.id = :id")
                .setParameter("id", id)
                .executeUpdate();

        transaction.commit();

        System.out.println("Lo stato del distributore automatico con id " + id + " è stato cambiato");
    }


}
