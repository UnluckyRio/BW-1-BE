package it.epicode.dao;

import it.epicode.entity.PuntoEmissione;
import it.epicode.entity.Biglietto;
import it.epicode.entity.Abbonamento;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Interfaccia DAO per l'entità PuntoEmissione.
 * Definisce le operazioni specifiche per la gestione dei punti di emissione.
 */
public interface PuntoEmissioneDAO extends GenericDAO<PuntoEmissione, Long> {
    
    /**
     * Trova un punto di emissione per nome
     * @param nome Il nome del punto di emissione
     * @return Optional contenente il punto di emissione se trovato
     */
    Optional<PuntoEmissione> findByNome(String nome);
    
    /**
     * Trova punti di emissione per tipo
     * @param tipo Il tipo di punto di emissione
     * @return Lista dei punti di emissione del tipo specificato
     */
    List<PuntoEmissione> findByTipo(String tipo);
    
    /**
     * Trova punti di emissione per indirizzo (ricerca parziale)
     * @param indirizzo L'indirizzo o parte dell'indirizzo
     * @return Lista dei punti di emissione che contengono l'indirizzo specificato
     */
    List<PuntoEmissione> findByIndirizzoContaining(String indirizzo);
    
    /**
     * Trova tutti i punti di emissione attivi
     * @return Lista dei punti di emissione attivi
     */
    List<PuntoEmissione> findActivePuntiEmissione();
    
    /**
     * Trova tutti i punti di emissione non attivi
     * @return Lista dei punti di emissione non attivi
     */
    List<PuntoEmissione> findInactivePuntiEmissione();
    
    /**
     * Trova punti di emissione creati in un periodo specifico
     * @param dataInizio Data di inizio del periodo
     * @param dataFine Data di fine del periodo
     * @return Lista dei punti di emissione creati nel periodo
     */
    List<PuntoEmissione> findPuntiEmissioneCreatedBetween(LocalDateTime dataInizio, LocalDateTime dataFine);
    
    /**
     * Trova punti di emissione che hanno emesso biglietti in un periodo
     * @param dataInizio Data di inizio del periodo
     * @param dataFine Data di fine del periodo
     * @return Lista dei punti di emissione che hanno emesso biglietti
     */
    List<PuntoEmissione> findPuntiEmissioneWithBigliettiInPeriodo(LocalDate dataInizio, LocalDate dataFine);
    
    /**
     * Trova punti di emissione che hanno emesso abbonamenti in un periodo
     * @param dataInizio Data di inizio del periodo
     * @param dataFine Data di fine del periodo
     * @return Lista dei punti di emissione che hanno emesso abbonamenti
     */
    List<PuntoEmissione> findPuntiEmissioneWithAbbonamentiInPeriodo(LocalDate dataInizio, LocalDate dataFine);
    
    /**
     * Trova punti di emissione senza biglietti emessi
     * @return Lista dei punti di emissione che non hanno mai emesso biglietti
     */
    List<PuntoEmissione> findPuntiEmissioneWithoutBiglietti();
    
    /**
     * Trova punti di emissione senza abbonamenti emessi
     * @return Lista dei punti di emissione che non hanno mai emesso abbonamenti
     */
    List<PuntoEmissione> findPuntiEmissioneWithoutAbbonamenti();
    
    /**
     * Conta i biglietti emessi da un punto di emissione
     * @param puntoEmissione Il punto di emissione
     * @return Numero di biglietti emessi
     */
    long countBigliettiByPuntoEmissione(PuntoEmissione puntoEmissione);
    
    /**
     * Conta gli abbonamenti emessi da un punto di emissione
     * @param puntoEmissione Il punto di emissione
     * @return Numero di abbonamenti emessi
     */
    long countAbbonamentiByPuntoEmissione(PuntoEmissione puntoEmissione);
    
    /**
     * Conta i biglietti emessi da un punto di emissione in un periodo
     * @param puntoEmissione Il punto di emissione
     * @param dataInizio Data di inizio del periodo
     * @param dataFine Data di fine del periodo
     * @return Numero di biglietti emessi nel periodo
     */
    long countBigliettiByPuntoEmissioneInPeriodo(PuntoEmissione puntoEmissione, LocalDate dataInizio, LocalDate dataFine);
    
    /**
     * Conta gli abbonamenti emessi da un punto di emissione in un periodo
     * @param puntoEmissione Il punto di emissione
     * @param dataInizio Data di inizio del periodo
     * @param dataFine Data di fine del periodo
     * @return Numero di abbonamenti emessi nel periodo
     */
    long countAbbonamentiByPuntoEmissioneInPeriodo(PuntoEmissione puntoEmissione, LocalDate dataInizio, LocalDate dataFine);
    
    /**
     * Verifica se esiste un punto di emissione con il nome specificato
     * @param nome Il nome da verificare
     * @return true se esiste, false altrimenti
     */
    boolean existsByNome(String nome);
    
    /**
     * Conta i punti di emissione attivi
     * @return Numero di punti di emissione attivi
     */
    long countActivePuntiEmissione();
    
    /**
     * Conta i punti di emissione per tipo
     * @param tipo Il tipo di punto di emissione
     * @return Numero di punti di emissione del tipo specificato
     */
    long countByTipo(String tipo);
    
    /**
     * Trova i punti di emissione più produttivi (con più emissioni) in un periodo
     * @param dataInizio Data di inizio del periodo
     * @param dataFine Data di fine del periodo
     * @param limit Numero massimo di risultati
     * @return Lista dei punti di emissione ordinati per produttività
     */
    List<PuntoEmissione> findMostProductivePuntiEmissione(LocalDate dataInizio, LocalDate dataFine, int limit);
    
    /**
     * Trova tutti i biglietti emessi da un punto di emissione
     * @param puntoEmissione Il punto di emissione
     * @return Lista dei biglietti emessi
     */
    List<Biglietto> findBigliettiByPuntoEmissione(PuntoEmissione puntoEmissione);
    
    /**
     * Trova tutti gli abbonamenti emessi da un punto di emissione
     * @param puntoEmissione Il punto di emissione
     * @return Lista degli abbonamenti emessi
     */
    List<Abbonamento> findAbbonamentiByPuntoEmissione(PuntoEmissione puntoEmissione);
}