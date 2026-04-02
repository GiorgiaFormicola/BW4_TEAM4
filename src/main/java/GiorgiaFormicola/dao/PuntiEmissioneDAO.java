package GiorgiaFormicola.dao;

import GiorgiaFormicola.entities.DistributoriAutomatici;
import GiorgiaFormicola.entities.PuntiEmissione;
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

    public void changeStatoDistributoriAutomatici(UUID id){
        EntityTransaction transaction = entityManager.getTransaction();

        PuntiEmissione found = this.getPuntoEmissioneById(id);

        if (found == null){
            throw new NotFoundException(id);
        }

        if (!(found instanceof DistributoriAutomatici)){
            System.out.println("Il punto di emissione non è un distributore automatico");
            return;
        }

        DistributoriAutomatici distributore = (DistributoriAutomatici) found;

        boolean statoPrecedente = distributore.isAttivo();

        transaction.begin();

        distributore.setAttivo(!statoPrecedente);

        transaction.commit();

        System.out.println("Lo stato del distributore automatico con id " + id + " è stato cambiato da " + statoPrecedente + " a " + distributore.isAttivo());
    }

    public List<PuntiEmissione> findPuntiEmissioneAttivi(){
        TypedQuery<PuntiEmissione> query = entityManager.createQuery("SELECT p FROM PuntiEmissione p WHERE p.attivo = true", PuntiEmissione.class);

        List<PuntiEmissione> listaPuntiEmissioneAttivi = query.getResultList();

        if (listaPuntiEmissioneAttivi.isEmpty()){
            System.out.println("Nessun punto di emissione trovato");
        } else {
            System.out.println("Lista dei punti di emissione attivi: ");
            listaPuntiEmissioneAttivi.forEach(System.out::println);
        }

        return listaPuntiEmissioneAttivi;
    }


}
