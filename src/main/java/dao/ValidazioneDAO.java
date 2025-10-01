package dao;

import entities.Mezzo;
import entities.Tessera;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ValidazioneDAO extends BaseDAO<Validazione> {
    
    public ValidazioneDAO() {
        super(Validazione.class);
    }
    
    public List<Validazione> findByTessera(Tessera tessera) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Validazione> query = em.createQuery(
                "SELECT v FROM Validazione v WHERE v.tessera = :tessera ORDER BY v.dataOraValidazione DESC", 
                Validazione.class
            );
            query.setParameter("tessera", tessera);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Validazione> findByMezzo(Mezzo mezzo) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Validazione> query = em.createQuery(
                "SELECT v FROM Validazione v WHERE v.mezzo = :mezzo ORDER BY v.dataOraValidazione DESC", 
                Validazione.class
            );
            query.setParameter("mezzo", mezzo);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Validazione> findByData(LocalDate data) {
        EntityManager em = getEntityManager();
        try {
            LocalDateTime inizioGiorno = data.atStartOfDay();
            LocalDateTime fineGiorno = data.atTime(23, 59, 59);
            
            TypedQuery<Validazione> query = em.createQuery(
                "SELECT v FROM Validazione v WHERE v.dataOraValidazione BETWEEN :inizio AND :fine ORDER BY v.dataOraValidazione DESC", 
                Validazione.class
            );
            query.setParameter("inizio", inizioGiorno);
            query.setParameter("fine", fineGiorno);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Validazione> findByPeriodo(LocalDateTime dataInizio, LocalDateTime dataFine) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Validazione> query = em.createQuery(
                "SELECT v FROM Validazione v WHERE v.dataOraValidazione BETWEEN :dataInizio AND :dataFine ORDER BY v.dataOraValidazione DESC", 
                Validazione.class
            );
            query.setParameter("dataInizio", dataInizio);
            query.setParameter("dataFine", dataFine);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public Long countByTessera(Tessera tessera) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(v) FROM Validazione v WHERE v.tessera = :tessera", 
                Long.class
            );
            query.setParameter("tessera", tessera);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
    
    public Long countByMezzo(Mezzo mezzo) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(v) FROM Validazione v WHERE v.mezzo = :mezzo", 
                Long.class
            );
            query.setParameter("mezzo", mezzo);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
    
    public Long countByData(LocalDate data) {
        EntityManager em = getEntityManager();
        try {
            LocalDateTime inizioGiorno = data.atStartOfDay();
            LocalDateTime fineGiorno = data.atTime(23, 59, 59);
            
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(v) FROM Validazione v WHERE v.dataOraValidazione BETWEEN :inizio AND :fine", 
                Long.class
            );
            query.setParameter("inizio", inizioGiorno);
            query.setParameter("fine", fineGiorno);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
}