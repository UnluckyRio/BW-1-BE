package dao;

import entities.Tratta;
import entities.Mezzo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.List;

public class TrattaDAO extends BaseDAO<Tratta> {
    
    public TrattaDAO() {
        super(Tratta.class);
    }
    
    public List<Tratta> findByMezzo(Mezzo mezzo) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Tratta> query = em.createQuery(
                "SELECT t FROM Tratta t WHERE t.mezzo = :mezzo ORDER BY t.dataOraInizio DESC", 
                Tratta.class
            );
            query.setParameter("mezzo", mezzo);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Tratta> findByPartenza(String partenzaFermata) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Tratta> query = em.createQuery(
                "SELECT t FROM Tratta t WHERE t.partenzaFermata = :partenza ORDER BY t.dataOraInizio", 
                Tratta.class
            );
            query.setParameter("partenza", partenzaFermata);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Tratta> findByArrivo(String arrivoCapolinea) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Tratta> query = em.createQuery(
                "SELECT t FROM Tratta t WHERE t.arrivoCapolinea = :arrivo ORDER BY t.dataOraInizio", 
                Tratta.class
            );
            query.setParameter("arrivo", arrivoCapolinea);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Tratta> findByPartenzaAndArrivo(String partenzaFermata, String arrivoCapolinea) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Tratta> query = em.createQuery(
                "SELECT t FROM Tratta t WHERE t.partenzaFermata = :partenza AND t.arrivoCapolinea = :arrivo ORDER BY t.dataOraInizio", 
                Tratta.class
            );
            query.setParameter("partenza", partenzaFermata);
            query.setParameter("arrivo", arrivoCapolinea);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Tratta> findByPeriodo(LocalDateTime dataOraInizio, LocalDateTime dataOraFine) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Tratta> query = em.createQuery(
                "SELECT t FROM Tratta t WHERE t.dataOraInizio BETWEEN :inizio AND :fine ORDER BY t.dataOraInizio", 
                Tratta.class
            );
            query.setParameter("inizio", dataOraInizio);
            query.setParameter("fine", dataOraFine);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Tratta> findCompletate() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Tratta> query = em.createQuery(
                "SELECT t FROM Tratta t WHERE t.tempoEffettivo IS NOT NULL", 
                Tratta.class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Tratta> findInCorso() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Tratta> query = em.createQuery(
                "SELECT t FROM Tratta t WHERE t.tempoEffettivo IS NULL", 
                Tratta.class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Tratta> findInRitardo() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Tratta> query = em.createQuery(
                "SELECT t FROM Tratta t WHERE t.tempoEffettivo > t.tempoPrevisto", 
                Tratta.class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public Long countByMezzo(Mezzo mezzo) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(t) FROM Tratta t WHERE t.mezzo = :mezzo", 
                Long.class
            );
            query.setParameter("mezzo", mezzo);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
    
    public Double getTempoMedioEffettivo(String partenzaFermata, String arrivoCapolinea) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Double> query = em.createQuery(
                "SELECT AVG(t.tempoEffettivo) FROM Tratta t WHERE t.partenzaFermata = :partenza AND t.arrivoCapolinea = :arrivo AND t.tempoEffettivo IS NOT NULL", 
                Double.class
            );
            query.setParameter("partenza", partenzaFermata);
            query.setParameter("arrivo", arrivoCapolinea);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
}