package it.epicode.dao;

import java.util.List;
import java.util.Optional;

/**
 * Interfaccia generica per le operazioni DAO comuni.
 * Definisce i metodi CRUD standard che saranno implementati da tutte le classi DAO.
 * 
 * @param <T> Il tipo dell'entità
 * @param <ID> Il tipo dell'identificatore primario
 */
public interface GenericDAO<T, ID> {
    
    /**
     * Salva una nuova entità nel database
     * @param entity L'entità da salvare
     * @return L'entità salvata con l'ID generato
     */
    T save(T entity);
    
    /**
     * Aggiorna un'entità esistente nel database
     * @param entity L'entità da aggiornare
     * @return L'entità aggiornata
     */
    T update(T entity);
    
    /**
     * Trova un'entità per ID
     * @param id L'ID dell'entità da cercare
     * @return Optional contenente l'entità se trovata, altrimenti vuoto
     */
    Optional<T> findById(ID id);
    
    /**
     * Trova tutte le entità
     * @return Lista di tutte le entità
     */
    List<T> findAll();
    
    /**
     * Elimina un'entità per ID
     * @param id L'ID dell'entità da eliminare
     * @return true se l'eliminazione è avvenuta con successo, false altrimenti
     */
    boolean deleteById(ID id);
    
    /**
     * Elimina un'entità
     * @param entity L'entità da eliminare
     * @return true se l'eliminazione è avvenuta con successo, false altrimenti
     */
    boolean delete(T entity);
    
    /**
     * Verifica se un'entità esiste per ID
     * @param id L'ID da verificare
     * @return true se l'entità esiste, false altrimenti
     */
    boolean existsById(ID id);
    
    /**
     * Conta il numero totale di entità
     * @return Il numero di entità
     */
    long count();
    
    /**
     * Trova entità con paginazione
     * @param offset L'offset da cui iniziare
     * @param limit Il numero massimo di risultati
     * @return Lista paginata delle entità
     */
    List<T> findWithPagination(int offset, int limit);
}