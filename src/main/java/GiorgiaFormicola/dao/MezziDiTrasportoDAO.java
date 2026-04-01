package GiorgiaFormicola.dao;

import GiorgiaFormicola.entities.*;
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
        System.out.println(nuovoMezzo.getClass().getSimpleName() + " " + nuovoMezzo.getId() + " correttamente aggiunto al parco mezzi ed operativo.");
    }

    public MezzoDiTrasporto findById(String idMezzo) {
        MezzoDiTrasporto found = entityManager.find(MezzoDiTrasporto.class, UUID.fromString(idMezzo));
        if (found == null)
            throw new RuntimeException("Mezzo di trasporto non trovato");//TODO: aggiungi eccezione not found
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

    public void updateOperativitàAttualeMezzo(String idMezzo, String operatività, String descrizione) {
        OperativitàMezzo found = this.getOperativitàAttualeMezzo(idMezzo);
        if (found instanceof Servizio && operatività.equals("servizio") || found instanceof Manutenzione && operatività.equals("manutenzione"))
            System.out.println("Mezzo " + idMezzo + " già in " + operatività + ".");
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
            System.out.println("Mezzo " + idMezzo + " in " + (operatività.equals("servizio") ? "servizio" : "manutenzione"));
        }
    }


    public List<Manutenzione> getManutenzioniMezzo(String idMezzo) {
        TypedQuery<Manutenzione> query = entityManager.createQuery("SELECT m FROM Manutenzione m WHERE m.mezzo.id = :idMezzo", Manutenzione.class);
        query.setParameter("idMezzo", UUID.fromString(idMezzo));
        List<Manutenzione> risultato = query.getResultList();
        if (risultato.isEmpty()) {
            throw new RuntimeException("Il mezzo " + idMezzo + " non è mai stato in manutenzione");
        } else {
            return risultato;
        }
    }

    public List<Servizio> getServiziMezzo(String idMezzo) {
        TypedQuery<Servizio> query = entityManager.createQuery("SELECT s FROM Servizio s WHERE s.mezzo.id = :idMezzo", Servizio.class);
        query.setParameter("idMezzo", UUID.fromString(idMezzo));
        List<Servizio> risultato = query.getResultList();
        if (risultato.isEmpty()) {
            throw new RuntimeException("Mezzo " + idMezzo + " non trovato");
        } else {
            return risultato;
        }
    }

    public List<MezzoDiTrasporto> getMezziInServizio() {
        TypedQuery<MezzoDiTrasporto> query = entityManager.createQuery("SELECT o.mezzo FROM OperativitàMezzo o WHERE o.dataFine IS NULL AND o.class = Servizio", MezzoDiTrasporto.class);
        List<MezzoDiTrasporto> found = query.getResultList();
        if (found.isEmpty()) throw new RuntimeException("Nessun mezzo in servizio");
        else return found;
    }

    public List<MezzoDiTrasporto> getMezziInManutenzione() {
        TypedQuery<MezzoDiTrasporto> query = entityManager.createQuery("SELECT o.mezzo FROM OperativitàMezzo o WHERE o.dataFine IS NULL AND o.class = Manutenzione", MezzoDiTrasporto.class);
        List<MezzoDiTrasporto> found = query.getResultList();
        if (found.isEmpty()) throw new RuntimeException("Nessun mezzo in manutenzione");
        else return found;
    }


}
