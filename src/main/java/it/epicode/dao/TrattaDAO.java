package it.epicode.dao;

import it.epicode.entity.Tratta;
import it.epicode.entity.Mezzo;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Interfaccia DAO per l'entità Tratta.
 * Estende GenericDAO e definisce metodi specifici per la gestione delle tratte.
 */
public interface TrattaDAO extends GenericDAO<Tratta, Long> {
    
    /**
     * Trova una tratta per nome
     * @param nome Il nome della tratta
     * @return Optional contenente la tratta se trovata
     */
    Optional<Tratta> findByNome(String nome);
    
    /**
     * Trova tratte per zona di partenza
     * @param zonaPartenza La zona di partenza
     * @return Lista delle tratte con la zona di partenza specificata
     */
    List<Tratta> findByZonaPartenza(String zonaPartenza);
    
    /**
     * Trova tratte per capolinea
     * @param capolinea Il capolinea
     * @return Lista delle tratte con il capolinea specificato
     */
    List<Tratta> findByCapolinea(String capolinea);
    
    /**
     * Trova tutte le tratte attive
     * @return Lista delle tratte attive
     */
    List<Tratta> findActiveTratte();
    
    /**
     * Trova tutte le tratte inattive
     * @return Lista delle tratte inattive
     */
    List<Tratta> findInactiveTratte();
    
    /**
     * Trova tratte per tempo previsto (range)
     * @param tempoMinimo Tempo minimo in minuti
     * @param tempoMassimo Tempo massimo in minuti
     * @return Lista delle tratte nel range di tempo specificato
     */
    List<Tratta> findByTempoPrevistoBetween(Integer tempoMinimo, Integer tempoMassimo);
    
    /**
     * Trova tratte per distanza (range)
     * @param distanzaMinima Distanza minima in km
     * @param distanzaMassima Distanza massima in km
     * @return Lista delle tratte nel range di distanza specificato
     */
    List<Tratta> findByDistanzaBetween(BigDecimal distanzaMinima, BigDecimal distanzaMassima);
    
    /**
     * Trova tratte create in un periodo
     * @param dataInizio Data inizio periodo
     * @param dataFine Data fine periodo
     * @return Lista delle tratte create nel periodo
     */
    List<Tratta> findTratteCreatedBetween(LocalDateTime dataInizio, LocalDateTime dataFine);
    
    /**
     * Trova tratte percorse da un mezzo
     * @param mezzo Il mezzo
     * @return Lista delle tratte percorse dal mezzo
     */
    List<Tratta> findTrattePercorseDaMezzo(Mezzo mezzo);
    
    /**
     * Trova tratte con vidimazioni in un periodo
     * @param dataInizio Data inizio periodo
     * @param dataFine Data fine periodo
     * @return Lista delle tratte con vidimazioni nel periodo
     */
    List<Tratta> findTratteWithVidimazioniInPeriodo(LocalDateTime dataInizio, LocalDateTime dataFine);
    
    /**
     * Trova tratte ordinate per popolarità (numero di vidimazioni)
     * @param limit Numero massimo di risultati
     * @return Lista delle tratte più popolari
     */
    List<Tratta> findTrattePiuPopolari(int limit);
    
    /**
     * Verifica se esiste una tratta con il nome specificato
     * @param nome Il nome da verificare
     * @return true se esiste, false altrimenti
     */
    boolean existsByNome(String nome);
    
    /**
     * Conta le tratte attive
     * @return Numero di tratte attive
     */
    long countActiveTratte();
    
    /**
     * Conta le tratte inattive
     * @return Numero di tratte inattive
     */
    long countInactiveTratte();
    
    /**
     * Conta le tratte per zona di partenza
     * @param zonaPartenza La zona di partenza
     * @return Numero di tratte con la zona di partenza specificata
     */
    long countByZonaPartenza(String zonaPartenza);
    
    /**
     * Calcola la distanza totale di tutte le tratte attive
     * @return Distanza totale in km
     */
    BigDecimal calculateDistanzaTotaleTratte();
    
    /**
     * Calcola il tempo medio previsto delle tratte attive
     * @return Tempo medio in minuti
     */
    double calculateTempoMedioPrevisto();
    
    /**
     * Calcola la distanza media delle tratte attive
     * @return Distanza media in km
     */
    BigDecimal calculateDistanzaMediaTratte();
    
    /**
     * Trova le tratte più lunghe (per distanza)
     * @param limit Numero massimo di risultati
     * @return Lista delle tratte più lunghe
     */
    List<Tratta> findTrattePiuLunghe(int limit);
    
    /**
     * Trova le tratte più veloci (velocità media più alta)
     * @param limit Numero massimo di risultati
     * @return Lista delle tratte più veloci
     */
    List<Tratta> findTrattePiuVeloci(int limit);
}