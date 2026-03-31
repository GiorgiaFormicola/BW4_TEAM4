package GiorgiaFormicola;

import GiorgiaFormicola.dao.MezziDiTrasportoDAO;
import GiorgiaFormicola.dao.PuntiEmissioneDAO;
import GiorgiaFormicola.entities.DistributoriAutomatici;
import GiorgiaFormicola.entities.RivenditoriAutorizzati;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Application {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("public_transport_company_pu");

    public static void main(String[] args) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //TEST DAO PER MEZZI
        MezziDiTrasportoDAO mezziDAO = new MezziDiTrasportoDAO(entityManager);
      /*  try {
            mezziDAO.addMezzo("autobus");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        try {
            mezziDAO.addMezzo("tram");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        try {
            mezziDAO.addMezzo("prova");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }*/

        /*try {
            OperativitàMezzo found = mezziDAO.getOperativitàMezzo("b0dbf5e1-916d-45c1-acb1-33caab6ad4ad");
            System.out.println(found);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }*/

       /* try {
            mezziDAO.aggiornaOperativitàMezzo("b0dbf5e1-916d-45c1-acb1-33caab6ad4ad", "servizio", "");
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }*/

      /*  try {
            mezziDAO.getMezziInServizio().forEach(System.out::println);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }*/

        try {
            mezziDAO.getMezziInManutenzione().forEach(System.out::println);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }



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
     /*   MezzoDiTrasporto autobus = new Autobus();
        MezzoDiTrasporto tram = new Tram();

        OperativitàMezzo manutenzione = new Manutenzione(autobus, "prova");
        OperativitàMezzo servizio = new Servizio(tram);

        System.out.println(manutenzione);
        System.out.println(servizio);*/


        //TEST DAO PER PUNTI DI EMISSIONE
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
        /* puntiEmissioneDAO.changeStatoPuntiEmissione(UUID.fromString("849f959b-3f64-4bd5-b6b3-0f94fffcd20a"));*/
        System.out.println("Hello World!");
    }
}
