package GiorgiaFormicola;

import GiorgiaFormicola.dao.PuntiEmissioneDAO;
import GiorgiaFormicola.entities.DistributoriAutomatici;
import GiorgiaFormicola.entities.RivenditoriAutorizzati;
import GiorgiaFormicola.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.UUID;

public class Application {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("public_transport_company_pu");

    public static void main(String[] args) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        PuntiEmissioneDAO puntiEmissioneDAO = new PuntiEmissioneDAO(entityManager);

        DistributoriAutomatici distributoriAutomatici1 = new DistributoriAutomatici(true);
        RivenditoriAutorizzati rivenditoriAutorizzati1 = new RivenditoriAutorizzati(true);

//        puntiEmissioneDAO.savePuntoEmissione(distributoriAutomatici1);
//        puntiEmissioneDAO.savePuntoEmissione(rivenditoriAutorizzati1);

//        try {
//            puntiEmissioneDAO.getPuntoEmissioneById(UUID.fromString("75b2777a-2e44-4511-b0a6-400ce0819a13"));
//        } catch (NotFoundException e){
//            System.out.println(e.getMessage());
//        }

//        puntiEmissioneDAO.deletePuntoEmissioneById(UUID.fromString("75b2777a-2e44-4511-b0a6-400ce0819a13"));

        try {
            puntiEmissioneDAO.changeStatoPuntiEmissione(UUID.fromString("849f959b-3f64-4bd5-b6b3-0f94fffcd20a"));
        } catch (NotFoundException e){
            System.out.println(e.getMessage());
        }

        puntiEmissioneDAO.findPuntiEmissioneAttivi();
    }
}
