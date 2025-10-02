import dao.*;
import entities.*;
import enums.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;

public class Application {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bw1be");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();

        // Inizializzazione dei DAO
        UtenteDAO utenteDAO = new UtenteDAO(em);
        TesseraDAO tesseraDAO = new TesseraDAO(em);
        RivenditoreDAO rivenditoreDAO = new RivenditoreDAO(em);
        DistributoreAutomaticoDAO distributoreDAO = new DistributoreAutomaticoDAO(em);
        MezzoDAO mezzoDAO = new MezzoDAO(em);
        BigliettoDAO bigliettoDAO = new BigliettoDAO(em, em);
        AbbonamentoDAO abbonamentoDAO = new AbbonamentoDAO(em, em);

        // Creazione degli utenti
        System.out.println("=== CREAZIONE UTENTI ===");
        Utente utente1 = new Utente("Mario", "Rossi", "mrossi", RuoloUtente.UTENTE_SEMPLICE);
        Utente utente2 = new Utente("Giulia", "Bianchi", "gbianchi", RuoloUtente.UTENTE_SEMPLICE);
        Utente utente3 = new Utente("Luca", "Verdi", "lverdi", RuoloUtente.UTENTE_SEMPLICE);
        Utente utente4 = new Utente("Anna", "Neri", "aneri", RuoloUtente.UTENTE_SEMPLICE);
        Utente utente5 = new Utente("Paolo", "Gialli", "pgialli", RuoloUtente.AMMINISTRATORE);

        utenteDAO.create(utente1);
        utenteDAO.create(utente2);
        utenteDAO.create(utente3);
        utenteDAO.create(utente4);
        utenteDAO.create(utente5);

        // Creazione delle tessere
        System.out.println("=== CREAZIONE TESSERE ===");
        Tessera tessera1 = new Tessera(LocalDate.now().minusDays(30), LocalDate.now().plusYears(1), utente1);
        Tessera tessera2 = new Tessera(LocalDate.now().minusDays(15), LocalDate.now().plusYears(1), utente2);
        Tessera tessera3 = new Tessera(LocalDate.now().minusDays(60), LocalDate.now().plusYears(1), utente3);
        Tessera tessera4 = new Tessera(LocalDate.now().minusDays(10), LocalDate.now().plusYears(1), utente4);
        Tessera tessera5 = new Tessera(LocalDate.now().minusDays(5), LocalDate.now().plusYears(1), utente5);

        tesseraDAO.create(tessera1);
        tesseraDAO.create(tessera2);
        tesseraDAO.create(tessera3);
        tesseraDAO.create(tessera4);
        tesseraDAO.create(tessera5);

        // Creazione dei rivenditori
        System.out.println("=== CREAZIONE RIVENDITORI ===");
        Rivenditore rivenditore1 = new Rivenditore("Via Roma 123", "Tabaccheria Centrale", StatoRivenditore.APERTO);
        Rivenditore rivenditore2 = new Rivenditore("Corso Italia 45", "Edicola del Corso", StatoRivenditore.APERTO);
        Rivenditore rivenditore3 = new Rivenditore("Piazza Garibaldi 7", "Bar Sport", StatoRivenditore.CHIUSO);

        rivenditoreDAO.save(rivenditore1);
        rivenditoreDAO.save(rivenditore2);
        rivenditoreDAO.save(rivenditore3);

        // Creazione dei distributori automatici
        System.out.println("=== CREAZIONE DISTRIBUTORI AUTOMATICI ===");
        DistributoreAutomatico distributore1 = new DistributoreAutomatico("Stazione Centrale", 0, StatoDistributore.IN_SERVIZIO);
        DistributoreAutomatico distributore2 = new DistributoreAutomatico("Fermata Università", 0, StatoDistributore.IN_SERVIZIO);
        DistributoreAutomatico distributore3 = new DistributoreAutomatico("Piazza del Duomo", 0, StatoDistributore.FUORI_SERVIZIO);

        distributoreDAO.save(distributore1);
        distributoreDAO.save(distributore2);
        distributoreDAO.save(distributore3);

        // Creazione dei mezzi
        System.out.println("=== CREAZIONE MEZZI ===");
        Mezzo mezzo1 = new Mezzo("AB123CD", TipoMezzo.AUTOBUS, 50, StatoMezzo.IN_SERVIZIO);
        Mezzo mezzo2 = new Mezzo("EF456GH", TipoMezzo.TRAM, 80, StatoMezzo.IN_SERVIZIO);
        Mezzo mezzo3 = new Mezzo("IJ789KL", TipoMezzo.AUTOBUS, 45, StatoMezzo.IN_MANUTENZIONE);
        Mezzo mezzo4 = new Mezzo("MN012OP", TipoMezzo.TRAM, 75, StatoMezzo.IN_SERVIZIO);
        Mezzo mezzo5 = new Mezzo("QR345ST", TipoMezzo.AUTOBUS, 55, StatoMezzo.IN_SERVIZIO);

        mezzoDAO.save(mezzo1);
        mezzoDAO.save(mezzo2);
        mezzoDAO.save(mezzo3);
        mezzoDAO.save(mezzo4);
        mezzoDAO.save(mezzo5);

        // Creazione dei biglietti
        System.out.println("=== CREAZIONE BIGLIETTI ===");
        
        // Biglietto 1 - Acquistato da rivenditore, validato
        Biglietto biglietto1 = new Biglietto(
            90, // durata in minuti
            1.50, // prezzo
            LocalDate.now().minusDays(2), // data emissione
            null, // distributore (acquistato da rivenditore)
            rivenditore1, // rivenditore
            true, // validato
            LocalDate.now().minusDays(1), // data validazione
            mezzo1 // mezzo validante
        );

        // Biglietto 2 - Acquistato da distributore automatico, non validato
        Biglietto biglietto2 = new Biglietto(
            90,
            1.50,
            LocalDate.now(),
            distributore1, // distributore
            null, // rivenditore (acquistato da distributore)
            false, // non validato
            null, // nessuna data validazione
            null // nessun mezzo validante
        );

        // Biglietto 3 - Acquistato da rivenditore, validato su tram
        Biglietto biglietto3 = new Biglietto(
            90,
            1.50,
            LocalDate.now().minusDays(1),
            null,
            rivenditore2,
            true,
            LocalDate.now(),
            mezzo2
        );

        // Biglietto 4 - Acquistato da distributore, validato
        Biglietto biglietto4 = new Biglietto(
            90,
            1.50,
            LocalDate.now().minusDays(3),
            distributore2,
            null,
            true,
            LocalDate.now().minusDays(2),
            mezzo4
        );

        // Biglietto 5 - Acquistato da rivenditore, non ancora validato
        Biglietto biglietto5 = new Biglietto(
            90,
            1.50,
            LocalDate.now(),
            null,
            rivenditore1,
            false,
            null,
            null
        );

        bigliettoDAO.save(biglietto1);
        bigliettoDAO.save(biglietto2);
        bigliettoDAO.save(biglietto3);
        bigliettoDAO.save(biglietto4);
        bigliettoDAO.save(biglietto5);

        // Creazione degli abbonamenti
        System.out.println("=== CREAZIONE ABBONAMENTI ===");

        // Abbonamento 1 - Settimanale, acquistato da rivenditore
        Abbonamento abbonamento1 = new Abbonamento(
            TipoAbbonamento.SETTIMANALE,
            LocalDate.now(), // data inizio validità
            LocalDate.now().plusWeeks(1), // data fine validità
            LocalDate.now().minusDays(1), // data emissione
            10.00, // prezzo
            rivenditore1, // rivenditore
            null, // distributore
            tessera1 // tessera
        );

        // Abbonamento 2 - Mensile, acquistato da distributore automatico
        Abbonamento abbonamento2 = new Abbonamento(
            TipoAbbonamento.MENSILE,
            LocalDate.now().plusDays(1),
            LocalDate.now().plusMonths(1).plusDays(1),
            LocalDate.now(),
            35.00,
            null, // rivenditore
            distributore1, // distributore
            tessera2
        );

        // Abbonamento 3 - Settimanale, acquistato da rivenditore
        Abbonamento abbonamento3 = new Abbonamento(
            TipoAbbonamento.SETTIMANALE,
            LocalDate.now().minusDays(3),
            LocalDate.now().plusDays(4),
            LocalDate.now().minusDays(4),
            10.00,
            rivenditore2,
            null,
            tessera3
        );

        // Abbonamento 4 - Mensile, acquistato da distributore
        Abbonamento abbonamento4 = new Abbonamento(
            TipoAbbonamento.MENSILE,
            LocalDate.now().minusDays(10),
            LocalDate.now().plusDays(20),
            LocalDate.now().minusDays(11),
            35.00,
            null,
            distributore2,
            tessera4
        );

        // Abbonamento 5 - Settimanale, acquistato da rivenditore
        Abbonamento abbonamento5 = new Abbonamento(
            TipoAbbonamento.SETTIMANALE,
            LocalDate.now().plusDays(2),
            LocalDate.now().plusWeeks(1).plusDays(2),
            LocalDate.now(),
            10.00,
            rivenditore1,
            null,
            tessera5
        );

        abbonamentoDAO.save(abbonamento1);
        abbonamentoDAO.save(abbonamento2);
        abbonamentoDAO.save(abbonamento3);
        abbonamentoDAO.save(abbonamento4);
        abbonamentoDAO.save(abbonamento5);

        System.out.println("=== CREAZIONE COMPLETATA ===");
        System.out.println("Sono stati creati:");
        System.out.println("- 5 Utenti");
        System.out.println("- 5 Tessere");
        System.out.println("- 3 Rivenditori");
        System.out.println("- 3 Distributori Automatici");
        System.out.println("- 5 Mezzi");
        System.out.println("- 5 Biglietti");
        System.out.println("- 5 Abbonamenti");

        em.close();
        emf.close();
    }
}
