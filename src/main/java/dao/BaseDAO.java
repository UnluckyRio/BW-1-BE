package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import exceptions.NotFoundException;

import java.util.List;

public abstract class BaseDAO<T> {
    
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bw1be");
    private final Class<T> entityClass;
    
    public BaseDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    protected EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public T save(T entity) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Errore durante il salvataggio dell'entità", e);
        } finally {
            em.close();
        }
    }
    
    public T findById(Long id) {
        EntityManager em = getEntityManager();
        try {
            T entity = em.find(entityClass, id);
            if (entity == null) {
                throw new NotFoundException(id);
            }
            return entity;
        } finally {
            em.close();
        }
    }
    
    public List<T> findAll() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<T> query = em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public T update(T entity) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            T updatedEntity = em.merge(entity);
            em.getTransaction().commit();
            return updatedEntity;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Errore durante l'aggiornamento dell'entità", e);
        } finally {
            em.close();
        }
    }
    
    public void deleteById(Long id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            T entity = em.find(entityClass, id);
            if (entity == null) {
                throw new NotFoundException(id);
            }
            em.remove(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Errore durante l'eliminazione dell'entità", e);
        } finally {
            em.close();
        }
    }
    
    public static void closeEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}