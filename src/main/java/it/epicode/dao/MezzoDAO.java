package it.epicode.dao;

import it.epicode.entity.Mezzo;
import it.epicode.entity.Mezzo.StatoMezzo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Interfaccia DAO per l'entità Mezzo.
 * Estende GenericDAO e definisce metodi specifici per la gestione dei mezzi di trasporto.
 */
public interface MezzoDAO extends GenericDAO<Mezzo, Long> {
    
    /**
     * Trova un mezzo per numero
     * @param numeroMezzo Il numero del mezzo
     * @return Optional contenente il mezzo se trovato
     */
    Optional<Mezzo> findByNumeroMezzo(String numeroMezzo);
    
    /**
     * Trova tutti i mezzi per stato
     * @param stato Lo stato del mezzo
     * @return Lista dei mezzi con lo stato specificato
     */
    List<Mezzo> findByStato(StatoMezzo stato);
    
    /**
     * Trova tutti i mezzi in servizio
     * @return Lista dei mezzi in servizio
     */
    List<Mezzo> findMezziInServizio();
    
    /**
     * Trova tutti i mezzi in manutenzione
     * @return Lista dei mezzi in manutenzione
     */
    List<Mezzo> findMezziInManutenzione();
    
    /**
     * Trova mezzi per tipo (AUTOBUS, TRAM)
     * @param tipo Il tipo di mezzo
     * @return Lista dei mezzi del tipo specificato
     */
    List<Mezzo> findByTipo(String tipo);
    
    /**
     * Trova mezzi per anno di immatricolazione
     * @param annoImmatricolazione L'anno di immatricolazione
     * @return Lista dei mezzi immatricolati nell'anno specificato
     */
    List<Mezzo> findByAnnoImmatricolazione(Integer annoImmatricolazione);
    
    /**
     * Trova mezzi per modello
     * @param modello Il modello del mezzo
     * @return Lista dei mezzi del modello specificato
     */
    List<Mezzo> findByModello(String modello);
    
    /**
     * Trova mezzi con capienza maggiore o uguale a quella specificata
     * @param capienzaMinima La capienza minima
     * @return Lista dei mezzi con capienza >= capienzaMinima
     */
    List<Mezzo> findByCapienzaGreaterThanEqual(Integer capienzaMinima);
    
    /**
     * Trova mezzi creati in un periodo
     * @param dataInizio Data inizio periodo
     * @param dataFine Data fine periodo
     * @return Lista dei mezzi creati nel periodo
     */
    List<Mezzo> findMezziCreatedBetween(LocalDateTime dataInizio, LocalDateTime dataFine);
    
    /**
     * Trova mezzi con vidimazioni in un periodo
     * @param dataInizio Data inizio periodo
     * @param dataFine Data fine periodo
     * @return Lista dei mezzi che hanno avuto vidimazioni nel periodo
     */
    List<Mezzo> findMezziWithVidimazioniInPeriodo(LocalDateTime dataInizio, LocalDateTime dataFine);
    
    /**
     * Verifica se esiste un mezzo con il numero specificato
     * @param numeroMezzo Il numero del mezzo da verificare
     * @return true se esiste, false altrimenti
     */
    boolean existsByNumeroMezzo(String numeroMezzo);
    
    /**
     * Conta i mezzi per stato
     * @param stato Lo stato del mezzo
     * @return Numero di mezzi con lo stato specificato
     */
    long countByStato(StatoMezzo stato);
    
    /**
     * Conta i mezzi per tipo
     * @param tipo Il tipo di mezzo
     * @return Numero di mezzi del tipo specificato
     */
    long countByTipo(String tipo);
    
    /**
     * Conta i mezzi in servizio
     * @return Numero di mezzi in servizio
     */
    long countMezziInServizio();
    
    /**
     * Conta i mezzi in manutenzione
     * @return Numero di mezzi in manutenzione
     */
    long countMezziInManutenzione();
    
    /**
     * Calcola la capienza totale di tutti i mezzi in servizio
     * @return Capienza totale dei mezzi in servizio
     */
    long calculateCapienzaTotaleMezziInServizio();
    
    /**
     * Trova i mezzi più utilizzati (con più vidimazioni) in un periodo
     * @param dataInizio Data inizio periodo
     * @param dataFine Data fine periodo
     * @param limit Numero massimo di risultati
     * @return Lista dei mezzi più utilizzati
     */
    List<Mezzo> findMezziPiuUtilizzatiInPeriodo(LocalDateTime dataInizio, LocalDateTime dataFine, int limit);
    
    /**
     * Calcola l'età media della flotta
     * @return Età media in anni
     */
    double calculateEtaMediaFlotta();
}