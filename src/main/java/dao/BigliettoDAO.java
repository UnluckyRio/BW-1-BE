package dao;

import entities.Biglietto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class BigliettoDAO extends BaseDAO<Biglietto> {
    
    public BigliettoDAO() {
        super(Biglietto.class);
    }
    
    public List<Biglietto> findByDataEmissione(LocalDate dataEmissione) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Biglietto> query = em.createQuery(
                "SELECT b FROM Biglietto b WHERE b.dataEmissione = :dataEmissione", 
                Biglietto.class
            );
            query.setParameter("dataEmissione", dataEmissione);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Biglietto> findByDistributoreAutomatico(Long idDistributore) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Biglietto> query = em.createQuery(
                "SELECT b FROM Biglietto b WHERE b.puntoEmissione.id = :idDistributore AND TYPE(b.puntoEmissione) = DistributoreAutomatico", 
                Biglietto.class
            );
            query.setParameter("idDistributore", idDistributore);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Biglietto> findByRivenditore(Long idRivenditore) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Biglietto> query = em.createQuery(
                "SELECT b FROM Biglietto b WHERE b.puntoEmissione.id = :idRivenditore AND TYPE(b.puntoEmissione) = Rivenditore", 
                Biglietto.class
            );
            query.setParameter("idRivenditore", idRivenditore);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Biglietto> findByPeriodo(LocalDate dataInizio, LocalDate dataFine) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Biglietto> query = em.createQuery(
                "SELECT b FROM Biglietto b WHERE b.dataEmissione BETWEEN :dataInizio AND :dataFine ORDER BY b.dataEmissione", 
                Biglietto.class
            );
            query.setParameter("dataInizio", dataInizio);
            query.setParameter("dataFine", dataFine);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
