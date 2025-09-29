package it.epicode.dao;

import it.epicode.entity.Vidimazione;
import it.epicode.entity.Biglietto;
import it.epicode.entity.Mezzo;
import it.epicode.entity.Tratta;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Interfaccia DAO per l'entità Vidimazione.
 * Estende GenericDAO e definisce metodi specifici per la gestione delle vidimazioni.
 */
public interface VidimazioneDAO extends GenericDAO<Vidimazione, Long> {
    
    /**
     * Trova vidimazioni per biglietto
     * @param biglietto Il biglietto
     * @return Lista delle vidimazioni del biglietto
     */
    List<Vidimazione> findByBiglietto(Biglietto biglietto);
    
    /**
     * Trova vidimazioni per mezzo
     * @param mezzo Il mezzo
     * @return Lista delle vidimazioni del mezzo
     */
    List<Vidimazione> findByMezzo(Mezzo mezzo);
    
    /**
     * Trova vidimazioni per tratta
     * @param tratta La tratta
     * @return Lista delle vidimazioni della tratta
     */
    List<Vidimazione> findByTratta(Tratta tratta);
    
    /**
     * Trova vidimazioni valide
     * @return Lista delle vidimazioni valide
     */
    List<Vidimazione> findValidVidimazioni();
    
    /**
     * Trova vidimazioni non valide
     * @return Lista delle vidimazioni non valide
     */
    List<Vidimazione> findInvalidVidimazioni();
    
    /**
     * Trova vidimazioni in un periodo
     * @param dataInizio Data inizio periodo
     * @param dataFine Data fine periodo
     * @return Lista delle vidimazioni nel periodo
     */
    List<Vidimazione> findVidimazioniInPeriodo(LocalDateTime dataInizio, LocalDateTime dataFine);
    
    /**
     * Trova vidimazioni per mezzo in un periodo
     * @param mezzo Il mezzo
     * @param dataInizio Data inizio periodo
     * @param dataFine Data fine periodo
     * @return Lista delle vidimazioni del mezzo nel periodo
     */
    List<Vidimazione> findByMezzoInPeriodo(Mezzo mezzo, LocalDateTime dataInizio, LocalDateTime dataFine);
    
    /**
     * Trova vidimazioni per tratta in un periodo
     * @param tratta La tratta
     * @param dataInizio Data inizio periodo
     * @param dataFine Data fine periodo
     * @return Lista delle vidimazioni della tratta nel periodo
     */
    List<Vidimazione> findByTrattaInPeriodo(Tratta tratta, LocalDateTime dataInizio, LocalDateTime dataFine);
    
    /**
     * Trova vidimazioni con note
     * @return Lista delle vidimazioni che hanno note
     */
    List<Vidimazione> findVidimazioniWithNote();
    
    /**
     * Trova l'ultima vidimazione di un biglietto
     * @param biglietto Il biglietto
     * @return Optional contenente l'ultima vidimazione se presente
     */
    Optional<Vidimazione> findLastVidimazioneByBiglietto(Biglietto biglietto);
    
    /**
     * Trova vidimazioni recenti (ultime N ore)
     * @param ore Numero di ore
     * @return Lista delle vidimazioni recenti
     */
    List<Vidimazione> findVidimazioniRecenti(int ore);
    
    /**
     * Conta le vidimazioni valide
     * @return Numero di vidimazioni valide
     */
    long countValidVidimazioni();
    
    /**
     * Conta le vidimazioni non valide
     * @return Numero di vidimazioni non valide
     */
    long countInvalidVidimazioni();
    
    /**
     * Conta le vidimazioni per mezzo
     * @param mezzo Il mezzo
     * @return Numero di vidimazioni del mezzo
     */
    long countByMezzo(Mezzo mezzo);
    
    /**
     * Conta le vidimazioni per tratta
     * @param tratta La tratta
     * @return Numero di vidimazioni della tratta
     */
    long countByTratta(Tratta tratta);
    
    /**
     * Conta le vidimazioni in un periodo
     * @param dataInizio Data inizio periodo
     * @param dataFine Data fine periodo
     * @return Numero di vidimazioni nel periodo
     */
    long countVidimazioniInPeriodo(LocalDateTime dataInizio, LocalDateTime dataFine);
    
    /**
     * Conta le vidimazioni per mezzo in un periodo
     * @param mezzo Il mezzo
     * @param dataInizio Data inizio periodo
     * @param dataFine Data fine periodo
     * @return Numero di vidimazioni del mezzo nel periodo
     */
    long countByMezzoInPeriodo(Mezzo mezzo, LocalDateTime dataInizio, LocalDateTime dataFine);
    
    /**
     * Calcola la percentuale di vidimazioni valide
     * @return Percentuale di vidimazioni valide (0-100)
     */
    double calculatePercentualeVidimazioniValide();
    
    /**
     * Trova i mezzi più utilizzati per numero di vidimazioni
     * @param limit Numero massimo di risultati
     * @return Lista dei mezzi più utilizzati
     */
    List<Mezzo> findMezziPiuUtilizzati(int limit);
    
    /**
     * Trova le tratte più utilizzate per numero di vidimazioni
     * @param limit Numero massimo di risultati
     * @return Lista delle tratte più utilizzate
     */
    List<Tratta> findTrattePiuUtilizzate(int limit);
    
    /**
     * Trova le ore di punta (ore con più vidimazioni)
     * @param dataInizio Data inizio periodo
     * @param dataFine Data fine periodo
     * @return Lista delle ore con il numero di vidimazioni
     */
    List<Object[]> findOreDiPunta(LocalDateTime dataInizio, LocalDateTime dataFine);
}