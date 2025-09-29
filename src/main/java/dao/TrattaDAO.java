package dao;

import entities.Tratta;
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

public class TrattaDAO {
    
    private final EntityManagerFactory emf;
    
    public TrattaDAO() {
        this.emf = Persistence.createEntityManagerFactory("bw1be");
    }
    
    public Tratta save(Tratta tratta) {
        if (tratta == null) {
            throw new IllegalArgumentException("La tratta non può essere null");
        }
        
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        
        try {
            transaction.begin();
            em.persist(tratta);
            transaction.commit();
            return tratta;
        } catch (PersistenceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Errore durante il salvataggio della tratta: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
    
    public Tratta findById(long id) throws NotFoundException {
        if (id <= 0) {
            throw new IllegalArgumentException("L'ID deve essere maggiore di zero");
        }
        
        try (EntityManager em = emf.createEntityManager()) {
            Tratta tratta = em.find(Tratta.class, id);
            if (tratta == null) {
                throw new NotFoundException(id);
            }
            return tratta;
        }
    }
    
    public List<Tratta> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Tratta> query = em.createQuery("SELECT t FROM Tratta t ORDER BY t.dataInizio DESC", Tratta.class);
            return query.getResultList();
        } catch (PersistenceException e) {
            throw new RuntimeException("Errore durante il recupero delle tratte: " + e.getMessage(), e);
        }
    }
    
    public List<Tratta> findByMezzo(Mezzo mezzo) {
        if (mezzo == null) {
            throw new IllegalArgumentException("Il mezzo non può essere null");
        }
        
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Tratta> query = em.createQuery(
                "SELECT t FROM Tratta t WHERE t.mezzo = :mezzo ORDER BY t.dataInizio DESC", Tratta.class);
            query.setParameter("mezzo", mezzo);
            return query.getResultList();
        } catch (PersistenceException e) {
            throw new RuntimeException("Errore durante il recupero delle tratte per mezzo: " + e.getMessage(), e);
        }
    }
    
    public List<Tratta> findByPeriodo(LocalDate dataInizio, LocalDate dataFine) {
        if (dataInizio == null || dataFine == null) {
            throw new IllegalArgumentException("Le date di inizio e fine non possono essere null");
        }
        if (dataInizio.isAfter(dataFine)) {
            throw new IllegalArgumentException("La data di inizio non può essere successiva alla data di fine");
        }
        
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Tratta> query = em.createQuery(
                "SELECT t FROM Tratta t WHERE t.dataInizio >= :dataInizio AND t.dataFine <= :dataFine ORDER BY t.dataInizio", 
                Tratta.class);
            query.setParameter("dataInizio", dataInizio);
            query.setParameter("dataFine", dataFine);
            return query.getResultList();
        } catch (PersistenceException e) {
            throw new RuntimeException("Errore durante il recupero delle tratte per periodo: " + e.getMessage(), e);
        }
    }
    
    public List<Tratta> findTratteAttive() {
        LocalDate oggi = LocalDate.now();
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Tratta> query = em.createQuery(
                "SELECT t FROM Tratta t WHERE t.dataInizio <= :oggi AND t.dataFine >= :oggi ORDER BY t.dataInizio", 
                Tratta.class);
            query.setParameter("oggi", oggi);
            return query.getResultList();
        } catch (PersistenceException e) {
            throw new RuntimeException("Errore durante il recupero delle tratte attive: " + e.getMessage(), e);
        }
    }
    
    public Tratta update(Tratta tratta) {
        if (tratta == null) {
            throw new IllegalArgumentException("La tratta non può essere null");
        }
        if (tratta.getId() <= 0) {
            throw new IllegalArgumentException("La tratta deve avere un ID valido per essere aggiornata");
        }
        
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        
        try {
            transaction.begin();
            
            Tratta existingTratta = em.find(Tratta.class, tratta.getId());
            if (existingTratta == null) {
                throw new NotFoundException(tratta.getId());
            }
            
            Tratta updatedTratta = em.merge(tratta);
            transaction.commit();
            return updatedTratta;
        } catch (NotFoundException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } catch (PersistenceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Errore durante l'aggiornamento della tratta: " + e.getMessage(), e);
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
            Tratta tratta = em.find(Tratta.class, id);
            if (tratta == null) {
                throw new NotFoundException(id);
            }
            em.remove(tratta);
            transaction.commit();
        } catch (NotFoundException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } catch (PersistenceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Errore durante l'eliminazione della tratta: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
    
    public long countTratte() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(t) FROM Tratta t", Long.class);
            return query.getSingleResult();
        } catch (PersistenceException e) {
            throw new RuntimeException("Errore durante il conteggio delle tratte: " + e.getMessage(), e);
        }
    }
    
    public boolean exists(long id) {
        if (id <= 0) {
            return false;
        }
        
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(t) FROM Tratta t WHERE t.id = :id", Long.class);
            query.setParameter("id", id);
            return query.getSingleResult() > 0;
        } catch (PersistenceException e) {
            throw new RuntimeException("Errore durante la verifica dell'esistenza della tratta: " + e.getMessage(), e);
        }
    }
    
    public List<Tratta> saveAll(List<Tratta> tratte) {
        if (tratte == null || tratte.isEmpty()) {
            throw new IllegalArgumentException("La lista delle tratte non può essere null o vuota");
        }
        
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        
        try {
            transaction.begin();
            
            for (int i = 0; i < tratte.size(); i++) {
                Tratta tratta = tratte.get(i);
                if (tratta == null) {
                    throw new IllegalArgumentException("La tratta alla posizione " + i + " non può essere null");
                }
                em.persist(tratta);
                
                // Flush ogni 20 entità per ottimizzare le performance
                if (i % 20 == 0) {
                    em.flush();
                    em.clear();
                }
            }
            
            transaction.commit();
            return tratte;
        } catch (PersistenceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Errore durante il salvataggio batch delle tratte: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public List<Tratta> findByMezzoAndPeriodo(Mezzo mezzo, LocalDate dataInizio, LocalDate dataFine) {
        if (mezzo == null) {
            throw new IllegalArgumentException("Il mezzo non può essere null");
        }
        if (dataInizio == null || dataFine == null) {
            throw new IllegalArgumentException("Le date di inizio e fine non possono essere null");
        }
        if (dataInizio.isAfter(dataFine)) {
            throw new IllegalArgumentException("La data di inizio non può essere successiva alla data di fine");
        }
        
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Tratta> query = em.createQuery(
                "SELECT t FROM Tratta t WHERE t.mezzo = :mezzo AND t.dataInizio >= :dataInizio AND t.dataFine <= :dataFine ORDER BY t.dataInizio", 
                Tratta.class);
            query.setParameter("mezzo", mezzo);
            query.setParameter("dataInizio", dataInizio);
            query.setParameter("dataFine", dataFine);
            return query.getResultList();
        } catch (PersistenceException e) {
            throw new RuntimeException("Errore durante il recupero delle tratte per mezzo e periodo: " + e.getMessage(), e);
        }
    }

    public List<Tratta> findWithPagination(int pageNumber, int pageSize) {
        if (pageNumber < 0) {
            throw new IllegalArgumentException("Il numero di pagina deve essere >= 0");
        }
        if (pageSize <= 0) {
            throw new IllegalArgumentException("La dimensione della pagina deve essere > 0");
        }
        
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Tratta> query = em.createQuery("SELECT t FROM Tratta t ORDER BY t.dataInizio DESC", Tratta.class);
            query.setFirstResult(pageNumber * pageSize);
            query.setMaxResults(pageSize);
            return query.getResultList();
        } catch (PersistenceException e) {
            throw new RuntimeException("Errore durante il recupero paginato delle tratte: " + e.getMessage(), e);
        }
    }
    
    /**
     * Chiude l'EntityManagerFactory e libera le risorse
     */
    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}