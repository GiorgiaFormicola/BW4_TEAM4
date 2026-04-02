package GiorgiaFormicola;

import GiorgiaFormicola.dao.*;
import GiorgiaFormicola.entities.DistributoriAutomatici;
import GiorgiaFormicola.entities.PuntiEmissione;
import GiorgiaFormicola.entities.Tratta;
import GiorgiaFormicola.entities.Utente;
import GiorgiaFormicola.enums.TipoDiUtente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalTime;

public class InserimentoDatiNelDb {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("public_transport_company_pu");

    public static void main(String[] args) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        TrattaMezzoDAO trattaMezzoDAO = new TrattaMezzoDAO(entityManager);

//        -------------------- UTENTE --------------------
        UtenteDAO utenteDAO = new UtenteDAO(entityManager);
        Utente utente = new Utente(TipoDiUtente.UTENTE_SEMPLICE, "abcde");
//        try {
//            utenteDAO.save(utente);
//        } catch (UserAlreadyEnteredException e){
//            System.out.println(e.getMessage());
//        }

//        -------------------- TESSERA --------------------
        TessereDAO tessereDAO = new TessereDAO(entityManager);
        /*Tessera tessera = new Tessera(3l);*/
//        try {
//            tessereDAO.deleteTesseraById(UUID.fromString("0f929dd1-9666-484f-b52e-c01d78f329f6"));
//        }catch (NotFoundException e){
//            System.out.println(e.getMessage());
//        }

//        tessereDAO.createNuovaTessera(3, "abcde");

//        Tessera tesseraDB = tessereDAO.findTesseraById(UUID.fromString("e6cb6ce4-2621-4de2-96d1-71120e80e067"));
//        System.out.println(tessera);
//        System.out.println(tesseraDB);

//        -------------------- PUNTI EMISSIONE --------------------
        DistributoriAutomatici distributoriAutomatici = new DistributoriAutomatici(false);
        PuntiEmissioneDAO puntiEmissioneDAO = new PuntiEmissioneDAO(entityManager);

        PuntiEmissione puntoEmissioneDb = puntiEmissioneDAO.getPuntoEmissioneById("25dab02d-f0aa-4f87-bdfd-2134dafd9302");
//        puntiEmissioneDAO.savePuntoEmissione(distributoriAutomatici);

//        -------------------- EMISSIONI --------------------
        EmissioniDAO emissioniDAO = new EmissioniDAO(entityManager);
//        Emissione abbonamento = new Abbonamento(puntoEmissioneDb, tesseraDB, TipoAbbonamento.SETTIMANALE);
//        try {
//            emissioniDAO.save(abbonamento);
//        }catch (NotFoundException e){
//            System.out.println(e.getMessage());
//        }

//        -------------------- MEZZI DI TRASPORTO --------------------
        MezziDiTrasportoDAO mezziDiTrasportoDAO = new MezziDiTrasportoDAO(entityManager);
//        mezziDiTrasportoDAO.addMezzo("tram");

//        -------------------- TRATTA --------------------
        TrattaDAO trattaDAO = new TrattaDAO(entityManager);
        Tratta tratta = new Tratta("Milano", "Bologna", LocalTime.now());
        trattaDAO.save(tratta);


//        -------------------- TESSERA --------------------

    }
}
