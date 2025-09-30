package dao;

import entities.Rivenditore;
import enums.StatoRivenditore;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class RivenditoreDAO extends BaseDAO<Rivenditore> {
    
    public RivenditoreDAO() {
        super(Rivenditore.class);
    }
    
    public Rivenditore findByNome(String nomeRivenditore) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Rivenditore> query = em.createQuery(
                "SELECT r FROM Rivenditore r WHERE r.nomeRivenditore = :nome", 
                Rivenditore.class
            );
            query.setParameter("nome", nomeRivenditore);
            List<Rivenditore> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }
    
    public List<Rivenditore> findByStato(StatoRivenditore stato) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Rivenditore> query = em.createQuery(
                "SELECT r FROM Rivenditore r WHERE r.statoRivenditore = :stato ORDER BY r.nomeRivenditore", 
                Rivenditore.class
            );
            query.setParameter("stato", stato);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Rivenditore> findAttivi() {
        return findByStato(StatoRivenditore.ATTIVO);
    }
    
    public List<Rivenditore> findChiusi() {
        return findByStato(StatoRivenditore.CHIUSO);
    }
    
    public List<Rivenditore> findByNomeParziale(String nomeParzialeRivenditore) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Rivenditore> query = em.createQuery(
                "SELECT r FROM Rivenditore r WHERE LOWER(r.nomeRivenditore) LIKE LOWER(:nomeParzialeRivenditore) ORDER BY r.nomeRivenditore", 
                Rivenditore.class
            );
            query.setParameter("nomeParzialeRivenditore", "%" + nomeParzialeRivenditore + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public Long countByStato(StatoRivenditore stato) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(r) FROM Rivenditore r WHERE r.statoRivenditore = :stato", 
                Long.class
            );
            query.setParameter("stato", stato);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
}