package GiorgiaFormicola.dao;

import GiorgiaFormicola.entities.DistributoriAutomatici;
import GiorgiaFormicola.entities.PuntiEmissione;
import GiorgiaFormicola.entities.RivenditoriAutorizzati;
import GiorgiaFormicola.exceptions.CambioStatoPuntoEmissioneException;
import GiorgiaFormicola.exceptions.NessunElementoTrovatoException;
import GiorgiaFormicola.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
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
                System.out.println("\nStato del distributore automatico con id " + idDistributore + " modificato con successo.");
            }
        } catch (NotFoundException e) {
            System.err.println("ERRORE: " + e.getMessage());
        }
    }

    public List<RivenditoriAutorizzati> ottieniRivenditori() {
        TypedQuery<RivenditoriAutorizzati> query = entityManager.createQuery("SELECT r FROM RivenditoriAutorizzati r", RivenditoriAutorizzati.class);

        List<RivenditoriAutorizzati> listaRivenditoriAutorizzatiAttivi = query.getResultList();

        if (listaRivenditoriAutorizzatiAttivi.isEmpty()) {
            throw new NessunElementoTrovatoException();
        } else return listaRivenditoriAutorizzatiAttivi;
    }

    public List<PuntiEmissione> findPuntiDiEmissioneAttivi() {
        List<RivenditoriAutorizzati> listaRivenditori = new ArrayList<>();
        List<DistributoriAutomatici> listaDistributoriAttivi = new ArrayList<>();
        try {
            listaRivenditori = this.ottieniRivenditori();
        } catch (NessunElementoTrovatoException e) {
            //
        }
        try {
            listaDistributoriAttivi = this.findDistributoriAttivi();
        } catch (NessunElementoTrovatoException e) {
            //
        }
        List<PuntiEmissione> listaPuntiAttivi = new ArrayList<>();
        listaPuntiAttivi.addAll(listaRivenditori);
        listaPuntiAttivi.addAll(listaDistributoriAttivi);
        return listaPuntiAttivi;
    }

    public List<DistributoriAutomatici> findDistributoriAttivi() {
        TypedQuery<DistributoriAutomatici> query = entityManager.createQuery("SELECT d FROM DistributoriAutomatici d WHERE d.attivo = true", DistributoriAutomatici.class);

        List<DistributoriAutomatici> listaDistributoriAutomaticiAttivi = query.getResultList();

        if (listaDistributoriAutomaticiAttivi.isEmpty()) {
            throw new NessunElementoTrovatoException();
        } else return listaDistributoriAutomaticiAttivi;
    }

    public List<DistributoriAutomatici> findDistributoriNonAttivi() {
        TypedQuery<DistributoriAutomatici> query = entityManager.createQuery("SELECT p FROM DistributoriAutomatici p WHERE p.attivo = false", DistributoriAutomatici.class);

        List<DistributoriAutomatici> listaDistributoriAutomaticiNonAttivi = query.getResultList();

        if (listaDistributoriAutomaticiNonAttivi.isEmpty()) {
            throw new NessunElementoTrovatoException();
        } else return listaDistributoriAutomaticiNonAttivi;
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
