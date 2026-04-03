package GiorgiaFormicola;

import GiorgiaFormicola.dao.*;
import GiorgiaFormicola.entities.*;
import GiorgiaFormicola.enums.TipoAbbonamento;
import GiorgiaFormicola.enums.TipoDiUtente;
import GiorgiaFormicola.exceptions.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class TestPerScanner {
    private static final Scanner scanner = new Scanner(System.in);
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("public_transport_company_pu");
    private static final EntityManager entityManager = entityManagerFactory.createEntityManager();
    private static final EmissioniDAO emissioniDAO = new EmissioniDAO(entityManager);
    private static final PuntiEmissioneDAO puntiDAO = new PuntiEmissioneDAO(entityManager);
    private static final MezziDiTrasportoDAO mezziDAO = new MezziDiTrasportoDAO(entityManager);
    private static final UtenteDAO utentiDAO = new UtenteDAO(entityManager);
    private static final TessereDAO tessereDAO = new TessereDAO(entityManager);
    private static final TrattaDAO tratteDAO = new TrattaDAO(entityManager);
    private static final TrattaMezzoDAO assegnazioniDAO = new TrattaMezzoDAO(entityManager);

    public static void main(String[] args) {
        // SCELTA TIPO DI UTENTE DA SIMULARE
        int tipoUtente;

        while (true) {
            System.out.println("SCEGLIERE IL TIPO DI UTENTE DA SIMULARE DIGITANDO IL RISPETTIVO NUMERO");
            System.out.println("1.PERSONA NON REGISTRATA");
            System.out.println("2.UTENTE REGISTRATO");
            System.out.println("3.AMMINISTRATORE");
            try {
                tipoUtente = Integer.parseInt(scanner.nextLine());
                if (tipoUtente < 1 || tipoUtente > 3)
                    System.err.println("\nERRORE: Tipo di utente selezionato non valido, riprovare! \n");
                else break;
            } catch (NumberFormatException e) {
                System.err.println("\nERRORE: Tipo di utente selezionato non valido, riprovare! \n");
            }
        }

        System.out.print("\nTIPO DI UTENTE SCELTO: " + switch (tipoUtente) {
            case 1 -> "PERSONA NON REGISTRATA";
            case 2 -> "UTENTE";
            case 3 -> "AMMINISTRATORE";
            default -> "TIPO DI UTENTE NON VALIDO";
        });
        System.out.println("\n");


        //SCELTA TIPO DI SIMULAZIONE
        int simulazione;

        if (tipoUtente == 1 || tipoUtente == 2) {
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
        } else {
            while (true) {
                System.out.println("SCEGLIERE IL TIPO DI SIMULAZIONE DA EFFETTUARE DIGITANDO IL RISPETTIVO NUMERO");
                System.out.println("1.PORTALE");
                try {
                    simulazione = Integer.parseInt(scanner.nextLine()) + 3;
                    if (simulazione != 4)
                        System.err.println("\nERRORE: Simulazione selezionata non valida, riprovare! \n");
                    else break;
                } catch (NumberFormatException e) {
                    System.err.println("\nERRORE: Simulazione selezionata non valida, riprovare! \n");
                }
            }
        }


        System.out.print("\nSIMULAZIONE SCELTA: " + switch (simulazione) {
            case 1 -> "PUNTO DI EMISSIONE";
            case 2 -> "MEZZO DI TRASPORTO";
            case 3, 4 -> "PORTALE";
            default -> "SIMULAZIONE NON VALIDA";
        });
        System.out.println();


        //GESTIONE PUNTO DI EMISSIONE + PERSONA NON REGISTRATA
        if (tipoUtente == 1 && simulazione == 1) {
            while (true) {
                int operazione;
                System.out.println("\nSCEGLIERE IL TIPO DI OPERAZIONE DA EFFETTUARE DIGITANDO IL RISPETTIVO NUMERO");
                System.out.println("1.Acquista biglietto");
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
                            System.out.println("Biglietto acquistato con successo!");
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
        if (tipoUtente == 1 && simulazione == 2) {
            while (true) {
                int operazione;
                System.out.println("\nSCEGLIERE IL TIPO DI OPERAZIONE DA EFFETTUARE DIGITANDO IL RISPETTIVO NUMERO");
                System.out.println("1.Vidima biglietto");
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
        if (tipoUtente == 1 && simulazione == 3) {
            while (true) {
                int operazione;
                System.out.println("\nSCEGLIERE IL TIPO DI OPERAZIONE DA EFFETTUARE DIGITANDO IL RISPETTIVO NUMERO");
                System.out.println("1.Registrati al portale");
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
        //TODO: test con abbonamenti scaduti
        if (tipoUtente == 2 && simulazione == 1) {
            while (true) {
                int operazione;
                System.out.println("SCEGLIERE IL TIPO DI OPERAZIONE DA EFFETTUARE DIGITANDO IL RISPETTIVO NUMERO");
                System.out.println("1.Acquista abbonamento");
                System.out.println("2.Rinnova abbonamento");
                System.out.println("3.Modifica e rinnova abbonamento");
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
                            long numeroTessera = Long.parseLong(scanner.nextLine());
                            tessera = tessereDAO.findTesseraByNumeroTessera(numeroTessera);

                            //OPERAZIONE 1
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
                                    emissioniDAO.acquistaAbbonamento(puntoEmissione, tessera, tipoAbbonamento);
                                }
                            }

                            //OPERAZIONE 2
                            if (operazione == 2) {
                                emissioniDAO.rinnovaAbbonamento(puntoEmissione, tessera);
                            }

                            //OPERAZIONE 3
                            if (operazione == 3) {
                                emissioniDAO.rinnovaEModificaAbbonamento(puntoEmissione, tessera);
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
        }

        //GESTIONE MEZZO DI TRASPORTO E UTENTE
        //TODO: test con abbonamento scaduto
        if (tipoUtente == 2 && simulazione == 2) {
            while (true) {
                int operazione;
                System.out.println("\nSCEGLIERE IL TIPO DI OPERAZIONE DA EFFETTUARE DIGITANDO IL RISPETTIVO NUMERO");
                System.out.println("1.Utilizza abbonamento");
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
                            long numeroTessera = Long.parseLong(scanner.nextLine());
                            Tessera tessera = tessereDAO.findTesseraByNumeroTessera(numeroTessera);
                            emissioniDAO.utilizzaAbbonamento(tessera, mezzo);
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
        }

        //GESTIONE PORTALE E UTENTE
        //TODO: test con tessere scadute
        if (tipoUtente == 2 && simulazione == 3) {
            Utente utente;
            while (true) {
                System.out.println("\nPer accedere al portale inserire il proprio codice fiscale");
                String codiceFiscale = scanner.nextLine();
                try {
                    utente = utentiDAO.findByCodiceFiscale(codiceFiscale);
                    break;
                } catch (UtenteNonTrovatoException e) {
                    System.err.println("ERRORE: " + e.getMessage());
                }
            }


            int operazione;
            while (true) {
                System.out.println("\nSCEGLIERE IL TIPO DI OPERAZIONE DA EFFETTUARE DIGITANDO IL RISPETTIVO NUMERO");
                System.out.println("1.Crea nuova tessera");
                System.out.println("2.Rinnova tessera");
                System.out.println("3.Controlla validità tessera");
                System.out.println("4.Controlla validità abbonamento");
                System.out.println("0.ESCI DALLA SIMULAZIONE");
                try {
                    operazione = Integer.parseInt(scanner.nextLine());
                    if (operazione == 0) break;
                    if (operazione < 1 || operazione > 4)
                        System.err.println("\nERRORE: Operazione selezionata non valida, riprovare!\n");

                    //OPERAZIONE 1
                    if (operazione == 1) {
                        System.out.println("\nInserire il numero di tessera con cui vuoi registrarti");
                        long numeroTessera = Long.parseLong(scanner.nextLine());
                        try {
                            tessereDAO.createNuovaTessera(numeroTessera, utente);
                        } catch (UtenteAssociatoATessera e) {
                            System.err.println("ERRORE: " + e.getMessage());
                        }
                    }

                    //OPERAZIONE 2
                    if (operazione == 2) {
                        try {
                            Tessera tesseraUtente = utente.getTessera();
                            tessereDAO.rinnovaTessera(tesseraUtente.getNumeroTessera());
                        } catch (TesseraAncoraValidaException e) {
                            System.err.println("ERRORE: " + e.getMessage());
                        }
                    }

                    //OPERAZIONE 3
                    if (operazione == 3) {
                        if (utente.getTessera().getDataScadenza().isBefore(LocalDate.now()))
                            System.out.println("Tessera scaduta in data " + utente.getTessera().getDataScadenza());
                        else
                            System.out.println("Tessera in corso di validità. Scadenza prevista in data " + utente.getTessera().getDataScadenza());
                    }

                    //OPERAZIONE 4
                    if (operazione == 4) {
                        try {
                            emissioniDAO.controllaValiditàAbbonamento(utente.getTessera());
                        } catch (TesseraScadutaException | AbbonamentoTesseraNonTrovatoException e) {
                            System.err.println("ERRORE: " + e.getMessage());
                        }
                    }

                } catch (UtenteNonTrovatoException | NumberFormatException e) {
                    if (e instanceof NumberFormatException)
                        System.err.println("\nERRORE: Operazione selezionata non valida, riprovare! \n");
                    else System.err.println("ERRORE: " + e.getMessage());
                }
            }
        }

        //GESTIONE AMMINISTRATORE + PORTALE
        if (tipoUtente == 3) {
            Utente amministratore;
            while (true) {
                System.out.println("\nPer accedere al portale come amministratore inserire il proprio codice fiscale");
                String codiceFiscale = scanner.nextLine();
                try {
                    amministratore = utentiDAO.findByCodiceFiscale(codiceFiscale);
                    if (amministratore.getTipo() != TipoDiUtente.AMMINISTRATORE)
                        throw new MancanzaPermessoAmministratoreException();
                    else break;
                } catch (UtenteNonTrovatoException |
                         MancanzaPermessoAmministratoreException e) {
                    System.err.println("ERRORE: " + e.getMessage());
                }
            }

            int areaPortale;
            while (true) {
                System.out.println("\nSCEGLIERE A CHE AREA DEL PORTALE SI VUOLE ACCEDERE DIGITANDO IL RISPETTIVO NUMERO");
                System.out.println("1.GESTIONE PUNTI EMISSIONE");
                System.out.println("2.GESTIONE EMISSIONI");
                System.out.println("3.GESTIONE PARCO MEZZI");
                System.out.println("4.GESTIONE TRATTE");
                System.out.println("0.ESCI DALLA SIMULAZIONE");
                try {
                    areaPortale = Integer.parseInt(scanner.nextLine());
                    if (areaPortale == 0) break;
                    if (areaPortale < 1 || areaPortale > 4)
                        System.err.println("\nERRORE: Area portale selezionata non valida, riprovare!\n");
                    //GESTIONE PUNTI EMISSIONE
                    if (areaPortale == 1) {
                        int operazione;
                        while (true) {
                            System.out.println("\nSCEGLIERE IL TIPO DI OPERAZIONE DA SVOLGERE DIGITANDO IL RISPETTIVO NUMERO");
                            System.out.println("1.Aggiungi un punto di emissione");
                            System.out.println("2.Controlla lo stato di un distributore automatico");
                            System.out.println("3.Imposta un distributore automatico a 'fuori servizio'");
                            System.out.println("4.Imposta un distributore automatico a 'in servizio'");
                            System.out.println("5.Visiona tutti i punti di emissione in servizio");
                            System.out.println("6.Visiona i distributori in servizio");
                            System.out.println("7.Visiona i distributori fuori servizio");
                            System.out.println("0.TORNA AL MENU GESTIONI");
                            try {
                                operazione = Integer.parseInt(scanner.nextLine());
                                if (operazione == 0) break;
                                if (operazione < 1 || operazione > 7)
                                    System.err.println("\nERRORE: Operazione selezionata non valida, riprovare!\n");

                                //OPERAZIONE 1
                                if (operazione == 1) {
                                    while (true) {
                                        System.out.println("\nChe punto di emissione vuoi aggiungere?");
                                        System.out.println("1.Distributore automatico");
                                        System.out.println("2.Rivenditore autorizzato");
                                        System.out.println("0.CAMBIA IL TIPO DI OPERAZIONE DA SVOLGERE");
                                        int tipoPuntoEmissione;
                                        try {
                                            tipoPuntoEmissione = Integer.parseInt(scanner.nextLine());
                                            if (tipoPuntoEmissione == 0) break;
                                            if (tipoPuntoEmissione < 1 || tipoPuntoEmissione > 2)
                                                System.err.println("\nERRORE: Tipo selezionato non valido, riprovare!\n");
                                            else {
                                                if (tipoPuntoEmissione == 1) {
                                                    puntiDAO.savePuntoEmissione(new DistributoriAutomatici(true));
                                                } else {
                                                    puntiDAO.savePuntoEmissione(new RivenditoriAutorizzati());
                                                }
                                            }
                                        } catch (NumberFormatException e) {
                                            System.err.println("\nERRORE: Tipo selezionato non valido, riprovare!\n");
                                        }
                                    }
                                }

                                //OPERAZIONE 2
                                if (operazione == 2) {
                                    System.out.println("\nInserire l'id del distributore automatico interessato");
                                    String idDistributore = scanner.nextLine();
                                    try {
                                        DistributoriAutomatici distributore = puntiDAO.getDistributoreById(idDistributore);
                                        if (distributore.isAttivo())
                                            System.out.println("\nIl distributore selezionato è in servizio");
                                        else System.out.println("\nIl distributore selezionato è fuori servizio");
                                    } catch (IllegalArgumentException | NotFoundException e) {
                                        if (e instanceof IllegalArgumentException)
                                            System.err.println("Formato ID non valido");
                                        else System.err.println("ERRORE: " + e.getMessage());
                                    }
                                }

                                //OPERAZIONE 3
                                if (operazione == 3) {
                                    System.out.println("\nInserire l'id del distributore automatico interessato");
                                    String idDistributore = scanner.nextLine();
                                    try {
                                        puntiDAO.changeStatoDistributore(idDistributore, false);
                                    } catch (IllegalArgumentException | CambioStatoPuntoEmissioneException e) {
                                        if (e instanceof IllegalArgumentException)
                                            System.err.println("Formato ID non valido");
                                        else System.err.println("ERRORE: " + e.getMessage());
                                    }
                                }

                                //OPERAZIONE 4
                                if (operazione == 4) {
                                    System.out.println("\nInserire l'id del distributore automatico interessato");
                                    String idDistributore = scanner.nextLine();
                                    try {
                                        puntiDAO.changeStatoDistributore(idDistributore, true);
                                    } catch (IllegalArgumentException | CambioStatoPuntoEmissioneException e) {
                                        if (e instanceof IllegalArgumentException)
                                            System.err.println("Formato ID non valido");
                                        else System.err.println("ERRORE: " + e.getMessage());
                                    }
                                }

                                //OPERAZIONE 5
                                if (operazione == 5) {
                                    try {
                                        List<PuntiEmissione> puntiAttivi = puntiDAO.findPuntiDiEmissioneAttivi();
                                        System.out.println("\nPUNTI DI EMISSIONE IN SERVIZIO:");
                                        puntiAttivi.forEach(punto -> System.out.println((punto.getClass().getSimpleName().equals("DistributoriAutomatici") ? "Distributore automatico" : "Rivenditore autorizzato") + "  " + punto.getId()));
                                    } catch (NessunElementoTrovatoException e) {
                                        System.err.println("ERRORE: " + e.getMessage());
                                    }
                                }

                                //OPERAZIONE 6
                                if (operazione == 6) {
                                    try {
                                        List<DistributoriAutomatici> puntiAttivi = puntiDAO.findDistributoriAttivi();
                                        System.out.println("\nDISTRIBUTORI IN SERVIZIO:");
                                        puntiAttivi.forEach(punto -> System.out.println("Distributore automatico" + "  " + punto.getId()));
                                    } catch (NessunElementoTrovatoException e) {
                                        System.err.println("ERRORE: " + e.getMessage());
                                    }
                                }

                                //OPERAZIONE 7
                                if (operazione == 7) {
                                    try {
                                        List<DistributoriAutomatici> puntiAttivi = puntiDAO.findDistributoriNonAttivi();
                                        System.out.println("\nDISTRIBUTORI NON IN SERVIZIO:");
                                        puntiAttivi.forEach(punto -> System.out.println("Distributore automatico" + "  " + punto.getId()));
                                    } catch (NessunElementoTrovatoException e) {
                                        System.err.println("ERRORE: " + e.getMessage());
                                    }
                                }


                            } catch (NumberFormatException e) {
                                System.err.println("\nERRORE: Operazione selezionata non valida, riprovare!\n");
                            }
                        }
                    }

                    //GESTIONE EMISSIONI
                    if (areaPortale == 2) {
                        int operazione;
                        while (true) {
                            System.out.println("\nSCEGLIERE IL TIPO DI OPERAZIONE DA SVOLGERE DIGITANDO IL RISPETTIVO NUMERO");
                            System.out.println("1.Ottieni il totale di emissioni divise per punto di emissione");
                            System.out.println("2.Ottieni il totale di emissioni avvenute in uno specifico arco di tempo");
                            System.out.println("3.Ottieni il totale di emissioni emesse da uno specifico punto di emissione");
                            System.out.println("4.Ottieni il totale di biglietti vidimati su uno specifico mezzo");
                            System.out.println("0.TORNA AL MENU GESTIONI");
                            try {
                                operazione = Integer.parseInt(scanner.nextLine());
                                if (operazione == 0) break;
                                if (operazione < 1 || operazione > 4)
                                    System.err.println("\nERRORE: Operazione selezionata non valida, riprovare!\n");

                                //OPERAZIONE 1
                                if (operazione == 1) {
                                    while (true) {
                                        System.out.println("\nChe tipo di emissione vuoi verificare?");
                                        System.out.println("1.Tutte");
                                        System.out.println("2.Solo biglietti");
                                        System.out.println("3.Solo abbonamenti");
                                        System.out.println("0.CAMBIA IL TIPO DI OPERAZIONE DA SVOLGERE");
                                        int tipoEmissione;
                                        try {
                                            tipoEmissione = Integer.parseInt(scanner.nextLine());
                                            if (tipoEmissione == 0) break;
                                            if (tipoEmissione < 1 || tipoEmissione > 3)
                                                System.err.println("\nERRORE: Tipo selezionato non valido, riprovare!\n");
                                            else {
                                                if (tipoEmissione == 1) {
                                                    emissioniDAO.getTotaleEmissioniPerPuntoDiEmissione().forEach(System.out::println);
                                                } else if (tipoEmissione == 2) {
                                                    emissioniDAO.getTotaleBigliettiPerPuntoDiEmissione().forEach(System.out::println);
                                                } else {
                                                    emissioniDAO.getTotaleAbbonamentiPerPuntoDiEmissione().forEach(System.out::println);
                                                }
                                            }
                                        } catch (NumberFormatException | NessunElementoTrovatoException e) {
                                            if (e instanceof NumberFormatException)
                                                System.err.println("\nERRORE: Tipo selezionato non valido, riprovare!\n");
                                            else System.err.println("ERRORE: " + e.getMessage());
                                        }

                                    }
                                }

                                //OPERAZIONE 2
                                if (operazione == 2) {
                                    while (true) {
                                        System.out.println("\nChe tipo di emissione vuoi verificare?");
                                        System.out.println("1.Tutte");
                                        System.out.println("2.Solo biglietti");
                                        System.out.println("3.Solo abbonamenti");
                                        System.out.println("0.CAMBIA IL TIPO DI OPERAZIONE DA SVOLGERE");
                                        int tipoEmissione;
                                        try {
                                            tipoEmissione = Integer.parseInt(scanner.nextLine());
                                            if (tipoEmissione == 0) break;
                                            if (tipoEmissione < 1 || tipoEmissione > 3)
                                                System.err.println("\nERRORE: Tipo selezionato non valido, riprovare!\n");
                                            else {
                                                System.out.println("\nChe periodo vuoi verificare?");
                                                System.out.println("1.In una specifica data");
                                                System.out.println("2.Prima di una specifica data");
                                                System.out.println("3.Dopo una specifica data");
                                                System.out.println("4.Tra due specifiche date");
                                                System.out.println("0.CAMBIA IL TIPO DI OPERAZIONE DA SVOLGERE");
                                                int periodo;
                                                periodo = Integer.parseInt(scanner.nextLine());
                                                if (periodo == 0) break;
                                                if (periodo < 1 || periodo > 4)
                                                    System.err.println("\nERRORE: Tipo selezionato non valido, riprovare!\n");
                                                else {
                                                    if (tipoEmissione == 1) {
                                                        if (periodo == 4)
                                                            System.out.println("\nINSERIRE LA DATA DI INIZIO DEL PERIODO DESIDERATO");
                                                        System.out.println("\n Inserire l'anno desiderato:");
                                                        int anno = Integer.parseInt(scanner.nextLine());
                                                        System.out.println("\n Inserire il mese desiderato:");
                                                        int mese = Integer.parseInt(scanner.nextLine());
                                                        System.out.println("\n Inserire il giorno desiderato:");
                                                        int giorno = Integer.parseInt(scanner.nextLine());
                                                        LocalDate dataInizio = LocalDate.of(anno, mese, giorno);
                                                        if (periodo == 1) {
                                                            System.out.println("EMISSIONI IN DATA " + dataInizio + " : " + emissioniDAO.getTotaleEmissioniInBaseAData(dataInizio, ""));
                                                        } else if (periodo == 2) {
                                                            System.out.println("EMISSIONI PRIMA DEL " + dataInizio + " : " + emissioniDAO.getTotaleEmissioniInBaseAData(dataInizio, "prima"));
                                                        } else if (periodo == 3) {
                                                            System.out.println("EMISSIONI DOPO IL " + dataInizio + " : " + emissioniDAO.getTotaleEmissioniInBaseAData(dataInizio, "dopo"));
                                                        } else {
                                                            System.out.println("\nINSERIRE LA DATA DI FINE DEL PERIODO DESIDERATO");
                                                            System.out.println("\n Inserire l'anno desiderato:");
                                                            int anno2 = Integer.parseInt(scanner.nextLine());
                                                            System.out.println("\n Inserire il mese desiderato:");
                                                            int mese2 = Integer.parseInt(scanner.nextLine());
                                                            System.out.println("\n Inserire il giorno desiderato:");
                                                            int giorno2 = Integer.parseInt(scanner.nextLine());
                                                            LocalDate dataFine = LocalDate.of(anno2, mese2, giorno2);
                                                            System.out.println("EMISSIONI TRA IL " + dataInizio + " ED IL " + dataFine + " : " + emissioniDAO.getTotaleEmissioniInArcoTemporale(dataInizio, dataFine));
                                                        }
                                                    } else if (tipoEmissione == 2) {
                                                        if (periodo == 4)
                                                            System.out.println("\nINSERIRE LA DATA DI INIZIO DEL PERIODO DESIDERATO");
                                                        System.out.println("\n Inserire l'anno desiderato:");
                                                        int anno = Integer.parseInt(scanner.nextLine());
                                                        System.out.println("\n Inserire il mese desiderato:");
                                                        int mese = Integer.parseInt(scanner.nextLine());
                                                        System.out.println("\n Inserire il giorno desiderato:");
                                                        int giorno = Integer.parseInt(scanner.nextLine());
                                                        LocalDate dataInizio = LocalDate.of(anno, mese, giorno);
                                                        if (periodo == 1) {
                                                            System.out.println("BIGLIETTI EMESSI IN DATA " + dataInizio + " : " + emissioniDAO.getTotaleBigliettiInBaseAData(dataInizio, ""));
                                                        } else if (periodo == 2) {
                                                            System.out.println("BIGLIETTI EMESSI PRIMA DEL " + dataInizio + " : " + emissioniDAO.getTotaleBigliettiInBaseAData(dataInizio, "prima"));
                                                        } else if (periodo == 3) {
                                                            System.out.println("BIGLIETTI EMESSI DOPO IL " + dataInizio + " : " + emissioniDAO.getTotaleBigliettiInBaseAData(dataInizio, "dopo"));
                                                        } else {
                                                            System.out.println("\nINSERIRE LA DATA DI FINE DEL PERIODO DESIDERATO");
                                                            System.out.println("\n Inserire l'anno desiderato:");
                                                            int anno2 = Integer.parseInt(scanner.nextLine());
                                                            System.out.println("\n Inserire il mese desiderato:");
                                                            int mese2 = Integer.parseInt(scanner.nextLine());
                                                            System.out.println("\n Inserire il giorno desiderato:");
                                                            int giorno2 = Integer.parseInt(scanner.nextLine());
                                                            LocalDate dataFine = LocalDate.of(anno2, mese2, giorno2);
                                                            System.out.println("BIGLIETTI EMESSI TRA IL " + dataInizio + " ED IL " + dataFine + " : " + emissioniDAO.getTotaleBigliettiInArcoTemporale(dataInizio, dataFine));
                                                        }
                                                    } else {
                                                        if (periodo == 4)
                                                            System.out.println("\nINSERIRE LA DATA DI INIZIO DEL PERIODO DESIDERATO");
                                                        System.out.println("\n Inserire l'anno desiderato:");
                                                        int anno = Integer.parseInt(scanner.nextLine());
                                                        System.out.println("\n Inserire il mese desiderato:");
                                                        int mese = Integer.parseInt(scanner.nextLine());
                                                        System.out.println("\n Inserire il giorno desiderato:");
                                                        int giorno = Integer.parseInt(scanner.nextLine());
                                                        LocalDate dataInizio = LocalDate.of(anno, mese, giorno);
                                                        if (periodo == 1) {
                                                            System.out.println("ABBONAMENTI EMESSI IN DATA " + dataInizio + " : " + emissioniDAO.getTotaleAbbonamentiInBaseAData(dataInizio, ""));
                                                        } else if (periodo == 2) {
                                                            System.out.println("ABBONAMENTI EMESSI PRIMA DEL " + dataInizio + " : " + emissioniDAO.getTotaleAbbonamentiInBaseAData(dataInizio, "prima"));
                                                        } else if (periodo == 3) {
                                                            System.out.println("ABBONAMENTI EMESSI DOPO IL " + dataInizio + " : " + emissioniDAO.getTotaleAbbonamentiInBaseAData(dataInizio, "dopo"));
                                                        } else {
                                                            System.out.println("\nINSERIRE LA DATA DI FINE DEL PERIODO DESIDERATO");
                                                            System.out.println("\n Inserire l'anno desiderato:");
                                                            int anno2 = Integer.parseInt(scanner.nextLine());
                                                            System.out.println("\n Inserire il mese desiderato:");
                                                            int mese2 = Integer.parseInt(scanner.nextLine());
                                                            System.out.println("\n Inserire il giorno desiderato:");
                                                            int giorno2 = Integer.parseInt(scanner.nextLine());
                                                            LocalDate dataFine = LocalDate.of(anno2, mese2, giorno2);
                                                            System.out.println("ABBONAMENTI EMESSI TRA IL " + dataInizio + " ED IL " + dataFine + " : " + emissioniDAO.getTotaleAbbonamentiInArcoTemporale(dataInizio, dataFine));
                                                        }
                                                    }
                                                }
                                            }
                                        } catch (NumberFormatException | NessunElementoTrovatoException |
                                                 DateTimeException e) {
                                            if (e instanceof NumberFormatException)
                                                System.err.println("\nERRORE: Tipo selezionato non valido, riprovare!\n");
                                            if (e instanceof DateTimeException) {
                                                System.err.println("\nERRORE: Data non valida, riprovare!\n");
                                            } else System.err.println("ERRORE: " + e.getMessage());
                                        }
                                    }
                                }

                                //OPERAZIONE 3
                                if (operazione == 3) {
                                    while (true) {
                                        System.out.println("\n Inserire l'id del punto di emissione da verificare");
                                        try {
                                            String idPunto = scanner.nextLine();
                                            puntiDAO.getPuntoEmissioneById(idPunto);
                                            System.out.println("\nChe tipo di emissione vuoi verificare?");
                                            System.out.println("1.Tutte");
                                            System.out.println("2.Solo biglietti");
                                            System.out.println("3.Solo abbonamenti");
                                            System.out.println("0.CAMBIA IL TIPO DI OPERAZIONE DA SVOLGERE");
                                            int tipoEmissione = Integer.parseInt(scanner.nextLine());
                                            if (tipoEmissione == 0) break;
                                            if (tipoEmissione < 1 || tipoEmissione > 3)
                                                System.err.println("\nERRORE: Tipo selezionato non valido, riprovare!\n");
                                            else {
                                                if (tipoEmissione == 1) {
                                                    System.out.println("TOTALE DELLE EMISSIONI AVVENUTE DAL PUNTO CON ID " + idPunto + " : " + emissioniDAO.getTotaleEmissioniDaPuntoDiEmissione(idPunto));
                                                } else if (tipoEmissione == 2) {
                                                    System.out.println("TOTALE DEI BIGLIETTI EMESSI DAL PUNTO CON ID " + idPunto + " : " + emissioniDAO.getTotaleEmissioniDaPuntoDiEmissione(idPunto));
                                                } else {
                                                    System.out.println("TOTALE DEGLI ABBONAMENTI EMESSI DAL PUNTO CON ID " + idPunto + " : " + emissioniDAO.getTotaleEmissioniDaPuntoDiEmissione(idPunto));
                                                }
                                            }
                                        } catch (NessunElementoTrovatoException | NotFoundException |
                                                 IllegalArgumentException e) {
                                            if (e instanceof NumberFormatException)
                                                System.err.println("\nERRORE: Tipo selezionato non valido, riprovare!\n");
                                            if (e instanceof IllegalArgumentException)
                                                System.err.println("\nERRORE: Formato ID non valido, riprovare!\n");
                                            else System.err.println("ERRORE: " + e.getMessage());
                                        }
                                    }
                                }


                                //OPERAZIONE 4
                                if (operazione == 4) {
                                    while (true) {
                                        System.out.println("\n Inserire l'id del mezzo da verificare");
                                        try {
                                            String idMezzo = scanner.nextLine();
                                            mezziDAO.findById(idMezzo);
                                            System.out.println("\nChe periodo vuoi verificare?");
                                            System.out.println("1.In una specifica data");
                                            System.out.println("2.Prima di una specifica data");
                                            System.out.println("3.Dopo una specifica data");
                                            System.out.println("4.Tra due specifiche date");
                                            System.out.println("5.Fino ad oggi");
                                            System.out.println("0.CAMBIA IL TIPO DI OPERAZIONE DA SVOLGERE");
                                            int periodo = Integer.parseInt(scanner.nextLine());
                                            if (periodo == 0) break;
                                            if (periodo < 1 || periodo > 5)
                                                System.err.println("\nERRORE: Periodo selezionato non valido, riprovare!\n");
                                            else {
                                                if (periodo == 5) {
                                                    System.out.println("BIGLIETTI VALIDATI SUL MEZZO " + idMezzo + " FINO AD OGGI : " + emissioniDAO.getBigliettiVidimatiSuUnMezzo(idMezzo));
                                                } else {
                                                    if (periodo == 4)
                                                        System.out.println("\nINSERIRE LA DATA DI INIZIO DEL PERIODO DESIDERATO");
                                                    System.out.println("\n Inserire l'anno desiderato:");
                                                    int anno = Integer.parseInt(scanner.nextLine());
                                                    System.out.println("\n Inserire il mese desiderato:");
                                                    int mese = Integer.parseInt(scanner.nextLine());
                                                    System.out.println("\n Inserire il giorno desiderato:");
                                                    int giorno = Integer.parseInt(scanner.nextLine());
                                                    LocalDate dataInizio = LocalDate.of(anno, mese, giorno);
                                                    if (periodo == 1) {
                                                        System.out.println("BIGLIETTI VALIDATI SUL MEZZO " + idMezzo + " IN DATA " + dataInizio + " : " + emissioniDAO.getBigliettiVidimatiInUnaDataSuUnMezzo(dataInizio, idMezzo));
                                                    } else if (periodo == 2) {
                                                        System.out.println("BIGLIETTI VALIDATI SUL MEZZO " + idMezzo + " PRIMA DEL " + dataInizio + " : " + emissioniDAO.getBigliettiVidimatiPrimaDiUnaDataSuUnMezzo(dataInizio, idMezzo));
                                                    } else if (periodo == 3) {
                                                        System.out.println("BIGLIETTI VALIDATI SUL MEZZO " + idMezzo + " DOPO IL " + dataInizio + " : " + emissioniDAO.getBigliettiVidimatiDopoUnaDataSuUnMezzo(dataInizio, idMezzo));
                                                    } else {
                                                        System.out.println("\nINSERIRE LA DATA DI FINE DEL PERIODO DESIDERATO");
                                                        System.out.println("\n Inserire l'anno desiderato:");
                                                        int anno2 = Integer.parseInt(scanner.nextLine());
                                                        System.out.println("\n Inserire il mese desiderato:");
                                                        int mese2 = Integer.parseInt(scanner.nextLine());
                                                        System.out.println("\n Inserire il giorno desiderato:");
                                                        int giorno2 = Integer.parseInt(scanner.nextLine());
                                                        LocalDate dataFine = LocalDate.of(anno2, mese2, giorno2);
                                                        System.out.println("BIGLIETTI VALIDATI SUL MEZZO " + idMezzo + " TRA IL " + dataInizio + " ED IL " + dataFine + " : " + emissioniDAO.getBigliettiVidimatiInUnArcoTemporaleSuUnMezzo(dataInizio, dataFine, idMezzo));
                                                    }
                                                }
                                            }
                                        } catch (NessunElementoTrovatoException | NotFoundException |
                                                 IllegalArgumentException | DateTimeException e) {
                                            if (e instanceof NumberFormatException)
                                                System.err.println("\nERRORE: Tipo selezionato non valido, riprovare!\n");
                                            if (e instanceof IllegalArgumentException)
                                                System.err.println("\nERRORE: Formato ID non valido, riprovare!\n");
                                            if (e instanceof DateTimeException)
                                                System.err.println("\nERRORE: Data non valida, riprovare!\n");
                                            else System.err.println("ERRORE: " + e.getMessage());
                                        }
                                    }
                                }
                            } catch (NumberFormatException e) {
                                System.err.println("\nERRORE: Operazione selezionata non valida, riprovare!\n");
                            }
                        }

                    }

                    //GESTIONE PARCO MEZZI
                    if (areaPortale == 3) {
                        int operazione;
                        while (true) {
                            System.out.println("\nSCEGLIERE IL TIPO DI OPERAZIONE DA SVOLGERE DIGITANDO IL RISPETTIVO NUMERO");
                            System.out.println("1.Aggiungi un mezzo al parco mezzi");
                            System.out.println("2.Ottieni l'operatività' di un mezzo");
                            System.out.println("3.Imposta l'operatività' di un mezzo a 'in servizio'");
                            System.out.println("4.Imposta l'operatività' di un mezzo a 'in manutenzione'");
                            System.out.println("5.Visiona i mezzi in servizio");
                            System.out.println("6.Visiona i mezzi in manutenzione");
                            System.out.println("7.Visiona i periodi di manutenzione di un mezzo");
                            System.out.println("8.Visiona i periodi di servizio di un mezzo");
                            System.out.println("0.TORNA AL MENU GESTIONI");
                            try {
                                operazione = Integer.parseInt(scanner.nextLine());
                                if (operazione == 0) break;
                                if (operazione < 1 || operazione > 8)
                                    System.err.println("\nERRORE: Operazione selezionata non valida, riprovare!\n");

                                //OPERAZIONE 1
                                if (operazione == 1) {
                                    System.out.println("\n Che tipo di mezzo vuoi aggiungere?");
                                    System.out.println("1.Autobus");
                                    System.out.println("2.Tram");
                                    System.out.println("0.CAMBIA IL TIPO DI OPERAZIONE DA SVOLGERE");
                                    int tipoMezzo;
                                    try {
                                        tipoMezzo = Integer.parseInt(scanner.nextLine());
                                        if (tipoMezzo == 0) continue;
                                        if (tipoMezzo < 1 || tipoMezzo > 2)
                                            System.err.println("\nERRORE: Tipo selezionato non valido, riprovare!\n");
                                        else {
                                            if (tipoMezzo == 1) {
                                                mezziDAO.addMezzo("autobus");
                                            } else {
                                                mezziDAO.addMezzo("tram");
                                            }
                                        }
                                    } catch (NumberFormatException e) {
                                        System.err.println("\nERRORE: Tipo selezionato non valido, riprovare!\n");
                                    }
                                }

                                //OPERAZIONE 2
                                if (operazione == 2) {
                                    System.out.println("\nInserire l'id del mezzo interessato");
                                    try {
                                        String idMezzo = scanner.nextLine();
                                        OperativitàMezzo operativitaAttuale = mezziDAO.getOperativitàAttualeMezzo(idMezzo);
                                        if (operativitaAttuale instanceof Servizio)
                                            System.out.println("\nIl mezzo " + idMezzo + " è attualmente in servizio");
                                        else {
                                            System.out.println("\nIl mezzo " + idMezzo + " è in manutenzione dal " + operativitaAttuale.getDataInizio());
                                            Manutenzione manutenzione = (Manutenzione) operativitaAttuale;
                                            System.out.println("DESCRIZIONE MANUTENZIONE: " + manutenzione.getDescrizione());
                                        }
                                    } catch (IllegalArgumentException | NotFoundException e) {
                                        if (e instanceof IllegalArgumentException)
                                            System.err.println("\nERRORE: Formato ID non valido, riprovare!\n");
                                    }
                                }

                                //OPERAZIONE 3
                                if (operazione == 3) {
                                    System.out.println("\nInserire l'id del mezzo interessato");
                                    try {
                                        String idMezzo = scanner.nextLine();
                                        mezziDAO.updateOperativitàAttualeMezzo(idMezzo, "servizio", "");
                                    } catch (IllegalArgumentException e) {
                                        System.err.println("\nERRORE: Formato ID non valido, riprovare!\n");
                                    }
                                }

                                //OPERAZIONE 4
                                if (operazione == 4) {
                                    System.out.println("\nInserire l'id del mezzo interessato");
                                    try {
                                        String idMezzo = scanner.nextLine();
                                        System.out.println("\nInserire una descrizione per la manutenzione");
                                        String descrizione = scanner.nextLine();
                                        mezziDAO.updateOperativitàAttualeMezzo(idMezzo, "manutenzione", descrizione);
                                    } catch (IllegalArgumentException e) {
                                        System.err.println("\nERRORE: Formato ID non valido, riprovare!\n");
                                    }
                                }

                                //OPERAZIONE 5
                                if (operazione == 5) {
                                    try {
                                        System.out.println("\nLISTA MEZZI IN SERVIZIO");
                                        mezziDAO.getMezziInServizio().forEach(System.out::println);
                                    } catch (NessunElementoTrovatoException e) {
                                        System.err.println("ERRORE: " + e.getMessage());
                                    }
                                }

                                //OPERAZIONE 6
                                if (operazione == 6) {
                                    try {
                                        System.out.println("\nLISTA MEZZI IN MANUTEZIONE");
                                        mezziDAO.getMezziInManutenzione().forEach(mezzo -> System.out.println((mezzo instanceof Autobus ? "Autobus " : "Tram ") + mezzo.getId()));
                                    } catch (NessunElementoTrovatoException e) {
                                        System.err.println("ERRORE: " + e.getMessage());
                                    }
                                }

                                //OPERAZIONE 7
                                if (operazione == 7) {
                                    System.out.println("\nInserire l'id del mezzo interessato");
                                    try {
                                        String idMezzo = scanner.nextLine();
                                        System.out.println("\nLISTA MANUTENZIONI MEZZO " + idMezzo);
                                        mezziDAO.getManutenzioniMezzo(idMezzo).forEach(manutenzione -> System.out.println("Manutenzione " + manutenzione.getId() + " [" + manutenzione.getDataInizio() + " | " + (manutenzione.getDataFine() == null ? "in corso" : manutenzione.getDataFine()) + "] DESCRIZIONE: " + manutenzione.getDescrizione()));
                                    } catch (IllegalArgumentException | NessunElementoTrovatoException e) {
                                        System.err.println("\nERRORE: Formato ID non valido, riprovare!\n");
                                    }
                                }

                                //OPERAZIONE 8
                                if (operazione == 8) {
                                    System.out.println("\nInserire l'id del mezzo interessato");
                                    try {
                                        String idMezzo = scanner.nextLine();
                                        System.out.println("\nLISTA SERVIZI MEZZO " + idMezzo);
                                        mezziDAO.getServiziMezzo(idMezzo).forEach(servizio -> System.out.println("Servizio " + servizio.getId() + " [" + servizio.getDataInizio() + " | " + (servizio.getDataFine() == null ? "in corso" : servizio.getDataFine()) + "]"));
                                    } catch (IllegalArgumentException | NessunElementoTrovatoException e) {
                                        System.err.println("\nERRORE: Formato ID non valido, riprovare!\n");
                                    }
                                }

                            } catch (NumberFormatException e) {
                                System.err.println("\nERRORE: Operazione selezionata non valida, riprovare!\n");
                            }
                        }
                    }

                    //GESTIONE TRATTE
                    if (areaPortale == 4) {
                        int operazione;
                        while (true) {
                            System.out.println("\nSCEGLIERE IL TIPO DI OPERAZIONE DA SVOLGERE DIGITANDO IL RISPETTIVO NUMERO");
                            System.out.println("1.Aggiungi una nuova tratta");
                            System.out.println("2.Assegna tratta ad un mezzo in una specifica data");
                            System.out.println("3.Aggiorna percorrenza di una tratta effettuata");
                            System.out.println("4.Ottieni il numero di volte che una tratta è stata percorsa da uno specifico mezzo");
                            System.out.println("5.Ottieni la media del tempo di percorrenza di una tratta");
                            System.out.println("6.Ottieni il tempo medio effettivo di percorrenza di una tratta da parte di un mezzo specifico");
                            System.out.println("0.TORNA AL MENU GESTIONI");
                            try {
                                operazione = Integer.parseInt(scanner.nextLine());
                                if (operazione == 0) break;
                                if (operazione < 1 || operazione > 6)
                                    System.err.println("\nERRORE: Operazione selezionata non valida, riprovare!\n");

                                //OPERAZIONE 1
                                if (operazione == 1) {
                                    System.out.println("\nInserire il punto di partenza della nuova tratta:");
                                    String partenza = scanner.nextLine();
                                    if (partenza.isEmpty()) System.err.println("ERRORE: Inserimento non valido");
                                    else {
                                        System.out.println("\nInserire il capolinea della nuova tratta");
                                        String capolinea = scanner.nextLine();
                                        if (capolinea.isEmpty()) System.err.println("ERRORE: Inserimento non valido");
                                        else {
                                            System.out.println("\nInserire la durata della percorrenza prevista:");
                                            System.out.println("\nInserire il numero di ore");
                                            try {
                                                int ore = Integer.parseInt(scanner.nextLine());
                                                System.out.println("\nInserire il numero di minuti");
                                                int minuti = Integer.parseInt(scanner.nextLine());
                                                LocalTime percorrenzaPrevista = LocalTime.of(ore, minuti);
                                                tratteDAO.save(new Tratta(partenza, capolinea, percorrenzaPrevista));
                                            } catch (NumberFormatException e) {
                                                System.err.println("\nERRORE: Inserimento non valido, digitare un numero intero");
                                            }
                                        }
                                    }
                                }

                                //OPERAZIONE 2
                                if (operazione == 2) {
                                    System.out.println("\nInserire l'id della tratta da assegnare");
                                    String idTratta = scanner.nextLine();
                                    try {
                                        Tratta tratta = tratteDAO.getTrattaById(idTratta);
                                        System.out.println("\nInserire l'id del mezzo a cui assegnare la tratta");
                                        String idMezzo = scanner.nextLine();
                                        MezzoDiTrasporto mezzo = mezziDAO.findById(idMezzo);
                                        System.out.println("\nInserire la data in cui il mezzo dovrà svolgere la tratta");
                                        System.out.println("\nInserire il giorno");
                                        int giorno = Integer.parseInt(scanner.nextLine());
                                        System.out.println("\nInserire il mese");
                                        int mese = Integer.parseInt(scanner.nextLine());
                                        System.out.println("\nInserire l'anno");
                                        int anno = Integer.parseInt(scanner.nextLine());
                                        LocalDate dataPercorrenza = LocalDate.of(anno, mese, giorno);
                                        assegnazioniDAO.assegnaTrattaAMezzoInUnaSpecificaData(dataPercorrenza, tratta, mezzo);
                                    } catch (NotFoundException | IllegalArgumentException | DateTimeException e) {
                                        if (e instanceof IllegalArgumentException)
                                            System.err.println("ERRORE: formato ID non valido");
                                        if (e instanceof DateTimeException)
                                            System.err.println("\nERRORE: Data non valida, riprovare!\n");
                                        else System.err.println("ERRORE: " + e.getMessage());
                                    }
                                }

                                //OPERAZIONE 3
                                if (operazione == 3) {
                                    System.out.println("\nInserire l'id della tratta effettuata di cui si vuole aggiornare la percorrenza");
                                    String idTrattaEffettuata = scanner.nextLine();
                                    try {
                                        TrattaMezzo trattaEffettuata = assegnazioniDAO.findById(idTrattaEffettuata);
                                        System.out.println("\nInserire la durata di percorrenza effettiva della tratta:");
                                        System.out.println("\nInserire il numero di ore");
                                        try {
                                            int ore = Integer.parseInt(scanner.nextLine());
                                            System.out.println("\nInserire il numero di minuti");
                                            int minuti = Integer.parseInt(scanner.nextLine());
                                            LocalTime percorrenzaEffettiva = LocalTime.of(ore, minuti);
                                            assegnazioniDAO.aggiornaPercorrenzaEffettivaTrattaAssegnata(percorrenzaEffettiva, trattaEffettuata);
                                        } catch (NumberFormatException e) {
                                            System.err.println("\nERRORE: Inserimento non valido, digitare un numero intero");
                                        }
                                    } catch (NotFoundException | IllegalArgumentException | DateTimeException e) {
                                        if (e instanceof IllegalArgumentException)
                                            System.err.println("ERRORE: formato ID non valido");
                                        if (e instanceof DateTimeException)
                                            System.err.println("\nERRORE: Data non valida, riprovare!\n");
                                        else System.err.println("ERRORE: " + e.getMessage());
                                    }
                                }

                                //OPERAZIONE 4
                                if (operazione == 4) {
                                    System.out.println("\nInserire l'id della tratta desiderata");
                                    String idTratta = scanner.nextLine();
                                    try {
                                        Tratta tratta = tratteDAO.getTrattaById(idTratta);
                                        System.out.println("\nInserire l'id del mezzo desiderato");
                                        String idMezzo = scanner.nextLine();
                                        mezziDAO.findById(idMezzo);
                                        long risultato = tratteDAO.countPercorrenzeMezzoSuTratta(idMezzo, idTratta);
                                        System.out.println("Il mezzo " + idMezzo + " ha effettuato la tratta " + tratta.getPartenza() + " - " + tratta.getCapolinea() + " " + risultato + " volte");
                                    } catch (NotFoundException | IllegalArgumentException e) {
                                        if (e instanceof IllegalArgumentException)
                                            System.err.println("ERRORE: formato ID non valido");
                                        else System.err.println("ERRORE: " + e.getMessage());
                                    }
                                }

                                //OPERAZIONE 5
                                if (operazione == 5) {
                                    System.out.println("\nInserire l'id della tratta desiderata");
                                    String idTratta = scanner.nextLine();
                                    try {
                                        Tratta tratta = tratteDAO.getTrattaById(idTratta);
                                        LocalTime media = tratteDAO.getTempoMedioEffettivo(idTratta);
                                        System.out.println("Il tempo medio impiegato per effettuare la tratta " + tratta.getPartenza() + " - " + tratta.getCapolinea() + " è di " + media.getHour() + " ore e " + media.getMinute() + " minuti");
                                    } catch (NotFoundException | IllegalArgumentException |
                                             TrattaMaiEffettuataException e) {
                                        if (e instanceof IllegalArgumentException)
                                            System.err.println("ERRORE: formato ID non valido");
                                        else System.err.println("ERRORE: " + e.getMessage());
                                    }
                                }

                                //OPERAZIONE 6
                                if (operazione == 6) {
                                    System.out.println("\nInserire l'id della tratta desiderata");
                                    String idTratta = scanner.nextLine();
                                    try {
                                        Tratta tratta = tratteDAO.getTrattaById(idTratta);
                                        System.out.println("\nInserire l'id del mezzo desiderato");
                                        String idMezzo = scanner.nextLine();
                                        mezziDAO.findById(idMezzo);
                                        LocalTime media = tratteDAO.getTempoMedioEffettivoInBaseAMezzo(idTratta, idMezzo);
                                        System.out.println("Il tempo medio impiegato dal mezzo " + idMezzo + " per effettuare la tratta " + tratta.getPartenza() + " - " + tratta.getCapolinea() + " è di " + media.getHour() + " ore e " + media.getMinute() + " minuti");
                                    } catch (NotFoundException | IllegalArgumentException |
                                             TrattaMaiEffettuataDalMezzoException e) {
                                        if (e instanceof IllegalArgumentException)
                                            System.err.println("ERRORE: formato ID non valido");
                                        else System.err.println("ERRORE: " + e.getMessage());
                                    }
                                }

                            } catch (NumberFormatException e) {
                                System.err.println("\nERRORE: Operazione selezionata non valida, riprovare!\n");
                            }
                        }
                    }
                } catch (NumberFormatException e) {
                    System.err.println("\nERRORE: Area portale selezionata non valida, riprovare!\n");
                }
            }

            /*//GESTIONE PUNTI EMISSIONE
            if (areaPortale == 1) {
                int operazione;
                while (true) {
                    System.out.println("\nSCEGLIERE IL TIPO DI OPERAZIONE DA SVOLGERE DIGITANDO IL RISPETTIVO NUMERO");
                    System.out.println("1.Aggiungi un punto di emissione");
                    System.out.println("2.Controlla lo stato di un distributore automatico");
                    System.out.println("3.Imposta un distributore automatico a 'fuori servizio'");
                    System.out.println("4.Imposta un distributore automatico a 'in servizio'");
                    System.out.println("5.Visiona tutti i punti di emissione in servizio");
                    System.out.println("6.Visiona i distributori in servizio");
                    System.out.println("7.Visiona i distributori fuori servizio");
                    System.out.println("0.ESCI DALLA SIMULAZIONE");
                    try {
                        operazione = Integer.parseInt(scanner.nextLine());
                        if (operazione == 0) break;
                        if (operazione < 1 || operazione > 7)
                            System.err.println("\nERRORE: Operazione selezionata non valida, riprovare!\n");

                        //OPERAZIONE 1
                        if (operazione == 1) {
                            while (true) {
                                System.out.println("\nChe punto di emissione vuoi aggiungere?");
                                System.out.println("1.Distributore automatico");
                                System.out.println("2.Rivenditore autorizzato");
                                System.out.println("0.CAMBIA IL TIPO DI OPERAZIONE DA SVOLGERE");
                                int tipoPuntoEmissione;
                                try {
                                    tipoPuntoEmissione = Integer.parseInt(scanner.nextLine());
                                    if (tipoPuntoEmissione == 0) break;
                                    if (tipoPuntoEmissione < 1 || tipoPuntoEmissione > 2)
                                        System.err.println("\nERRORE: Tipo selezionato non valido, riprovare!\n");
                                    else {
                                        if (tipoPuntoEmissione == 1) {
                                            puntiDAO.savePuntoEmissione(new DistributoriAutomatici(true));
                                        } else {
                                            puntiDAO.savePuntoEmissione(new RivenditoriAutorizzati());
                                        }
                                    }
                                } catch (NumberFormatException e) {
                                    System.err.println("\nERRORE: Tipo selezionato non valido, riprovare!\n");
                                }
                            }
                        }

                        //OPERAZIONE 2
                        if (operazione == 2) {
                            System.out.println("\nInserire l'id del distributore automatico interessato");
                            String idDistributore = scanner.nextLine();
                            try {
                                DistributoriAutomatici distributore = puntiDAO.getDistributoreById(idDistributore);
                                if (distributore.isAttivo())
                                    System.out.println("\nIl distributore selezionato è in servizio");
                                else System.out.println("\nIl distributore selezionato è fuori servizio");
                            } catch (IllegalArgumentException | NotFoundException e) {
                                if (e instanceof IllegalArgumentException) System.err.println("Formato ID non valido");
                                else System.err.println("ERRORE: " + e.getMessage());
                            }
                        }

                        //OPERAZIONE 3
                        if (operazione == 3) {
                            System.out.println("\nInserire l'id del distributore automatico interessato");
                            String idDistributore = scanner.nextLine();
                            try {
                                puntiDAO.changeStatoDistributore(idDistributore, false);
                            } catch (IllegalArgumentException | CambioStatoPuntoEmissioneException e) {
                                if (e instanceof IllegalArgumentException) System.err.println("Formato ID non valido");
                                else System.err.println("ERRORE: " + e.getMessage());
                            }
                        }

                        //OPERAZIONE 4
                        if (operazione == 4) {
                            System.out.println("\nInserire l'id del distributore automatico interessato");
                            String idDistributore = scanner.nextLine();
                            try {
                                puntiDAO.changeStatoDistributore(idDistributore, true);
                            } catch (IllegalArgumentException | CambioStatoPuntoEmissioneException e) {
                                if (e instanceof IllegalArgumentException) System.err.println("Formato ID non valido");
                                else System.err.println("ERRORE: " + e.getMessage());
                            }
                        }

                        //OPERAZIONE 5
                        if (operazione == 5) {
                            try {
                                List<PuntiEmissione> puntiAttivi = puntiDAO.findPuntiEmissioneAttivi();
                                System.out.println("\nPUNTI DI EMISSIONE IN SERVIZIO:");
                                puntiAttivi.forEach(punto -> System.out.println((punto.getClass().getSimpleName().equals("DistributoriAutomatici") ? "Distributore automatico" : "Rivenditore autorizzato") + "  " + punto.getId()));
                            } catch (NessunElementoTrovatoException e) {
                                System.err.println("ERRORE: " + e.getMessage());
                            }
                        }

                        //OPERAZIONE 6
                        if (operazione == 6) {
                            try {
                                List<PuntiEmissione> puntiAttivi = puntiDAO.findDistributoriAttivi();
                                System.out.println("\nDISTRIBUTORI IN SERVIZIO:");
                                puntiAttivi.forEach(punto -> System.out.println("Distributore automatico" + "  " + punto.getId()));
                            } catch (NessunElementoTrovatoException e) {
                                System.err.println("ERRORE: " + e.getMessage());
                            }
                        }

                        //OPERAZIONE 7
                        if (operazione == 7) {
                            try {
                                List<PuntiEmissione> puntiAttivi = puntiDAO.findDistributoriNonAttivi();
                                System.out.println("\nDISTRIBUTORI NON IN SERVIZIO:");
                                puntiAttivi.forEach(punto -> System.out.println("Distributore automatico" + "  " + punto.getId()));
                            } catch (NessunElementoTrovatoException e) {
                                System.err.println("ERRORE: " + e.getMessage());
                            }
                        }


                    } catch (NumberFormatException e) {
                        System.err.println("\nERRORE: Operazione selezionata non valida, riprovare!\n");
                    }
                }
            }

            //GESTIONE EMISSIONI
            if (areaPortale == 2) {
                int operazione;
                while (true) {
                    System.out.println("\nSCEGLIERE IL TIPO DI OPERAZIONE DA SVOLGERE DIGITANDO IL RISPETTIVO NUMERO");
                    System.out.println("1.Ottieni il totale di emissioni divise per punto di emissione");
                    System.out.println("2.Ottieni il totale di emissioni avvenute in uno specifico arco di tempo");
                    System.out.println("3.Ottieni il totale di emissioni emesse da uno specifico punto di emissione");
                    System.out.println("4.Ottieni il totale di biglietti vidimati su uno specifico mezzo");
                    System.out.println("0.ESCI DALLA SIMULAZIONE");
                    try {
                        operazione = Integer.parseInt(scanner.nextLine());
                        if (operazione == 0) break;
                        if (operazione < 1 || operazione > 4)
                            System.err.println("\nERRORE: Operazione selezionata non valida, riprovare!\n");

                        //OPERAZIONE 1
                        if (operazione == 1) {
                            while (true) {
                                System.out.println("\nChe tipo di emissione vuoi verificare?");
                                System.out.println("1.Tutte");
                                System.out.println("2.Solo biglietti");
                                System.out.println("3.Solo abbonamenti");
                                System.out.println("0.CAMBIA IL TIPO DI OPERAZIONE DA SVOLGERE");
                                int tipoEmissione;
                                try {
                                    tipoEmissione = Integer.parseInt(scanner.nextLine());
                                    if (tipoEmissione == 0) break;
                                    if (tipoEmissione < 1 || tipoEmissione > 3)
                                        System.err.println("\nERRORE: Tipo selezionato non valido, riprovare!\n");
                                    else {
                                        if (tipoEmissione == 1) {
                                            emissioniDAO.getTotaleEmissioniPerPuntoDiEmissione().forEach(System.out::println);
                                        } else if (tipoEmissione == 2) {
                                            emissioniDAO.getTotaleBigliettiPerPuntoDiEmissione().forEach(System.out::println);
                                        } else {
                                            emissioniDAO.getTotaleAbbonamentiPerPuntoDiEmissione().forEach(System.out::println);
                                        }
                                    }
                                } catch (NumberFormatException | NessunElementoTrovatoException e) {
                                    if (e instanceof NumberFormatException)
                                        System.err.println("\nERRORE: Tipo selezionato non valido, riprovare!\n");
                                    else System.err.println("ERRORE: " + e.getMessage());
                                }

                            }
                        }

                        //OPERAZIONE 2
                        if (operazione == 2) {
                            while (true) {
                                System.out.println("\nChe tipo di emissione vuoi verificare?");
                                System.out.println("1.Tutte");
                                System.out.println("2.Solo biglietti");
                                System.out.println("3.Solo abbonamenti");
                                System.out.println("0.CAMBIA IL TIPO DI OPERAZIONE DA SVOLGERE");
                                int tipoEmissione;
                                try {
                                    tipoEmissione = Integer.parseInt(scanner.nextLine());
                                    if (tipoEmissione == 0) break;
                                    if (tipoEmissione < 1 || tipoEmissione > 3)
                                        System.err.println("\nERRORE: Tipo selezionato non valido, riprovare!\n");
                                    else {
                                        System.out.println("\nChe periodo vuoi verificare?");
                                        System.out.println("1.In una specifica data");
                                        System.out.println("2.Prima di una specifica data");
                                        System.out.println("3.Dopo una specifica data");
                                        System.out.println("4.Tra due specifiche date");
                                        System.out.println("0.CAMBIA IL TIPO DI OPERAZIONE DA SVOLGERE");
                                        int periodo;
                                        periodo = Integer.parseInt(scanner.nextLine());
                                        if (periodo == 0) break;
                                        if (periodo < 1 || periodo > 4)
                                            System.err.println("\nERRORE: Tipo selezionato non valido, riprovare!\n");
                                        else {
                                            if (tipoEmissione == 1) {
                                                if (periodo == 4)
                                                    System.out.println("\nINSERIRE LA DATA DI INIZIO DEL PERIODO DESIDERATO");
                                                System.out.println("\n Inserire l'anno desiderato:");
                                                int anno = Integer.parseInt(scanner.nextLine());
                                                System.out.println("\n Inserire il mese desiderato:");
                                                int mese = Integer.parseInt(scanner.nextLine());
                                                System.out.println("\n Inserire il giorno desiderato:");
                                                int giorno = Integer.parseInt(scanner.nextLine());
                                                LocalDate dataInizio = LocalDate.of(anno, mese, giorno);
                                                if (periodo == 1) {
                                                    System.out.println("EMISSIONI IN DATA " + dataInizio + " : " + emissioniDAO.getTotaleEmissioniInBaseAData(dataInizio, ""));
                                                } else if (periodo == 2) {
                                                    System.out.println("EMISSIONI PRIMA DEL " + dataInizio + " : " + emissioniDAO.getTotaleEmissioniInBaseAData(dataInizio, "prima"));
                                                } else if (periodo == 3) {
                                                    System.out.println("EMISSIONI DOPO IL " + dataInizio + " : " + emissioniDAO.getTotaleEmissioniInBaseAData(dataInizio, "dopo"));
                                                } else {
                                                    System.out.println("\nINSERIRE LA DATA DI FINE DEL PERIODO DESIDERATO");
                                                    System.out.println("\n Inserire l'anno desiderato:");
                                                    int anno2 = Integer.parseInt(scanner.nextLine());
                                                    System.out.println("\n Inserire il mese desiderato:");
                                                    int mese2 = Integer.parseInt(scanner.nextLine());
                                                    System.out.println("\n Inserire il giorno desiderato:");
                                                    int giorno2 = Integer.parseInt(scanner.nextLine());
                                                    LocalDate dataFine = LocalDate.of(anno2, mese2, giorno2);
                                                    System.out.println("EMISSIONI TRA IL " + dataInizio + " ED IL " + dataFine + " : " + emissioniDAO.getTotaleEmissioniInArcoTemporale(dataInizio, dataFine));
                                                }
                                            } else if (tipoEmissione == 2) {
                                                if (periodo == 4)
                                                    System.out.println("\nINSERIRE LA DATA DI INIZIO DEL PERIODO DESIDERATO");
                                                System.out.println("\n Inserire l'anno desiderato:");
                                                int anno = Integer.parseInt(scanner.nextLine());
                                                System.out.println("\n Inserire il mese desiderato:");
                                                int mese = Integer.parseInt(scanner.nextLine());
                                                System.out.println("\n Inserire il giorno desiderato:");
                                                int giorno = Integer.parseInt(scanner.nextLine());
                                                LocalDate dataInizio = LocalDate.of(anno, mese, giorno);
                                                if (periodo == 1) {
                                                    System.out.println("BIGLIETTI EMESSI IN DATA " + dataInizio + " : " + emissioniDAO.getTotaleBigliettiInBaseAData(dataInizio, ""));
                                                } else if (periodo == 2) {
                                                    System.out.println("BIGLIETTI EMESSI PRIMA DEL " + dataInizio + " : " + emissioniDAO.getTotaleBigliettiInBaseAData(dataInizio, "prima"));
                                                } else if (periodo == 3) {
                                                    System.out.println("BIGLIETTI EMESSI DOPO IL " + dataInizio + " : " + emissioniDAO.getTotaleBigliettiInBaseAData(dataInizio, "dopo"));
                                                } else {
                                                    System.out.println("\nINSERIRE LA DATA DI FINE DEL PERIODO DESIDERATO");
                                                    System.out.println("\n Inserire l'anno desiderato:");
                                                    int anno2 = Integer.parseInt(scanner.nextLine());
                                                    System.out.println("\n Inserire il mese desiderato:");
                                                    int mese2 = Integer.parseInt(scanner.nextLine());
                                                    System.out.println("\n Inserire il giorno desiderato:");
                                                    int giorno2 = Integer.parseInt(scanner.nextLine());
                                                    LocalDate dataFine = LocalDate.of(anno2, mese2, giorno2);
                                                    System.out.println("BIGLIETTI EMESSI TRA IL " + dataInizio + " ED IL " + dataFine + " : " + emissioniDAO.getTotaleBigliettiInArcoTemporale(dataInizio, dataFine));
                                                }
                                            } else {
                                                if (periodo == 4)
                                                    System.out.println("\nINSERIRE LA DATA DI INIZIO DEL PERIODO DESIDERATO");
                                                System.out.println("\n Inserire l'anno desiderato:");
                                                int anno = Integer.parseInt(scanner.nextLine());
                                                System.out.println("\n Inserire il mese desiderato:");
                                                int mese = Integer.parseInt(scanner.nextLine());
                                                System.out.println("\n Inserire il giorno desiderato:");
                                                int giorno = Integer.parseInt(scanner.nextLine());
                                                LocalDate dataInizio = LocalDate.of(anno, mese, giorno);
                                                if (periodo == 1) {
                                                    System.out.println("ABBONAMENTI EMESSI IN DATA " + dataInizio + " : " + emissioniDAO.getTotaleAbbonamentiInBaseAData(dataInizio, ""));
                                                } else if (periodo == 2) {
                                                    System.out.println("ABBONAMENTI EMESSI PRIMA DEL " + dataInizio + " : " + emissioniDAO.getTotaleAbbonamentiInBaseAData(dataInizio, "prima"));
                                                } else if (periodo == 3) {
                                                    System.out.println("ABBONAMENTI EMESSI DOPO IL " + dataInizio + " : " + emissioniDAO.getTotaleAbbonamentiInBaseAData(dataInizio, "dopo"));
                                                } else {
                                                    System.out.println("\nINSERIRE LA DATA DI FINE DEL PERIODO DESIDERATO");
                                                    System.out.println("\n Inserire l'anno desiderato:");
                                                    int anno2 = Integer.parseInt(scanner.nextLine());
                                                    System.out.println("\n Inserire il mese desiderato:");
                                                    int mese2 = Integer.parseInt(scanner.nextLine());
                                                    System.out.println("\n Inserire il giorno desiderato:");
                                                    int giorno2 = Integer.parseInt(scanner.nextLine());
                                                    LocalDate dataFine = LocalDate.of(anno2, mese2, giorno2);
                                                    System.out.println("ABBONAMENTI EMESSI TRA IL " + dataInizio + " ED IL " + dataFine + " : " + emissioniDAO.getTotaleAbbonamentiInArcoTemporale(dataInizio, dataFine));
                                                }
                                            }
                                        }
                                    }
                                } catch (NumberFormatException | NessunElementoTrovatoException | DateTimeException e) {
                                    if (e instanceof NumberFormatException)
                                        System.err.println("\nERRORE: Tipo selezionato non valido, riprovare!\n");
                                    if (e instanceof DateTimeException) {
                                        System.err.println("\nERRORE: Data non valida, riprovare!\n");
                                    } else System.err.println("ERRORE: " + e.getMessage());
                                }
                            }
                        }

                        //OPERAZIONE 3
                        if (operazione == 3) {
                            while (true) {
                                System.out.println("\n Inserire l'id del punto di emissione da verificare");
                                try {
                                    String idPunto = scanner.nextLine();
                                    puntiDAO.getPuntoEmissioneById(idPunto);
                                    System.out.println("\nChe tipo di emissione vuoi verificare?");
                                    System.out.println("1.Tutte");
                                    System.out.println("2.Solo biglietti");
                                    System.out.println("3.Solo abbonamenti");
                                    System.out.println("0.CAMBIA IL TIPO DI OPERAZIONE DA SVOLGERE");
                                    int tipoEmissione = Integer.parseInt(scanner.nextLine());
                                    if (tipoEmissione == 0) break;
                                    if (tipoEmissione < 1 || tipoEmissione > 3)
                                        System.err.println("\nERRORE: Tipo selezionato non valido, riprovare!\n");
                                    else {
                                        if (tipoEmissione == 1) {
                                            System.out.println("TOTALE DELLE EMISSIONI AVVENUTE DAL PUNTO CON ID " + idPunto + " : " + emissioniDAO.getTotaleEmissioniDaPuntoDiEmissione(idPunto));
                                        } else if (tipoEmissione == 2) {
                                            System.out.println("TOTALE DEI BIGLIETTI EMESSI DAL PUNTO CON ID " + idPunto + " : " + emissioniDAO.getTotaleEmissioniDaPuntoDiEmissione(idPunto));
                                        } else {
                                            System.out.println("TOTALE DEGLI ABBONAMENTI EMESSI DAL PUNTO CON ID " + idPunto + " : " + emissioniDAO.getTotaleEmissioniDaPuntoDiEmissione(idPunto));
                                        }
                                    }
                                } catch (NessunElementoTrovatoException | NotFoundException |
                                         IllegalArgumentException e) {
                                    if (e instanceof NumberFormatException)
                                        System.err.println("\nERRORE: Tipo selezionato non valido, riprovare!\n");
                                    if (e instanceof IllegalArgumentException)
                                        System.err.println("\nERRORE: Formato ID non valido, riprovare!\n");
                                    else System.err.println("ERRORE: " + e.getMessage());
                                }
                            }
                        }


                        //OPERAZIONE 4
                        if (operazione == 4) {
                            while (true) {
                                System.out.println("\n Inserire l'id del mezzo da verificare");
                                try {
                                    String idMezzo = scanner.nextLine();
                                    mezziDAO.findById(idMezzo);
                                    System.out.println("\nChe periodo vuoi verificare?");
                                    System.out.println("1.In una specifica data");
                                    System.out.println("2.Prima di una specifica data");
                                    System.out.println("3.Dopo una specifica data");
                                    System.out.println("4.Tra due specifiche date");
                                    System.out.println("5.Fino ad oggi");
                                    System.out.println("0.CAMBIA IL TIPO DI OPERAZIONE DA SVOLGERE");
                                    int periodo = Integer.parseInt(scanner.nextLine());
                                    if (periodo == 0) break;
                                    if (periodo < 1 || periodo > 5)
                                        System.err.println("\nERRORE: Periodo selezionato non valido, riprovare!\n");
                                    else {
                                        if (periodo == 5) {
                                            System.out.println("BIGLIETTI VALIDATI SUL MEZZO " + idMezzo + " FINO AD OGGI : " + emissioniDAO.getBigliettiVidimatiSuUnMezzo(idMezzo));
                                        } else {
                                            if (periodo == 4)
                                                System.out.println("\nINSERIRE LA DATA DI INIZIO DEL PERIODO DESIDERATO");
                                            System.out.println("\n Inserire l'anno desiderato:");
                                            int anno = Integer.parseInt(scanner.nextLine());
                                            System.out.println("\n Inserire il mese desiderato:");
                                            int mese = Integer.parseInt(scanner.nextLine());
                                            System.out.println("\n Inserire il giorno desiderato:");
                                            int giorno = Integer.parseInt(scanner.nextLine());
                                            LocalDate dataInizio = LocalDate.of(anno, mese, giorno);
                                            if (periodo == 1) {
                                                System.out.println("BIGLIETTI VALIDATI SUL MEZZO " + idMezzo + " IN DATA " + dataInizio + " : " + emissioniDAO.getBigliettiVidimatiInUnaDataSuUnMezzo(dataInizio, idMezzo));
                                            } else if (periodo == 2) {
                                                System.out.println("BIGLIETTI VALIDATI SUL MEZZO " + idMezzo + " PRIMA DEL " + dataInizio + " : " + emissioniDAO.getBigliettiVidimatiPrimaDiUnaDataSuUnMezzo(dataInizio, idMezzo));
                                            } else if (periodo == 3) {
                                                System.out.println("BIGLIETTI VALIDATI SUL MEZZO " + idMezzo + " DOPO IL " + dataInizio + " : " + emissioniDAO.getBigliettiVidimatiDopoUnaDataSuUnMezzo(dataInizio, idMezzo));
                                            } else {
                                                System.out.println("\nINSERIRE LA DATA DI FINE DEL PERIODO DESIDERATO");
                                                System.out.println("\n Inserire l'anno desiderato:");
                                                int anno2 = Integer.parseInt(scanner.nextLine());
                                                System.out.println("\n Inserire il mese desiderato:");
                                                int mese2 = Integer.parseInt(scanner.nextLine());
                                                System.out.println("\n Inserire il giorno desiderato:");
                                                int giorno2 = Integer.parseInt(scanner.nextLine());
                                                LocalDate dataFine = LocalDate.of(anno2, mese2, giorno2);
                                                System.out.println("BIGLIETTI VALIDATI SUL MEZZO " + idMezzo + " TRA IL " + dataInizio + " ED IL " + dataFine + " : " + emissioniDAO.getBigliettiVidimatiInUnArcoTemporaleSuUnMezzo(dataInizio, dataFine, idMezzo));
                                            }
                                        }
                                    }
                                } catch (NessunElementoTrovatoException | NotFoundException |
                                         IllegalArgumentException | DateTimeException e) {
                                    if (e instanceof NumberFormatException)
                                        System.err.println("\nERRORE: Tipo selezionato non valido, riprovare!\n");
                                    if (e instanceof IllegalArgumentException)
                                        System.err.println("\nERRORE: Formato ID non valido, riprovare!\n");
                                    if (e instanceof DateTimeException)
                                        System.err.println("\nERRORE: Data non valida, riprovare!\n");
                                    else System.err.println("ERRORE: " + e.getMessage());
                                }
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("\nERRORE: Operazione selezionata non valida, riprovare!\n");
                    }
                }

            }

            //GESTIONE PARCO MEZZI
            if (areaPortale == 3) {
                int operazione;
                while (true) {
                    System.out.println("\nSCEGLIERE IL TIPO DI OPERAZIONE DA SVOLGERE DIGITANDO IL RISPETTIVO NUMERO");
                    System.out.println("1.Aggiungi un mezzo al parco mezzi");
                    System.out.println("2.Ottieni l'operatività' di un mezzo");
                    System.out.println("3.Imposta l'operatività' di un mezzo a 'in servizio'");
                    System.out.println("4.Imposta l'operatività' di un mezzo a 'in manutenzione'");
                    System.out.println("5.Visiona i mezzi in servizio");
                    System.out.println("6.Visiona i mezzi in manutenzione");
                    System.out.println("7.Visiona i periodi di manutenzione di un mezzo");
                    System.out.println("8.Visiona i periodi di servizio di un mezzo");
                    System.out.println("0.ESCI DALLA SIMULAZIONE");
                    try {
                        operazione = Integer.parseInt(scanner.nextLine());
                        if (operazione == 0) break;
                        if (operazione < 1 || operazione > 8)
                            System.err.println("\nERRORE: Operazione selezionata non valida, riprovare!\n");

                        //OPERAZIONE 1
                        if (operazione == 1) {
                            System.out.println("\n Che tipo di mezzo vuoi aggiungere?");
                            System.out.println("1.Autobus");
                            System.out.println("2.Tram");
                            System.out.println("0.CAMBIA IL TIPO DI OPERAZIONE DA SVOLGERE");
                            int tipoMezzo;
                            try {
                                tipoMezzo = Integer.parseInt(scanner.nextLine());
                                if (tipoMezzo == 0) continue;
                                if (tipoMezzo < 1 || tipoMezzo > 2)
                                    System.err.println("\nERRORE: Tipo selezionato non valido, riprovare!\n");
                                else {
                                    if (tipoMezzo == 1) {
                                        mezziDAO.addMezzo("autobus");
                                    } else {
                                        mezziDAO.addMezzo("tram");
                                    }
                                }
                            } catch (NumberFormatException e) {
                                System.err.println("\nERRORE: Tipo selezionato non valido, riprovare!\n");
                            }
                        }

                        //OPERAZIONE 2
                        if (operazione == 2) {
                            System.out.println("\nInserire l'id del mezzo interessato");
                            try {
                                String idMezzo = scanner.nextLine();
                                OperativitàMezzo operativitaAttuale = mezziDAO.getOperativitàAttualeMezzo(idMezzo);
                                if (operativitaAttuale instanceof Servizio)
                                    System.out.println("\nIl mezzo " + idMezzo + " è attualmente in servizio");
                                else {
                                    System.out.println("\nIl mezzo " + idMezzo + " è in manutenzione dal " + operativitaAttuale.getDataInizio());
                                    Manutenzione manutenzione = (Manutenzione) operativitaAttuale;
                                    System.out.println("DESCRIZIONE MANUTENZIONE: " + manutenzione.getDescrizione());
                                }
                            } catch (IllegalArgumentException | NotFoundException e) {
                                if (e instanceof IllegalArgumentException)
                                    System.err.println("\nERRORE: Formato ID non valido, riprovare!\n");
                            }
                        }

                        //OPERAZIONE 3
                        if (operazione == 3) {
                            System.out.println("\nInserire l'id del mezzo interessato");
                            try {
                                String idMezzo = scanner.nextLine();
                                mezziDAO.updateOperativitàAttualeMezzo(idMezzo, "servizio", "");
                            } catch (IllegalArgumentException e) {
                                System.err.println("\nERRORE: Formato ID non valido, riprovare!\n");
                            }
                        }

                        //OPERAZIONE 4
                        if (operazione == 4) {
                            System.out.println("\nInserire l'id del mezzo interessato");
                            try {
                                String idMezzo = scanner.nextLine();
                                System.out.println("\nInserire una descrizione per la manutenzione");
                                String descrizione = scanner.nextLine();
                                mezziDAO.updateOperativitàAttualeMezzo(idMezzo, "manutenzione", descrizione);
                            } catch (IllegalArgumentException e) {
                                System.err.println("\nERRORE: Formato ID non valido, riprovare!\n");
                            }
                        }

                        //OPERAZIONE 5
                        if (operazione == 5) {
                            try {
                                System.out.println("\nLISTA MEZZI IN SERVIZIO");
                                mezziDAO.getMezziInServizio().forEach(System.out::println);
                            } catch (NessunElementoTrovatoException e) {
                                System.err.println("ERRORE: " + e.getMessage());
                            }
                        }

                        //OPERAZIONE 6
                        if (operazione == 6) {
                            try {
                                System.out.println("\nLISTA MEZZI IN MANUTEZIONE");
                                mezziDAO.getMezziInManutenzione().forEach(mezzo -> System.out.println((mezzo instanceof Autobus ? "Autobus " : "Tram ") + mezzo.getId()));
                            } catch (NessunElementoTrovatoException e) {
                                System.err.println("ERRORE: " + e.getMessage());
                            }
                        }

                        //OPERAZIONE 7
                        if (operazione == 7) {
                            System.out.println("\nInserire l'id del mezzo interessato");
                            try {
                                String idMezzo = scanner.nextLine();
                                System.out.println("\nLISTA MANUTENZIONI MEZZO " + idMezzo);
                                mezziDAO.getManutenzioniMezzo(idMezzo).forEach(manutenzione -> System.out.println("Manutenzione " + manutenzione.getId() + " [" + manutenzione.getDataInizio() + " | " + (manutenzione.getDataFine() == null ? "in corso" : manutenzione.getDataFine()) + "] DESCRIZIONE: " + manutenzione.getDescrizione()));
                            } catch (IllegalArgumentException | NessunElementoTrovatoException e) {
                                System.err.println("\nERRORE: Formato ID non valido, riprovare!\n");
                            }
                        }

                        //OPERAZIONE 8
                        if (operazione == 8) {
                            System.out.println("\nInserire l'id del mezzo interessato");
                            try {
                                String idMezzo = scanner.nextLine();
                                System.out.println("\nLISTA SERVIZI MEZZO " + idMezzo);
                                mezziDAO.getServiziMezzo(idMezzo).forEach(servizio -> System.out.println("Servizio " + servizio.getId() + " [" + servizio.getDataInizio() + " | " + (servizio.getDataFine() == null ? "in corso" : servizio.getDataFine()) + "]"));
                            } catch (IllegalArgumentException | NessunElementoTrovatoException e) {
                                System.err.println("\nERRORE: Formato ID non valido, riprovare!\n");
                            }
                        }

                    } catch (NumberFormatException e) {
                        System.err.println("\nERRORE: Operazione selezionata non valida, riprovare!\n");
                    }
                }
            }

            //GESTIONE TRATTE
            if (areaPortale == 4) {
                int operazione;
                while (true) {
                    System.out.println("\nSCEGLIERE IL TIPO DI OPERAZIONE DA SVOLGERE DIGITANDO IL RISPETTIVO NUMERO");
                    System.out.println("1.Aggiungi una nuova tratta");
                    System.out.println("2.Assegna tratta ad un mezzo in una specifica data");
                    System.out.println("3.Aggiorna percorrenza di una tratta effettuata");
                    System.out.println("4.Ottieni il numero di volte che una tratta è stata percorsa da uno specifico mezzo");
                    System.out.println("5.Ottieni la media del tempo di percorrenza di una tratta");
                    System.out.println("6.Ottieni il tempo medio effettivo di percorrenza di una tratta da parte di un mezzo specifico");
                    System.out.println("0.ESCI DALLA SIMULAZIONE");
                    try {
                        operazione = Integer.parseInt(scanner.nextLine());
                        if (operazione == 0) break;
                        if (operazione < 1 || operazione > 6)
                            System.err.println("\nERRORE: Operazione selezionata non valida, riprovare!\n");

                        //OPERAZIONE 1
                        if (operazione == 1) {
                            System.out.println("\nInserire il punto di partenza della nuova tratta:");
                            String partenza = scanner.nextLine();
                            if (partenza.isEmpty()) System.err.println("ERRORE: Inserimento non valido");
                            else {
                                System.out.println("\nInserire il capolinea della nuova tratta");
                                String capolinea = scanner.nextLine();
                                if (capolinea.isEmpty()) System.err.println("ERRORE: Inserimento non valido");
                                else {
                                    System.out.println("\nInserire la durata della percorrenza prevista:");
                                    System.out.println("\nInserire il numero di ore");
                                    try {
                                        int ore = Integer.parseInt(scanner.nextLine());
                                        System.out.println("\nInserire il numero di minuti");
                                        int minuti = Integer.parseInt(scanner.nextLine());
                                        LocalTime percorrenzaPrevista = LocalTime.of(ore, minuti);
                                        tratteDAO.save(new Tratta(partenza, capolinea, percorrenzaPrevista));
                                    } catch (NumberFormatException e) {
                                        System.err.println("\nERRORE: Inserimento non valido, digitare un numero intero");
                                    }
                                }
                            }
                        }

                        //OPERAZIONE 2
                        if (operazione == 2) {
                            System.out.println("\nInserire l'id della tratta da assegnare");
                            String idTratta = scanner.nextLine();
                            try {
                                Tratta tratta = tratteDAO.getTrattaById(idTratta);
                                System.out.println("\nInserire l'id del mezzo a cui assegnare la tratta");
                                String idMezzo = scanner.nextLine();
                                MezzoDiTrasporto mezzo = mezziDAO.findById(idMezzo);
                                System.out.println("\nInserire la data in cui il mezzo dovrà svolgere la tratta");
                                System.out.println("\nInserire il giorno");
                                int giorno = Integer.parseInt(scanner.nextLine());
                                System.out.println("\nInserire il mese");
                                int mese = Integer.parseInt(scanner.nextLine());
                                System.out.println("\nInserire l'anno");
                                int anno = Integer.parseInt(scanner.nextLine());
                                LocalDate dataPercorrenza = LocalDate.of(anno, mese, giorno);
                                assegnazioniDAO.assegnaTrattaAMezzoInUnaSpecificaData(dataPercorrenza, tratta, mezzo);
                            } catch (NotFoundException | IllegalArgumentException | DateTimeException e) {
                                if (e instanceof IllegalArgumentException)
                                    System.err.println("ERRORE: formato ID non valido");
                                if (e instanceof DateTimeException)
                                    System.err.println("\nERRORE: Data non valida, riprovare!\n");
                                else System.err.println("ERRORE: " + e.getMessage());
                            }
                        }

                        //OPERAZIONE 3
                        if (operazione == 3) {
                            System.out.println("\nInserire l'id della tratta effettuata di cui si vuole aggiornare la percorrenza");
                            String idTrattaEffettuata = scanner.nextLine();
                            try {
                                TrattaMezzo trattaEffettuata = assegnazioniDAO.findById(idTrattaEffettuata);
                                System.out.println("\nInserire la durata di percorrenza effettiva della tratta:");
                                System.out.println("\nInserire il numero di ore");
                                try {
                                    int ore = Integer.parseInt(scanner.nextLine());
                                    System.out.println("\nInserire il numero di minuti");
                                    int minuti = Integer.parseInt(scanner.nextLine());
                                    LocalTime percorrenzaEffettiva = LocalTime.of(ore, minuti);
                                    assegnazioniDAO.aggiornaPercorrenzaEffettivaTrattaAssegnata(percorrenzaEffettiva, trattaEffettuata);
                                } catch (NumberFormatException e) {
                                    System.err.println("\nERRORE: Inserimento non valido, digitare un numero intero");
                                }
                            } catch (NotFoundException | IllegalArgumentException | DateTimeException e) {
                                if (e instanceof IllegalArgumentException)
                                    System.err.println("ERRORE: formato ID non valido");
                                if (e instanceof DateTimeException)
                                    System.err.println("\nERRORE: Data non valida, riprovare!\n");
                                else System.err.println("ERRORE: " + e.getMessage());
                            }
                        }

                        //OPERAZIONE 4
                        if (operazione == 4) {
                            System.out.println("\nInserire l'id della tratta desiderata");
                            String idTratta = scanner.nextLine();
                            try {
                                Tratta tratta = tratteDAO.getTrattaById(idTratta);
                                System.out.println("\nInserire l'id del mezzo desiderato");
                                String idMezzo = scanner.nextLine();
                                mezziDAO.findById(idMezzo);
                                long risultato = tratteDAO.countPercorrenzeMezzoSuTratta(idMezzo, idTratta);
                                System.out.println("Il mezzo " + idMezzo + " ha effettuato la tratta " + tratta.getPartenza() + " - " + tratta.getCapolinea() + " " + risultato + " volte");
                            } catch (NotFoundException | IllegalArgumentException e) {
                                if (e instanceof IllegalArgumentException)
                                    System.err.println("ERRORE: formato ID non valido");
                                else System.err.println("ERRORE: " + e.getMessage());
                            }
                        }

                        //OPERAZIONE 5
                        if (operazione == 5) {
                            System.out.println("\nInserire l'id della tratta desiderata");
                            String idTratta = scanner.nextLine();
                            try {
                                Tratta tratta = tratteDAO.getTrattaById(idTratta);
                                LocalTime media = tratteDAO.getTempoMedioEffettivo(idTratta);
                                System.out.println("Il tempo medio impiegato per effettuare la tratta " + tratta.getPartenza() + " - " + tratta.getCapolinea() + " è di " + media.getHour() + " ore e " + media.getMinute() + " minuti");
                            } catch (NotFoundException | IllegalArgumentException | TrattaMaiEffettuataException e) {
                                if (e instanceof IllegalArgumentException)
                                    System.err.println("ERRORE: formato ID non valido");
                                else System.err.println("ERRORE: " + e.getMessage());
                            }
                        }

                        //OPERAZIONE 6
                        if (operazione == 6) {
                            System.out.println("\nInserire l'id della tratta desiderata");
                            String idTratta = scanner.nextLine();
                            try {
                                Tratta tratta = tratteDAO.getTrattaById(idTratta);
                                System.out.println("\nInserire l'id del mezzo desiderato");
                                String idMezzo = scanner.nextLine();
                                mezziDAO.findById(idMezzo);
                                LocalTime media = tratteDAO.getTempoMedioEffettivoInBaseAMezzo(idTratta, idMezzo);
                                System.out.println("Il tempo medio impiegato dal mezzo " + idMezzo + " per effettuare la tratta " + tratta.getPartenza() + " - " + tratta.getCapolinea() + " è di " + media.getHour() + " ore e " + media.getMinute() + " minuti");
                            } catch (NotFoundException | IllegalArgumentException |
                                     TrattaMaiEffettuataDalMezzoException e) {
                                if (e instanceof IllegalArgumentException)
                                    System.err.println("ERRORE: formato ID non valido");
                                else System.err.println("ERRORE: " + e.getMessage());
                            }
                        }

                    } catch (NumberFormatException e) {
                        System.err.println("\nERRORE: Operazione selezionata non valida, riprovare!\n");
                    }
                }
            }*/
        }
    }
}
