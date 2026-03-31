package GiorgiaFormicola.dao;

import GiorgiaFormicola.entities.*;
import GiorgiaFormicola.enums.TipoAbbonamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class EmissioniDAO {
    private final EntityManager entityManager;

    public EmissioniDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Emissione nuovaEmissione) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(nuovaEmissione);
        transaction.commit();
        System.out.println((nuovaEmissione instanceof Biglietto ? "Biglietto" : "Abbonamento") + " acquistato con successo");
    }

    public Emissione findById(String idEmissione) {
        Emissione found = entityManager.find(Emissione.class, UUID.fromString(idEmissione));
        if (found == null)
            throw new RuntimeException("Emissione non trovata");//TODO: aggiungi eccezione not found
        else return found;
    }

    public void delete(String idEmissione) {
        Emissione found = this.findById(idEmissione);
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(found);
        transaction.commit();
        System.out.println(found.getClass().getSimpleName() + " " + idEmissione + " correttamente eliminata dallo storico.");
    }

    public void acquistaBiglietto(PuntiEmissione puntoEmissione) {
        if (!puntoEmissione.isAttivo())
            throw new RuntimeException("Impossibile acquistare il biglietto, distributore automatico non in funzione");
        else {
            Emissione nuovaBiglietto = new Biglietto(puntoEmissione);
            this.save(nuovaBiglietto);
        }
    }

    public void acquistaAbbonamento(PuntiEmissione puntoEmissione, Tessera tessera, TipoAbbonamento tipo) {
        if (!puntoEmissione.isAttivo())
            throw new RuntimeException("Impossibile acquistare l'abbonamento, distributore automatico non in funzione");
        if (tessera.getDataScadenza().isBefore(LocalDate.now()))
            throw new RuntimeException("Impossibile acquistare l'abbonamento, tessera scaduta il " + tessera.getDataScadenza());
        else {
            Emissione nuovoAbbonamento = new Abbonamento(puntoEmissione, tessera, tipo);
            this.save(nuovoAbbonamento);
        }
    }

    public Abbonamento controllaValiditàAbbonamento(Tessera tessera) {
        Abbonamento abbonamentoUtente;
        if (tessera.getDataScadenza().isBefore(LocalDate.now())) {
            throw new RuntimeException("Tessera utente scaduta");
        } else {
            TypedQuery<Abbonamento> query = entityManager.createQuery("SELECT a FROM Abbonamento a WHERE a.tessera.id = :idTessera ORDER BY a.dataEmissione DESC", Abbonamento.class);
            query.setMaxResults(1);
            List<Abbonamento> found = query.getResultList();
            if (found.isEmpty()) {
                throw new RuntimeException("Nessun abbonamento trovato");
            } else {
                abbonamentoUtente = found.getFirst();
                if (abbonamentoUtente.getDataScadenza().isBefore(LocalDate.now())) {
                    System.out.println("Abbonamento scaduto in data " + abbonamentoUtente.getDataScadenza());
                } else {
                    System.out.println("Abbonamento valido. Scadenza prevista in data " + abbonamentoUtente.getDataScadenza());
                }
                return abbonamentoUtente;
            }
        }
    }

    public void rinnovaAbbonamento(PuntiEmissione puntoEmissione, Tessera tessera) {
        Abbonamento abbonamentoDaRinnovare = this.controllaValiditàAbbonamento(tessera);
        if (abbonamentoDaRinnovare.getDataScadenza().isAfter(LocalDate.now()))
            throw new RuntimeException("Impossibile rinnovare l'abbonamento. Abbonamento ancora in corso di validità");
        else {
            this.acquistaAbbonamento(puntoEmissione, tessera, abbonamentoDaRinnovare.getTipo());
        }
        System.out.println("Abbonamento rinnovato con successo");
    }

    public void rinnovaEModificaAbbonamento(PuntiEmissione puntoEmissione, Tessera tessera) {
        Abbonamento abbonamentoDaModificare = this.controllaValiditàAbbonamento(tessera);
        if (abbonamentoDaModificare.getDataScadenza().isAfter(LocalDate.now()))
            throw new RuntimeException("Impossibile modificare l'abbonamento. Abbonamento ancora in corso di validità");
        else {
            if (abbonamentoDaModificare.getTipo().equals(TipoAbbonamento.MENSILE))
                this.acquistaAbbonamento(puntoEmissione, tessera, TipoAbbonamento.SETTIMANALE);
            else this.acquistaAbbonamento(puntoEmissione, tessera, TipoAbbonamento.MENSILE);
        }
        System.out.println("Abbonamento modificato con successo");
    }
    
     /*public void controllaValiditàAbbonamento(Utente utente) {
        if (utente.getTessera() == null) {
            throw new RuntimeException("Nessuna tessera associata all'utente");
        } else if (utente.getTessera().getDataScadenza().isBefore(LocalDate.now())) {
            throw new RuntimeException("Tessera utente scaduta");
        } else {
            TypedQuery<Abbonamento> query = entityManager.createQuery("SELECT a FROM Abbonamento a WHERE a.tessera.id = :idTessera ORDER BY a.dataEmissione DESC", Abbonamento.class);
            query.setMaxResults(1);
            List<Abbonamento> found = query.getResultList();
            if (found.isEmpty()) {
                throw new RuntimeException("Nessun abbonamento trovato");
            } else {
                Abbonamento abbonamentoUtente = found.getFirst();
                if (abbonamentoUtente.getDataScadenza().isBefore(LocalDate.now())) System.out.println("Abbonamento scaduto in data " + abbonamentoUtente.getDataScadenza());
                else System.out.println("Abbonamento valido. Scadenza prevista in data " +abbonamentoUtente.getDataScadenza());
            }
        }
    }*/


    public void utilizzaEmissione(Emissione emissione, MezzoDiTrasporto mezzo) {
        if (emissione instanceof Biglietto) { //VIDIMA BIGLIETTO
            Biglietto biglietto = (Biglietto) emissione;
            if (biglietto.getDataVidimazione() != null)
                throw new RuntimeException("Impossibile vidimare il biglietto, biglietto già utilizzato");
            if (mezzo.getBigliettiValidati().size() == mezzo.getCapienza())
                throw new RuntimeException("Impossibile validare il biglietto, " + mezzo.getClass().getSimpleName() + " pieno. Aspettare la prossima corsa");
            else {
                Query updateQuery = entityManager.createQuery("UPDATE Biglietto b SET b.dataVidimazione = CURRENT_DATE, b.mezzo = :mezzo  WHERE b.id = :idBiglietto ");
                updateQuery.setParameter("mezzo", mezzo);
                updateQuery.setParameter("idBiglietto", biglietto.getId());
                EntityTransaction transaction = entityManager.getTransaction();
                transaction.begin();
                updateQuery.executeUpdate();
                transaction.commit();
                System.out.println("Biglietto vidimato in data " + LocalDate.now() + " sul mezzo " + mezzo.getId());
            }
        } else { //UTILIZZA ABBONAMENTO
            Abbonamento abbonamento = (Abbonamento) emissione;
            if (abbonamento.getTessera().getDataScadenza().isBefore(LocalDate.now()))
                throw new RuntimeException("Impossibile utilizzare l'abbonamento, tessera scaduta il " + abbonamento.getTessera().getDataScadenza());
            if (abbonamento.getDataScadenza().isBefore(LocalDate.now()))
                throw new RuntimeException("Impossibile utilizzare l'abbonamento, abbonamento scaduto il" + abbonamento.getDataScadenza());
            else System.out.println("Abbonamento valido, salire a bordo");
        }
    }

    public List<Emissione> getTotaleEmissioniInBaseAData(LocalDate data, String quando) {
        TypedQuery<Emissione> query;
        if (quando.equals("prima")) {
            query = entityManager.createQuery("SELECT e FROM Emissione e WHERE e.dataEmissione < :data ", Emissione.class);
        } else if (quando.equals("dopo")) {
            query = entityManager.createQuery("SELECT e FROM Emissione e WHERE e.dataEmissione > :data ", Emissione.class);
        } else {
            query = entityManager.createQuery("SELECT e FROM Emissione e WHERE e.dataEmissione = :data ", Emissione.class);
        }
        query.setParameter("data", data);
        List<Emissione> found = query.getResultList();
        if (found.isEmpty()) throw new RuntimeException("Nessuna emissione trovata");
        else return found;
    }


    public List<Biglietto> getTotaleBigliettiInBaseAData(LocalDate data, String quando) {
        TypedQuery<Biglietto> query;
        if (quando.equals("prima")) {
            query = entityManager.createQuery("SELECT b FROM Biglietto b WHERE b.dataEmissione < :data ", Biglietto.class);
        } else if (quando.equals("dopo")) {
            query = entityManager.createQuery("SELECT b FROM Biglietto b WHERE b.dataEmissione > :data ", Biglietto.class);
        } else {
            query = entityManager.createQuery("SELECT b FROM Biglietto b WHERE b.dataEmissione = :data ", Biglietto.class);
        }
        query.setParameter("data", data);
        List<Biglietto> found = query.getResultList();
        if (found.isEmpty()) throw new RuntimeException("Nessun biglietto trovato");
        else return found;
    }

    public List<Abbonamento> getTotaleAbbonamentiInBaseAData(LocalDate data, String quando) {
        TypedQuery<Abbonamento> query;
        if (quando.equals("prima")) {
            query = entityManager.createQuery("SELECT a FROM Abbonamento a WHERE a.dataEmissione < :data ", Abbonamento.class);
        } else if (quando.equals("dopo")) {
            query = entityManager.createQuery("SELECT a FROM Abbonamento a WHERE a.dataEmissione > :data ", Abbonamento.class);
        } else {
            query = entityManager.createQuery("SELECT a FROM Abbonamento a WHERE a.dataEmissione = :data ", Abbonamento.class);
        }
        query.setParameter("data", data);
        List<Abbonamento> found = query.getResultList();
        if (found.isEmpty()) throw new RuntimeException("Nessun abbonamento trovato");
        else return found;
    }

    public List<Emissione> getTotaleEmissioniInArcoTemporale(LocalDate dataInizio, LocalDate dataFine) {
        TypedQuery<Emissione> query = entityManager.createQuery("SELECT e FROM Emissione e WHERE  e.dataEmissione BETWEEN :dataInizio AND :dataFine", Emissione.class);
        query.setParameter("dataInizio", dataInizio);
        query.setParameter("dataFine", dataFine);
        List<Emissione> found = query.getResultList();
        if (found.isEmpty()) throw new RuntimeException("Nessuna emissione trovata");
        else return found;
    }

    public List<Biglietto> getTotaleBigliettiInArcoTemporale(LocalDate dataInizio, LocalDate dataFine) {
        TypedQuery<Biglietto> query = entityManager.createQuery("SELECT b FROM Biglietto b WHERE b.dataEmissione BETWEEN :dataInizio AND :dataFine", Biglietto.class);
        query.setParameter("dataInizio", dataInizio);
        query.setParameter("dataFine", dataFine);
        List<Biglietto> found = query.getResultList();
        if (found.isEmpty()) throw new RuntimeException("Nessun biglietto trovato");
        else return found;
    }

    public List<Abbonamento> getTotaleAbbonamentiInArcoTemporale(LocalDate dataInizio, LocalDate dataFine) {
        TypedQuery<Abbonamento> query = entityManager.createQuery("SELECT a FROM Abbonamento a WHERE a.dataEmissione BETWEEN :dataInizio AND :dataFine", Abbonamento.class);
        query.setParameter("dataInizio", dataInizio);
        query.setParameter("dataFine", dataFine);
        List<Abbonamento> found = query.getResultList();
        if (found.isEmpty()) throw new RuntimeException("Nessun abbonamento trovato");
        else return found;
    }

    public List<Object[]> getTotaleEmissioniPerPuntoDiEmissione() {
        TypedQuery<Object[]> query = entityManager.createQuery("SELECT e.puntiEmissione, COUNT(e) FROM Emissione e GROUP BY e.puntiEmissione", Object[].class);
        List<Object[]> found = query.getResultList();
        if (found.isEmpty()) throw new RuntimeException("Nessuna emissione trovata");
        return query.getResultList();
    }

    public List<Object[]> getTotaleBigliettiPerPuntoDiEmissione() {
        TypedQuery<Object[]> query = entityManager.createQuery("SELECT b.puntiEmissione, COUNT(b) FROM Biglietto b GROUP BY b.puntiEmissione", Object[].class);
        List<Object[]> found = query.getResultList();
        if (found.isEmpty()) throw new RuntimeException("Nessun biglietto trovato");
        return query.getResultList();
    }

    public List<Object[]> getTotaleAbbonamentiPerPuntoDiEmissione() {
        TypedQuery<Object[]> query = entityManager.createQuery("SELECT a.puntiEmissione, COUNT(a) FROM Abbonamento a GROUP BY a.puntiEmissione", Object[].class);
        List<Object[]> found = query.getResultList();
        if (found.isEmpty()) throw new RuntimeException("Nessun abbonamento trovato");
        return query.getResultList();
    }

    public List<Emissione> getTotaleEmissioniDaPuntoDiEmissione(String idPunto) {
        TypedQuery<Emissione> query = entityManager.createQuery("SELECT e FROM Emissione e WHERE e.puntiEmissione.id = :idPunto", Emissione.class);
        query.setParameter("idPunto", UUID.fromString(idPunto));
        List<Emissione> found = query.getResultList();
        if (found.isEmpty()) throw new RuntimeException("Nessuna emissione trovata");
        return query.getResultList();
    }

    public List<Biglietto> getTotaleBigliettiDaPuntoDiEmissione(String idPunto) {
        TypedQuery<Biglietto> query = entityManager.createQuery("SELECT b FROM Biglietto b WHERE b.puntiEmissione.id = :idPunto", Biglietto.class);
        query.setParameter("idPunto", UUID.fromString(idPunto));
        List<Biglietto> found = query.getResultList();
        if (found.isEmpty()) throw new RuntimeException("Nessun biglietto trovato");
        return query.getResultList();
    }

    public List<Abbonamento> getTotaleAbbonamentiDaPuntoDiEmissione(String idPunto) {
        TypedQuery<Abbonamento> query = entityManager.createQuery("SELECT a FROM Abbonamento a WHERE a.puntiEmissione.id = :idPunto", Abbonamento.class);
        query.setParameter("idPunto", UUID.fromString(idPunto));
        List<Abbonamento> found = query.getResultList();
        if (found.isEmpty()) throw new RuntimeException("Nessun abbonamento trovato");
        return query.getResultList();
    }

    public List<Emissione> getTotaleEmissioniInBaseADataDaPuntoDiEmissione(LocalDate data, String quando, String idPunto) {
        TypedQuery<Emissione> query;
        if (quando.equals("prima")) {
            query = entityManager.createQuery("SELECT e FROM Emissione e WHERE e.dataEmissione < :data AND e.puntiEmissione.id = :idPunto ", Emissione.class);
        } else if (quando.equals("dopo")) {
            query = entityManager.createQuery("SELECT e FROM Emissione e WHERE e.dataEmissione > :data AND e.puntiEmissione.id = :idPunto ", Emissione.class);
        } else {
            query = entityManager.createQuery("SELECT e FROM Emissione e WHERE e.dataEmissione = :data AND e.puntiEmissione.id = :idPunto ", Emissione.class);
        }
        query.setParameter("data", data);
        query.setParameter("idPunto", idPunto);
        List<Emissione> found = query.getResultList();
        if (found.isEmpty()) throw new RuntimeException("Nessuna emissione trovata");
        else return found;
    }

    public List<Biglietto> getTotaleBigliettiInBaseADataDaPuntoDiEmissione(LocalDate data, String quando, String idPunto) {
        TypedQuery<Biglietto> query;
        if (quando.equals("prima")) {
            query = entityManager.createQuery("SELECT b FROM Biglietto b WHERE b.dataEmissione < :data AND b.puntiEmissione.id = :idPunto", Biglietto.class);
        } else if (quando.equals("dopo")) {
            query = entityManager.createQuery("SELECT b FROM Biglietto b WHERE b.dataEmissione > :data AND b.puntiEmissione.id = :idPunto", Biglietto.class);
        } else {
            query = entityManager.createQuery("SELECT b FROM Biglietto b WHERE b.dataEmissione = :data AND b.puntiEmissione.id = :idPunto", Biglietto.class);
        }
        query.setParameter("data", data);
        query.setParameter("idPunto", idPunto);
        List<Biglietto> found = query.getResultList();
        if (found.isEmpty()) throw new RuntimeException("Nessun biglietto trovato");
        else return found;
    }

    public List<Abbonamento> getTotaleAbbonamentiInBaseADataDaPuntoDiEmissione(LocalDate data, String quando, String idPunto) {
        TypedQuery<Abbonamento> query;
        if (quando.equals("prima")) {
            query = entityManager.createQuery("SELECT a FROM Abbonamento a WHERE a.dataEmissione < :data AND a.puntiEmissione.id = :idPunto ", Abbonamento.class);
        } else if (quando.equals("dopo")) {
            query = entityManager.createQuery("SELECT a FROM Abbonamento a WHERE a.dataEmissione > :data AND a.puntiEmissione.id = :idPunto", Abbonamento.class);
        } else {
            query = entityManager.createQuery("SELECT a FROM Abbonamento a WHERE a.dataEmissione = :data AND a.puntiEmissione.id = :idPunto", Abbonamento.class);
        }
        query.setParameter("data", data);
        query.setParameter("idPunto", idPunto);
        List<Abbonamento> found = query.getResultList();
        if (found.isEmpty()) throw new RuntimeException("Nessun abbonamento trovato");
        else return found;
    }

    public List<Emissione> getTotaleEmissioniInArcoTemporaleDaPuntoDiEmissione(LocalDate dataInizio, LocalDate dataFine, String idPunto) {
        TypedQuery<Emissione> query = entityManager.createQuery("SELECT e FROM Emissione e WHERE e.puntiEmissione.id = :idPunto AND e.dataEmissione BETWEEN :dataInizio AND :dataFine", Emissione.class);
        query.setParameter("dataInizio", dataInizio);
        query.setParameter("dataFine", dataFine);
        query.setParameter("idPunto", idPunto);
        List<Emissione> found = query.getResultList();
        if (found.isEmpty()) throw new RuntimeException("Nessuna emissione trovata");
        else return found;
    }

    public List<Biglietto> getTotaleBigliettiInArcoTemporaleDaPuntoDiEmissione(LocalDate dataInizio, LocalDate dataFine, String idPunto) {
        TypedQuery<Biglietto> query = entityManager.createQuery("SELECT b FROM Biglietto b WHERE b.puntiEmissione.id = :idPunto AND b.dataEmissione BETWEEN :dataInizio AND :dataFine", Biglietto.class);
        query.setParameter("dataInizio", dataInizio);
        query.setParameter("dataFine", dataFine);
        query.setParameter("idPunto", idPunto);
        List<Biglietto> found = query.getResultList();
        if (found.isEmpty()) throw new RuntimeException("Nessun biglietto trovato");
        else return found;
    }

    public List<Abbonamento> getTotaleAbbonamentiInArcoTemporaleDaPuntoDiEmissione(LocalDate dataInizio, LocalDate dataFine, String idPunto) {
        TypedQuery<Abbonamento> query = entityManager.createQuery("SELECT a FROM Abbonamento a WHERE a.puntiEmissione.id = :idPunto AND a.dataEmissione BETWEEN :dataInizio AND :dataFine", Abbonamento.class);
        query.setParameter("dataInizio", dataInizio);
        query.setParameter("dataFine", dataFine);
        query.setParameter("idPunto", idPunto);
        List<Abbonamento> found = query.getResultList();
        if (found.isEmpty()) throw new RuntimeException("Nessun abbonamento trovato");
        else return found;
    }


}
