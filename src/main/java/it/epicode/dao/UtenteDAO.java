package it.epicode.dao;

import it.epicode.entity.Utente;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Interfaccia DAO per l'entità Utente.
 * Estende GenericDAO e aggiunge metodi specifici per la gestione degli utenti.
 */
public interface UtenteDAO extends GenericDAO<Utente, Long> {
    
    /**
     * Trova un utente per email
     * @param email L'email dell'utente
     * @return Optional contenente l'utente se trovato
     */
    Optional<Utente> findByEmail(String email);
    
    /**
     * Trova utenti per nome e cognome
     * @param nome Il nome dell'utente
     * @param cognome Il cognome dell'utente
     * @return Lista degli utenti trovati
     */
    List<Utente> findByNomeAndCognome(String nome, String cognome);
    
    /**
     * Trova utenti per nome (ricerca parziale)
     * @param nome Il nome o parte del nome
     * @return Lista degli utenti trovati
     */
    List<Utente> findByNomeContaining(String nome);
    
    /**
     * Trova utenti per cognome (ricerca parziale)
     * @param cognome Il cognome o parte del cognome
     * @return Lista degli utenti trovati
     */
    List<Utente> findByCognomeContaining(String cognome);
    
    /**
     * Trova utenti nati in una data specifica
     * @param dataNascita La data di nascita
     * @return Lista degli utenti trovati
     */
    List<Utente> findByDataNascita(LocalDate dataNascita);
    
    /**
     * Trova utenti nati in un periodo
     * @param dataInizio Data di inizio del periodo
     * @param dataFine Data di fine del periodo
     * @return Lista degli utenti trovati
     */
    List<Utente> findByDataNascitaBetween(LocalDate dataInizio, LocalDate dataFine);
    
    /**
     * Trova utenti per numero di telefono
     * @param telefono Il numero di telefono
     * @return Optional contenente l'utente se trovato
     */
    Optional<Utente> findByTelefono(String telefono);
    
    /**
     * Trova utenti con tessere attive
     * @return Lista degli utenti con almeno una tessera attiva
     */
    List<Utente> findUsersWithActiveTessere();
    
    /**
     * Trova utenti senza tessere
     * @return Lista degli utenti senza tessere
     */
    List<Utente> findUsersWithoutTessere();
    
    /**
     * Trova utenti con tessere scadute
     * @return Lista degli utenti con tessere scadute
     */
    List<Utente> findUsersWithExpiredTessere();
    
    /**
     * Verifica se un'email è già utilizzata
     * @param email L'email da verificare
     * @return true se l'email è già utilizzata, false altrimenti
     */
    boolean existsByEmail(String email);
    
    /**
     * Verifica se un numero di telefono è già utilizzato
     * @param telefono Il numero di telefono da verificare
     * @return true se il telefono è già utilizzato, false altrimenti
     */
    boolean existsByTelefono(String telefono);
    
    /**
     * Conta gli utenti registrati in un periodo
     * @param dataInizio Data di inizio del periodo
     * @param dataFine Data di fine del periodo
     * @return Numero di utenti registrati nel periodo
     */
    long countUsersRegisteredBetween(LocalDate dataInizio, LocalDate dataFine);
    
    /**
     * Trova utenti per età
     * @param etaMinima Età minima
     * @param etaMassima Età massima
     * @return Lista degli utenti nell'intervallo di età specificato
     */
    List<Utente> findByEtaBetween(int etaMinima, int etaMassima);
}