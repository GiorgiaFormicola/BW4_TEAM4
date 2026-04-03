package GiorgiaFormicola;

import GiorgiaFormicola.dao.*;
import GiorgiaFormicola.entities.*;
import GiorgiaFormicola.enums.TipoDiUtente;
import GiorgiaFormicola.exceptions.UtenteAssociatoATessera;
import GiorgiaFormicola.exceptions.UtenteGiàPresenteNelDBException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.time.LocalTime;

public class InserimentoDatiNelDb {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("public_transport_company_pu");

    public static void main(String[] args) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        TrattaMezzoDAO trattaMezzoDAO = new TrattaMezzoDAO(entityManager);

        // -------------------- UTENTI --------------------
        UtenteDAO utenteDAO = new UtenteDAO(entityManager);
        Utente utente1 = new Utente(TipoDiUtente.UTENTE_SEMPLICE, "RSSMRA85M01H501Z");
        Utente utente2 = new Utente(TipoDiUtente.AMMINISTRATORE, "BNCGPP92C15F839K");
        Utente utente3 = new Utente(TipoDiUtente.AMMINISTRATORE, "VRDLGI78T22A662X");
        Utente utente4 = new Utente(TipoDiUtente.UTENTE_SEMPLICE, "MNTFRC01L10D325Q");
        Utente utente5 = new Utente(TipoDiUtente.UTENTE_SEMPLICE, "PLZCRL66R45G273W");
        Utente utente6 = new Utente(TipoDiUtente.UTENTE_SEMPLICE, "ABCCRL66R45G272H");
        Utente utente7 = new Utente(TipoDiUtente.UTENTE_SEMPLICE, "CFGCRL66R45G272I");
        Utente utente8 = new Utente(TipoDiUtente.UTENTE_SEMPLICE, "OPDCRL66R45G272D");
        Utente utente9 = new Utente(TipoDiUtente.UTENTE_SEMPLICE, "FUVCRL66R45G272H");
        Utente utente10 = new Utente(TipoDiUtente.UTENTE_SEMPLICE, "LDMCRL66R45G272P");

        try {
            utenteDAO.save(utente1);
            utenteDAO.save(utente2);
            utenteDAO.save(utente3);
            utenteDAO.save(utente4);
            utenteDAO.save(utente5);
            utenteDAO.save(utente6);
            utenteDAO.save(utente7);
            utenteDAO.save(utente8);
            utenteDAO.save(utente9);
            utenteDAO.save(utente10);
        } catch (UtenteGiàPresenteNelDBException e) {
            System.out.println(e.getMessage());
        }


        //UTENTI CON TESSERA SCADUTA
        Utente utenteConTesseraScadutaFromDB1 = utenteDAO.findByCodiceFiscale("PLZCRL66R45G273W");
        Utente utenteConTesseraScadutaFromDB2 = utenteDAO.findByCodiceFiscale("ABCCRL66R45G272H");

        //UTENTI CON TESSERA VALIDA CON ABBONAMENTO
        Utente utenteConTesseraValidaFromDB3 = utenteDAO.findByCodiceFiscale("MNTFRC01L10D325Q");
        Utente utenteConTesseraValidaFromDB4 = utenteDAO.findByCodiceFiscale("RSSMRA85M01H501Z");

        //UTENTI CON TESSERA VALIDA SENZA ABBONAMENTO
        Utente utenteConTesseraValidaFromDB5 = utenteDAO.findByCodiceFiscale("FUVCRL66R45G272H");
        Utente utenteConTesseraValidaFromDB6 = utenteDAO.findByCodiceFiscale("LDMCRL66R45G272P");

        //UTENTI SENZA TESSERA
        Utente utenteSenzaTesseraFromDB1 = utenteDAO.findByCodiceFiscale("CFGCRL66R45G272I");
        Utente utenteSenzaTesseraFromDB2 = utenteDAO.findByCodiceFiscale("OPDCRL66R45G272D");

        //AMMINISTRATORI
        Utente amministratoreFromDB1 = utenteDAO.findByCodiceFiscale("BNCGPP92C15F839K");
        Utente amministratoreFromDB2 = utenteDAO.findByCodiceFiscale("VRDLGI78T22A662X");

        //-------------------- TESSERE --------------------
        TessereDAO tessereDAO = new TessereDAO(entityManager);

        try {
            tessereDAO.createNuovaTessera(1, utenteConTesseraScadutaFromDB1);
            tessereDAO.createNuovaTessera(2, utenteConTesseraScadutaFromDB2);
            tessereDAO.createNuovaTessera(3, utenteConTesseraValidaFromDB3);
            tessereDAO.createNuovaTessera(4, utenteConTesseraValidaFromDB4);
            tessereDAO.createNuovaTessera(5, utenteConTesseraValidaFromDB5);
            tessereDAO.createNuovaTessera(6, utenteConTesseraValidaFromDB6);
        } catch (UtenteAssociatoATessera e) {
            System.out.println(e.getMessage());
        }


        /*Tessera tesseraFromDB1 = tessereDAO.findTesseraByNumeroTessera(1);
        Tessera tesseraFromDB2 = tessereDAO.findTesseraByNumeroTessera(2);*/

        /*tessereDAO.modificaScadenzaTessera(1);
        tessereDAO.modificaScadenzaTessera(2);*/

        //TESSERE SCADUTE
        Tessera tesseraScadutaFromDB1 = tessereDAO.findTesseraByNumeroTessera(1);
        Tessera tesseraScadutaFromDB2 = tessereDAO.findTesseraByNumeroTessera(2);

        //TESSERE VALIDE CON ABBONAMENTO
        Tessera tesseraValidaConAbbonamentoValidoFromDB1 = tessereDAO.findTesseraByNumeroTessera(3);
        Tessera tesseraValidaConAbbonamentoScadutoFromDB2 = tessereDAO.findTesseraByNumeroTessera(4);

        //TESSERE VALIDE SENZA ABBONAMENTO
        Tessera tesseraValidaSenzaAbbonamentoFromDB1 = tessereDAO.findTesseraByNumeroTessera(5);
        Tessera tesseraValidaSenzaAbbonamentoFromDB2 = tessereDAO.findTesseraByNumeroTessera(6);

        //-------------------- PUNTI EMISSIONE --------------------
        PuntiEmissioneDAO puntiEmissioneDAO = new PuntiEmissioneDAO(entityManager);
        /*puntiEmissioneDAO.savePuntoEmissione(new RivenditoriAutorizzati());
        puntiEmissioneDAO.savePuntoEmissione(new RivenditoriAutorizzati());
        puntiEmissioneDAO.savePuntoEmissione(new DistributoriAutomatici(true));
        puntiEmissioneDAO.savePuntoEmissione(new DistributoriAutomatici(true));
        puntiEmissioneDAO.savePuntoEmissione(new DistributoriAutomatici(false));
        puntiEmissioneDAO.savePuntoEmissione(new DistributoriAutomatici(false));*/

        //RIVENDITORI AUTORIZZATI
        PuntiEmissione rivenditoreFromDB1 = puntiEmissioneDAO.getPuntoEmissioneById("15c4f726-4627-4b3c-ba5c-8a72fcbce881");
        PuntiEmissione rivenditoreFromDB2 = puntiEmissioneDAO.getPuntoEmissioneById("dd83094a-6b84-4b85-ab36-f01f2418d23e");

        //DISTRIBUTORI ATTIVI
        PuntiEmissione distributoriAttiviFromDB1 = puntiEmissioneDAO.getPuntoEmissioneById("5e6d6394-2e6b-441e-a693-fceb7e967fa3");
        PuntiEmissione distributoriAttiviFromDB2 = puntiEmissioneDAO.getPuntoEmissioneById("ac95ae7e-bc92-45dc-98bf-1e2b216edab1");

        //DISTRIBUTORI NON ATTIVI
        PuntiEmissione distributoriFuoriServizioFromDB1 = puntiEmissioneDAO.getPuntoEmissioneById("39cebfa3-fe67-46ca-a441-64b5132afcb5");
        PuntiEmissione distributoriFuoriServizioFromDB2 = puntiEmissioneDAO.getPuntoEmissioneById("bedaff1a-4aef-45d3-85ea-db0d7661fab0");


        //-------------------- MEZZI DI TRASPORTO --------------------
        MezziDiTrasportoDAO mezziDiTrasportoDAO = new MezziDiTrasportoDAO(entityManager);
        /*mezziDiTrasportoDAO.addMezzo("tram");
        mezziDiTrasportoDAO.addMezzo("tram");
        mezziDiTrasportoDAO.addMezzo("tram");
        mezziDiTrasportoDAO.addMezzo("autobus");
        mezziDiTrasportoDAO.addMezzo("autobus");
        mezziDiTrasportoDAO.addMezzo("autobus");*/

        //MEZZI IN SERVIZIO
        MezzoDiTrasporto mezzoInServizioFromDB1 = mezziDiTrasportoDAO.findById("23050bd5-a65b-49d0-b265-f384459d93ca");
        MezzoDiTrasporto mezzoInServizioFromDB2 = mezziDiTrasportoDAO.findById("3c1eb68a-2298-4bc7-a03c-9278a7afb7d2");

        //MEZZI FUORI SERVIZIO
        MezzoDiTrasporto mezzoInManutenzioneFromDB1 = mezziDiTrasportoDAO.findById("4225d884-1c6a-4da1-86d5-c81ebe003317");
        MezzoDiTrasporto mezzoInManutenzioneFromDB2 = mezziDiTrasportoDAO.findById("ad883afe-0144-4230-9d56-35235e49024f");

        //MEZZO CON PIU' MANUTENZIONI IN MANUTEZIONE
        MezzoDiTrasporto mezzoConPiùManutenzioniFromDB1 = mezziDiTrasportoDAO.findById("9819677b-f1b1-459f-bf3e-aeec3558e60f");

        //MEZZO CON PIU' MANUTENZIONI IN SERVIZIO
        MezzoDiTrasporto mezzoConPiùManutenzioniFromDB2 = mezziDiTrasportoDAO.findById("bf87348e-d420-45a2-ab96-8bfc9bff03ab");

        //-------------------- OPERATIVITA MEZZI --------------------
        /*mezziDiTrasportoDAO.updateOperativitàAttualeMezzo("4225d884-1c6a-4da1-86d5-c81ebe003317", "manutenzione", "Cambio gomme");
        mezziDiTrasportoDAO.updateOperativitàAttualeMezzo("ad883afe-0144-4230-9d56-35235e49024f", "manutenzione", "Controllo motore");

        mezziDiTrasportoDAO.updateOperativitàAttualeMezzo("9819677b-f1b1-459f-bf3e-aeec3558e60f", "manutenzione", "Cambio olio");
        mezziDiTrasportoDAO.updateOperativitàAttualeMezzo("9819677b-f1b1-459f-bf3e-aeec3558e60f", "servizio", "");
        mezziDiTrasportoDAO.updateOperativitàAttualeMezzo("9819677b-f1b1-459f-bf3e-aeec3558e60f", "manutenzione", "Revisione freni");

        mezziDiTrasportoDAO.updateOperativitàAttualeMezzo("bf87348e-d420-45a2-ab96-8bfc9bff03ab", "manutenzione", "Tagliando");
        mezziDiTrasportoDAO.updateOperativitàAttualeMezzo("bf87348e-d420-45a2-ab96-8bfc9bff03ab", "servizio", "");
        mezziDiTrasportoDAO.updateOperativitàAttualeMezzo("bf87348e-d420-45a2-ab96-8bfc9bff03ab", "manutenzione", "Cambio volante");
        mezziDiTrasportoDAO.updateOperativitàAttualeMezzo("bf87348e-d420-45a2-ab96-8bfc9bff03ab", "servizio", "");*/


        // -------------------- EMISSIONI --------------------
        EmissioniDAO emissioniDAO = new EmissioniDAO(entityManager);

        //ABBONAMENTI
        /*emissioniDAO.acquistaAbbonamento(rivenditoreFromDB1, tesseraValidaFromDB1, TipoAbbonamento.SETTIMANALE);
        emissioniDAO.acquistaAbbonamento(rivenditoreFromDB1, tesseraValidaFromDB2, TipoAbbonamento.MENSILE);*/

        //ABBONAMENTO VALIDO
        Emissione abbonamentoValidoFromDB = emissioniDAO.findById("131ecf41-40b3-4f68-95cd-f5e10be0ac0c");

        //ABBONAMENTO SCADUTO
        Emissione abbonamentoScadutoFromDB = emissioniDAO.findById("5e1b078d-174d-486a-9056-f5a08e407abd");

        /*emissioniDAO.cambiaDataEmissioneEScadenzaAbbonamentoSettimanale(abbonamentoScadutoFromDB);*/

        //BIGLIETTI
        /*emissioniDAO.save(new Biglietto(rivenditoreFromDB1));
        emissioniDAO.save(new Biglietto(rivenditoreFromDB1));
        emissioniDAO.save(new Biglietto(rivenditoreFromDB1));
        emissioniDAO.save(new Biglietto(rivenditoreFromDB2));
        emissioniDAO.save(new Biglietto(distributoriAttiviFromDB1));
        emissioniDAO.save(new Biglietto(distributoriAttiviFromDB1));
        emissioniDAO.save(new Biglietto(distributoriAttiviFromDB2));
        emissioniDAO.save(new Biglietto(distributoriAttiviFromDB2));
        emissioniDAO.save(new Biglietto(distributoriAttiviFromDB2));
        emissioniDAO.save(new Biglietto(distributoriAttiviFromDB2));*/

        //BIGLIETTI VALIDI
        Biglietto bigliettoValidoFromDB1 = emissioniDAO.findBigliettoById("0be241e3-d7dd-4c5b-8db1-3f48a8d2819f");
        Biglietto bigliettoValidoFromDB2 = emissioniDAO.findBigliettoById("0f6a4a38-4f5f-4bda-8968-3e8ac18c482a");
        Biglietto bigliettoValidoFromDB3 = emissioniDAO.findBigliettoById("10d4a61e-c4a8-4e15-afb6-2450ce6f94e1");
        Biglietto bigliettoValidoFromDB4 = emissioniDAO.findBigliettoById("120b46f4-0bbc-48e3-9422-f9b4157b15ec");

        //BIGLIETTI VIDIMATI OGGI MEZZO 1   2026-04-03
        Biglietto bigliettoVidimatoFromDB1 = emissioniDAO.findBigliettoById("17498549-843d-4f5f-a196-530328dac227");

        //BIGLIETTI VIDIMATI IERI MEZZO 1   2026-04-02
        Biglietto bigliettoVidimatoFromDB2 = emissioniDAO.findBigliettoById("4511a6cc-b021-4e70-8edf-36be21c83bdb");

        //BIGLIETTI VIDIMATI L'ALTRO IERI MEZZO 1   2026-04-02
        Biglietto bigliettoVidimatoFromDB3 = emissioniDAO.findBigliettoById("6c4cdaeb-189b-4fad-be32-13c98d0d60d7");
        Biglietto bigliettoVidimatoFromDB4 = emissioniDAO.findBigliettoById("bbb8784b-e466-4010-a8e8-952fe936e856");

        //BIGLIETTI VIDIMATI MEZZO 2   2026-04-03
        Biglietto bigliettoVidimatoFromDB5 = emissioniDAO.findBigliettoById("ca915cda-08f9-497d-94bf-b9558047647a");
        Biglietto bigliettoVidimatoFromDB6 = emissioniDAO.findBigliettoById("fb677b59-915d-4788-925d-7a9cef5b0ebf");

        /*emissioniDAO.utilizzaBiglietto(bigliettoVidimatoFromDB1, mezzoInServizioFromDB1);
        emissioniDAO.utilizzaBiglietto(bigliettoVidimatoFromDB2, mezzoInServizioFromDB1);
        emissioniDAO.utilizzaBiglietto(bigliettoVidimatoFromDB3, mezzoInServizioFromDB1);
        emissioniDAO.utilizzaBiglietto(bigliettoVidimatoFromDB4, mezzoInServizioFromDB1);

        emissioniDAO.utilizzaBiglietto(bigliettoVidimatoFromDB5, mezzoInServizioFromDB2);
        emissioniDAO.utilizzaBiglietto(bigliettoVidimatoFromDB6, mezzoInServizioFromDB2);*/

        /*emissioniDAO.cambiaDataVidimazioneBiglietto(bigliettoVidimatoFromDB2, LocalDate.now().minusDays(1));
        emissioniDAO.cambiaDataVidimazioneBiglietto(bigliettoVidimatoFromDB3, LocalDate.now().minusDays(2));
        emissioniDAO.cambiaDataVidimazioneBiglietto(bigliettoVidimatoFromDB4, LocalDate.now().minusDays(2));*/


        // -------------------- TRATTA --------------------
        TrattaDAO trattaDAO = new TrattaDAO(entityManager);

        /*Tratta tratta1 = new Tratta("Milano", "Bologna", LocalTime.of(3, 30));
        Tratta tratta2 = new Tratta("Pescara", "Brindisi", LocalTime.of(5, 10));
        Tratta tratta3 = new Tratta("Foggia", "Catania", LocalTime.of(2, 40));
        Tratta tratta4 = new Tratta("Roma", "Napoli", LocalTime.of(3, 20));

        trattaDAO.save(tratta1);
        trattaDAO.save(tratta2);
        trattaDAO.save(tratta3);
        trattaDAO.save(tratta4);*/

        //TRATTA ASSEGNATA E CONCLUSA DA PIU' MEZZI (ID: 23050bd5-a65b-49d0-b265-f384459d93ca  / 3c1eb68a-2298-4bc7-a03c-9278a7afb7d2)
        Tratta trattaFromDB1 = trattaDAO.getTrattaById("121c4285-734d-4d10-b1a1-522c6298db1b");

        //TRATTE NON ASSEGNATE
        Tratta trattaFromDB2 = trattaDAO.getTrattaById("35739230-e223-49a8-913a-3e7577ccf9a2");
        Tratta trattaFromDB3 = trattaDAO.getTrattaById("3e49f7e9-e639-4234-b67f-ff08dc2eca53");
        Tratta trattaFromDB4 = trattaDAO.getTrattaById("9d881068-e565-48d7-bb94-76978136e0d5");

        // -------------------- ASSEGNAZIONI_TRATTE --------------------
        TrattaMezzoDAO assegnazioniDAO = new TrattaMezzoDAO(entityManager);

        TrattaMezzo assegnazione1 = new TrattaMezzo(mezzoInServizioFromDB1, trattaFromDB1, LocalDate.now());
        TrattaMezzo assegnazione2 = new TrattaMezzo(mezzoInServizioFromDB1, trattaFromDB1, LocalDate.now().minusWeeks(1));
        TrattaMezzo assegnazione3 = new TrattaMezzo(mezzoInServizioFromDB1, trattaFromDB1, LocalDate.now().minusWeeks(2));
        TrattaMezzo assegnazione4 = new TrattaMezzo(mezzoInServizioFromDB1, trattaFromDB1, LocalDate.now().minusWeeks(3));

        TrattaMezzo assegnazione5 = new TrattaMezzo(mezzoInServizioFromDB2, trattaFromDB1, LocalDate.now().minusDays(3));
        TrattaMezzo assegnazione6 = new TrattaMezzo(mezzoInServizioFromDB2, trattaFromDB1, LocalDate.now().minusWeeks(4));


        /*trattaMezzoDAO.registraPercorrenza(assegnazione1);
        trattaMezzoDAO.registraPercorrenza(assegnazione2);
        trattaMezzoDAO.registraPercorrenza(assegnazione3);
        trattaMezzoDAO.registraPercorrenza(assegnazione4);
        trattaMezzoDAO.registraPercorrenza(assegnazione5);
        trattaMezzoDAO.registraPercorrenza(assegnazione6);*/

        TrattaMezzo assegnazioneFromDB1 = trattaMezzoDAO.findById("1ecff081-4cc8-4e29-959a-de7a0fe6dd92");
        TrattaMezzo assegnazioneFromDB2 = trattaMezzoDAO.findById("673f9d42-9ada-4a36-826c-2a4fb237fc3e");
        TrattaMezzo assegnazioneFromDB3 = trattaMezzoDAO.findById("67ea2fe3-b8be-4d94-950f-3b8b036fc201");
        TrattaMezzo assegnazioneFromDB4 = trattaMezzoDAO.findById("81a5f5f5-ee72-49c3-86fc-7bba19077102");
        TrattaMezzo assegnazioneFromDB5 = trattaMezzoDAO.findById("92ee4155-5264-4519-bc64-5935872f644e");
        TrattaMezzo assegnazioneFromDB6 = trattaMezzoDAO.findById("93140c37-960b-42ee-b8d0-c2137a74c076");

        trattaMezzoDAO.aggiornaPercorrenzaEffettivaTrattaAssegnata(LocalTime.of(2, 10), assegnazioneFromDB1);
        trattaMezzoDAO.aggiornaPercorrenzaEffettivaTrattaAssegnata(LocalTime.of(4, 10), assegnazioneFromDB2);
        trattaMezzoDAO.aggiornaPercorrenzaEffettivaTrattaAssegnata(LocalTime.of(2, 50), assegnazioneFromDB3);
        trattaMezzoDAO.aggiornaPercorrenzaEffettivaTrattaAssegnata(LocalTime.of(3, 30), assegnazioneFromDB4);
        trattaMezzoDAO.aggiornaPercorrenzaEffettivaTrattaAssegnata(LocalTime.of(3, 10), assegnazioneFromDB5);
        trattaMezzoDAO.aggiornaPercorrenzaEffettivaTrattaAssegnata(LocalTime.of(5, 0), assegnazioneFromDB6);
    }
}
