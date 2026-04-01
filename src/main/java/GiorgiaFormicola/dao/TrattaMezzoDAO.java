package GiorgiaFormicola.dao;

import GiorgiaFormicola.entities.TrattaMezzo;
import GiorgiaFormicola.entities.Servizio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class TrattaMezzoDAO {
    private EntityManager em;
    private MezziDiTrasportoDAO mezziDAO;

    public TrattaMezzoDAO(EntityManager em) {
        this.em = em;
        this.mezziDAO = new MezziDiTrasportoDAO(em);
    }

    public void registraPercorrenza(TrattaMezzo tm) {
        try {
//           controllo se il mezzo è in servizio
            Object statoAttuale = mezziDAO.getOperativitàAttualeMezzo(tm.getMezzo().getId().toString());

            if (!(statoAttuale instanceof Servizio)) {
                System.err.println("ERRORE: Il mezzo " + tm.getMezzo().getId() + " è in MANUTENZIONE.");
                return;
            }

            EntityTransaction tdt = em.getTransaction();
            tdt.begin();
            em.persist(tm);
            tdt.commit();
            System.out.println("Tratta assegnata con successo al mezzo.");

        } catch (Exception e) {
            System.err.println("Errore durante la registrazione: " + e.getMessage());
        }
    }
}