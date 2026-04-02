package GiorgiaFormicola;

import GiorgiaFormicola.dao.*;
import GiorgiaFormicola.entities.DistributoriAutomatici;
import GiorgiaFormicola.entities.RivenditoriAutorizzati;
import GiorgiaFormicola.entities.Tessera;
import GiorgiaFormicola.entities.Utente;
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

        /*try {
            mezziDAO.getMezziInManutenzione().forEach(System.out::println);
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
     /*   MezzoDiTrasporto autobus = new Autobus();
        MezzoDiTrasporto tram = new Tram();

        OperativitàMezzo manutenzione = new Manutenzione(autobus, "prova");
        OperativitàMezzo servizio = new Servizio(tram);

        System.out.println(manutenzione);
        System.out.println(servizio);*/


        //TEST DAO PER PUNTI DI EMISSIONE
        PuntiEmissioneDAO puntiEmissioneDAO = new PuntiEmissioneDAO(entityManager);

        DistributoriAutomatici distributoriAutomatici1 = new DistributoriAutomatici(true);
        RivenditoriAutorizzati rivenditoriAutorizzati1 = new RivenditoriAutorizzati();

//        puntiEmissioneDAO.savePuntoEmissione(distributoriAutomatici1);
//        puntiEmissioneDAO.savePuntoEmissione(rivenditoriAutorizzati1);

//        try {
//            puntiEmissioneDAO.getPuntoEmissioneById(UUID.fromString("75b2777a-2e44-4511-b0a6-400ce0819a13"));
//        } catch (NotFoundException e){
//            System.out.println(e.getMessage());
//        }

//        puntiEmissioneDAO.deletePuntoEmissioneById(UUID.fromString("75b2777a-2e44-4511-b0a6-400ce0819a13"));

//        try {
//            puntiEmissioneDAO.changeStatoPuntiEmissione(UUID.fromString("849f959b-3f64-4bd5-b6b3-0f94fffcd20a"));
//        } catch (NotFoundException e){
//            System.out.println(e.getMessage());
//        }


//        puntiEmissioneDAO.findPuntiEmissioneAttivi();

//        try {
//            puntiEmissioneDAO.changeStatoDistributoriAutomatici(UUID.fromString("374b0324-f952-4d41-8e30-fadd6921a6f0"));
//
//        } catch (NotFoundException e){
//            System.out.println(e.getMessage());
//        }

        //TEST DAO Emissioni
        EmissioniDAO emissioniDAO = new EmissioniDAO(entityManager);

        /*DistributoriAutomatici distributoreAutomatico = new DistributoriAutomatici(true);
        RivenditoriAutorizzati rivenditoreAutorizzato = new RivenditoriAutorizzati(true);

        puntiEmissioneDAO.savePuntoEmissione(distributoreAutomatico);
        puntiEmissioneDAO.savePuntoEmissione(rivenditoreAutorizzato);

        emissioniDAO.acquistaBiglietto(distributoreAutomatico);
        mezziDAO.addMezzo("autobus");*/

     /*   Emissione bigliettoFromDB = emissioniDAO.findById("7afe097e-b900-41b0-a57e-64af3b9526e6");
        MezzoDiTrasporto mezzoFromDB = mezziDAO.findById("76246ff1-f859-4320-a520-97073d7ebea6");
        emissioniDAO.utilizzaEmissione(bigliettoFromDB, mezzoFromDB);*/


        //TEST DAO PER TESSERA
      /*  Utente utente = new Utente(TipoDiUtente.UTENTE_SEMPLICE, "abc123");
        Tessera tessera = new Tessera(3L);*/
        TessereDAO tessereDAO = new TessereDAO(entityManager);
        UtenteDAO utenteDAO = new UtenteDAO(entityManager);
//        utenteDAO.save(utente);

//        try {
//            tessereDAO.createNuovaTessera(2, "abc123");
//        } catch (NotFoundUserException e){
//            System.out.println(e.getMessage());
//        }

//        tessereDAO.deleteTesseraById(UUID.fromString("cff31457-dbaf-4e65-b4a0-9f22d7b6a49f"));
//        tessereDAO.rinnovaTessera(1);

        /*  tessereDAO.checkScadenzaTessera(1);*/

        System.out.println("Hello World!");

    /*    puntiEmissioneDAO.savePuntoEmissione(new DistributoriAutomatici(true));
        puntiEmissioneDAO.savePuntoEmissione(new DistributoriAutomatici(false));
        puntiEmissioneDAO.savePuntoEmissione(new RivenditoriAutorizzati(true));

        mezziDAO.addMezzo("autobus");
        mezziDAO.addMezzo("tram");*/
//        mezziDAO.updateOperativitàAttualeMezzo("765997dd-9881-432d-9b93-f336e9e8b164", "manutenzione", "prova update");

        /*Utente amministratore = new Utente(TipoDiUtente.AMMINISTRATORE, "provaadmin");
        utenteDAO.save(amministratore);*/
        Utente utente = utenteDAO.findById("6b239d68-94bc-4d4c-87d7-0c5d6b190a7b");
        tessereDAO.saveTessera(new Tessera(1L, utente));


    }
}

