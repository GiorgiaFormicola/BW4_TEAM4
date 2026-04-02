package GiorgiaFormicola.dao;

import GiorgiaFormicola.entities.*;
import GiorgiaFormicola.enums.TipoAbbonamento;
import GiorgiaFormicola.exceptions.*;
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
    }

    public Emissione findById(String idEmissione) {
        Emissione found = entityManager.find(Emissione.class, UUID.fromString(idEmissione));
        if (found == null)
            throw new NotFoundException(idEmissione);
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

   /* public void acquistaBiglietto(PuntiEmissione puntoEmissione) {
        if (!puntoEmissione.isAttivo())
            throw new PuntoDiEmissioneNonAttivoException();
        else {
            Emissione nuovaBiglietto = new Biglietto(puntoEmissione);
            this.save(nuovaBiglietto);
            System.out.println("\nBiglietto acquistato con successo!\n");
        }
    }*/

    public void acquistaBiglietto(PuntiEmissione puntoEmissione) {

        Emissione nuovoBiglietto;
        if (puntoEmissione instanceof DistributoriAutomatici) {
            DistributoriAutomatici distributore = (DistributoriAutomatici) puntoEmissione;
            if (!distributore.isAttivo())
                throw new PuntoDiEmissioneNonAttivoException();
            else {
                nuovoBiglietto = new Biglietto(distributore);
            }
        } else {
            nuovoBiglietto = new Biglietto(puntoEmissione);
        }
        this.save(nuovoBiglietto);
    }


   /* public void acquistaAbbonamento(PuntiEmissione puntoEmissione, Tessera tessera, TipoAbbonamento tipo) {
        if (!puntoEmissione.isAttivo())
            throw new PuntoDiEmissioneNonAttivoException();
        if (tessera.getDataScadenza().isBefore(LocalDate.now()))
            throw new TesseraScadutaException();
        else {
            Emissione nuovoAbbonamento = new Abbonamento(puntoEmissione, tessera, tipo);
            this.save(nuovoAbbonamento);
        }
    }*/

    public void acquistaAbbonamento(PuntiEmissione puntoEmissione, Tessera tessera, TipoAbbonamento tipo) {
        Emissione nuovoAbbonamento;
        if (puntoEmissione instanceof DistributoriAutomatici) {
            DistributoriAutomatici distributore = (DistributoriAutomatici) puntoEmissione;
            if (!distributore.isAttivo())
                throw new PuntoDiEmissioneNonAttivoException();
            else {
                nuovoAbbonamento = new Abbonamento(distributore, tessera, tipo);
            }
        } else {
            nuovoAbbonamento = new Abbonamento(puntoEmissione, tessera, tipo);
        }

        if (tessera.getDataScadenza().isBefore(LocalDate.now()))
            throw new TesseraScadutaException();
        else {
            this.save(nuovoAbbonamento);
        }
    }

    public Abbonamento ottieniUltimoAbbonamentoInBaseATessera(Tessera tessera) {
        Abbonamento abbonamentoUtente;
        if (tessera.getDataScadenza().isBefore(LocalDate.now())) {
            throw new TesseraScadutaException();
        } else {
            TypedQuery<Abbonamento> query = entityManager.createQuery("SELECT a FROM Abbonamento a WHERE a.tessera.id = :idTessera ORDER BY a.dataEmissione DESC", Abbonamento.class);
            query.setMaxResults(1);
            List<Abbonamento> found = query.getResultList();
            if (found.isEmpty()) {
                throw new AbbonamentoTesseraNonTrovatoException();
            } else {
                abbonamentoUtente = found.getFirst();
                return abbonamentoUtente;
            }
        }
    }

   /* public Abbonamento controllaValiditàAbbonamento(Tessera tessera) {
        Abbonamento abbonamentoUtente;
        if (tessera.getDataScadenza().isBefore(LocalDate.now())) {
            throw new TesseraScadutaException();
        } else {
            TypedQuery<Abbonamento> query = entityManager.createQuery("SELECT a FROM Abbonamento a WHERE a.tessera.id = :idTessera ORDER BY a.dataEmissione DESC", Abbonamento.class);
            query.setMaxResults(1);
            List<Abbonamento> found = query.getResultList();
            if (found.isEmpty()) {
                throw new AbbonamentoTesseraNonTrovatoException();
            } else {
                abbonamentoUtente = found.getFirst();
                if (abbonamentoUtente.getDataScadenza().isBefore(LocalDate.now())) {
                    System.out.println("Abbonamento scaduto in data " + abbonamentoUtente.getDataScadenza());
                } else {
                   *//* System.out.println("Abbonamento valido. Scadenza prevista in data " + abbonamentoUtente.getDataScadenza());*//*
                }
                return abbonamentoUtente;
            }
        }
    }*/

    public boolean controllaValiditàAbbonamento(Tessera tessera) {
        if (tessera.getDataScadenza().isBefore(LocalDate.now())) {
            throw new TesseraScadutaException();
        } else {
            TypedQuery<Abbonamento> query = entityManager.createQuery("SELECT a FROM Abbonamento a WHERE a.tessera.id = :idTessera ORDER BY a.dataEmissione DESC", Abbonamento.class);
            query.setMaxResults(1);
            List<Abbonamento> found = query.getResultList();
            if (found.isEmpty()) {
                throw new AbbonamentoTesseraNonTrovatoException();
            } else {
                Abbonamento abbonamentoUtente = found.getFirst();
                if (abbonamentoUtente.getDataScadenza().isBefore(LocalDate.now())) {
                    System.out.println("Abbonamento scaduto in data " + abbonamentoUtente.getDataScadenza());
                    return false;
                } else {
                    System.out.println("Abbonamento valido. Scadenza prevista in data " + abbonamentoUtente.getDataScadenza());
                    return true;
                }
            }
        }
    }

    public void rinnovaAbbonamento(PuntiEmissione puntoEmissione, Tessera tessera) {
        Abbonamento abbonamentoDaRinnovare = this.ottieniUltimoAbbonamentoInBaseATessera(tessera);
        if (abbonamentoDaRinnovare.getDataScadenza().isAfter(LocalDate.now()))
            throw new AbbonamentoAncoraValidoException();
        else {
            this.acquistaAbbonamento(puntoEmissione, tessera, abbonamentoDaRinnovare.getTipo());
        }
        System.out.println("Abbonamento rinnovato con successo!");
    }

    public void rinnovaEModificaAbbonamento(PuntiEmissione puntoEmissione, Tessera tessera) {
        Abbonamento abbonamentoDaModificare = this.ottieniUltimoAbbonamentoInBaseATessera(tessera);
        if (abbonamentoDaModificare.getDataScadenza().isAfter(LocalDate.now()))
            throw new AbbonamentoAncoraValidoException();
        else {
            if (abbonamentoDaModificare.getTipo().equals(TipoAbbonamento.MENSILE))
                this.acquistaAbbonamento(puntoEmissione, tessera, TipoAbbonamento.SETTIMANALE);
            else this.acquistaAbbonamento(puntoEmissione, tessera, TipoAbbonamento.MENSILE);
        }
        System.out.println("Abbonamento modificato e rinnovato con successo!");
    }

    public void utilizzaAbbonamento(Tessera tessera, MezzoDiTrasporto mezzo) {
        Abbonamento abbonamentoDaUtilizzare = this.ottieniUltimoAbbonamentoInBaseATessera(tessera);
        if (abbonamentoDaUtilizzare.getDataScadenza().isBefore(LocalDate.now()))
            throw new AbbonamentoScadutoException();
        else System.out.println("\nAbbonamento valido, salire a bordo");
    }

    public Biglietto findBigliettoById(String idBiglietto) {
        Biglietto found = entityManager.find(Biglietto.class, UUID.fromString(idBiglietto));
        if (found == null)
            throw new NotFoundException(idBiglietto);
        else return found;
    }


    public void utilizzaBiglietto(Biglietto biglietto, MezzoDiTrasporto mezzo) {
        if (biglietto.getDataVidimazione() != null)
            throw new BigliettoGiàVidimatoException();
        if (mezzo.getBigliettiValidati().size() == mezzo.getCapienza())
            throw new CapienzaMassimaRaggiuntaException();
        else {
            Query updateQuery = entityManager.createQuery("UPDATE Biglietto b SET b.dataVidimazione = CURRENT_DATE, b.mezzo = :mezzo  WHERE b.id = :idBiglietto ");
            updateQuery.setParameter("mezzo", mezzo);
            updateQuery.setParameter("idBiglietto", biglietto.getId());
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            updateQuery.executeUpdate();
            transaction.commit();
            System.out.println("\nBiglietto vidimato con successo, salire a bordo!");
        }
    }

     /*public boolean controllaValiditàAbbonamento(Utente utente) {
        if (utente.getTessera() == null) {
            throw new TesseraUtenteNonTrovata();
        } else if (utente.getTessera().getDataScadenza().isBefore(LocalDate.now())) {
            throw new TesseraScadutaException();
        } else {
            TypedQuery<Abbonamento> query = entityManager.createQuery("SELECT a FROM Abbonamento a WHERE a.tessera.id = :idTessera ORDER BY a.dataEmissione DESC", Abbonamento.class);
            query.setMaxResults(1);
            List<Abbonamento> found = query.getResultList();
            if (found.isEmpty()) {
                throw new AbbonamentoTesseraNonTrovatoException();
            } else {
                Abbonamento abbonamentoUtente = found.getFirst();
                if (abbonamentoUtente.getDataScadenza().isBefore(LocalDate.now())) {
                    System.out.println("Abbonamento scaduto in data " + abbonamentoUtente.getDataScadenza());
                    return false;
                } else {
                    System.out.println("Abbonamento valido. Scadenza prevista in data " +abbonamentoUtente.getDataScadenza());
                    return true;
                }
            }
        }
    }*/


    public Long getTotaleEmissioniInBaseAData(LocalDate data, String quando) {
        TypedQuery<Long> query;
        if (quando.equals("prima")) {
            query = entityManager.createQuery("SELECT COUNT(e) FROM Emissione e WHERE e.dataEmissione < :data ", Long.class);
        } else if (quando.equals("dopo")) {
            query = entityManager.createQuery("SELECT COUNT(e) FROM Emissione e WHERE e.dataEmissione > :data ", Long.class);
        } else {
            query = entityManager.createQuery("SELECT COUNT(e) FROM Emissione e WHERE e.dataEmissione = :data ", Long.class);
        }
        query.setParameter("data", data);
        Long found = query.getSingleResultOrNull();
        if (found == null || found == 0) throw new NessunElementoTrovatoException();
        else return found;
    }


    public Long getTotaleBigliettiInBaseAData(LocalDate data, String quando) {
        TypedQuery<Long> query;
        if (quando.equals("prima")) {
            query = entityManager.createQuery("SELECT COUNT(b) FROM Biglietto b WHERE b.dataEmissione < :data ", Long.class);
        } else if (quando.equals("dopo")) {
            query = entityManager.createQuery("SELECT COUNT(b) FROM Biglietto b WHERE b.dataEmissione > :data ", Long.class);
        } else {
            query = entityManager.createQuery("SELECT COUNT(b) FROM Biglietto b WHERE b.dataEmissione = :data ", Long.class);
        }
        query.setParameter("data", data);
        Long found = query.getSingleResultOrNull();
        if (found == null || found == 0) throw new NessunElementoTrovatoException();
        else return found;
    }

    public Long getTotaleAbbonamentiInBaseAData(LocalDate data, String quando) {
        TypedQuery<Long> query;
        if (quando.equals("prima")) {
            query = entityManager.createQuery("SELECT COUNT(a) FROM Abbonamento a WHERE a.dataEmissione < :data ", Long.class);
        } else if (quando.equals("dopo")) {
            query = entityManager.createQuery("SELECT COUNT(a) FROM Abbonamento a WHERE a.dataEmissione > :data ", Long.class);
        } else {
            query = entityManager.createQuery("SELECT COUNT(a) FROM Abbonamento a WHERE a.dataEmissione = :data ", Long.class);
        }
        query.setParameter("data", data);
        Long found = query.getSingleResultOrNull();
        if (found == null || found == 0) throw new NessunElementoTrovatoException();
        else return found;
    }

    public Long getTotaleEmissioniInArcoTemporale(LocalDate dataInizio, LocalDate dataFine) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(e) FROM Emissione e WHERE  e.dataEmissione BETWEEN :dataInizio AND :dataFine", Long.class);
        query.setParameter("dataInizio", dataInizio);
        query.setParameter("dataFine", dataFine);
        Long found = query.getSingleResultOrNull();
        if (found == null || found == 0) throw new NessunElementoTrovatoException();
        else return found;
    }

    public Long getTotaleBigliettiInArcoTemporale(LocalDate dataInizio, LocalDate dataFine) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT b FROM Biglietto b WHERE b.dataEmissione BETWEEN :dataInizio AND :dataFine", Long.class);
        query.setParameter("dataInizio", dataInizio);
        query.setParameter("dataFine", dataFine);
        Long found = query.getSingleResultOrNull();
        if (found == null || found == 0) throw new NessunElementoTrovatoException();
        else return found;
    }

    public Long getTotaleAbbonamentiInArcoTemporale(LocalDate dataInizio, LocalDate dataFine) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT a FROM Abbonamento a WHERE a.dataEmissione BETWEEN :dataInizio AND :dataFine", Long.class);
        query.setParameter("dataInizio", dataInizio);
        query.setParameter("dataFine", dataFine);
        Long found = query.getSingleResultOrNull();
        if (found == null || found == 0) throw new NessunElementoTrovatoException();
        else return found;
    }

    public List<Object[]> getTotaleEmissioniPerPuntoDiEmissione() {
        TypedQuery<Object[]> query = entityManager.createQuery("SELECT e.puntiEmissione, COUNT(e) FROM Emissione e GROUP BY e.puntiEmissione", Object[].class);
        List<Object[]> found = query.getResultList();
        if (found.isEmpty()) throw new NessunElementoTrovatoException();
        return query.getResultList();
    }

    public List<Object[]> getTotaleBigliettiPerPuntoDiEmissione() {
        TypedQuery<Object[]> query = entityManager.createQuery("SELECT b.puntiEmissione, COUNT(b) FROM Biglietto b GROUP BY b.puntiEmissione", Object[].class);
        List<Object[]> found = query.getResultList();
        if (found.isEmpty()) throw new NessunElementoTrovatoException();
        return query.getResultList();
    }

    public List<Object[]> getTotaleAbbonamentiPerPuntoDiEmissione() {
        TypedQuery<Object[]> query = entityManager.createQuery("SELECT a.puntiEmissione, COUNT(a) FROM Abbonamento a GROUP BY a.puntiEmissione", Object[].class);
        List<Object[]> found = query.getResultList();
        if (found.isEmpty()) throw new NessunElementoTrovatoException();
        return query.getResultList();
    }

    public Long getTotaleEmissioniDaPuntoDiEmissione(String idPunto) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(e) FROM Emissione e WHERE e.puntiEmissione.id = :idPunto", Long.class);
        query.setParameter("idPunto", UUID.fromString(idPunto));
        Long found = query.getSingleResultOrNull();
        if (found == null || found == 0) throw new NessunElementoTrovatoException();
        return found;
    }

    public Long getTotaleBigliettiDaPuntoDiEmissione(String idPunto) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(b) FROM Biglietto b WHERE b.puntiEmissione.id = :idPunto", Long.class);
        query.setParameter("idPunto", UUID.fromString(idPunto));
        Long found = query.getSingleResultOrNull();
        if (found == null || found == 0) throw new NessunElementoTrovatoException();
        return found;
    }

    public Long getTotaleAbbonamentiDaPuntoDiEmissione(String idPunto) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(a) FROM Abbonamento a WHERE a.puntiEmissione.id = :idPunto", Long.class);
        query.setParameter("idPunto", UUID.fromString(idPunto));
        Long found = query.getSingleResultOrNull();
        if (found == null || found == 0) throw new NessunElementoTrovatoException();
        return found;
    }

   /* public Long getTotaleEmissioniInBaseADataDaPuntoDiEmissione(LocalDate data, String quando, String idPunto) {
        TypedQuery<Long> query;
        if (quando.equals("prima")) {
            query = entityManager.createQuery("SELECT COUNT(e) FROM Emissione e WHERE e.dataEmissione < :data AND e.puntiEmissione.id = :idPunto ", Long.class);
        } else if (quando.equals("dopo")) {
            query = entityManager.createQuery("SELECT COUNT(e) FROM Emissione e WHERE e.dataEmissione > :data AND e.puntiEmissione.id = :idPunto ", Long.class);
        } else {
            query = entityManager.createQuery("SELECT COUNT(e) FROM Emissione e WHERE e.dataEmissione = :data AND e.puntiEmissione.id = :idPunto ", Long.class);
        }
        query.setParameter("data", data);
        query.setParameter("idPunto", idPunto);
        Long found = query.getSingleResultOrNull();
        if (found == null || found == 0) throw new NessunElementoTrovatoException();
        else return found;
    }*/

    /*public Long getTotaleBigliettiInBaseADataDaPuntoDiEmissione(LocalDate data, String quando, String idPunto) {
        TypedQuery<Long> query;
        if (quando.equals("prima")) {
            query = entityManager.createQuery("SELECT COUNT(b) FROM Biglietto b WHERE b.dataEmissione < :data AND b.puntiEmissione.id = :idPunto", Long.class);
        } else if (quando.equals("dopo")) {
            query = entityManager.createQuery("SELECT COUNT(b) FROM Biglietto b WHERE b.dataEmissione > :data AND b.puntiEmissione.id = :idPunto", Long.class);
        } else {
            query = entityManager.createQuery("SELECT COUNT(b) FROM Biglietto b WHERE b.dataEmissione = :data AND b.puntiEmissione.id = :idPunto", Long.class);
        }
        query.setParameter("data", data);
        query.setParameter("idPunto", idPunto);
        Long found = query.getSingleResultOrNull();
        if (found == null || found == 0) throw new NessunElementoTrovatoException();
        else return found;
    }*/

    /*public Long getTotaleAbbonamentiInBaseADataDaPuntoDiEmissione(LocalDate data, String quando, String idPunto) {
        TypedQuery<Long> query;
        if (quando.equals("prima")) {
            query = entityManager.createQuery("SELECT COUNT(a) FROM Abbonamento a WHERE a.dataEmissione < :data AND a.puntiEmissione.id = :idPunto ", Long.class);
        } else if (quando.equals("dopo")) {
            query = entityManager.createQuery("SELECT COUNT(a) FROM Abbonamento a WHERE a.dataEmissione > :data AND a.puntiEmissione.id = :idPunto", Long.class);
        } else {
            query = entityManager.createQuery("SELECT COUNT(a) FROM Abbonamento a WHERE a.dataEmissione = :data AND a.puntiEmissione.id = :idPunto", Long.class);
        }
        query.setParameter("data", data);
        query.setParameter("idPunto", idPunto);
        Long found = query.getSingleResultOrNull();
        if (found == null || found == 0) throw new NessunElementoTrovatoException();
        else return found;
    }*/

    /*public Long getTotaleEmissioniInArcoTemporaleDaPuntoDiEmissione(LocalDate dataInizio, LocalDate dataFine, String idPunto) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(e) FROM Emissione e WHERE e.puntiEmissione.id = :idPunto AND e.dataEmissione BETWEEN :dataInizio AND :dataFine", Long.class);
        query.setParameter("dataInizio", dataInizio);
        query.setParameter("dataFine", dataFine);
        query.setParameter("idPunto", idPunto);
        Long found = query.getSingleResultOrNull();
        if (found == null || found == 0) throw new NessunElementoTrovatoException();
        else return found;
    }*/

   /* public Long getTotaleBigliettiInArcoTemporaleDaPuntoDiEmissione(LocalDate dataInizio, LocalDate dataFine, String idPunto) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(b) FROM Biglietto b WHERE b.puntiEmissione.id = :idPunto AND b.dataEmissione BETWEEN :dataInizio AND :dataFine", Long.class);
        query.setParameter("dataInizio", dataInizio);
        query.setParameter("dataFine", dataFine);
        query.setParameter("idPunto", idPunto);
        Long found = query.getSingleResultOrNull();
        if (found == null || found == 0) throw new NessunElementoTrovatoException();
        else return found;
    }*/

    /*public Long getTotaleAbbonamentiInArcoTemporaleDaPuntoDiEmissione(LocalDate dataInizio, LocalDate dataFine, String idPunto) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(a) FROM Abbonamento a WHERE a.puntiEmissione.id = :idPunto AND a.dataEmissione BETWEEN :dataInizio AND :dataFine", Long.class);
        query.setParameter("dataInizio", dataInizio);
        query.setParameter("dataFine", dataFine);
        query.setParameter("idPunto", idPunto);
        Long found = query.getSingleResultOrNull();
        if (found == null || found == 0) throw new NessunElementoTrovatoException();
        else return found;
    }*/

    public Long getBigliettiVidimatiSuUnMezzo(String idMezzo) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(b) FROM Biglietto b WHERE b.mezzo.id = :idMezzo AND b.dataVidimazione IS NOT NULL", Long.class);
        query.setParameter("idMezzo", UUID.fromString(idMezzo));
        Long found = query.getSingleResultOrNull();
        if (found == null || found == 0) throw new NessunElementoTrovatoException();
        else return found;
    }

    /*public Long getBigliettiVidimatiPrimaDiUnaData(LocalDate data) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(b) FROM Biglietto b WHERE b.dataVidimazione < :data", Long.class);
        query.setParameter("data", data);
        Long found = query.getSingleResultOrNull();
        if (found == null || found == 0) throw new NessunElementoTrovatoException();
        else return found;
    }*/

    /*public Long getBigliettiVidimatiDopoUnaData(LocalDate data) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(b) FROM Biglietto b WHERE b.dataVidimazione > :data", Long.class);
        query.setParameter("data", data);
        Long found = query.getSingleResultOrNull();
        if (found == null || found == 0) throw new NessunElementoTrovatoException();
        else return found;
    }*/

    /*public Long getBigliettiVidimatiInUnaData(LocalDate data) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(b) FROM Biglietto b WHERE b.dataVidimazione = :data", Long.class);
        query.setParameter("data", data);
        Long found = query.getSingleResultOrNull();
        if (found == null || found == 0) throw new NessunElementoTrovatoException();
        else return found;
    }*/

   /* public Long getBigliettiVidimatiInUnArcoTemporale(LocalDate dataInizio, LocalDate dataFine) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(b) FROM Biglietto b WHERE b.dataVidimazione BETWEEN :dataInizio AND :dataFine", Long.class);
        query.setParameter("dataInizio", dataInizio);
        query.setParameter("dataFine", dataFine);
        Long found = query.getSingleResultOrNull();
        if (found == null || found == 0) throw new NessunElementoTrovatoException();
        else return found;
    }*/


    public Long getBigliettiVidimatiPrimaDiUnaDataSuUnMezzo(LocalDate data, String idMezzo) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(b) FROM Biglietto b WHERE b.mezzo.id = :idMezzo AND b.dataVidimazione < :data", Long.class);
        query.setParameter("data", data);
        query.setParameter("idMezzo", UUID.fromString(idMezzo));
        Long found = query.getSingleResultOrNull();
        if (found == null || found == 0) throw new NessunElementoTrovatoException();
        else return found;
    }

    public Long getBigliettiVidimatiDopoUnaDataSuUnMezzo(LocalDate data, String idMezzo) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(b) FROM Biglietto b WHERE b.mezzo.id = :idMezzo AND b.dataVidimazione > :data", Long.class);
        query.setParameter("data", data);
        query.setParameter("idMezzo", UUID.fromString(idMezzo));
        Long found = query.getSingleResultOrNull();
        if (found == null || found == 0) throw new NessunElementoTrovatoException();
        else return found;
    }

    public Long getBigliettiVidimatiInUnaDataSuUnMezzo(LocalDate data, String idMezzo) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(b) FROM Biglietto b WHERE b.mezzo.id = :idMezzo AND b.dataVidimazione = :data", Long.class);
        query.setParameter("data", data);
        query.setParameter("idMezzo", UUID.fromString(idMezzo));
        Long found = query.getSingleResultOrNull();
        if (found == null || found == 0) throw new NessunElementoTrovatoException();
        else return found;
    }

    public Long getBigliettiVidimatiInUnArcoTemporaleSuUnMezzo(LocalDate dataInizio, LocalDate dataFine, String idMezzo) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(b) FROM Biglietto b WHERE b.mezzo.id = :idMezzo AND b.dataVidimazione BETWEEN :dataInizio AND :dataFine", Long.class);
        query.setParameter("dataInizio", dataInizio);
        query.setParameter("dataFine", dataFine);
        query.setParameter("idMezzo", UUID.fromString(idMezzo));
        Long found = query.getSingleResultOrNull();
        if (found == null || found == 0) throw new NessunElementoTrovatoException();
        else return found;
    }


}
