package GiorgiaFormicola;

import GiorgiaFormicola.dao.*;
import GiorgiaFormicola.entities.*;
import GiorgiaFormicola.enums.TipoDiUtente;
import GiorgiaFormicola.exceptions.NotFoundException;
import GiorgiaFormicola.exceptions.TesseraGiaEsistente;
import GiorgiaFormicola.exceptions.UserAlreadyEnteredException;
import GiorgiaFormicola.exceptions.UtenteAssociatoATessera;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class InserimentoDatiNelDb {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("public_transport_company_pu");

    public static void main(String[] args) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        TrattaMezzoDAO trattaMezzoDAO = new TrattaMezzoDAO(entityManager);

//        -------------------- UTENTE --------------------
        UtenteDAO utenteDAO = new UtenteDAO(entityManager);
        Utente utente = new Utente(TipoDiUtente.UTENTE_SEMPLICE, "abcde");
        Utente utente2 = new Utente(TipoDiUtente.AMMINISTRATORE, "abcde12345");
        Utente utente3 = new Utente(TipoDiUtente.AMMINISTRATORE, "abcdef123456");
        Utente utente4 = new Utente(TipoDiUtente.UTENTE_SEMPLICE, "abcdefg1234567");
        Utente utente5 = new Utente(TipoDiUtente.UTENTE_SEMPLICE, "abc1234");

        Utente utenteFromDB = utenteDAO.findById(UUID.fromString("0cc02bc2-c6e1-447d-a9d6-dc41c90d75df"));
        Utente utenteFromDB2 = utenteDAO.findById(UUID.fromString("542ff36b-c323-4446-a43f-2dd088e5dbb3"));
        Utente utenteFromDB3 = utenteDAO.findById(UUID.fromString("7299ed20-2710-43b2-8f73-db5bbc87edfd"));
        Utente utenteFromDB4 = utenteDAO.findById(UUID.fromString("a89909c2-e1c1-4057-aab5-953fa3b16a60"));
//        try {
//            utenteDAO.save(utente);
//            utenteDAO.save(utente2);
//            utenteDAO.save(utente3);
//            utenteDAO.save(utente4);
//            utenteDAO.save(utente5);
//        } catch (UserAlreadyEnteredException e){
//            System.out.println(e.getMessage());
//        }

//        -------------------- TESSERA --------------------
        TessereDAO tessereDAO = new TessereDAO(entityManager);
        /*Tessera tessera = new Tessera(3l);*/
        Tessera tessera = new Tessera(4l, utenteFromDB);
//        try {
//            tessereDAO.deleteTesseraById(UUID.fromString("1ac135e7-c52d-4a81-afb8-6fd21fa9e4ce"));
//            tessereDAO.deleteTesseraById(UUID.fromString("cf1572b7-78cf-44b8-93a6-d41689e5f3da"));
//            tessereDAO.deleteTesseraById(UUID.fromString("e2039265-a918-40c8-b18d-98acafc917ca"));
//        }catch (NotFoundException e){
//            System.out.println(e.getMessage());
//        }

//        Tessera tesseraDB = tessereDAO.findTesseraById(UUID.fromString("3f8c12d0-e4c2-4fd0-9390-d814fbb31b61"));
//        Tessera tesseraDB2 = tessereDAO.findTesseraById(UUID.fromString("b441de56-1ede-443b-825c-88e99f735e9a"));

        try {
//        tessereDAO.createNuovaTessera(1, utenteFromDB);
//        tessereDAO.createNuovaTessera(2, utenteFromDB2);
//        tessereDAO.createNuovaTessera(3, utenteFromDB3);
//        tessereDAO.createNuovaTessera(4, utenteFromDB4);
        } catch (UtenteAssociatoATessera e){
            System.out.println(e.getMessage());
        }

//        -------------------- PUNTI EMISSIONE --------------------
        DistributoriAutomatici distributoriAutomatici = new DistributoriAutomatici(false);
        RivenditoriAutorizzati rivenditoriAutorizzati = new RivenditoriAutorizzati();
        PuntiEmissioneDAO puntiEmissioneDAO = new PuntiEmissioneDAO(entityManager);
        PuntiEmissione distributoreAutomatico2 = new DistributoriAutomatici(true);

        PuntiEmissione puntoEmissioneDB = puntiEmissioneDAO.getPuntoEmissioneById("559d2102-ae07-45f2-b62e-bfcb8eca48fe");
//        PuntiEmissione puntoEmissioneDB2 = puntiEmissioneDAO.getPuntoEmissioneById(UUID.fromString("5ac5fea5-ad48-4895-a917-7c75634d91a5"));

        PuntiEmissione puntoEmissioneDb = puntiEmissioneDAO.getPuntoEmissioneById("25dab02d-f0aa-4f87-bdfd-2134dafd9302");
//        puntiEmissioneDAO.savePuntoEmissione(distributoriAutomatici);
//        puntiEmissioneDAO.savePuntoEmissione(rivenditoriAutorizzati);


//        -------------------- EMISSIONI --------------------
        EmissioniDAO emissioniDAO = new EmissioniDAO(entityManager);
//        Emissione abbonamento = new Abbonamento(puntoEmissioneDB, tesseraDB, TipoAbbonamento.SETTIMANALE);
//        Emissione abbonamento2 = new Abbonamento(puntoEmissioneDB2, tesseraDB2, TipoAbbonamento.MENSILE);
//        Emissione abbonamento3 = new Abbonamento(puntoEmissioneDB, tesseraDB, TipoAbbonamento.MENSILE);

        Biglietto biglietto = new Biglietto(puntoEmissioneDB);

        try {
            emissioniDAO.save(biglietto);
        }catch (NotFoundException e){
            System.out.println(e.getMessage());
        }

//        -------------------- MEZZI DI TRASPORTO --------------------
        MezziDiTrasportoDAO mezziDiTrasportoDAO = new MezziDiTrasportoDAO(entityManager);
//        mezziDiTrasportoDAO.addMezzo("tram");
//        mezziDiTrasportoDAO.addMezzo("autobus");
//        mezziDiTrasportoDAO.addMezzo("autobus");
//        mezziDiTrasportoDAO.addMezzo("tram");

//        -------------------- TRATTA --------------------
        TrattaDAO trattaDAO = new TrattaDAO(entityManager);
        Tratta tratta = new Tratta("Milano", "Bologna", LocalTime.now());
        Tratta tratta2 = new Tratta("Pescara", "Brindisi", LocalTime.now());
        Tratta tratta3 = new Tratta("Foggia", "Catania", LocalTime.now());

        trattaDAO.findByPartenzaECapolinea("Milano", "Bologna");
//        trattaDAO.save(tratta);
//        trattaDAO.save(tratta2);
//        trattaDAO.save(tratta3);



//        -------------------- TRATTE-MEZZI --------------------
        TrattaMezzoDAO trattaMezzoDAO1 = new TrattaMezzoDAO(entityManager);
        MezzoDiTrasporto mezzoDiTrasporto = new Autobus();
//        Tratta trattaFromDB = trattaDAO.getTrattaById(UUID.fromString("2104d876-babc-400b-b843-7406216d1f1d"));
//        Tratta trattaFromDB2 = trattaDAO.getTrattaById(UUID.fromString("fc33338e-b072-4b99-a265-19caf7a5cde4"));
//        Tratta trattaFromDB3 = trattaDAO.getTrattaById(UUID.fromString("fecc660d-f8d7-433c-8646-ebdb64e8d42e"));


//        MezzoDiTrasporto mezzoFromDB = mezziDiTrasportoDAO.findById("3d8d21a3-e6a1-49d9-a37b-b183ef2205e7");
//        MezzoDiTrasporto mezzoFromDB2 = mezziDiTrasportoDAO.findById("42cd0f7f-d76f-446f-b780-570d4947d052");
//        MezzoDiTrasporto mezzoFromDB3 = mezziDiTrasportoDAO.findById("50e224a1-6757-40b2-968c-46af82323f1a");

//        TrattaMezzo trattaMezzo = new TrattaMezzo(mezzoFromDB, trattaFromDB, LocalTime.now(), LocalDate.now());
//        TrattaMezzo trattaMezzo2 = new TrattaMezzo(mezzoFromDB2, trattaFromDB2, LocalTime.now(), LocalDate.now());
//        TrattaMezzo trattaMezzo3 = new TrattaMezzo(mezzoFromDB3, trattaFromDB3, LocalTime.now(), LocalDate.now());

//        trattaMezzoDAO.registraPercorrenza(trattaMezzo);
//        trattaMezzoDAO.registraPercorrenza(trattaMezzo2);
//        trattaMezzoDAO.registraPercorrenza(trattaMezzo3);

//        -------------------- OPERATIVITA MEZZI --------------------
        OperativitàMezziDAO operativitàMezziDAO = new OperativitàMezziDAO(entityManager);
        MezzoDiTrasporto mezzoDiTrasporto1 = new Autobus();
        OperativitàMezzo operativitàMezzo = new Servizio(mezzoDiTrasporto1);

//        OperativitàMezzo operativitàMezzoFromDB = operativitàMezziDAO.findById("13a5e14f-32c1-461c-8507-f2365fc196c5");
        OperativitàMezzo operativitàMezzoFromDB1 = operativitàMezziDAO.findById("609dc661-8cc3-4a98-9c3d-b3c293c057e2");

//        operativitàMezziDAO.delete("13a5e14f-32c1-461c-8507-f2365fc196c5");

//        operativitàMezziDAO.save(operativitàMezzoFromDB1);


    }
}
