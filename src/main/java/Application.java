import dao.*;
import entities.*;
import enums.*;
import exceptions.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;

public class Application {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("bw1be");
    private static EntityManager em = emf.createEntityManager();
    private static UtenteDAO utenteDAO = new UtenteDAO(em);
    private static TesseraDAO tesseraDAO = new TesseraDAO(em);
    private static RivenditoreDAO rivenditoreDAO = new RivenditoreDAO(em);
    private static DistributoreAutomaticoDAO distributoreDAO = new DistributoreAutomaticoDAO(em);
    private static MezzoDAO mezzoDAO = new MezzoDAO(em);
    private static BigliettoDAO bigliettoDAO = new BigliettoDAO(em, em);
    private static AbbonamentoDAO abbonamentoDAO = new AbbonamentoDAO(em, em);
    private static ManutenzioneDAO manutenzioneDAO = new ManutenzioneDAO(em);
    private static TrattaDAO trattaDAO = new TrattaDAO(em);

    private static PercorrenzaMediaDAO percorrenzaMediaDAO = new PercorrenzaMediaDAO(em);
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        popolaDatabase();

        boolean continua = true;
        while (continua) {
            try {
                mostraMenu();
                int scelta = leggiIntero();

                switch (scelta) {
                    case 1:
                        contaBigliettiEmessiPerPeriodo();
                        break;
                    case 2:
                        contaBigliettiEmessiPerPuntoEmissione();
                        break;
                    case 3:
                        contaBigliettiVidimatiPerMezzo();
                        break;
                    case 4:
                        contaBigliettiVidimatiTotali();
                        break;
                    case 5:
                        contaAbbonamentiEmessiPerPeriodo();
                        break;
                    case 6:
                        contaAbbonamentiEmessiPerPuntoEmissione();
                        break;
                    case 7:
                        verificaValiditaAbbonamento();
                        break;
                    case 8:
                        visualizzaMezziPerStato();
                        break;
                    case 9:
                        verificaManutenzione();
                        break;
                    case 10:
                        visualizzaManutenzioniMezzo();
                        break;
                    case 11:
                        calcolaPercorrenzePerUnaTratta();
                        break;
                    case 12:
                        calcolaEStampaPercorrenzeMedie();
                        break;
                    case 13:
                        System.out.println("\u001b[32mAntimo è ascendente su Valorant!\u001b[0m");
                        break;
                    case 0:
                        continua = false;
                        System.out.println("\nArrivederci!");
                        break;
                    default:
                        System.err.println("Scelta non valida! Seleziona un numero tra 0 e 12.");
                }
            } catch (InputMismatchCustomException e) {
                System.err.println(e.getMessage());
                scanner.nextLine();
            } catch (Exception e) {
                System.err.println("Errore imprevisto: " + e.getMessage());
                scanner.nextLine();
            }
        }

        em.close();
        emf.close();
        scanner.close();
    }

    private static void mostraMenu() {
        System.out.println(" ");
        System.out.println("\n////////////// MENU GESTIONE TRASPORTI /////////////");
        System.out.println("1. Conta biglietti emessi in un periodo");
        System.out.println("2. Conta biglietti emessi per punto di emissione");
        System.out.println("3. Conta biglietti vidimati per mezzo");
        System.out.println("4. Conta biglietti vidimati totali");
        System.out.println("5. Conta abbonamenti emessi in un periodo");
        System.out.println("6. Conta abbonamenti emessi per punto di emissione");
        System.out.println("7. Verifica validità abbonamento tramite tessera");
        System.out.println("8. Visualizza mezzi per stato");
        System.out.println("9. Verifica se mezzo è in manutenzione");
        System.out.println("10. Visualizza manutenzioni di un mezzo");
        System.out.println("11. Conta percorrenze di una tratta");
        System.out.println("12. Calcola tempo medio di percorrenza");
        System.out.println("13. Una piccola chicca su Antimo");
        System.out.println("0. Esci");
        System.out.print("Scegli un'opzione: ");
    }

    private static int leggiIntero() {
        try {
            int valore = scanner.nextInt();
            scanner.nextLine();
            return valore;
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.err.println("Input non valido. Inserisci un numero intero.");
            return -1;
        }
    }

    private static long leggiLong(String messaggio) {
        System.out.print(messaggio);
        try {
            long valore = scanner.nextLong();
            scanner.nextLine();
            if (valore < 0) {
                throw new InvalidDataException("L'ID deve essere un numero positivo!");
            }
            return valore;
        } catch (InputMismatchException e) {
            scanner.nextLine();
            throw new InputMismatchCustomException("Input non valido! Inserisci un numero intero.");
        }
    }

    private static LocalDate leggiData(String messaggio) {
        System.out.print(messaggio);
        try {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                throw new InvalidDataException("La data non può essere vuota!");
            }
            return LocalDate.parse(input);
        } catch (DateTimeParseException e) {
            throw new InvalidDataException("Formato data non valido! Usa il formato YYYY-MM-DD (es: 2025-09-01)");
        }
    }

    private static String leggiStringa(String messaggio) {
        System.out.print(messaggio);
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            throw new InvalidDataException("Il valore non può essere vuoto!");
        }
        return input;
    }

    private static boolean verificaEsistenzaPuntoEmissione(long id) {
        DistributoreAutomatico dist = distributoreDAO.findById(id);
        if (dist != null) return true;
        Rivenditore riv = rivenditoreDAO.findById(id);
        return riv != null;
    }

    private static void popolaDatabase() {
        System.out.println("POPOLAMENTO DATABASE IN CORSO...");


        try {
        /*    System.out.println("--- CREAZIONE UTENTI ---");*/
            Utente utente1 = new Utente("Mario", "Rossi", "mrossi", RuoloUtente.UTENTE_SEMPLICE);
            Utente utente2 = new Utente("Luigi", "Bianchi", "lbianchi", RuoloUtente.UTENTE_SEMPLICE);
            Utente utente3 = new Utente("Anna", "Verdi", "averdi", RuoloUtente.AMMINISTRATORE);
            utenteDAO.create(utente1);
            utenteDAO.create(utente2);
            utenteDAO.create(utente3);
            System.out.println("✓ 3 Utenti creati\n");

         /*   System.out.println("--- CREAZIONE TESSERE ---");*/
            Tessera tessera1 = new Tessera(LocalDate.of(2024, 1, 1), LocalDate.of(2025, 12, 31), utente1);
            Tessera tessera2 = new Tessera(LocalDate.of(2024, 6, 1), LocalDate.of(2025, 12, 31), utente2);
            Tessera tessera3 = new Tessera(LocalDate.of(2024, 3, 1), LocalDate.of(2025, 12, 31), utente3);
            tesseraDAO.create(tessera1);
            tesseraDAO.create(tessera2);
            tesseraDAO.create(tessera3);
            /*System.out.println("Tessera 1 - ID: " + tessera1.getId() + " | Emissione: " + tessera1.getDataEmissione() + " | Scadenza: " + tessera1.getDataScadenza() + " | Utente: " + utente1.getNome());
            System.out.println("Tessera 2 - ID: " + tessera2.getId() + " | Emissione: " + tessera2.getDataEmissione() + " | Scadenza: " + tessera2.getDataScadenza() + " | Utente: " + utente2.getNome());
            System.out.println("Tessera 3 - ID: " + tessera3.getId() + " | Emissione: " + tessera3.getDataEmissione() + " | Scadenza: " + tessera3.getDataScadenza() + " | Utente: " + utente3.getNome());*/
            System.out.println("✓ 3 Tessere create\n");

            /*System.out.println("--- CREAZIONE DISTRIBUTORI AUTOMATICI ---");*/
            DistributoreAutomatico dist1 = new DistributoreAutomatico("Via Roma 1", 1L, StatoDistributore.IN_SERVIZIO);
            DistributoreAutomatico dist2 = new DistributoreAutomatico("Via Milano 25", 2L, StatoDistributore.IN_SERVIZIO);
            DistributoreAutomatico dist3 = new DistributoreAutomatico("Piazza Napoli 10", 3L, StatoDistributore.FUORI_SERVIZIO);
            distributoreDAO.save(dist1);
            distributoreDAO.save(dist2);
            distributoreDAO.save(dist3);
            System.out.println("✓ 3 Distributori Automatici creati\n");

         /*   System.out.println("--- CREAZIONE RIVENDITORI ---");*/
            Rivenditore riv1 = new Rivenditore("Corso Italia 50", "Tabacchi Centrale", StatoRivenditore.APERTO);
            Rivenditore riv2 = new Rivenditore("Via Torino 88", "Edicola Mattino", StatoRivenditore.APERTO);
            Rivenditore riv3 = new Rivenditore("Viale Firenze 12", "Bar Sport", StatoRivenditore.CHIUSO);
            rivenditoreDAO.save(riv1);
            rivenditoreDAO.save(riv2);
            rivenditoreDAO.save(riv3);
            System.out.println("✓ 3 Rivenditori creati\n");

           /* System.out.println("--- CREAZIONE MEZZI ---");*/
            Mezzo bus1 = new Mezzo("AB123CD", TipoMezzo.AUTOBUS, 50, StatoMezzo.IN_SERVIZIO);
            Mezzo bus2 = new Mezzo("EF456GH", TipoMezzo.AUTOBUS, 45, StatoMezzo.IN_MANUTENZIONE);
            Mezzo bus3 = new Mezzo("IJ789KL", TipoMezzo.AUTOBUS, 55, StatoMezzo.IN_SERVIZIO);
            mezzoDAO.save(bus1);
            mezzoDAO.save(bus2);
            mezzoDAO.save(bus3);

            Mezzo tram1 = new Mezzo("MN012OP", TipoMezzo.TRAM, 80, StatoMezzo.IN_SERVIZIO);
            Mezzo tram2 = new Mezzo("QR345ST", TipoMezzo.TRAM, 75, StatoMezzo.IN_MANUTENZIONE);
            Mezzo tram3 = new Mezzo("UV678WX", TipoMezzo.TRAM, 85, StatoMezzo.IN_SERVIZIO);
            mezzoDAO.save(tram1);
            mezzoDAO.save(tram2);
            mezzoDAO.save(tram3);
          /*  System.out.println("Bus 1 - ID: " + bus1.getId() + " | Targa: " + bus1.getTarga() + " | Stato: " + bus1.getStatoMezzo());
            System.out.println("Bus 2 - ID: " + bus2.getId() + " | Targa: " + bus2.getTarga() + " | Stato: " + bus2.getStatoMezzo());
            System.out.println("Bus 3 - ID: " + bus3.getId() + " | Targa: " + bus3.getTarga() + " | Stato: " + bus3.getStatoMezzo());
            System.out.println("Tram 1 - ID: " + tram1.getId() + " | Targa: " + tram1.getTarga() + " | Stato: " + tram1.getStatoMezzo());
            System.out.println("Tram 2 - ID: " + tram2.getId() + " | Targa: " + tram2.getTarga() + " | Stato: " + tram2.getStatoMezzo());
            System.out.println("Tram 3 - ID: " + tram3.getId() + " | Targa: " + tram3.getTarga() + " | Stato: " + tram3.getStatoMezzo());*/
            System.out.println("✓ 6 Mezzi creati\n");

          /*  System.out.println("--- CREAZIONE MANUTENZIONI ---");*/
            Manutenzione man1 = new Manutenzione(null, bus2, LocalDate.of(2025, 9, 15), LocalDate.of(2025, 10, 15));
            Manutenzione man2 = new Manutenzione(null, tram2, LocalDate.of(2025, 9, 20), LocalDate.of(2025, 10, 20));
            manutenzioneDAO.save(man1);
            manutenzioneDAO.save(man2);
         /*   System.out.println("Manutenzione 1 - Mezzo: " + bus2.getTarga() + " | Data Inizio: 2025-09-15 | Data Fine: 2025-10-15");
            System.out.println("Manutenzione 2 - Mezzo: " + tram2.getTarga() + " | Data Inizio: 2025-09-20 | Data Fine: 2025-10-20");*/
            System.out.println("✓ 2 Manutenzioni create\n");

     /*       System.out.println("--- CREAZIONE BIGLIETTI ---");*/
            Biglietto big1 = new Biglietto(90, 5.20, LocalDate.of(2025, 9, 1), dist1, LocalDate.of(2025, 9, 1), bus1);
            Biglietto big2 = new Biglietto(60, 4.10, LocalDate.of(2025, 9, 5), riv1, LocalDate.of(2025, 9, 5), tram1);
            Biglietto big3 = new Biglietto(90, 5.20, LocalDate.of(2025, 9, 10), dist2, null, null);
            Biglietto big4 = new Biglietto(15, 2.00, LocalDate.of(2025, 9, 15), riv2, LocalDate.of(2025, 9, 15), bus3);
            Biglietto big5 = new Biglietto(30, 2.95, LocalDate.of(2025, 9, 20), dist1, LocalDate.of(2025, 9, 20), tram3);
            bigliettoDAO.save(big1);
            bigliettoDAO.save(big2);
            bigliettoDAO.save(big3);
            bigliettoDAO.save(big4);
            bigliettoDAO.save(big5);
            System.out.println("✓ 5 Biglietti creati\n");

          /*  System.out.println("--- CREAZIONE ABBONAMENTI ---");*/
            Abbonamento abb1 = new Abbonamento(TipoAbbonamento.MENSILE, LocalDate.of(2025, 9, 1),
                    LocalDate.of(2025, 10, 1), LocalDate.of(2025, 9, 1), 35.00, riv1, tessera1);
            Abbonamento abb2 = new Abbonamento(TipoAbbonamento.SETTIMANALE, LocalDate.of(2025, 9, 10),
                    LocalDate.of(2025, 9, 17), LocalDate.of(2025, 9, 10), 15.00, dist1, tessera2);
            Abbonamento abb3 = new Abbonamento(TipoAbbonamento.MENSILE, LocalDate.of(2025, 8, 1),
                    LocalDate.of(2025, 9, 1), LocalDate.of(2025, 8, 1), 35.00, riv2, tessera3);
            Abbonamento abb4 = new Abbonamento(TipoAbbonamento.SETTIMANALE, LocalDate.of(2025, 9, 15),
                    LocalDate.of(2025, 9, 22), LocalDate.of(2025, 9, 15), 15.00, dist2, tessera1);
            Abbonamento abb5 = new Abbonamento(TipoAbbonamento.MENSILE, LocalDate.of(2025, 9, 20),
                    LocalDate.of(2025, 10, 20), LocalDate.of(2025, 9, 20), 35.00, riv1, tessera2);
            abbonamentoDAO.save(abb1);
            abbonamentoDAO.save(abb2);
            abbonamentoDAO.save(abb3);
            abbonamentoDAO.save(abb4);
            abbonamentoDAO.save(abb5);
            System.out.println("✓ 5 Abbonamenti creati\n");

           /* System.out.println("--- CREAZIONE TRATTE ---");*/
            Tratta tratta1 = new Tratta(bus1, LocalTime.of(8, 0), "Roma", "Milano");
            Tratta tratta2 = new Tratta(bus3, LocalTime.of(8, 0), "Roma", "Milano");
            Tratta tratta3 = new Tratta(tram1, LocalTime.of(8, 0), "Roma", "Milano");
            Tratta tratta4 = new Tratta(tram3, LocalTime.of(6, 0), "Napoli", "Firenze");
            Tratta tratta5 = new Tratta(bus1, LocalTime.of(6, 0), "Napoli", "Firenze");
            Tratta tratta6 = new Tratta(bus3, LocalTime.of(6, 0), "Napoli", "Firenze");
            Tratta tratta7 = new Tratta(tram1, LocalTime.of(10, 0), "Bologna", "Torino");
            Tratta tratta8 = new Tratta(tram3, LocalTime.of(14, 0), "Venezia", "Genova");
            Tratta tratta9 = new Tratta(bus1, LocalTime.of(16, 0), "Palermo", "Catania");
            Tratta tratta10 = new Tratta(bus3, LocalTime.of(12, 0), "Bari", "Lecce");

            trattaDAO.save(tratta1);
            trattaDAO.save(tratta2);
            trattaDAO.save(tratta3);
            trattaDAO.save(tratta4);
            trattaDAO.save(tratta5);
            trattaDAO.save(tratta6);
            trattaDAO.save(tratta7);
            trattaDAO.save(tratta8);
            trattaDAO.save(tratta9);
            trattaDAO.save(tratta10);
           /* System.out.println("Tratta 1 - ID: " + tratta1.getId() + " | " + tratta1.getPartenza() + " → " + tratta1.getArrivo() + " | Tempo Previsto: " + tratta1.getTempoPrevisto());
            System.out.println("Tratta 2 - ID: " + tratta2.getId() + " | " + tratta2.getPartenza() + " → " + tratta2.getArrivo() + " | Tempo Previsto: " + tratta2.getTempoPrevisto());
            System.out.println("Tratta 3 - ID: " + tratta3.getId() + " | " + tratta3.getPartenza() + " → " + tratta3.getArrivo() + " | Tempo Previsto: " + tratta3.getTempoPrevisto());
            System.out.println("Tratta 4 - ID: " + tratta4.getId() + " | " + tratta4.getPartenza() + " → " + tratta4.getArrivo() + " | Tempo Previsto: " + tratta4.getTempoPrevisto());
            System.out.println("Tratta 5 - ID: " + tratta5.getId() + " | " + tratta5.getPartenza() + " → " + tratta5.getArrivo() + " | Tempo Previsto: " + tratta5.getTempoPrevisto());
            System.out.println("Tratta 6 - ID: " + tratta6.getId() + " | " + tratta6.getPartenza() + " → " + tratta6.getArrivo() + " | Tempo Previsto: " + tratta6.getTempoPrevisto());
            System.out.println("Tratta 7 - ID: " + tratta7.getId() + " | " + tratta7.getPartenza() + " → " + tratta7.getArrivo() + " | Tempo Previsto: " + tratta7.getTempoPrevisto());
            System.out.println("Tratta 8 - ID: " + tratta8.getId() + " | " + tratta8.getPartenza() + " → " + tratta8.getArrivo() + " | Tempo Previsto: " + tratta8.getTempoPrevisto());
            System.out.println("Tratta 9 - ID: " + tratta9.getId() + " | " + tratta9.getPartenza() + " → " + tratta9.getArrivo() + " | Tempo Previsto: " + tratta9.getTempoPrevisto());
            System.out.println("Tratta 10 - ID: " + tratta10.getId() + " | " + tratta10.getPartenza() + " → " + tratta10.getArrivo() + " | Tempo Previsto: " + tratta10.getTempoPrevisto());*/
            System.out.println("✓ 10 Tratte create\n");

          /*  System.out.println("--- CREAZIONE PERCORRENZE MEDIE ---");*/
            PercorrenzaMedia pm1 = new PercorrenzaMedia(tratta1, LocalTime.of(6, 45));
            PercorrenzaMedia pm2 = new PercorrenzaMedia(tratta2, LocalTime.of(7, 10));
            PercorrenzaMedia pm3 = new PercorrenzaMedia(tratta3, LocalTime.of(6, 55));
            PercorrenzaMedia pm4 = new PercorrenzaMedia(tratta4, LocalTime.of(4, 30));
            PercorrenzaMedia pm5 = new PercorrenzaMedia(tratta5, LocalTime.of(4, 45));
            PercorrenzaMedia pm6 = new PercorrenzaMedia(tratta6, LocalTime.of(4, 20));
            percorrenzaMediaDAO.save(pm1);
            percorrenzaMediaDAO.save(pm2);
            percorrenzaMediaDAO.save(pm3);
            percorrenzaMediaDAO.save(pm4);
            percorrenzaMediaDAO.save(pm5);
            percorrenzaMediaDAO.save(pm6);

            /*System.out.println("Percorrenza Media 1 - Tratta ID: " + tratta1.getId() + " | Tempo Effettivo: " + pm1.getTempoEffettivo());
            System.out.println("Percorrenza Media 2 - Tratta ID: " + tratta2.getId() + " | Tempo Effettivo: " + pm2.getTempoEffettivo());
            System.out.println("Percorrenza Media 3 - Tratta ID: " + tratta3.getId() + " | Tempo Effettivo: " + pm3.getTempoEffettivo());
            System.out.println("Percorrenza Media 4 - Tratta ID: " + tratta4.getId() + " | Tempo Effettivo: " + pm4.getTempoEffettivo());
            System.out.println("Percorrenza Media 5 - Tratta ID: " + tratta5.getId() + " | Tempo Effettivo: " + pm5.getTempoEffettivo());
            System.out.println("Percorrenza Media 6 - Tratta ID: " + tratta6.getId() + " | Tempo Effettivo: " + pm6.getTempoEffettivo());*/
            System.out.println("✓ 6 Percorrenze Medie create\n");

            System.out.println("RIEPILOGO DATE UTILI PER LE QUERY:");
            System.out.println("BIGLIETTI:");
            System.out.println("  - Emessi tra: 2025-09-01 e 2025-09-20");
            System.out.println("  - Validati: big1 (2025-09-01), big2 (2025-09-05), big4 (2025-09-15), big5 (2025-09-20)");
            System.out.println("\nABBONAMENTI:");
            System.out.println("  - Emessi tra: 2025-08-01 e 2025-09-20");
            System.out.println("  - abb1: MENSILE (2025-09-01 → 2025-10-01)");
            System.out.println("  - abb2: SETTIMANALE (2025-09-10 → 2025-09-17) - SCADUTO");
            System.out.println("  - abb3: MENSILE (2025-08-01 → 2025-09-01) - SCADUTO");
            System.out.println("  - abb4: SETTIMANALE (2025-09-15 → 2025-09-22) - SCADUTO");
            System.out.println("  - abb5: MENSILE (2025-09-20 → 2025-10-20)");
            System.out.println("\nMANUTENZIONI:");
            System.out.println("  - Bus2: 2025-09-15 → 2025-10-15");
            System.out.println("  - Tram2: 2025-09-20 → 2025-10-20");


            System.out.println("DATABASE POPOLATO CON SUCCESSO!");
        } catch (Exception e) {
            System.err.println("Errore durante il popolamento del database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void contaBigliettiEmessiPerPeriodo() {
        try {
            System.out.println("\n--- CONTA BIGLIETTI EMESSI PER PERIODO ---");
            LocalDate dataInizio = leggiData("Data inizio (YYYY-MM-DD): ");
            LocalDate dataFine = leggiData("Data fine (YYYY-MM-DD): ");

            if (dataInizio.isAfter(dataFine)) {
                throw new InvalidDataException("La data di inizio non può essere successiva alla data di fine!");
            }

            long count = bigliettoDAO.countBigliettiByPeriodo(dataInizio, dataFine);
            System.out.println("Numero biglietti emessi nel periodo: " + count);

            if (count > 0){
                List<Biglietto> biglietti = bigliettoDAO.findBigliettoByPeriodo(dataInizio, dataFine);
                for(Biglietto b : biglietti){
                    System.out.println("ID biglietto emesso: " + b.getId());
                }
            }

        } catch (InvalidDataException | InputMismatchCustomException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Errore durante il conteggio: " + e.getMessage());
        }
    }

    private static void contaBigliettiEmessiPerPuntoEmissione() {
        try {
            System.out.println("\n--- CONTA BIGLIETTI EMESSI PER PUNTO DI EMISSIONE ---");
            long puntoId = leggiLong("ID punto di emissione: ");

            if (!verificaEsistenzaPuntoEmissione(puntoId)) {
                throw new NotFoundException("Id " + puntoId + " non trovato");
            }

            LocalDate dataInizio = leggiData("Data inizio (YYYY-MM-DD): ");
            LocalDate dataFine = leggiData("Data fine (YYYY-MM-DD): ");

            if (dataInizio.isAfter(dataFine)) {
                throw new InvalidDataException("La data di inizio non può essere successiva alla data di fine!");
            }

            long count = bigliettoDAO.countBigliettiByPuntoEmissioneAndPeriodo(puntoId, dataInizio, dataFine);
            System.out.println("Numero biglietti emessi: " + count);

            if (count > 0) {
                List<Biglietto> biglietti = bigliettoDAO.findBigliettoByPuntoEmissioneAndPeriodo(puntoId, dataInizio, dataFine);
                for(Biglietto b : biglietti) {
                    System.out.println("ID biglietto emesso: " + b.getId());
                }
            }


        } catch (NotFoundException e) {
            System.err.println(e.getMessage());
        } catch (InvalidDataException | InputMismatchCustomException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Errore durante il conteggio: " + e.getMessage());
        }
    }

    private static void contaBigliettiVidimatiPerMezzo() {
        try {
            System.out.println("\n--- CONTA BIGLIETTI VIDIMATI PER MEZZO ---");
            long mezzoId = leggiLong("ID mezzo: ");

            Mezzo mezzo = mezzoDAO.findById(mezzoId);
            if (mezzo == null) {
                throw new NotFoundException("Id " + mezzoId + " non trovato");
            }

            LocalDate dataInizio = leggiData("Data inizio (YYYY-MM-DD): ");
            LocalDate dataFine = leggiData("Data fine (YYYY-MM-DD): ");

            if (dataInizio.isAfter(dataFine)) {
                throw new InvalidDataException("La data di inizio non può essere successiva alla data di fine!");
            }

            long count = bigliettoDAO.countBigliettiVidimatiByMezzo(mezzoId, dataInizio, dataFine);
            System.out.println("Numero biglietti vidimati sul mezzo: " + count);
            if (count > 0) {
                List<Biglietto> biglietti = bigliettoDAO.trovaBigliettiVidimatiByMezzo(mezzoId, dataInizio, dataFine);
                for(Biglietto b : biglietti) {
                    System.out.println("ID biglietto vidimato: " + b.getId());
                }
            }
        } catch (NotFoundException e) {
            System.err.println(e.getMessage());
        } catch (InvalidDataException | InputMismatchCustomException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Errore durante il conteggio: " + e.getMessage());
        }
    }

    private static void contaBigliettiVidimatiTotali() {
        try {
            System.out.println("\n--- CONTA BIGLIETTI VIDIMATI TOTALI ---");
            LocalDate dataInizio = leggiData("Data inizio (YYYY-MM-DD): ");
            LocalDate dataFine = leggiData("Data fine (YYYY-MM-DD): ");

            if (dataInizio.isAfter(dataFine)) {
                throw new InvalidDataException("La data di inizio non può essere successiva alla data di fine!");
            }

            long count = bigliettoDAO.countBigliettiVidimatiByPeriodo(dataInizio, dataFine);
            System.out.println("Numero totale biglietti vidimati: " + count);
            if (count > 0) {
                List<Biglietto> biglietti = bigliettoDAO.countBigliettiVidimatiTotal(dataInizio, dataFine);
                for(Biglietto b : biglietti) {
                    System.out.println("ID biglietto vidimato: " + b.getId());
                }
            }
        } catch (InvalidDataException | InputMismatchCustomException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Errore durante il conteggio: " + e.getMessage());
        }
    }

    private static void contaAbbonamentiEmessiPerPeriodo() {
        try {
            System.out.println("\n--- CONTA ABBONAMENTI EMESSI PER PERIODO ---");
            LocalDate dataInizio = leggiData("Data inizio (YYYY-MM-DD): ");
            LocalDate dataFine = leggiData("Data fine (YYYY-MM-DD): ");

            if (dataInizio.isAfter(dataFine)) {
                throw new InvalidDataException("La data di inizio non può essere successiva alla data di fine!");
            }

            long count = abbonamentoDAO.countAbbonamentiByPeriodo(dataInizio, dataFine);
            System.out.println("Numero abbonamenti emessi nel periodo: " + count);
            if (count > 0) {
                List<Abbonamento> abbonamenti = abbonamentoDAO.StampaAbbonamentiByPeriodo(dataInizio, dataFine);
                for(Abbonamento a : abbonamenti) {
                    System.out.println("ID abbonamento emesso: " + a.getId());
                }
            }


        } catch (InvalidDataException | InputMismatchCustomException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Errore durante il conteggio: " + e.getMessage());
        }
    }

    private static void contaAbbonamentiEmessiPerPuntoEmissione() {
        try {
            System.out.println("\n--- CONTA ABBONAMENTI EMESSI PER PUNTO DI EMISSIONE ---");
            long puntoId = leggiLong("ID punto di emissione: ");

            if (!verificaEsistenzaPuntoEmissione(puntoId)) {
                throw new NotFoundException("Id " + puntoId + " non trovato");
            }

            LocalDate dataInizio = leggiData("Data inizio (YYYY-MM-DD): ");
            LocalDate dataFine = leggiData("Data fine (YYYY-MM-DD): ");

            if (dataInizio.isAfter(dataFine)) {
                throw new InvalidDataException("La data di inizio non può essere successiva alla data di fine!");
            }

            long count = abbonamentoDAO.countAbbonamentiByPuntoEmissioneAndPeriodo(puntoId, dataInizio, dataFine);
            System.out.println("Numero abbonamenti emessi: " + count);

            if (count > 0) {
                List<Abbonamento> abbonamenti = abbonamentoDAO.stampaAbbonamentiByPuntoEmissioneAndPeriodo(puntoId,dataInizio, dataFine);
                for(Abbonamento a : abbonamenti) {
                    System.out.println("ID abbonamento emesso: " + a.getId());
                }
            }
        } catch (NotFoundException e) {
            System.err.println(e.getMessage());
        } catch (InvalidDataException | InputMismatchCustomException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Errore durante il conteggio: " + e.getMessage());
        }
    }

    private static void verificaValiditaAbbonamento() {
        try {
            System.out.println("\n--- VERIFICA VALIDITÀ ABBONAMENTO ---");
            long tesseraId = leggiLong("ID tessera: ");

            Tessera tessera = tesseraDAO.findById(tesseraId);
            if (tessera == null) {
                throw new NotFoundException("Id " + tesseraId + " non trovato");
            }

            boolean valido = tesseraDAO.verificaValiditaAbbonamento(tesseraId);
            if (valido) {
                System.out.println("La tessera ha un abbonamento VALIDO!");
                Abbonamento abb = tesseraDAO.findAbbonamentoValidoByTessera(tesseraId);
                if (abb != null) {
                    System.out.println("Abbonamento: " + abb);
                }
            } else {
                System.out.println("La tessera NON ha abbonamenti validi.");
            }
        } catch (NotFoundException e) {
            System.err.println(e.getMessage());
        } catch (InvalidDataException | InputMismatchCustomException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Errore durante la verifica: " + e.getMessage());
        }
    }

    private static void visualizzaMezziPerStato() {
        try {
            System.out.println("\n--- VISUALIZZA MEZZI PER STATO ---");
            System.out.println("Seleziona lo stato:");
            System.out.println("1. IN_SERVIZIO");
            System.out.println("2. IN_MANUTENZIONE");
            System.out.print("Scelta: ");

            int scelta = leggiIntero();

            StatoMezzo stato;
            switch (scelta) {
                case 1:
                    stato = StatoMezzo.IN_SERVIZIO;
                    break;
                case 2:
                    stato = StatoMezzo.IN_MANUTENZIONE;
                    break;
                default:
                    throw new InvalidDataException("Scelta non valida! Seleziona 1 o 2.");
            }

            var mezzi = mezzoDAO.findByStato(stato);
            System.out.println("\nMezzi con stato " + stato + ":");
            if (mezzi.isEmpty()) {
                System.out.println("Nessun mezzo trovato.");
            } else {
                mezzi.forEach(System.out::println);
            }
        } catch (InvalidDataException | InputMismatchCustomException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Errore durante la ricerca: " + e.getMessage());
        }
    }

    private static void verificaManutenzione() {
        try {
            System.out.println("\n--- VERIFICA SE MEZZO È IN MANUTENZIONE ---");
            long mezzoId = leggiLong("ID mezzo: ");

            Mezzo mezzo = mezzoDAO.findById(mezzoId);
            if (mezzo == null) {
                throw new NotFoundException("Id " + mezzoId + " non trovato");
            }

            boolean inManutenzione = mezzoDAO.isInManutenzione(mezzoId);
            if (inManutenzione) {
                System.out.println("Il mezzo è IN MANUTENZIONE");
            } else {
                System.out.println("Il mezzo NON è in manutenzione");
            }
        } catch (NotFoundException e) {
            System.err.println(e.getMessage());
        } catch (InvalidDataException | InputMismatchCustomException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Errore durante la verifica: " + e.getMessage());
        }
    }

    private static void visualizzaManutenzioniMezzo() {
        try {
            System.out.println("\n--- VISUALIZZA MANUTENZIONI MEZZO ---");
            long mezzoId = leggiLong("ID mezzo: ");

            Mezzo mezzo = mezzoDAO.findById(mezzoId);
            if (mezzo == null) {
                throw new NotFoundException("Id " + mezzoId + " non trovato");
            }

            var manutenzioni = manutenzioneDAO.findByMezzo(mezzoId);
            System.out.println("Manutenzioni del mezzo:");
            if (manutenzioni.isEmpty()) {
                System.out.println("Nessuna manutenzione trovata per questo mezzo.");
            } else {
                manutenzioni.forEach(System.out::println);
            }
        } catch (NotFoundException e) {
            System.err.println(e.getMessage());
        } catch (InvalidDataException | InputMismatchCustomException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Errore durante la ricerca: " + e.getMessage());
        }
    }
    private static void calcolaPercorrenzePerUnaTratta() {
        try {
            System.out.println("\n--- CONTA PERCORRENZE PER TRATTA ---");
            System.out.println("Seleziona una tratta tra:");
            System.out.println("1. Roma → Milano");
            System.out.println("2. Napoli → Firenze");
            System.out.println("3. Bologna → Torino");
            System.out.println("4. Venezia → Genova");
            System.out.println("5. Palermo → Catania");
            System.out.println("6. Bari → Lecce");
            System.out.print("Scelta: ");

            int scelta = leggiIntero();
            if (scelta < 1 || scelta > 6) {
                System.err.println("Scelta non valida. Seleziona un numero tra 1 e 6.");
                return;
            }

            String partenza = "";
            String arrivo = "";

            switch (scelta) {
                case 1:
                    partenza = "Roma"; arrivo = "Milano"; break;
                case 2:
                    partenza = "Napoli"; arrivo = "Firenze"; break;
                case 3:
                    partenza = "Bologna"; arrivo = "Torino"; break;
                case 4:
                    partenza = "Venezia"; arrivo = "Genova"; break;
                case 5:
                    partenza = "Palermo"; arrivo = "Catania"; break;
                case 6:
                    partenza = "Bari"; arrivo = "Lecce"; break;
            }

            long count = trattaDAO.contaPercorrenzePerTratta(partenza, arrivo);
            List<Long> mezziId = trattaDAO.getMezziIdPerTratta(partenza, arrivo);

            System.out.println("\nRisultato:");
            System.out.println("La tratta " + partenza + " → " + arrivo +
                    " è percorsa da " + count + " mezzo/i");

            if (!mezziId.isEmpty()) {
                System.out.print("ID Mezzo/i: ");
                System.out.println(mezziId.stream()
                        .map(String::valueOf)
                        .collect(java.util.stream.Collectors.joining(", ")));
            }
        } catch (Exception e) {
            System.err.println("Errore durante il conteggio: " + e.getMessage());
        }
    }


    private static void calcolaEStampaPercorrenzeMedie() {
        List<PercorrenzaMedia> percorrenze = percorrenzaMediaDAO.getAllPercorrenzeMedie();

        if (percorrenze.isEmpty()) {
            System.out.println("Nessuna percorrenza media presente.");
            return;
        }

        System.out.println("--- Percorrenze medie registrate ---");
        for (PercorrenzaMedia pm : percorrenze) {
            Long idTratta = pm.getTratta() != null ? pm.getTratta().getId() : null;
            Long idMezzo = pm.getIdMezzo();

            System.out.println("ID Tratta: " + idTratta + ", ID Mezzo: " + idMezzo +
                    ", Tempo medio percorrenza: " + pm.getTempoEffettivo());
        }
    }
    }

