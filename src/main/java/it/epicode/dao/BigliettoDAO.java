package it.epicode.dao;

import it.epicode.entity.Biglietto;
import it.epicode.entity.PuntoEmissione;
import it.epicode.entity.Vidimazione;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Interfaccia DAO per l'entità Biglietto.
 * Definisce le operazioni specifiche per la gestione dei biglietti.
 */
public interface BigliettoDAO extends GenericDAO<Biglietto, Long> {
    
    /**
     * Trova un biglietto per codice
     * @param codiceBiglietto Il codice del biglietto
     * @return Optional contenente il biglietto se trovato
     */
    Optional<Biglietto> findByCodiceBiglietto(String codiceBiglietto);
    
    /**
     * Trova biglietti emessi in una data specifica
     * @param dataEmissione La data di emissione
     * @return Lista dei biglietti emessi nella data specificata
     */
    List<Biglietto> findByDataEmissione(LocalDate dataEmissione);
    
    /**
     * Trova biglietti emessi in un periodo
     * @param dataInizio Data di inizio del periodo
     * @param dataFine Data di fine del periodo
     * @return Lista dei biglietti emessi nel periodo
     */
    List<Biglietto> findByDataEmissioneBetween(LocalDate dataInizio, LocalDate dataFine);
    
    /**
     * Trova biglietti che scadono in una data specifica
     * @param dataScadenza La data di scadenza
     * @return Lista dei biglietti che scadono nella data specificata
     */
    List<Biglietto> findByDataScadenza(LocalDate dataScadenza);
    
    /**
     * Trova biglietti che scadono entro una data
     * @param dataScadenza La data limite di scadenza
     * @return Lista dei biglietti che scadono entro la data specificata
     */
    List<Biglietto> findByDataScadenzaBefore(LocalDate dataScadenza);
    
    /**
     * Trova biglietti per prezzo
     * @param prezzo Il prezzo del biglietto
     * @return Lista dei biglietti con il prezzo specificato
     */
    List<Biglietto> findByPrezzo(BigDecimal prezzo);
    
    /**
     * Trova biglietti con prezzo in un range
     * @param prezzoMin Prezzo minimo
     * @param prezzoMax Prezzo massimo
     * @return Lista dei biglietti nel range di prezzo
     */
    List<Biglietto> findByPrezzoBetween(BigDecimal prezzoMin, BigDecimal prezzoMax);
    
    /**
     * Trova biglietti validati
     * @return Lista dei biglietti validati
     */
    List<Biglietto> findValidatedBiglietti();
    
    /**
     * Trova biglietti non validati
     * @return Lista dei biglietti non validati
     */
    List<Biglietto> findNonValidatedBiglietti();
    
    /**
     * Trova biglietti validati in una data specifica
     * @param dataValidazione La data di validazione
     * @return Lista dei biglietti validati nella data specificata
     */
    List<Biglietto> findByDataValidazione(LocalDate dataValidazione);
    
    /**
     * Trova biglietti validati in un periodo
     * @param dataInizio Data di inizio del periodo
     * @param dataFine Data di fine del periodo
     * @return Lista dei biglietti validati nel periodo
     */
    List<Biglietto> findByDataValidazioneBetween(LocalDate dataInizio, LocalDate dataFine);
    
    /**
     * Trova biglietti emessi da un punto di emissione
     * @param puntoEmissione Il punto di emissione
     * @return Lista dei biglietti emessi dal punto specificato
     */
    List<Biglietto> findByPuntoEmissione(PuntoEmissione puntoEmissione);
    
    /**
     * Trova biglietti scaduti
     * @return Lista dei biglietti scaduti
     */
    List<Biglietto> findExpiredBiglietti();
    
    /**
     * Trova biglietti validi (non scaduti e non validati)
     * @return Lista dei biglietti ancora validi
     */
    List<Biglietto> findValidBiglietti();
    
    /**
     * Trova biglietti che scadono oggi
     * @return Lista dei biglietti che scadono oggi
     */
    List<Biglietto> findBigliettiExpiringToday();
    
    /**
     * Trova biglietti che scadono entro un numero di giorni
     * @param giorni Numero di giorni
     * @return Lista dei biglietti che scadono entro i giorni specificati
     */
    List<Biglietto> findBigliettiExpiringWithinDays(int giorni);
    
    /**
     * Trova biglietti senza vidimazioni
     * @return Lista dei biglietti che non sono mai stati vidimati
     */
    List<Biglietto> findBigliettiWithoutVidimazioni();
    
    /**
     * Trova biglietti con vidimazioni
     * @return Lista dei biglietti che hanno almeno una vidimazione
     */
    List<Biglietto> findBigliettiWithVidimazioni();
    
    /**
     * Verifica se esiste un biglietto con il codice specificato
     * @param codiceBiglietto Il codice da verificare
     * @return true se esiste, false altrimenti
     */
    boolean existsByCodiceBiglietto(String codiceBiglietto);
    
    /**
     * Conta i biglietti emessi in una data
     * @param dataEmissione La data di emissione
     * @return Numero di biglietti emessi
     */
    long countByDataEmissione(LocalDate dataEmissione);
    
    /**
     * Conta i biglietti emessi in un periodo
     * @param dataInizio Data di inizio del periodo
     * @param dataFine Data di fine del periodo
     * @return Numero di biglietti emessi nel periodo
     */
    long countByDataEmissioneBetween(LocalDate dataInizio, LocalDate dataFine);
    
    /**
     * Conta i biglietti validati
     * @return Numero di biglietti validati
     */
    long countValidatedBiglietti();
    
    /**
     * Conta i biglietti non validati
     * @return Numero di biglietti non validati
     */
    long countNonValidatedBiglietti();
    
    /**
     * Conta i biglietti scaduti
     * @return Numero di biglietti scaduti
     */
    long countExpiredBiglietti();
    
    /**
     * Conta i biglietti validi
     * @return Numero di biglietti ancora validi
     */
    long countValidBiglietti();
    
    /**
     * Conta i biglietti emessi da un punto di emissione
     * @param puntoEmissione Il punto di emissione
     * @return Numero di biglietti emessi dal punto
     */
    long countByPuntoEmissione(PuntoEmissione puntoEmissione);
    
    /**
     * Calcola il ricavo totale dei biglietti emessi in un periodo
     * @param dataInizio Data di inizio del periodo
     * @param dataFine Data di fine del periodo
     * @return Ricavo totale
     */
    BigDecimal calculateTotalRevenueInPeriod(LocalDate dataInizio, LocalDate dataFine);
    
    /**
     * Calcola il ricavo totale dei biglietti validati in un periodo
     * @param dataInizio Data di inizio del periodo
     * @param dataFine Data di fine del periodo
     * @return Ricavo totale dei biglietti validati
     */
    BigDecimal calculateValidatedRevenueInPeriod(LocalDate dataInizio, LocalDate dataFine);
    
    /**
     * Trova le vidimazioni di un biglietto
     * @param biglietto Il biglietto
     * @return Lista delle vidimazioni del biglietto
     */
    List<Vidimazione> findVidimazioniByBiglietto(Biglietto biglietto);
    
    /**
     * Trova i biglietti più venduti (per prezzo)
     * @param limit Numero massimo di risultati
     * @return Lista dei biglietti ordinati per frequenza di vendita
     */
    List<Object[]> findMostSoldBigliettiByPrice(int limit);
}