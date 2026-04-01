package GiorgiaFormicola;

import GiorgiaFormicola.dao.EmissioniDAO;
import GiorgiaFormicola.dao.PuntiEmissioneDAO;
import GiorgiaFormicola.entities.PuntiEmissione;
import GiorgiaFormicola.exceptions.NotFoundException;
import GiorgiaFormicola.exceptions.PuntoDiEmissioneNonAttivo;
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
                    System.out.println("\nERRORE: Simulazione selezionata non valida, riprovare! \n");
                else break;
            } catch (NumberFormatException e) {
                System.out.println("\nERRORE: Simulazione selezionata non valida, riprovare! \n");
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
                    System.out.println("\nERRORE: Interazione selezionata non valida, riprovare! \n");
                else break;
            } catch (NumberFormatException e) {
                System.out.println("\nERRORE: Interazione selezionata non valida, riprovare! \n");
            }
        }

        System.out.print("\nINTERAZIONE SCELTA: " + switch (interazione) {
            case 1 -> "CON PERSONA NON REGISTRATA";
            case 2 -> "CON UTENTE";
            case 3 -> "CON AMMINISTRATORE";
            default -> "INTERAZIONE NON VALIDA";
        });
        System.out.println();

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
                        System.out.println("\nERRORE: Operazione selezionata non valida, riprovare!\n");
                    else {
                        System.out.println("\nInserire l'id del punto di emissione in cui ti trovi per acquistare il biglietto");
                        String idPuntoEmissione = scanner.nextLine();
                        PuntiEmissione puntoEmissione;
                        try {
                            puntoEmissione = puntiDAO.getPuntoEmissioneById(idPuntoEmissione);
                            emissioniDAO.acquistaBiglietto(puntoEmissione);
                        } catch (NotFoundException | IllegalArgumentException | PuntoDiEmissioneNonAttivo e) {
                            System.out.println(e.getMessage());
                        }

                       /* Il punto emissione con id a229f590-8a19-40fb-b93d-55816ee258e3 è stato creato TRUE
                        Il punto emissione con id 6ddc561e-1892-4172-9ce0-fe22fd642383 è stato creato FALSE
                        Il punto emissione con id 71133d75-ff97-47af-86f7-cc10dc277f02 è stato creato TRUE*/
                    }
                } catch (NumberFormatException e) {
                    System.out.println("\nERRORE: Operazione selezionata non valida, riprovare! \n");
                }

            }
        }


    }
}
