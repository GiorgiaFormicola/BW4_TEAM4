package GiorgiaFormicola;

import GiorgiaFormicola.dao.MezziDiTrasportoDAO;
import GiorgiaFormicola.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Application {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("public_transport_company_pu");

    public static void main(String[] args) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //TEST DAO PER MEZZI
        MezziDiTrasportoDAO mezziDAO = new MezziDiTrasportoDAO(entityManager);
       /* try {
            mezziDAO.aggiungiMezzo("prova");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }*/

       /* try {
            MezzoDiTrasporto mezzoDaDB = mezziDAO.findById("17c292b6-fe07-4b9f-86cb-d42e88e749ca");
            System.out.println(mezzoDaDB);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }*/

       /* try {
            mezziDAO.delete("17c292b6-fe07-4b9f-86cb-d42e88e749ca");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }*/

        //TEST DAO PER OPERATIVITà
        MezzoDiTrasporto autobus = new Autobus();
        MezzoDiTrasporto tram = new Tram();

        OperativitàMezzo manutenzione = new Manutenzione(autobus, "prova");
        OperativitàMezzo servizio = new Servizio(tram);

        System.out.println(manutenzione);
        System.out.println(servizio);


        System.out.println("Hello World!");
    }
}
