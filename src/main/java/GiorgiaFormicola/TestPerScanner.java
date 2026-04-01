package GiorgiaFormicola;

import GiorgiaFormicola.dao.EmissioniDAO;
import GiorgiaFormicola.dao.MezziDiTrasportoDAO;
import GiorgiaFormicola.dao.PuntiEmissioneDAO;
import GiorgiaFormicola.dao.UtenteDAO;
import GiorgiaFormicola.entities.Biglietto;
import GiorgiaFormicola.entities.MezzoDiTrasporto;
import GiorgiaFormicola.entities.PuntiEmissione;
import GiorgiaFormicola.entities.Utente;
import GiorgiaFormicola.enums.TipoDiUtente;
import GiorgiaFormicola.exceptions.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Scanner;

public class TestPerScanner {
    private static final Scanner scanner = new Scanner(System.in);
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("public_transport_company_pu");
    private static final EntityManager entityManager = entityManagerFactory.createEntityManager();
    private static final EmissioniDAO emissioniDAO = new EmissioniDAO(entityManager);
    private static final PuntiEmissioneDAO puntiDAO = new PuntiEmissioneDAO(entityManager);
    private static final MezziDiTrasportoDAO mezziDAO = new MezziDiTrasportoDAO(entityManager);
    private static final UtenteDAO utentiDAO = new UtenteDAO(entityManager);
    /*private static final TesseraDAO tessereDAO = new TesseraDAO(entityManager);*/  //TODO

    public static void main(String[] args) {
        //SCEGLIERE TIPO DI SIMULAZIONE
        int simulazione;

        while (true) {
            System.out.println("SCEGLIERE IL TIPO DI SIMULAZIONE DA EFFETTUARE DIGITANDO IL RISPETTIVO NUMERO");
            System.out.println("1.PUNTO DI EMISSIONE");
            System.out.println("2.MEZZO DI TRASPORTO");
            System.out.println("3.PORTALE");
            try {
                simulazione = Integer.parseInt(scanner.nextLine());
                if (simulazione < 1 || simulazione > 3)
                    System.err.println("\nERRORE: Simulazione selezionata non valida, riprovare! \n");
                else break;
            } catch (NumberFormatException e) {
                System.err.println("\nERRORE: Simulazione selezionata non valida, riprovare! \n");
            }
        }

        System.out.print("\nSIMULAZIONE SCELTA: " + switch (simulazione) {
            case 1 -> "PUNTO DI EMISSIONE";
            case 2 -> "MEZZO DI TRASPORTO";
            case 3 -> "PORTALE";
            default -> "SIMULAZIONE NON VALIDA";
        });
        System.out.println();


        // SCEGLIERE TIPO DI INTERAZIONE
        int interazione;

        while (true) {
            System.out.println("SCEGLIERE IL TIPO DI INTERAZIONE DA EFFETTUARE DIGITANDO IL RISPETTIVO NUMERO");
            System.out.println("1.CON PERSONA NON REGISTRATA");
            System.out.println("2.CON UTENTE");
            System.out.println("3.CON AMMINISTRATORE");
            try {
                interazione = Integer.parseInt(scanner.nextLine());
                if (interazione < 1 || interazione > 3)
                    System.err.println("\nERRORE: Interazione selezionata non valida, riprovare! \n");
                else break;
            } catch (NumberFormatException e) {
                System.err.println("\nERRORE: Interazione selezionata non valida, riprovare! \n");
            }
        }

        System.out.print("\nINTERAZIONE SCELTA: " + switch (interazione) {
            case 1 -> "CON PERSONA NON REGISTRATA";
            case 2 -> "CON UTENTE";
            case 3 -> "CON AMMINISTRATORE";
            default -> "INTERAZIONE NON VALIDA";
        });
        System.out.println("\n");

        //GESTIONE PUNTO DI EMISSIONE + PERSONA NON REGISTRATA
        if (interazione == 1 && simulazione == 1) {
            while (true) {
                int operazione;
                System.out.println("SCEGLIERE IL TIPO DI OPERAZIONE DA EFFETTUARE DIGITANDO IL RISPETTIVO NUMERO");
                System.out.println("1.ACQUISTA BIGLIETTO");
                System.out.println("0.ESCI DALLA SIMULAZIONE");
                try {
                    operazione = Integer.parseInt(scanner.nextLine());
                    if (operazione == 0) break;
                    if (operazione != 1)
                        System.err.println("\nERRORE: Operazione selezionata non valida, riprovare!\n");
                    else {
                        System.out.println("\nInserire l'id del punto di emissione in cui ti trovi per acquistare il biglietto");
                        String idPuntoEmissione = scanner.nextLine();
                        PuntiEmissione puntoEmissione;
                        try {
                            puntoEmissione = puntiDAO.getPuntoEmissioneById(idPuntoEmissione);
                            emissioniDAO.acquistaBiglietto(puntoEmissione);
                        } catch (NotFoundException | IllegalArgumentException | PuntoDiEmissioneNonAttivoException e) {
                            if (e instanceof IllegalArgumentException)
                                System.err.println("\nERRORE: Formato ID non valido\n");
                            else System.err.println("\nERRORE:" + e.getMessage() + "\n");
                        }
                    }
                } catch (NumberFormatException e) {
                    System.err.println("\nERRORE: Operazione selezionata non valida, riprovare! \n");
                }

            }
        }

        //GESTIONE MEZZO DI TRAPORTO + PERSONA NON REGISTRATA
        //TODO: check eccezione capienza massima raggiunta
        //TODO: aggiustare doppia vidimazione valida nella stessa simulazione
        if (interazione == 1 && simulazione == 2) {
            while (true) {
                int operazione;
                System.out.println("\nSCEGLIERE IL TIPO DI OPERAZIONE DA EFFETTUARE DIGITANDO IL RISPETTIVO NUMERO");
                System.out.println("1.VIDIMA BIGLIETTO");
                System.out.println("0.ESCI DALLA SIMULAZIONE");
                try {
                    operazione = Integer.parseInt(scanner.nextLine());
                    if (operazione == 0) break;
                    if (operazione != 1)
                        System.err.println("\nERRORE: Operazione selezionata non valida, riprovare!\n");
                    else {
                        System.out.println("\nInserire l'id del mezzo su cui vuoi salire");
                        String idMezzo = scanner.nextLine();
                        MezzoDiTrasporto mezzo;
                        try {
                            mezzo = mezziDAO.findById(idMezzo);
                            mezziDAO.controllaSeInServizio(mezzo);
                            System.out.println("\nInserire l'id del biglietto da vidimare");
                            String idBiglietto = scanner.nextLine();
                            Biglietto biglietto;
                            biglietto = emissioniDAO.findBigliettoById(idBiglietto);
                            emissioniDAO.utilizzaBiglietto(biglietto, mezzo);
                        } catch (
                                NotFoundException |
                                IllegalArgumentException |
                                MezzoNonInServizioException |
                                BigliettoGiàVidimatoException |
                                CapienzaMassimaRaggiuntaException e) {
                            if (e instanceof IllegalArgumentException)
                                System.err.println("\nERRORE: Formato ID non valido\n");
                            else System.err.println("\nERRORE:" + e.getMessage() + "\n");
                        }
                    }
                } catch (NumberFormatException e) {
                    System.err.println("\nERRORE: Operazione selezionata non valida, riprovare! \n");
                }
            }
        }

        //GESTIONE PORTALE + PERSONA NON REGISTRATA
        //TODO: controllo su formato codice fiscale
        //TODO: messaggio all'utente se codice fiscale già associato ad una persona nel DB
        if (interazione == 1 && simulazione == 3) {
            while (true) {
                int operazione;
                System.out.println("\nSCEGLIERE IL TIPO DI OPERAZIONE DA EFFETTUARE DIGITANDO IL RISPETTIVO NUMERO");
                System.out.println("1.REGISTRATI AL PORTALE");
                System.out.println("0.ESCI DALLA SIMULAZIONE");
                try {
                    operazione = Integer.parseInt(scanner.nextLine());
                    if (operazione == 0) break;
                    if (operazione != 1)
                        System.err.println("\nERRORE: Operazione selezionata non valida, riprovare!\n");
                    else {
                        System.out.println("\nInserire il proprio codice fiscale");
                        String codiceFiscale = scanner.nextLine();
                        Utente utente = new Utente(TipoDiUtente.UTENTE_SEMPLICE, codiceFiscale);
                        try {
                            utentiDAO.save(utente);
                        } catch (UtenteGiàPresenteNelDBException e) {
                            System.err.println("ERRORE: " + e.getMessage());
                        }
                    }
                } catch (NumberFormatException e) {
                    System.err.println("\nERRORE: Operazione selezionata non valida, riprovare! \n");
                }
            }
        }

        //GESTIONE PUNTO DI EMISSIONE E UTENTE
        //TODO: test
        //TODO: getTesseraByNumeroTessera
        /*if (interazione == 2 && simulazione == 1) {
            while (true) {
                int operazione;
                System.out.println("SCEGLIERE IL TIPO DI OPERAZIONE DA EFFETTUARE DIGITANDO IL RISPETTIVO NUMERO");
                System.out.println("1.ACQUISTA ABBONAMENTO");
                System.out.println("2.RINNOVA ABBONAMENTO");
                System.out.println("3.MODIFICA E RINNOVA ABBONAMENTO");
                System.out.println("0.ESCI DALLA SIMULAZIONE");
                try {
                    operazione = Integer.parseInt(scanner.nextLine());
                    if (operazione == 0) break;
                    if (operazione < 1 || operazione > 3)
                        System.err.println("\nERRORE: Operazione selezionata non valida, riprovare!\n");
                    else {
                        System.out.println("\nInserire l'id del punto di emissione in cui ti trovi");
                        String idPuntoEmissione = scanner.nextLine();
                        PuntiEmissione puntoEmissione;
                        Tessera tessera;
                        try {
                            puntoEmissione = puntiDAO.getPuntoEmissioneById(idPuntoEmissione);
                            System.out.println("\nInserire il proprio numero di tessera");
                            String numeroTessera = scanner.nextLine();
                            *//*tessera = tessereDAO.getTesseraByNumeroTessera(numeroTessera);*//* //TODO
                            if (operazione == 1) {
                                System.out.println("\nScegliere il tipo di abbonamento");
                                System.out.println("1.SETTIMANALE");
                                System.out.println("2.MENSILE");
                                int abbonamento = Integer.parseInt(scanner.nextLine());
                                if (abbonamento < 1 || abbonamento > 2)
                                    System.err.println("\nERRORE: Tipo di abbonamento selezionato non valido, riprovare!\n");
                                else {
                                    TipoAbbonamento tipoAbbonamento;
                                    if (abbonamento == 1) tipoAbbonamento = TipoAbbonamento.SETTIMANALE;
                                    else tipoAbbonamento = TipoAbbonamento.MENSILE;
                                    *//*emissioniDAO.acquistaAbbonamento(puntoEmissione, tessera, tipoAbbonamento);*//* //TODO
                                }
                            } else if (operazione == 2) {
                                *//*emissioniDAO.rinnovaAbbonamento(puntoEmissione, tessera);*//*
                            } else {
                                *//*emissioniDAO.rinnovaEModificaAbbonamento(puntoEmissione, tessera);*//*
                            }
                        } catch (NotFoundException | IllegalArgumentException | PuntoDiEmissioneNonAttivoException |
                                 TesseraScadutaException | AbbonamentoTesseraNonTrovatoException |
                                 AbbonamentoAncoraValidoException e) {
                            if (e instanceof IllegalArgumentException)
                                System.err.println("\nERRORE: Formato ID non valido\n");
                            else System.err.println("\nERRORE:" + e.getMessage() + "\n");
                        }
                    }
                } catch (NumberFormatException e) {
                    System.err.println("\nERRORE: Operazione selezionata non valida, riprovare! \n");
                }
            }
        }*/

        //GESTIONE MEZZO DI TRASPORTO E UTENTE
        //TODO: test
        //TODO: getTesseraByNumeroTessera
        /*if (interazione == 2 && simulazione == 2) {
            while (true) {
                int operazione;
                System.out.println("\nSCEGLIERE IL TIPO DI OPERAZIONE DA EFFETTUARE DIGITANDO IL RISPETTIVO NUMERO");
                System.out.println("1.UTILIZZA ABBONAMENTO");
                System.out.println("0.ESCI DALLA SIMULAZIONE");
                try {
                    operazione = Integer.parseInt(scanner.nextLine());
                    if (operazione == 0) break;
                    if (operazione != 1)
                        System.err.println("\nERRORE: Operazione selezionata non valida, riprovare!\n");
                    else {
                        System.out.println("\nInserire l'id del mezzo su cui vuoi salire");
                        String idMezzo = scanner.nextLine();
                        MezzoDiTrasporto mezzo;
                        try {
                            mezzo = mezziDAO.findById(idMezzo);
                            mezziDAO.controllaSeInServizio(mezzo);
                            System.out.println("\nInserire il proprio numero di tessera");
                            String numeroTessera = scanner.nextLine();
                            *//*tessera = tessereDAO.getTesseraByNumeroTessera(numeroTessera);*//* //TODO
         *//*emissioniDAO.utilizzaAbbonamento(tessera, mezzo);*//*
                        } catch (
                                NotFoundException |
                                IllegalArgumentException |
                                MezzoNonInServizioException |
                                TesseraScadutaException |
                                AbbonamentoTesseraNonTrovatoException |
                                AbbonamentoScadutoException e) {
                            if (e instanceof IllegalArgumentException)
                                System.err.println("\nERRORE: Formato ID non valido\n");
                            else System.err.println("\nERRORE:" + e.getMessage() + "\n");
                        }
                    }
                } catch (NumberFormatException e) {
                    System.err.println("\nERRORE: Operazione selezionata non valida, riprovare! \n");
                }
            }
        }*/

        //GESTIONE PORTALE E UTENTE
        if (interazione == 2 && simulazione == 3) {
            while (true) {
                System.out.println("\nPer accedere al portale inserire il proprio codice fiscale");
                String codiceFiscale = scanner.nextLine();
                try {
                    Utente utente = utentiDAO.findByCodiceFiscale(codiceFiscale);
                    int operazione;
                    System.out.println("\nSCEGLIERE IL TIPO DI OPERAZIONE DA EFFETTUARE DIGITANDO IL RISPETTIVO NUMERO");
                    System.out.println("1.CREA TESSERA");
                    System.out.println("2.RINNOVA TESSERA");
                    System.out.println("3.CONTROLLA VALIDITA' TESSERA");
                    System.out.println("4.CONTROLLA VALIDITA' ABBONAMENTO");
                    System.out.println("0.ESCI DALLA SIMULAZIONE");
                    operazione = Integer.parseInt(scanner.nextLine());
                    if (operazione == 0) break;
                    if (operazione < 1 || operazione > 4)
                        System.err.println("\nERRORE: Operazione selezionata non valida, riprovare!\n");
                    else {
                        if (operazione == 1) {
                            //CREAZIONE NUOVA TESSERA (NUMERO SERIALE E PASSA UTENTE, CHECK SE CODICE FISCALE GIA ASSOCIATO A TESSERA
                        } else if (operazione == 2) {
                            //TROVA TESSERA IN BASE A UTENTE
                            //RINNOVA TESSERA (PASSA TESSERA, SE NO SCADUTA ECCEZIONE SE NO UPDATE DATA SCADENZA PIù UN ANNO DA ORA)
                        } else if (operazione == 3) {
                            //TROVA TESSERA IN BASE A UTENTE
                            //CONTROLLA VALIDITA' TESSERA (PASSA TESSERA, STAMPA SE SCADUTA O MENO)
                        } else {
                            //TROVA TESSERA IN BASE A UTENTE
                            /*emissioniDAO.controllaValiditàAbbonamento(tessera);*/
                        }
                    }
                } catch (UtenteNonTrovatoException | NumberFormatException e) {
                    if (e instanceof NumberFormatException)
                        System.err.println("\nERRORE: Operazione selezionata non valida, riprovare! \n");
                    else System.err.println("ERRORE: " + e.getMessage());
                }
            }
        }
    }
}
