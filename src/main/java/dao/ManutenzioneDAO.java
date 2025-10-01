package dao;

import entities.Manutenzione;
import entities.Mezzo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class ManutenzioneDAO extends BaseDAO<Manutenzione> {
    
    public ManutenzioneDAO() {
        super(Manutenzione.class);
    }
    
    public List<Manutenzione> findByMezzo(Mezzo mezzo) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Manutenzione> query = em.createQuery(
                "SELECT m FROM Manutenzione m WHERE m.mezzo = :mezzo ORDER BY m.dataInizio DESC", 
                Manutenzione.class
            );
            query.setParameter("mezzo", mezzo);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Manutenzione> findInCorso() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Manutenzione> query = em.createQuery(
                "SELECT m FROM Manutenzione m WHERE m.dataFine IS NULL ORDER BY m.dataInizio DESC", 
                Manutenzione.class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Manutenzione> findCompletate() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Manutenzione> query = em.createQuery(
                "SELECT m FROM Manutenzione m WHERE m.dataFine IS NOT NULL ORDER BY m.dataFine DESC", 
                Manutenzione.class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Manutenzione> findByPeriodoInizio(LocalDate dataInizio, LocalDate dataFine) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Manutenzione> query = em.createQuery(
                "SELECT m FROM Manutenzione m WHERE m.dataInizio BETWEEN :dataInizio AND :dataFine ORDER BY m.dataInizio DESC", 
                Manutenzione.class
            );
            query.setParameter("dataInizio", dataInizio);
            query.setParameter("dataFine", dataFine);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Manutenzione> findCompletateInPeriodo(LocalDate dataInizio, LocalDate dataFine) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Manutenzione> query = em.createQuery(
                "SELECT m FROM Manutenzione m WHERE m.dataFine BETWEEN :dataInizio AND :dataFine ORDER BY m.dataFine DESC", 
                Manutenzione.class
            );
            query.setParameter("dataInizio", dataInizio);
            query.setParameter("dataFine", dataFine);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public Long countByMezzo(Mezzo mezzo) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(m) FROM Manutenzione m WHERE m.mezzo = :mezzo", 
                Long.class
            );
            query.setParameter("mezzo", mezzo);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
}