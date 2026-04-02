package GiorgiaFormicola.dao;

import GiorgiaFormicola.entities.Tessera;
import GiorgiaFormicola.entities.Utente;
import GiorgiaFormicola.exceptions.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class TessereDAO {

    private final EntityManager entityManager;


    public TessereDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void saveTessera(Tessera nuovaTessera) {
        try {
            this.controllaSeTesseraGiàEsistente(nuovaTessera.getNumeroTessera());
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(nuovaTessera);
            transaction.commit();
            System.out.println("La tessera " + nuovaTessera.getNumeroTessera() + " è stata salvata correttamente");
        } catch (TesseraGiaEsistente e) {
            System.err.println("ERRORE: impossibile creare una nuova tessera. " + e.getMessage());
        }
    }

    public void createNuovaTessera(long numeroTessera, Utente utente) {
        if (utente.getTessera() != null) throw new UtenteAssociatoATessera();
        Tessera tesseraDaSalvare = new Tessera(numeroTessera, utente);
        try {
            this.saveTessera(tesseraDaSalvare);
        } catch (TesseraGiaEsistente e) {
            System.err.println(e.getMessage());
        }
    }


    public void controllaSeTesseraGiàEsistente(long numeroTessera) {
        TypedQuery<Tessera> query = entityManager.createQuery("SELECT t FROM Tessera t WHERE t.numeroTessera = :numeroTessera", Tessera.class);
        query.setParameter("numeroTessera", numeroTessera);
        Tessera found = query.getSingleResultOrNull();
        if (found != null) throw new TesseraGiaEsistente();
    }

    public Tessera findTesseraById(String tesseraId) {
        Tessera found = entityManager.find(Tessera.class, tesseraId);
        if (found == null) {
            throw new NotFoundException(tesseraId);
        } else {
            System.out.println("La tessera con id " + tesseraId + " è stata trovata");
            return found;
        }
    }

    public Tessera findTesseraByNumeroTessera(long numeroTessera) {
        TypedQuery<Tessera> query = entityManager.createQuery("SELECT t FROM Tessera t WHERE t.numeroTessera = :numeroTessera", Tessera.class);
        query.setParameter("numeroTessera", numeroTessera);
        List<Tessera> risultatoLista = query.getResultList();
        if (risultatoLista.isEmpty()) {
            throw new NotFoundException(String.valueOf(numeroTessera));
        } else return risultatoLista.getFirst();
    }

    public void deleteTesseraById(String tesseraId) {
        Tessera found = this.findTesseraById(tesseraId);
        if (found == null) {
            System.out.println("Tessera non trovata");
            return;
        }
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        if (found.getUtente() != null) {
            Utente utente = found.getUtente();
            utente.setTessera(null);
        }
        entityManager.remove(found);
        transaction.commit();
        System.out.println("La tessera con id " + tesseraId + " è stata cancellata");
    }


//    public void createNuovaTessera(long numeroTessera, String codiceFiscale) {
//
//        EntityTransaction transaction = entityManager.getTransaction();
//
//        TypedQuery<Utente> query = entityManager.createQuery("SELECT u FROM Utente u WHERE u.codiceFiscale = :codiceFiscale", Utente.class);
//
//        query.setParameter("codiceFiscale", codiceFiscale);
//
//        List<Utente> risultatoLista = query.getResultList();
//
//        if (risultatoLista.isEmpty()){
//            throw new NotFoundUserException(codiceFiscale);
//        }
//
//        Utente utente = risultatoLista.get(0);
//
//        TypedQuery<Tessera> queryTessera = entityManager.createQuery("SELECT t FROM Tessera t WHERE t.utente = :utente", Tessera.class);
//        queryTessera.setParameter("utente", utente);
//
//        if (!queryTessera.getResultList().isEmpty()){
//            System.out.println("L'utente ha gia una tessera");
//            return;
//        }
//
//        Tessera numeroTesseraEsistente = this.findTesseraByNumeroTessera(numeroTessera);
//
//        if (numeroTesseraEsistente != null) {
//            System.out.println("Esiste gia una tessera con numero tessera " + numeroTessera);
//            return;
//        }
//
//        Tessera tessera = new Tessera(numeroTessera, utente);
//
//        tessera.setUtente(utente);
//
//        LocalDate oggi = LocalDate.now();
//        tessera.setDataEmissione(oggi);
//        tessera.setDataScadenza(oggi.plusYears(1));
//
//        transaction.begin();
//        entityManager.persist(tessera);
//        transaction.commit();
//
//        System.out.println("Tessera creata per utente " + codiceFiscale + " con scadenza: " + tessera.getDataScadenza());
//    }

//    public void createNuovaTessera(Tessera nuovaTessera){
//        EntityTransaction transaction = entityManager.getTransaction();
//
//        Tessera numeroTesseraEsistente = this.findTesseraByNumeroTessera(nuovaTessera.getNumeroTessera());
//
//        if (numeroTesseraEsistente != null){
//            System.out.println("Esiste gia una tessera con numero tessera " + nuovaTessera.getNumeroTessera());
//            return;
//        }
//
//        Utente utente = nuovaTessera.getUtente();
//
//        if (utente == null){
//            System.out.println("Nessun utente associato alla tessera");
//            return;
//        }
//
//        if (utente.getId() == null){
//            System.out.println("Utente non salvato nel DB");
//            return;
//        }
//
//        Utente utentePresente = entityManager.find(Utente.class, utente.getId());
//
//        if (utentePresente == null){
//            throw new NotFoundUserException("Utente non trovato con id" + utente.getId());
//        }
//
//        TypedQuery<Tessera> queryTessera = entityManager.createQuery("SELECT t FROM Tessera t WHERE t.utente = :utente", Tessera.class);
//
//        queryTessera.setParameter("utente", utentePresente);
//
//        if (!queryTessera.getResultList().isEmpty()){
//            System.out.println("L'utente ha gia una tessera");
//            return;
//        } else {
//            System.out.println("Nessun utente trovato con codice fiscale " + codiceFiscale);
//        }
//
//        nuovaTessera.setUtente(utentePresente);
//
//        LocalDate oggi = LocalDate.now();
//        nuovaTessera.setDataEmissione(oggi);
//        nuovaTessera.setDataScadenza(oggi.plusYears(1));
//
//        transaction.begin();
//        entityManager.persist(nuovaTessera);
//        transaction.commit();
//
//        System.out.println("Tessera creata: " + nuovaTessera);
//    }

    public void rinnovaTessera(long numeroTessera) {
        try {
            Tessera tessera = this.findTesseraByNumeroTessera(numeroTessera);
            EntityTransaction transaction = entityManager.getTransaction();
            LocalDate oggi = LocalDate.now();
            if (oggi.isBefore(tessera.getDataScadenza())) throw new TesseraAncoraValidaException();
            transaction.begin();
            tessera.setDataEmissione(oggi);
            tessera.setDataScadenza(oggi.plusYears(1));
            transaction.commit();
            System.out.println("Tessera rinnovata con successo. Nuova scadenza: " + tessera.getDataScadenza());
        } catch (NotFoundException e) {
            System.err.println("ERRORE: " + e.getMessage());
        }
    }

    public void modificaScadenzaTessera(long numeroTessera) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            Tessera tessera = this.findTesseraByNumeroTessera(numeroTessera);
            transaction.begin();
            tessera.setDataEmissione(LocalDate.now().minusYears(2));
            tessera.setDataScadenza(LocalDate.now().minusYears(1));
            transaction.commit();
            System.out.println("La data di scadenza è stata modificata con successo");
        } catch (NotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public void checkScadenzaTessera(long numeroTessera) {
        Tessera tessera = this.findTesseraByNumeroTessera(numeroTessera);
        if (tessera == null) {
            throw new NotFoundTesseraException(tessera);
        }
        LocalDate oggi = LocalDate.now();
        if (!oggi.isAfter(tessera.getDataScadenza())) {
            System.out.println("La tessera è valida e scadrà il " + tessera.getDataScadenza());
        } else {
            System.out.println("Tessera scaduta il " + tessera.getDataScadenza());
            Scanner scanner = new Scanner(System.in);
            System.out.println("Vuoi rinnovare la tessera? (si/no)");
            String risposta = scanner.nextLine();
            if (risposta.equalsIgnoreCase("si")) {
                EntityTransaction transaction = entityManager.getTransaction();
                transaction.begin();
                LocalDate nuovaData = LocalDate.now();
                tessera.setDataEmissione(nuovaData);
                tessera.setDataScadenza(nuovaData.plusYears(1));
                transaction.commit();
                System.out.println("Tessera rinnovata! Nuova scadenza " + tessera.getDataScadenza());
            } else {
                System.out.println("Rinnovo annullato");
            }
        }
    }


}
