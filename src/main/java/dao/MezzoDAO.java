package dao;

import entities.Mezzo;
import enums.StatoMezzo;
import enums.TipoMezzo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class MezzoDAO extends BaseDAO<Mezzo> {
    
    public MezzoDAO() {
        super(Mezzo.class);
    }
    
    public Mezzo findByTarga(String targa) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Mezzo> query = em.createQuery(
                "SELECT m FROM Mezzo m WHERE m.targa = :targa", 
                Mezzo.class
            );
            query.setParameter("targa", targa);
            List<Mezzo> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }
    
    public List<Mezzo> findByTipo(TipoMezzo tipoMezzo) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Mezzo> query = em.createQuery(
                "SELECT m FROM Mezzo m WHERE m.tipoMezzo = :tipoMezzo ORDER BY m.targa", 
                Mezzo.class
            );
            query.setParameter("tipoMezzo", tipoMezzo);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Mezzo> findByStato(StatoMezzo statoMezzo) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Mezzo> query = em.createQuery(
                "SELECT m FROM Mezzo m WHERE m.statoMezzo = :statoMezzo ORDER BY m.targa", 
                Mezzo.class
            );
            query.setParameter("statoMezzo", statoMezzo);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Mezzo> findInServizio() {
        return findByStato(StatoMezzo.IN_SERVIZIO);
    }
    
    public List<Mezzo> findInManutenzione() {
        return findByStato(StatoMezzo.IN_MANUTENZIONE);
    }
    
    public List<Mezzo> findByCapienzaMinima(Integer capienzaMinima) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Mezzo> query = em.createQuery(
                "SELECT m FROM Mezzo m WHERE m.capienza >= :capienzaMinima ORDER BY m.capienza", 
                Mezzo.class
            );
            query.setParameter("capienzaMinima", capienzaMinima);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Mezzo> findByTipoAndStato(TipoMezzo tipoMezzo, StatoMezzo statoMezzo) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Mezzo> query = em.createQuery(
                "SELECT m FROM Mezzo m WHERE m.tipoMezzo = :tipoMezzo AND m.statoMezzo = :statoMezzo ORDER BY m.targa", 
                Mezzo.class
            );
            query.setParameter("tipoMezzo", tipoMezzo);
            query.setParameter("statoMezzo", statoMezzo);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public Long countByTipo(TipoMezzo tipoMezzo) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(m) FROM Mezzo m WHERE m.tipoMezzo = :tipoMezzo", 
                Long.class
            );
            query.setParameter("tipoMezzo", tipoMezzo);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
    
    public Long countByStato(StatoMezzo statoMezzo) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(m) FROM Mezzo m WHERE m.statoMezzo = :statoMezzo", 
                Long.class
            );
            query.setParameter("statoMezzo", statoMezzo);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
}