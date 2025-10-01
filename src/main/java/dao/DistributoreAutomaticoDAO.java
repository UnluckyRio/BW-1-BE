package dao;

import entities.DistributoreAutomatico;
import enums.StatoDistributore;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class DistributoreAutomaticoDAO extends BaseDAO<DistributoreAutomatico> {
    
    public DistributoreAutomaticoDAO() {
        super(DistributoreAutomatico.class);
    }
    
    public List<DistributoreAutomatico> findByStato(StatoDistributore stato) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<DistributoreAutomatico> query = em.createQuery(
                "SELECT d FROM DistributoreAutomatico d WHERE d.statoDistributore = :stato", 
                DistributoreAutomatico.class
            );
            query.setParameter("stato", stato);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<DistributoreAutomatico> findAttivi() {
        return findByStato(StatoDistributore.valueOf("ATTIVO"));

    }
    
    public List<DistributoreAutomatico> findFuoriServizio() {
        return findByStato(StatoDistributore.FUORI_SERVIZIO);
    }
    
    public Long countByStato(StatoDistributore stato) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(d) FROM DistributoreAutomatico d WHERE d.statoDistributore = :stato", 
                Long.class
            );
            query.setParameter("stato", stato);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
}