package it.epicode.dao;

import it.epicode.entity.Tessera;
import it.epicode.entity.Utente;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Interfaccia DAO per l'entità Tessera.
 * Estende GenericDAO e aggiunge metodi specifici per la gestione delle tessere.
 */
public interface TesseraDAO extends GenericDAO<Tessera, Long> {
    
    /**
     * Trova una tessera per numero tessera
     * @param numeroTessera Il numero della tessera
     * @return Optional contenente la tessera se trovata
     */
    Optional<Tessera> findByNumeroTessera(String numeroTessera);
    
    /**
     * Trova tutte le tessere di un utente
     * @param utente L'utente proprietario delle tessere
     * @return Lista delle tessere dell'utente
     */
    List<Tessera> findByUtente(Utente utente);
    
    /**
     * Trova tutte le tessere attive
     * @return Lista delle tessere attive
     */
    List<Tessera> findActiveTessere();
    
    /**
     * Trova tutte le tessere scadute
     * @return Lista delle tessere scadute
     */
    List<Tessera> findExpiredTessere();
    
    /**
     * Trova tessere che scadono entro una data specifica
     * @param dataScadenza La data limite di scadenza
     * @return Lista delle tessere che scadono entro la data specificata
     */
    List<Tessera> findTessereExpiringBefore(LocalDate dataScadenza);
    
    /**
     * Trova tessere che scadono in un periodo specifico
     * @param dataInizio Data di inizio del periodo
     * @param dataFine Data di fine del periodo
     * @return Lista delle tessere che scadono nel periodo
     */
    List<Tessera> findTessereExpiringBetween(LocalDate dataInizio, LocalDate dataFine);
    
    /**
     * Trova tessere emesse in un periodo
     * @param dataInizio Data di inizio del periodo
     * @param dataFine Data di fine del periodo
     * @return Lista delle tessere emesse nel periodo
     */
    List<Tessera> findTessereEmesseInPeriodo(LocalDate dataInizio, LocalDate dataFine);
    
    /**
     * Trova tessere valide (attive e non scadute)
     * @return Lista delle tessere valide
     */
    List<Tessera> findValidTessere();
    
    /**
     * Trova tessere valide di un utente
     * @param utente L'utente proprietario delle tessere
     * @return Lista delle tessere valide dell'utente
     */
    List<Tessera> findValidTessereByUtente(Utente utente);
    
    /**
     * Trova tessere con abbonamenti attivi
     * @return Lista delle tessere con almeno un abbonamento attivo
     */
    List<Tessera> findTessereWithActiveAbbonamenti();
    
    /**
     * Trova tessere senza abbonamenti
     * @return Lista delle tessere senza abbonamenti
     */
    List<Tessera> findTessereWithoutAbbonamenti();
    
    /**
     * Verifica se un numero tessera è già utilizzato
     * @param numeroTessera Il numero tessera da verificare
     * @return true se il numero è già utilizzato, false altrimenti
     */
    boolean existsByNumeroTessera(String numeroTessera);
    
    /**
     * Conta le tessere attive
     * @return Numero di tessere attive
     */
    long countActiveTessere();
    
    /**
     * Conta le tessere scadute
     * @return Numero di tessere scadute
     */
    long countExpiredTessere();
    
    /**
     * Conta le tessere emesse in un periodo
     * @param dataInizio Data di inizio del periodo
     * @param dataFine Data di fine del periodo
     * @return Numero di tessere emesse nel periodo
     */
    long countTessereEmesseInPeriodo(LocalDate dataInizio, LocalDate dataFine);
    
    /**
     * Trova tessere che necessitano rinnovo (scadono nei prossimi giorni)
     * @param giorni Numero di giorni entro cui la tessera scade
     * @return Lista delle tessere che necessitano rinnovo
     */
    List<Tessera> findTessereNeedingRenewal(int giorni);
}