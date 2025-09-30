package dao;

import entities.Tessera;
import entities.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class TesseraDAO extends BaseDAO<Tessera> {
    
    public TesseraDAO() {
        super(Tessera.class);
    }
    
    public Tessera findByUtente(Utente utente) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Tessera> query = em.createQuery(
                "SELECT t FROM Tessera t WHERE t.utente = :utente", 
                Tessera.class
            );
            query.setParameter("utente", utente);
            List<Tessera> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }
    
    public List<Tessera> findByDataEmissione(LocalDate dataEmissione) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Tessera> query = em.createQuery(
                "SELECT t FROM Tessera t WHERE t.dataEmissione = :dataEmissione", 
                Tessera.class
            );
            query.setParameter("dataEmissione", dataEmissione);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Tessera> findValidInData(LocalDate data) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Tessera> query = em.createQuery(
                "SELECT t FROM Tessera t WHERE t.dataEmissione <= :data AND t.dataScadenza >= :data", 
                Tessera.class
            );
            query.setParameter("data", data);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Tessera> findScadute() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Tessera> query = em.createQuery(
                "SELECT t FROM Tessera t WHERE t.dataScadenza < CURRENT_DATE", 
                Tessera.class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Tessera> findInScadenzaEntro(int giorni) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Tessera> query = em.createQuery(
                "SELECT t FROM Tessera t WHERE t.dataScadenza BETWEEN CURRENT_DATE AND :dataLimite", 
                Tessera.class
            );
            query.setParameter("dataLimite", LocalDate.now().plusDays(giorni));
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Tessera> findByPeriodoEmissione(LocalDate dataInizio, LocalDate dataFine) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Tessera> query = em.createQuery(
                "SELECT t FROM Tessera t WHERE t.dataEmissione BETWEEN :dataInizio AND :dataFine", 
                Tessera.class
            );
            query.setParameter("dataInizio", dataInizio);
            query.setParameter("dataFine", dataFine);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Tessera> findByPeriodoScadenza(LocalDate dataInizio, LocalDate dataFine) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Tessera> query = em.createQuery(
                "SELECT t FROM Tessera t WHERE t.dataScadenza BETWEEN :dataInizio AND :dataFine", 
                Tessera.class
            );
            query.setParameter("dataInizio", dataInizio);
            query.setParameter("dataFine", dataFine);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public Long countAttive() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(t) FROM Tessera t WHERE t.dataScadenza >= CURRENT_DATE", 
                Long.class
            );
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
}