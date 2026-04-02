package GiorgiaFormicola.dao;

import GiorgiaFormicola.entities.*;
import GiorgiaFormicola.exceptions.MezzoNonInServizioException;
import GiorgiaFormicola.exceptions.NessunElementoTrovatoException;
import GiorgiaFormicola.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.UUID;

public class MezziDiTrasportoDAO {
    private final EntityManager entityManager;

    public MezziDiTrasportoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    protected void save(MezzoDiTrasporto nuovoMezzo) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(nuovoMezzo);
        Servizio primoServizio = new Servizio(nuovoMezzo);
        entityManager.persist(primoServizio);
        transaction.commit();
        System.out.println("\n" + nuovoMezzo.getClass().getSimpleName() + " " + nuovoMezzo.getId() + " correttamente aggiunto al parco mezzi ed operativo.");
    }

    public MezzoDiTrasporto findById(String idMezzo) {
        MezzoDiTrasporto found = entityManager.find(MezzoDiTrasporto.class, UUID.fromString(idMezzo));
        if (found == null)
            throw new NotFoundException(idMezzo);
        else return found;
    }

    public void delete(String idMezzo) {
        MezzoDiTrasporto found = this.findById(idMezzo);
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(found);
        transaction.commit();
        System.out.println(found.getClass().getSimpleName() + " " + idMezzo + " correttamente eliminato dal parco mezzi.");
    }

    public void addMezzo(String tipo) {
        MezzoDiTrasporto nuovoMezzo;
        if (tipo.equals("autobus")) {
            nuovoMezzo = new Autobus();
        } else if (tipo.equals("tram")) {
            nuovoMezzo = new Tram();
        } else {
            throw new RuntimeException("Tipo di mezzo da aggiungere al parco non valido"); //TODO: aggiungi eccezione mezzo non valido
        }
        this.save(nuovoMezzo);
    }

    public OperativitàMezzo getOperativitàAttualeMezzo(String idMezzo) {
        TypedQuery<OperativitàMezzo> query = entityManager.createQuery("SELECT o FROM OperativitàMezzo o WHERE o.mezzo.id = :idMezzo AND o.dataFine IS NULL", OperativitàMezzo.class);
        query.setParameter("idMezzo", UUID.fromString(idMezzo));
        OperativitàMezzo found = query.getSingleResultOrNull();
        if (found == null) {
            throw new NotFoundException(idMezzo);
        } else {
            /*System.out.println("Il mezzo è in " + (found instanceof Servizio ? "servizio" : "manutenzione"));*/
            return found;
        }
    }

    public void controllaSeInServizio(MezzoDiTrasporto mezzo) {
        TypedQuery<OperativitàMezzo> query = entityManager.createQuery("SELECT o FROM OperativitàMezzo o WHERE o.mezzo.id = :idMezzo AND o.dataFine IS NULL AND o.class = Servizio", OperativitàMezzo.class);
        query.setParameter("idMezzo", mezzo.getId());
        OperativitàMezzo found = query.getSingleResultOrNull();
        if (found == null) throw new MezzoNonInServizioException();
    }

    public void updateOperativitàAttualeMezzo(String idMezzo, String operatività, String descrizione) {
        try {
            OperativitàMezzo found = this.getOperativitàAttualeMezzo(idMezzo);
            if (found instanceof Servizio && operatività.equals("servizio") || found instanceof Manutenzione && operatività.equals("manutenzione"))
                System.err.println("Impossibile effettuare la modifica dell'operatività. Mezzo " + idMezzo + " già in " + operatività + ".");
            else {
                Query updateQuery = entityManager.createQuery("UPDATE OperativitàMezzo o SET o.dataFine = CURRENT_DATE WHERE o.mezzo.id = :idMezzo AND o.dataFine IS NULL ");
                updateQuery.setParameter("idMezzo", UUID.fromString(idMezzo));
                OperativitàMezzo nuovaOperatività;
                if (operatività.equals("servizio")) {
                    nuovaOperatività = new Servizio(found.getMezzo());
                } else {
                    nuovaOperatività = new Manutenzione(found.getMezzo(), descrizione);
                }
                EntityTransaction transaction = entityManager.getTransaction();
                transaction.begin();
                updateQuery.executeUpdate();  //UPDATE DATA FINE ULTIMA OPERATIVITA'
                entityManager.persist(nuovaOperatività); //SALVATAGGIO NUOVA OPERATIVITA'
                transaction.commit();
                System.out.println("Operatività del mezzo " + idMezzo + " modificata con successo. Mezzo attualmente in " + (operatività.equals("servizio") ? "servizio" : "manutenzione"));
            }
        } catch (NotFoundException e) {
            System.err.println("ERRORE: " + e.getMessage());
        }
    }


    public List<Manutenzione> getManutenzioniMezzo(String idMezzo) {
        TypedQuery<Manutenzione> query = entityManager.createQuery("SELECT m FROM Manutenzione m WHERE m.mezzo.id = :idMezzo ORDER BY m.dataFine DESC", Manutenzione.class);
        query.setParameter("idMezzo", UUID.fromString(idMezzo));
        List<Manutenzione> risultato = query.getResultList();
        if (risultato.isEmpty()) {
            throw new NessunElementoTrovatoException();
        } else {
            return risultato;
        }
    }

    public List<Servizio> getServiziMezzo(String idMezzo) {
        TypedQuery<Servizio> query = entityManager.createQuery("SELECT s FROM Servizio s WHERE s.mezzo.id = :idMezzo ORDER BY s.dataFine DESC", Servizio.class);
        query.setParameter("idMezzo", UUID.fromString(idMezzo));
        List<Servizio> risultato = query.getResultList();
        if (risultato.isEmpty()) {
            throw new NessunElementoTrovatoException();
        } else {
            return risultato;
        }
    }

    public List<MezzoDiTrasporto> getMezziInServizio() {
        TypedQuery<MezzoDiTrasporto> query = entityManager.createQuery("SELECT o.mezzo FROM OperativitàMezzo o WHERE o.dataFine IS NULL AND o.class = Servizio ORDER BY o.dataFine DESC", MezzoDiTrasporto.class);
        List<MezzoDiTrasporto> found = query.getResultList();
        if (found.isEmpty()) throw new NessunElementoTrovatoException();
        else return found;
    }

    public List<MezzoDiTrasporto> getMezziInManutenzione() {
        TypedQuery<MezzoDiTrasporto> query = entityManager.createQuery("SELECT o.mezzo FROM OperativitàMezzo o WHERE o.dataFine IS NULL AND o.class = Manutenzione ORDER BY o.dataFine DESC", MezzoDiTrasporto.class);
        List<MezzoDiTrasporto> found = query.getResultList();
        if (found.isEmpty()) throw new NessunElementoTrovatoException();
        else return found;
    }


}
