package it.epicode.dao.impl;

import it.epicode.dao.UtenteDAO;
import it.epicode.entity.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Implementazione del DAO per l'entità Utente.
 * Fornisce tutte le operazioni CRUD e query specifiche per gli utenti.
 */
public class UtenteDAOImpl extends GenericDAOImpl<Utente, Long> implements UtenteDAO {
    
    private static final Logger logger = LoggerFactory.getLogger(UtenteDAOImpl.class);
    
    /**
     * Costruttore che inizializza il DAO con l'EntityManager
     * @param entityManager L'EntityManager per le operazioni di persistenza
     */
    public UtenteDAOImpl(EntityManager entityManager) {
        super(entityManager, Utente.class);
    }
    
    @Override
    public Optional<Utente> findByEmail(String email) {
        try {
            String jpql = "SELECT u FROM Utente u WHERE u.email = :email";
            TypedQuery<Utente> query = entityManager.createQuery(jpql, Utente.class);
            query.setParameter("email", email);
            
            List<Utente> results = query.getResultList();
            Optional<Utente> result = results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
            
            logger.debug("Ricerca utente per email '{}': {}", email, result.isPresent() ? "trovato" : "non trovato");
            return result;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca dell'utente per email '{}': {}", email, e.getMessage(), e);
            return Optional.empty();
        }
    }
    
    @Override
    public List<Utente> findByNomeAndCognome(String nome, String cognome) {
        try {
            String jpql = "SELECT u FROM Utente u WHERE u.nome = :nome AND u.cognome = :cognome";
            TypedQuery<Utente> query = entityManager.createQuery(jpql, Utente.class);
            query.setParameter("nome", nome);
            query.setParameter("cognome", cognome);
            
            List<Utente> results = query.getResultList();
            logger.debug("Trovati {} utenti con nome '{}' e cognome '{}'", results.size(), nome, cognome);
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca degli utenti per nome '{}' e cognome '{}': {}", 
                        nome, cognome, e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca per nome e cognome", e);
        }
    }
    
    @Override
    public List<Utente> findByNomeContaining(String nome) {
        try {
            String jpql = "SELECT u FROM Utente u WHERE LOWER(u.nome) LIKE LOWER(:nome)";
            TypedQuery<Utente> query = entityManager.createQuery(jpql, Utente.class);
            query.setParameter("nome", "%" + nome + "%");
            
            List<Utente> results = query.getResultList();
            logger.debug("Trovati {} utenti con nome contenente '{}'", results.size(), nome);
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca degli utenti per nome contenente '{}': {}", 
                        nome, e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca per nome", e);
        }
    }
    
    @Override
    public List<Utente> findByCognomeContaining(String cognome) {
        try {
            String jpql = "SELECT u FROM Utente u WHERE LOWER(u.cognome) LIKE LOWER(:cognome)";
            TypedQuery<Utente> query = entityManager.createQuery(jpql, Utente.class);
            query.setParameter("cognome", "%" + cognome + "%");
            
            List<Utente> results = query.getResultList();
            logger.debug("Trovati {} utenti con cognome contenente '{}'", results.size(), cognome);
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca degli utenti per cognome contenente '{}': {}", 
                        cognome, e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca per cognome", e);
        }
    }
    
    @Override
    public List<Utente> findByDataNascita(LocalDate dataNascita) {
        try {
            String jpql = "SELECT u FROM Utente u WHERE u.dataNascita = :dataNascita";
            TypedQuery<Utente> query = entityManager.createQuery(jpql, Utente.class);
            query.setParameter("dataNascita", dataNascita);
            
            List<Utente> results = query.getResultList();
            logger.debug("Trovati {} utenti nati il {}", results.size(), dataNascita);
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca degli utenti per data di nascita '{}': {}", 
                        dataNascita, e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca per data di nascita", e);
        }
    }
    
    @Override
    public List<Utente> findByDataNascitaBetween(LocalDate dataInizio, LocalDate dataFine) {
        try {
            String jpql = "SELECT u FROM Utente u WHERE u.dataNascita BETWEEN :dataInizio AND :dataFine";
            TypedQuery<Utente> query = entityManager.createQuery(jpql, Utente.class);
            query.setParameter("dataInizio", dataInizio);
            query.setParameter("dataFine", dataFine);
            
            List<Utente> results = query.getResultList();
            logger.debug("Trovati {} utenti nati tra {} e {}", results.size(), dataInizio, dataFine);
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca degli utenti per periodo di nascita {} - {}: {}", 
                        dataInizio, dataFine, e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca per periodo di nascita", e);
        }
    }
    
    @Override
    public Optional<Utente> findByTelefono(String telefono) {
        try {
            String jpql = "SELECT u FROM Utente u WHERE u.telefono = :telefono";
            TypedQuery<Utente> query = entityManager.createQuery(jpql, Utente.class);
            query.setParameter("telefono", telefono);
            
            List<Utente> results = query.getResultList();
            Optional<Utente> result = results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
            
            logger.debug("Ricerca utente per telefono '{}': {}", telefono, result.isPresent() ? "trovato" : "non trovato");
            return result;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca dell'utente per telefono '{}': {}", telefono, e.getMessage(), e);
            return Optional.empty();
        }
    }
    
    @Override
    public List<Utente> findUsersWithActiveTessere() {
        try {
            String jpql = "SELECT DISTINCT u FROM Utente u JOIN u.tessere t WHERE t.attiva = true AND t.dataScadenza > CURRENT_DATE";
            TypedQuery<Utente> query = entityManager.createQuery(jpql, Utente.class);
            
            List<Utente> results = query.getResultList();
            logger.debug("Trovati {} utenti con tessere attive", results.size());
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca degli utenti con tessere attive: {}", e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca utenti con tessere attive", e);
        }
    }
    
    @Override
    public List<Utente> findUsersWithoutTessere() {
        try {
            String jpql = "SELECT u FROM Utente u WHERE u.tessere IS EMPTY";
            TypedQuery<Utente> query = entityManager.createQuery(jpql, Utente.class);
            
            List<Utente> results = query.getResultList();
            logger.debug("Trovati {} utenti senza tessere", results.size());
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca degli utenti senza tessere: {}", e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca utenti senza tessere", e);
        }
    }
    
    @Override
    public List<Utente> findUsersWithExpiredTessere() {
        try {
            String jpql = "SELECT DISTINCT u FROM Utente u JOIN u.tessere t WHERE t.dataScadenza < CURRENT_DATE";
            TypedQuery<Utente> query = entityManager.createQuery(jpql, Utente.class);
            
            List<Utente> results = query.getResultList();
            logger.debug("Trovati {} utenti con tessere scadute", results.size());
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca degli utenti con tessere scadute: {}", e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca utenti con tessere scadute", e);
        }
    }
    
    @Override
    public boolean existsByEmail(String email) {
        try {
            String jpql = "SELECT COUNT(u) FROM Utente u WHERE u.email = :email";
            TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
            query.setParameter("email", email);
            
            Long count = query.getSingleResult();
            boolean exists = count > 0;
            logger.debug("Verifica esistenza email '{}': {}", email, exists);
            return exists;
        } catch (Exception e) {
            logger.error("Errore durante la verifica di esistenza dell'email '{}': {}", email, e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    public boolean existsByTelefono(String telefono) {
        try {
            String jpql = "SELECT COUNT(u) FROM Utente u WHERE u.telefono = :telefono";
            TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
            query.setParameter("telefono", telefono);
            
            Long count = query.getSingleResult();
            boolean exists = count > 0;
            logger.debug("Verifica esistenza telefono '{}': {}", telefono, exists);
            return exists;
        } catch (Exception e) {
            logger.error("Errore durante la verifica di esistenza del telefono '{}': {}", telefono, e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    public long countUsersRegisteredBetween(LocalDate dataInizio, LocalDate dataFine) {
        try {
            String jpql = "SELECT COUNT(u) FROM Utente u WHERE DATE(u.createdAt) BETWEEN :dataInizio AND :dataFine";
            TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
            query.setParameter("dataInizio", dataInizio);
            query.setParameter("dataFine", dataFine);
            
            Long count = query.getSingleResult();
            logger.debug("Conteggio utenti registrati tra {} e {}: {}", dataInizio, dataFine, count);
            return count;
        } catch (Exception e) {
            logger.error("Errore durante il conteggio degli utenti registrati tra {} e {}: {}", 
                        dataInizio, dataFine, e.getMessage(), e);
            return 0;
        }
    }
    
    @Override
    public List<Utente> findByEtaBetween(int etaMinima, int etaMassima) {
        try {
            // Calcola le date di nascita corrispondenti alle età
            LocalDate dataFineNascita = LocalDate.now().minusYears(etaMinima);
            LocalDate dataInizioNascita = LocalDate.now().minusYears(etaMassima + 1);
            
            String jpql = "SELECT u FROM Utente u WHERE u.dataNascita BETWEEN :dataInizio AND :dataFine";
            TypedQuery<Utente> query = entityManager.createQuery(jpql, Utente.class);
            query.setParameter("dataInizio", dataInizioNascita);
            query.setParameter("dataFine", dataFineNascita);
            
            List<Utente> results = query.getResultList();
            logger.debug("Trovati {} utenti con età tra {} e {} anni", results.size(), etaMinima, etaMassima);
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca degli utenti per età tra {} e {}: {}", 
                        etaMinima, etaMassima, e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca per età", e);
        }
    }
}