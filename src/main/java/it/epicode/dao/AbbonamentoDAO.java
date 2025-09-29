package it.epicode.dao;

import it.epicode.entity.Abbonamento;
import it.epicode.entity.Tessera;
import it.epicode.entity.PuntoEmissione;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Interfaccia DAO per l'entità Abbonamento.
 * Estende GenericDAO e definisce metodi specifici per la gestione degli abbonamenti.
 */
public interface AbbonamentoDAO extends GenericDAO<Abbonamento, Long> {
    
    /**
     * Trova un abbonamento per codice
     * @param codiceAbbonamento Il codice dell'abbonamento
     * @return Optional contenente l'abbonamento se trovato
     */
    Optional<Abbonamento> findByCodiceAbbonamento(String codiceAbbonamento);
    
    /**
     * Trova tutti gli abbonamenti di una tessera
     * @param tessera La tessera
     * @return Lista degli abbonamenti della tessera
     */
    List<Abbonamento> findByTessera(Tessera tessera);
    
    /**
     * Trova tutti gli abbonamenti attivi
     * @return Lista degli abbonamenti attivi
     */
    List<Abbonamento> findActiveAbbonamenti();
    
    /**
     * Trova tutti gli abbonamenti scaduti
     * @return Lista degli abbonamenti scaduti
     */
    List<Abbonamento> findExpiredAbbonamenti();
    
    /**
     * Trova abbonamenti per tipo
     * @param tipo Il tipo di abbonamento
     * @return Lista degli abbonamenti del tipo specificato
     */
    List<Abbonamento> findByTipo(Abbonamento.TipoAbbonamento tipo);
    
    /**
     * Trova abbonamenti emessi in un periodo
     * @param dataInizio Data inizio periodo
     * @param dataFine Data fine periodo
     * @return Lista degli abbonamenti emessi nel periodo
     */
    List<Abbonamento> findAbbonamentiEmessiInPeriodo(LocalDate dataInizio, LocalDate dataFine);
    
    /**
     * Trova abbonamenti validi in una data specifica
     * @param data La data da verificare
     * @return Lista degli abbonamenti validi nella data
     */
    List<Abbonamento> findValidAbbonamentiAtDate(LocalDate data);
    
    /**
     * Trova abbonamenti per punto emissione
     * @param puntoEmissione Il punto emissione
     * @return Lista degli abbonamenti emessi dal punto
     */
    List<Abbonamento> findByPuntoEmissione(PuntoEmissione puntoEmissione);
    
    /**
     * Trova abbonamenti in scadenza entro un numero di giorni
     * @param giorni Numero di giorni
     * @return Lista degli abbonamenti in scadenza
     */
    List<Abbonamento> findAbbonamentiInScadenza(int giorni);
    
    /**
     * Verifica se esiste un abbonamento con il codice specificato
     * @param codiceAbbonamento Il codice da verificare
     * @return true se esiste, false altrimenti
     */
    boolean existsByCodiceAbbonamento(String codiceAbbonamento);
    
    /**
     * Conta gli abbonamenti attivi
     * @return Numero di abbonamenti attivi
     */
    long countActiveAbbonamenti();
    
    /**
     * Conta gli abbonamenti per tipo
     * @param tipo Il tipo di abbonamento
     * @return Numero di abbonamenti del tipo specificato
     */
    long countByTipo(Abbonamento.TipoAbbonamento tipo);
    
    /**
     * Conta gli abbonamenti emessi in un periodo
     * @param dataInizio Data inizio periodo
     * @param dataFine Data fine periodo
     * @return Numero di abbonamenti emessi nel periodo
     */
    long countAbbonamentiEmessiInPeriodo(LocalDate dataInizio, LocalDate dataFine);
    
    /**
     * Calcola il ricavo totale degli abbonamenti in un periodo
     * @param dataInizio Data inizio periodo
     * @param dataFine Data fine periodo
     * @return Ricavo totale del periodo
     */
    double calculateRicavoTotaleInPeriodo(LocalDate dataInizio, LocalDate dataFine);
    
    /**
     * Trova l'abbonamento attivo di una tessera
     * @param tessera La tessera
     * @return Optional contenente l'abbonamento attivo se presente
     */
    Optional<Abbonamento> findActiveAbbonamentoByTessera(Tessera tessera);
}