package dao;

import entities.Utente;
import enums.RuoloUtente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class UtenteDAO extends BaseDAO<Utente> {
    
    public UtenteDAO() {
        super(Utente.class);
    }
    
    public Utente findByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username non può essere null o vuoto");
        }
        
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Utente> query = em.createQuery(
                "SELECT u FROM Utente u WHERE u.username = :username", 
                Utente.class
            );
            query.setParameter("username", username.trim());
            List<Utente> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }
    
    public List<Utente> findByRuolo(RuoloUtente ruolo) {
        if (ruolo == null) {
            throw new IllegalArgumentException("Ruolo non può essere null");
        }
        
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Utente> query = em.createQuery(
                "SELECT u FROM Utente u WHERE u.ruoloUtente = :ruolo ORDER BY u.cognome, u.nome", 
                Utente.class
            );
            query.setParameter("ruolo", ruolo);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Utente> findAmministratori() {
        return findByRuolo(RuoloUtente.AMMINISTRATORE);
    }
    
    public List<Utente> findUtentiNormali() {
        return findByRuolo(RuoloUtente.UTENTE_SEMPLICE);
    }
    
    public List<Utente> findByNomeAndCognome(String nome, String cognome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome non può essere null o vuoto");
        }
        if (cognome == null || cognome.trim().isEmpty()) {
            throw new IllegalArgumentException("Cognome non può essere null o vuoto");
        }
        
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Utente> query = em.createQuery(
                "SELECT u FROM Utente u WHERE u.nome = :nome AND u.cognome = :cognome ORDER BY u.username", 
                Utente.class
            );
            query.setParameter("nome", nome.trim());
            query.setParameter("cognome", cognome.trim());
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Utente> findByNomeParziale(String nomeParzialeUtente) {
        if (nomeParzialeUtente == null || nomeParzialeUtente.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome parziale non può essere null o vuoto");
        }
        
        EntityManager em = getEntityManager();
        try {
            String searchTerm = nomeParzialeUtente.trim();
            TypedQuery<Utente> query = em.createQuery(
                "SELECT u FROM Utente u WHERE LOWER(u.nome) LIKE LOWER(:nome) OR LOWER(u.cognome) LIKE LOWER(:nome) ORDER BY u.cognome, u.nome", 
                Utente.class
            );
            query.setParameter("nome", "%" + searchTerm + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Utente> findByUsernameParziale(String usernameParzialeUtente) {
        if (usernameParzialeUtente == null || usernameParzialeUtente.trim().isEmpty()) {
            throw new IllegalArgumentException("Username parziale non può essere null o vuoto");
        }
        
        EntityManager em = getEntityManager();
        try {
            String searchTerm = usernameParzialeUtente.trim();
            TypedQuery<Utente> query = em.createQuery(
                "SELECT u FROM Utente u WHERE LOWER(u.username) LIKE LOWER(:username) ORDER BY u.username", 
                Utente.class
            );
            query.setParameter("username", "%" + searchTerm + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public boolean existsByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username non può essere null o vuoto");
        }
        
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(u) FROM Utente u WHERE u.username = :username", 
                Long.class
            );
            query.setParameter("username", username.trim());
            return query.getSingleResult() > 0;
        } finally {
            em.close();
        }
    }
    
    public Long countByRuolo(RuoloUtente ruolo) {
        if (ruolo == null) {
            throw new IllegalArgumentException("Ruolo non può essere null");
        }
        
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(u) FROM Utente u WHERE u.ruoloUtente = :ruolo", 
                Long.class
            );
            query.setParameter("ruolo", ruolo);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
}