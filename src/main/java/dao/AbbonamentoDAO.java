package dao;

import entities.Abbonamento;
import entities.Tessera;
import enums.TipoAbbonamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class AbbonamentoDAO extends BaseDAO<Abbonamento> {
    
    public AbbonamentoDAO() {
        super(Abbonamento.class);
    }
    
    public List<Abbonamento> findByTessera(Tessera tessera) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Abbonamento> query = em.createQuery(
                "SELECT a FROM Abbonamento a WHERE a.tessera = :tessera", 
                Abbonamento.class
            );
            query.setParameter("tessera", tessera);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Abbonamento> findByTipo(TipoAbbonamento tipoAbbonamento) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Abbonamento> query = em.createQuery(
                "SELECT a FROM Abbonamento a WHERE a.tipoAbbonamento = :tipo", 
                Abbonamento.class
            );
            query.setParameter("tipo", tipoAbbonamento);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Abbonamento> findValidInData(LocalDate data) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Abbonamento> query = em.createQuery(
                "SELECT a FROM Abbonamento a WHERE a.dataInizioValidita <= :data AND a.dataFineValidita >= :data", 
                Abbonamento.class
            );
            query.setParameter("data", data);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Abbonamento> findScaduti() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Abbonamento> query = em.createQuery(
                "SELECT a FROM Abbonamento a WHERE a.dataFineValidita < CURRENT_DATE", 
                Abbonamento.class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Abbonamento> findByPeriodoEmissione(LocalDate dataInizio, LocalDate dataFine) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Abbonamento> query = em.createQuery(
                "SELECT a FROM Abbonamento a WHERE a.dataEmissione BETWEEN :dataInizio AND :dataFine", 
                Abbonamento.class
            );
            query.setParameter("dataInizio", dataInizio);
            query.setParameter("dataFine", dataFine);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Abbonamento> findByDistributoreAutomatico(Long idDistributore) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Abbonamento> query = em.createQuery(
                "SELECT a FROM Abbonamento a WHERE a.idDistributoreAutomatico = :idDistributore", 
                Abbonamento.class
            );
            query.setParameter("idDistributore", idDistributore);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Abbonamento> findByRivenditore(Long idRivenditore) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Abbonamento> query = em.createQuery(
                "SELECT a FROM Abbonamento a WHERE a.idRivenditore = :idRivenditore", 
                Abbonamento.class
            );
            query.setParameter("idRivenditore", idRivenditore);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}