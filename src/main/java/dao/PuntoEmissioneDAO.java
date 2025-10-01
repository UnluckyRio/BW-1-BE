package dao;

import entities.DistributoreAutomatico;
import entities.Rivenditore;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class PuntoEmissioneDAO extends BaseDAO<PuntoEmissione> {
    
    public PuntoEmissioneDAO() {
        super(PuntoEmissione.class);
    }
    
    public List<DistributoreAutomatico> findAllDistributori() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<DistributoreAutomatico> query = em.createQuery(
                "SELECT d FROM DistributoreAutomatico d ORDER BY d.id", 
                DistributoreAutomatico.class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Rivenditore> findAllRivenditori() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Rivenditore> query = em.createQuery(
                "SELECT r FROM Rivenditore r ORDER BY r.id", 
                Rivenditore.class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<PuntoEmissione> findByLocalita(String localita) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<PuntoEmissione> query = em.createQuery(
                "SELECT p FROM PuntoEmissione p WHERE LOWER(p.localita) = LOWER(:localita) ORDER BY p.indirizzo", 
                PuntoEmissione.class
            );
            query.setParameter("localita", localita);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<PuntoEmissione> findByIndirizzo(String indirizzo) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<PuntoEmissione> query = em.createQuery(
                "SELECT p FROM PuntoEmissione p WHERE LOWER(p.indirizzo) LIKE LOWER(:indirizzo) ORDER BY p.indirizzo", 
                PuntoEmissione.class
            );
            query.setParameter("indirizzo", "%" + indirizzo + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<PuntoEmissione> findByZona(String zona) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<PuntoEmissione> query = em.createQuery(
                "SELECT p FROM PuntoEmissione p WHERE LOWER(p.zona) = LOWER(:zona) ORDER BY p.indirizzo", 
                PuntoEmissione.class
            );
            query.setParameter("zona", zona);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public Long countDistributori() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(d) FROM DistributoreAutomatico d", 
                Long.class
            );
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
    
    public Long countRivenditori() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(r) FROM Rivenditore r", 
                Long.class
            );
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
    
    public Long countByLocalita(String localita) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(p) FROM PuntoEmissione p WHERE LOWER(p.localita) = LOWER(:localita)", 
                Long.class
            );
            query.setParameter("localita", localita);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
    
    public List<String> findDistinctLocalita() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<String> query = em.createQuery(
                "SELECT DISTINCT p.localita FROM PuntoEmissione p ORDER BY p.localita", 
                String.class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<PuntoEmissione> findPuntiPiuAttivi(int limite) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<PuntoEmissione> query = em.createQuery(
                "SELECT p FROM PuntoEmissione p ORDER BY SIZE(p.titoliViaggio) DESC", 
                PuntoEmissione.class
            );
            query.setMaxResults(limite);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}