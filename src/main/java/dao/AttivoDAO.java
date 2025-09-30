package dao;

import entities.Attivo;
import entities.Mezzo;
import exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.PersistenceException;

import java.time.LocalDate;
import java.util.List;

public class AttivoDAO {
    
    private final EntityManagerFactory emf;
    
    public AttivoDAO() {
        this.emf = Persistence.createEntityManagerFactory("bw1be");
    }
    
    public Attivo save(Attivo attivo) {
        if (attivo == null) {
            throw new IllegalArgumentException("Il periodo di attività non può essere null");
        }
        
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        
        try {
            transaction.begin();
            em.persist(attivo);
            transaction.commit();
            return attivo;
        } catch (PersistenceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Errore durante il salvataggio del periodo di attività: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
    
    public Attivo findById(long id) throws NotFoundException {
        if (id <= 0) {
            throw new IllegalArgumentException("L'ID deve essere maggiore di zero");
        }
        
        EntityManager em = emf.createEntityManager();
        try {
            Attivo attivo = em.find(Attivo.class, id);
            if (attivo == null) {
                throw new NotFoundException(id);
            }
            return attivo;
        } finally {
            em.close();
        }
    }
    
    public List<Attivo> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT a FROM Attivo a", Attivo.class).getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Attivo> findByMezzo(Mezzo mezzo) {
        if (mezzo == null) {
            throw new IllegalArgumentException("Il mezzo non può essere null");
        }
        
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Attivo> query = em.createQuery(
                "SELECT a FROM Attivo a WHERE a.mezzo = :mezzo ORDER BY a.dataInizio DESC", 
                Attivo.class
            );
            query.setParameter("mezzo", mezzo);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Attivo> findActiveOnDate(LocalDate data) {
        if (data == null) {
            throw new IllegalArgumentException("La data non può essere null");
        }
        
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Attivo> query = em.createQuery(
                "SELECT a FROM Attivo a WHERE a.dataInizio <= :data AND a.dataFine >= :data", 
                Attivo.class
            );
            query.setParameter("data", data);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Attivo> findCurrentlyActive() {
        return findActiveOnDate(LocalDate.now());
    }
    
    public List<Attivo> findByPeriodo(LocalDate dataInizio, LocalDate dataFine) {
        if (dataInizio == null || dataFine == null) {
            throw new IllegalArgumentException("Le date non possono essere null");
        }
        if (dataInizio.isAfter(dataFine)) {
            throw new IllegalArgumentException("La data di inizio deve essere precedente o uguale alla data di fine");
        }
        
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Attivo> query = em.createQuery(
                "SELECT a FROM Attivo a WHERE " +
                "(a.dataInizio <= :dataFine AND a.dataFine >= :dataInizio) " +
                "ORDER BY a.dataInizio", 
                Attivo.class
            );
            query.setParameter("dataInizio", dataInizio);
            query.setParameter("dataFine", dataFine);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public Attivo update(Attivo attivo) {
        if (attivo == null) {
            throw new IllegalArgumentException("Il periodo di attività non può essere null");
        }
        
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        
        try {
            transaction.begin();
            
            Attivo existing = em.find(Attivo.class, attivo.getId());
            if (existing == null) {
                throw new NotFoundException(attivo.getId());
            }
            
            Attivo updated = em.merge(attivo);
            transaction.commit();
            return updated;
        } catch (PersistenceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Errore durante l'aggiornamento del periodo di attività: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
    
    public void delete(long id) throws NotFoundException {
        if (id <= 0) {
            throw new IllegalArgumentException("L'ID deve essere maggiore di zero");
        }
        
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        
        try {
            transaction.begin();
            
            Attivo attivo = em.find(Attivo.class, id);
            if (attivo == null) {
                throw new NotFoundException(id);
            }
            
            em.remove(attivo);
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Errore durante l'eliminazione del periodo di attività: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
    
    public long countAttivi() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT COUNT(a) FROM Attivo a", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }
    
    public boolean exists(long id) {
        if (id <= 0) {
            return false;
        }
        
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Attivo.class, id) != null;
        } finally {
            em.close();
        }
    }
    
    public List<Attivo> saveAll(List<Attivo> attivi) {
        if (attivi == null || attivi.isEmpty()) {
            throw new IllegalArgumentException("La lista dei periodi di attività non può essere null o vuota");
        }
        
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        
        try {
            transaction.begin();
            
            for (Attivo attivo : attivi) {
                if (attivo == null) {
                    throw new IllegalArgumentException("Nessun periodo di attività nella lista può essere null");
                }
                em.persist(attivo);
            }
            
            transaction.commit();
            return attivi;
        } catch (PersistenceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Errore durante il salvataggio dei periodi di attività: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
    
    public List<Attivo> findWithPagination(int pageNumber, int pageSize) {
        if (pageNumber < 0) {
            throw new IllegalArgumentException("Il numero di pagina deve essere >= 0");
        }
        if (pageSize <= 0) {
            throw new IllegalArgumentException("La dimensione della pagina deve essere > 0");
        }
        
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Attivo> query = em.createQuery(
                "SELECT a FROM Attivo a ORDER BY a.dataInizio DESC", 
                Attivo.class
            );
            query.setFirstResult(pageNumber * pageSize);
            query.setMaxResults(pageSize);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}