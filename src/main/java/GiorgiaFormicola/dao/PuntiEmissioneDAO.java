package GiorgiaFormicola.dao;

import GiorgiaFormicola.entities.DistributoriAutomatici;
import GiorgiaFormicola.entities.PuntiEmissione;
import GiorgiaFormicola.exceptions.CambioStatoPuntoEmissioneException;
import GiorgiaFormicola.exceptions.NessunElementoTrovatoException;
import GiorgiaFormicola.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.UUID;

public class PuntiEmissioneDAO {

    private final EntityManager entityManager;

    public PuntiEmissioneDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void savePuntoEmissione(PuntiEmissione nuovoPuntoEmissione) {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        entityManager.persist(nuovoPuntoEmissione);

        transaction.commit();

        System.out.println("Nuovo punto di emissione con id " + nuovoPuntoEmissione.getId() + " aggiunto correttamente\n");
    }

    public PuntiEmissione getPuntoEmissioneById(String puntoEmissioneId) {
        PuntiEmissione found = entityManager.find(PuntiEmissione.class, UUID.fromString(puntoEmissioneId));
        if (found == null) {
            throw new NotFoundException(puntoEmissioneId);
        } else {
            /* System.out.println("Il punto di emissione con id: " + puntoEmissioneId + " è stato trovato");*/
            return found;
        }
    }

    public void deletePuntoEmissioneById(String puntoEmissioneId) {
        PuntiEmissione found = this.getPuntoEmissioneById(puntoEmissioneId);

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        entityManager.remove(found);

        transaction.commit();

        System.out.println("Il punto di emissione con id " + puntoEmissioneId + " è stato cancellato");
    }

    public void changeStatoDistributore(String idDistributore, Boolean statoNuovo) {
        try {
            EntityTransaction transaction = entityManager.getTransaction();
            DistributoriAutomatici distributore = this.getDistributoreById(idDistributore);
            boolean statoPrecedente = distributore.isAttivo();
            if (statoPrecedente == statoNuovo) throw new CambioStatoPuntoEmissioneException(statoPrecedente);
            else {
                transaction.begin();
                distributore.setAttivo(!statoPrecedente);
                transaction.commit();
                System.out.println("Stato del distributore automatico con id " + idDistributore + " modificato con successo.");
            }
        } catch (NotFoundException e) {
            System.err.println("ERRORE: " + e.getMessage());
        }
    }

    public List<PuntiEmissione> findPuntiEmissioneAttivi() {
        TypedQuery<PuntiEmissione> query = entityManager.createQuery("SELECT p FROM PuntiEmissione p WHERE p.attivo = true OR p.attivo IS NULL", PuntiEmissione.class);

        List<PuntiEmissione> listaPuntiEmissioneAttivi = query.getResultList();

        if (listaPuntiEmissioneAttivi.isEmpty()) {
            throw new NessunElementoTrovatoException();
        } else return listaPuntiEmissioneAttivi;
    }

    public List<PuntiEmissione> findDistributoriAttivi() {
        TypedQuery<PuntiEmissione> query = entityManager.createQuery("SELECT p FROM PuntiEmissione p WHERE p.attivo = true", PuntiEmissione.class);

        List<PuntiEmissione> listaPuntiEmissioneAttivi = query.getResultList();

        if (listaPuntiEmissioneAttivi.isEmpty()) {
            throw new NessunElementoTrovatoException();
        } else return listaPuntiEmissioneAttivi;
    }

    public List<PuntiEmissione> findDistributoriNonAttivi() {
        TypedQuery<PuntiEmissione> query = entityManager.createQuery("SELECT p FROM PuntiEmissione p WHERE p.attivo = false", PuntiEmissione.class);

        List<PuntiEmissione> listaPuntiEmissioneNonAttivi = query.getResultList();

        if (listaPuntiEmissioneNonAttivi.isEmpty()) {
            throw new NessunElementoTrovatoException();
        } else return listaPuntiEmissioneNonAttivi;
    }

    public DistributoriAutomatici getDistributoreById(String distributoreId) {
        DistributoriAutomatici found = entityManager.find(DistributoriAutomatici.class, UUID.fromString(distributoreId));
        if (found == null) {
            throw new NotFoundException(distributoreId);
        } else {
            /* System.out.println("Il punto di emissione con id: " + distributoreId + " è stato trovato");*/
            return found;
        }
    }


}
