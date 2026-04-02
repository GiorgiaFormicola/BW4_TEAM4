package GiorgiaFormicola.dao;

import GiorgiaFormicola.entities.MezzoDiTrasporto;
import GiorgiaFormicola.entities.Servizio;
import GiorgiaFormicola.entities.Tratta;
import GiorgiaFormicola.entities.TrattaMezzo;
import GiorgiaFormicola.exceptions.MezzoAssegnatoException;
import GiorgiaFormicola.exceptions.MezzoNonInServizioException;
import GiorgiaFormicola.exceptions.NotFoundException;
import GiorgiaFormicola.exceptions.TrattaAssegnataException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public class TrattaMezzoDAO {
    private final EntityManager em;
    private final MezziDiTrasportoDAO mezziDAO;

    public TrattaMezzoDAO(EntityManager em) {
        this.em = em;
        this.mezziDAO = new MezziDiTrasportoDAO(em);
    }

    public void save(TrattaMezzo assegnazione) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(assegnazione);
        transaction.commit();
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

    public void controllaSeTrattaGiàAssegnataInUnaSpecificaData(LocalDate data, Tratta tratta) {
        TypedQuery<TrattaMezzo> query = em.createQuery("SELECT t FROM TrattaMezzo t WHERE t.dataInizio = :data AND t.tratta.id = :idTratta", TrattaMezzo.class);
        query.setParameter("data", data);
        query.setParameter("idTratta", tratta.getId());
        List<TrattaMezzo> found = query.getResultList();
        if (!found.isEmpty()) throw new TrattaAssegnataException();
    }

    public void controllaSeMezzoGiàAssegnatoInUnaSpecificaData(LocalDate data, MezzoDiTrasporto mezzo) {
        TypedQuery<TrattaMezzo> query = em.createQuery("SELECT t FROM TrattaMezzo t WHERE t.dataInizio = :data AND t.mezzo.id = :idMezzo", TrattaMezzo.class);
        query.setParameter("data", data);
        query.setParameter("idMezzo", mezzo.getId());
        List<TrattaMezzo> found = query.getResultList();
        if (!found.isEmpty()) throw new MezzoAssegnatoException();
    }

    public void assegnaTrattaAMezzoInUnaSpecificaData(LocalDate data, Tratta tratta, MezzoDiTrasporto mezzo) {
        try {
            this.controllaSeTrattaGiàAssegnataInUnaSpecificaData(data, tratta);
            mezziDAO.controllaSeInServizio(mezzo);
            this.controllaSeMezzoGiàAssegnatoInUnaSpecificaData(data, mezzo);
            TrattaMezzo nuovaAssegnazione = new TrattaMezzo(mezzo, tratta, data);
            this.save(nuovaAssegnazione);
            System.out.println("Tratta assegnata con successo al mezzo " + mezzo.getId() + " in data " + data);
        } catch (MezzoNonInServizioException | MezzoAssegnatoException | TrattaAssegnataException e) {
            System.err.println("ERRORE: " + e.getMessage());
        }
    }

    public TrattaMezzo findById(String idAssegnazione) {
        TrattaMezzo found = em.find(TrattaMezzo.class, UUID.fromString(idAssegnazione));
        if (found == null)
            throw new NotFoundException(idAssegnazione);
        else return found;
    }

    public void aggiornaPercorrenzaEffettivaTrattaAssegnata(LocalTime percorrenzaEffettiva, TrattaMezzo trattaEffettuata) {
        Query query = em.createQuery("UPDATE TrattaMezzo t SET t.percorrenza = :percorrenzaEffettiva WHERE t.id = :trattaId AND t.percorrenza IS NULL");
        query.setParameter("percorrenzaEffettiva", percorrenzaEffettiva);
        query.setParameter("trattaId", trattaEffettuata.getId());
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        int update = query.executeUpdate();
        transaction.commit();
        if (update == 0)
            System.err.println("ERRORE: impossibile aggiornare la percorrenza, percorrenza della tratta già aggiornata");
        else System.out.println("Percorrenza effettiva della tratta effettuata aggiornata con successo");
    }
}