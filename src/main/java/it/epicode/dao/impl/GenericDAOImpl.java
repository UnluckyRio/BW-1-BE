package it.epicode.dao.impl;

import it.epicode.dao.GenericDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * Implementazione base del DAO generico che fornisce le operazioni CRUD comuni.
 * Questa classe astratta deve essere estesa dalle implementazioni specifiche dei DAO.
 * 
 * @param <T> Il tipo dell'entità
 * @param <ID> Il tipo dell'identificatore primario
 */
public abstract class GenericDAOImpl<T, ID> implements GenericDAO<T, ID> {
    
    private static final Logger logger = LoggerFactory.getLogger(GenericDAOImpl.class);
    
    protected final EntityManager entityManager;
    protected final Class<T> entityClass;
    
    /**
     * Costruttore che inizializza l'EntityManager e la classe dell'entità
     * @param entityManager L'EntityManager per le operazioni di persistenza
     * @param entityClass La classe dell'entità gestita da questo DAO
     */
    public GenericDAOImpl(EntityManager entityManager, Class<T> entityClass) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
    }
    
    @Override
    public T save(T entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
            logger.debug("Entità {} salvata con successo", entityClass.getSimpleName());
            return entity;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.error("Errore durante il salvataggio dell'entità {}: {}", 
                        entityClass.getSimpleName(), e.getMessage(), e);
            throw new RuntimeException("Errore durante il salvataggio", e);
        }
    }
    
    @Override
    public T update(T entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            T updatedEntity = entityManager.merge(entity);
            transaction.commit();
            logger.debug("Entità {} aggiornata con successo", entityClass.getSimpleName());
            return updatedEntity;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.error("Errore durante l'aggiornamento dell'entità {}: {}", 
                        entityClass.getSimpleName(), e.getMessage(), e);
            throw new RuntimeException("Errore durante l'aggiornamento", e);
        }
    }
    
    @Override
    public Optional<T> findById(ID id) {
        try {
            T entity = entityManager.find(entityClass, id);
            logger.debug("Ricerca entità {} con ID {}: {}", 
                        entityClass.getSimpleName(), id, entity != null ? "trovata" : "non trovata");
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            logger.error("Errore durante la ricerca dell'entità {} con ID {}: {}", 
                        entityClass.getSimpleName(), id, e.getMessage(), e);
            return Optional.empty();
        }
    }
    
    @Override
    public List<T> findAll() {
        try {
            String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e";
            TypedQuery<T> query = entityManager.createQuery(jpql, entityClass);
            List<T> results = query.getResultList();
            logger.debug("Trovate {} entità di tipo {}", results.size(), entityClass.getSimpleName());
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca di tutte le entità {}: {}", 
                        entityClass.getSimpleName(), e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca", e);
        }
    }
    
    @Override
    public boolean deleteById(ID id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            T entity = entityManager.find(entityClass, id);
            if (entity != null) {
                entityManager.remove(entity);
                transaction.commit();
                logger.debug("Entità {} con ID {} eliminata con successo", entityClass.getSimpleName(), id);
                return true;
            } else {
                transaction.rollback();
                logger.warn("Tentativo di eliminazione di entità {} con ID {} non esistente", 
                           entityClass.getSimpleName(), id);
                return false;
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.error("Errore durante l'eliminazione dell'entità {} con ID {}: {}", 
                        entityClass.getSimpleName(), id, e.getMessage(), e);
            throw new RuntimeException("Errore durante l'eliminazione", e);
        }
    }
    
    @Override
    public boolean delete(T entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            if (entityManager.contains(entity)) {
                entityManager.remove(entity);
            } else {
                entityManager.remove(entityManager.merge(entity));
            }
            transaction.commit();
            logger.debug("Entità {} eliminata con successo", entityClass.getSimpleName());
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.error("Errore durante l'eliminazione dell'entità {}: {}", 
                        entityClass.getSimpleName(), e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    public boolean existsById(ID id) {
        try {
            String jpql = "SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e WHERE e.id = :id";
            TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
            query.setParameter("id", id);
            Long count = query.getSingleResult();
            boolean exists = count > 0;
            logger.debug("Verifica esistenza entità {} con ID {}: {}", 
                        entityClass.getSimpleName(), id, exists);
            return exists;
        } catch (Exception e) {
            logger.error("Errore durante la verifica di esistenza dell'entità {} con ID {}: {}", 
                        entityClass.getSimpleName(), id, e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    public long count() {
        try {
            String jpql = "SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e";
            TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
            Long count = query.getSingleResult();
            logger.debug("Conteggio entità {}: {}", entityClass.getSimpleName(), count);
            return count;
        } catch (Exception e) {
            logger.error("Errore durante il conteggio delle entità {}: {}", 
                        entityClass.getSimpleName(), e.getMessage(), e);
            return 0;
        }
    }
    
    @Override
    public List<T> findWithPagination(int offset, int limit) {
        try {
            String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e";
            TypedQuery<T> query = entityManager.createQuery(jpql, entityClass);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            List<T> results = query.getResultList();
            logger.debug("Trovate {} entità di tipo {} con paginazione (offset: {}, limit: {})", 
                        results.size(), entityClass.getSimpleName(), offset, limit);
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca paginata delle entità {}: {}", 
                        entityClass.getSimpleName(), e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca paginata", e);
        }
    }
    
    /**
     * Metodo di utilità per eseguire query personalizzate
     * @param jpql La query JPQL da eseguire
     * @param parameters I parametri della query (nome, valore)
     * @return Lista dei risultati
     */
    protected List<T> executeQuery(String jpql, Object... parameters) {
        try {
            TypedQuery<T> query = entityManager.createQuery(jpql, entityClass);
            
            // Imposta i parametri se presenti (assumendo che siano in coppie nome-valore)
            for (int i = 0; i < parameters.length; i += 2) {
                if (i + 1 < parameters.length) {
                    query.setParameter((String) parameters[i], parameters[i + 1]);
                }
            }
            
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Errore durante l'esecuzione della query personalizzata: {}", e.getMessage(), e);
            throw new RuntimeException("Errore durante l'esecuzione della query", e);
        }
    }
    
    /**
     * Metodo di utilità per eseguire query che restituiscono un singolo risultato
     * @param jpql La query JPQL da eseguire
     * @param parameters I parametri della query (nome, valore)
     * @return Optional contenente il risultato se trovato
     */
    protected Optional<T> executeSingleResultQuery(String jpql, Object... parameters) {
        try {
            TypedQuery<T> query = entityManager.createQuery(jpql, entityClass);
            
            // Imposta i parametri se presenti
            for (int i = 0; i < parameters.length; i += 2) {
                if (i + 1 < parameters.length) {
                    query.setParameter((String) parameters[i], parameters[i + 1]);
                }
            }
            
            List<T> results = query.getResultList();
            return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
        } catch (Exception e) {
            logger.error("Errore durante l'esecuzione della query singola: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }
}