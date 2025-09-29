package it.epicode.dao.impl;

import it.epicode.dao.TesseraDAO;
import it.epicode.entity.Tessera;
import it.epicode.entity.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Implementazione del DAO per l'entità Tessera.
 * Fornisce tutte le operazioni CRUD e query specifiche per le tessere.
 */
public class TesseraDAOImpl extends GenericDAOImpl<Tessera, Long> implements TesseraDAO {
    
    private static final Logger logger = LoggerFactory.getLogger(TesseraDAOImpl.class);
    
    /**
     * Costruttore che inizializza il DAO con l'EntityManager
     * @param entityManager L'EntityManager per le operazioni di persistenza
     */
    public TesseraDAOImpl(EntityManager entityManager) {
        super(entityManager, Tessera.class);
    }
    
    @Override
    public Optional<Tessera> findByNumeroTessera(String numeroTessera) {
        try {
            String jpql = "SELECT t FROM Tessera t WHERE t.numeroTessera = :numeroTessera";
            TypedQuery<Tessera> query = entityManager.createQuery(jpql, Tessera.class);
            query.setParameter("numeroTessera", numeroTessera);
            
            List<Tessera> results = query.getResultList();
            Optional<Tessera> result = results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
            
            logger.debug("Ricerca tessera per numero '{}': {}", numeroTessera, result.isPresent() ? "trovata" : "non trovata");
            return result;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca della tessera per numero '{}': {}", numeroTessera, e.getMessage(), e);
            return Optional.empty();
        }
    }
    
    @Override
    public List<Tessera> findByUtente(Utente utente) {
        try {
            String jpql = "SELECT t FROM Tessera t WHERE t.utente = :utente ORDER BY t.dataEmissione DESC";
            TypedQuery<Tessera> query = entityManager.createQuery(jpql, Tessera.class);
            query.setParameter("utente", utente);
            
            List<Tessera> results = query.getResultList();
            logger.debug("Trovate {} tessere per l'utente {}", results.size(), utente.getId());
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca delle tessere per utente {}: {}", utente.getId(), e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca tessere per utente", e);
        }
    }
    
    @Override
    public List<Tessera> findActiveTessere() {
        try {
            String jpql = "SELECT t FROM Tessera t WHERE t.attiva = true ORDER BY t.dataEmissione DESC";
            TypedQuery<Tessera> query = entityManager.createQuery(jpql, Tessera.class);
            
            List<Tessera> results = query.getResultList();
            logger.debug("Trovate {} tessere attive", results.size());
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca delle tessere attive: {}", e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca tessere attive", e);
        }
    }
    
    @Override
    public List<Tessera> findExpiredTessere() {
        try {
            String jpql = "SELECT t FROM Tessera t WHERE t.dataScadenza < CURRENT_DATE ORDER BY t.dataScadenza DESC";
            TypedQuery<Tessera> query = entityManager.createQuery(jpql, Tessera.class);
            
            List<Tessera> results = query.getResultList();
            logger.debug("Trovate {} tessere scadute", results.size());
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca delle tessere scadute: {}", e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca tessere scadute", e);
        }
    }
    
    @Override
    public List<Tessera> findTessereExpiringBefore(LocalDate dataScadenza) {
        try {
            String jpql = "SELECT t FROM Tessera t WHERE t.dataScadenza <= :dataScadenza AND t.attiva = true ORDER BY t.dataScadenza ASC";
            TypedQuery<Tessera> query = entityManager.createQuery(jpql, Tessera.class);
            query.setParameter("dataScadenza", dataScadenza);
            
            List<Tessera> results = query.getResultList();
            logger.debug("Trovate {} tessere che scadono entro {}", results.size(), dataScadenza);
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca delle tessere che scadono entro {}: {}", dataScadenza, e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca tessere in scadenza", e);
        }
    }
    
    @Override
    public List<Tessera> findTessereExpiringBetween(LocalDate dataInizio, LocalDate dataFine) {
        try {
            String jpql = "SELECT t FROM Tessera t WHERE t.dataScadenza BETWEEN :dataInizio AND :dataFine ORDER BY t.dataScadenza ASC";
            TypedQuery<Tessera> query = entityManager.createQuery(jpql, Tessera.class);
            query.setParameter("dataInizio", dataInizio);
            query.setParameter("dataFine", dataFine);
            
            List<Tessera> results = query.getResultList();
            logger.debug("Trovate {} tessere che scadono tra {} e {}", results.size(), dataInizio, dataFine);
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca delle tessere che scadono tra {} e {}: {}", 
                        dataInizio, dataFine, e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca tessere per periodo scadenza", e);
        }
    }
    
    @Override
    public List<Tessera> findTessereEmesseInPeriodo(LocalDate dataInizio, LocalDate dataFine) {
        try {
            String jpql = "SELECT t FROM Tessera t WHERE t.dataEmissione BETWEEN :dataInizio AND :dataFine ORDER BY t.dataEmissione DESC";
            TypedQuery<Tessera> query = entityManager.createQuery(jpql, Tessera.class);
            query.setParameter("dataInizio", dataInizio);
            query.setParameter("dataFine", dataFine);
            
            List<Tessera> results = query.getResultList();
            logger.debug("Trovate {} tessere emesse tra {} e {}", results.size(), dataInizio, dataFine);
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca delle tessere emesse tra {} e {}: {}", 
                        dataInizio, dataFine, e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca tessere per periodo emissione", e);
        }
    }
    
    @Override
    public List<Tessera> findValidTessere() {
        try {
            String jpql = "SELECT t FROM Tessera t WHERE t.attiva = true AND t.dataScadenza > CURRENT_DATE ORDER BY t.dataScadenza ASC";
            TypedQuery<Tessera> query = entityManager.createQuery(jpql, Tessera.class);
            
            List<Tessera> results = query.getResultList();
            logger.debug("Trovate {} tessere valide", results.size());
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca delle tessere valide: {}", e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca tessere valide", e);
        }
    }
    
    @Override
    public List<Tessera> findValidTessereByUtente(Utente utente) {
        try {
            String jpql = "SELECT t FROM Tessera t WHERE t.utente = :utente AND t.attiva = true AND t.dataScadenza > CURRENT_DATE ORDER BY t.dataScadenza ASC";
            TypedQuery<Tessera> query = entityManager.createQuery(jpql, Tessera.class);
            query.setParameter("utente", utente);
            
            List<Tessera> results = query.getResultList();
            logger.debug("Trovate {} tessere valide per l'utente {}", results.size(), utente.getId());
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca delle tessere valide per utente {}: {}", utente.getId(), e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca tessere valide per utente", e);
        }
    }
    
    @Override
    public List<Tessera> findTessereWithActiveAbbonamenti() {
        try {
            String jpql = "SELECT DISTINCT t FROM Tessera t JOIN t.abbonamenti a WHERE a.attivo = true AND a.dataFineValidita > CURRENT_DATE";
            TypedQuery<Tessera> query = entityManager.createQuery(jpql, Tessera.class);
            
            List<Tessera> results = query.getResultList();
            logger.debug("Trovate {} tessere con abbonamenti attivi", results.size());
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca delle tessere con abbonamenti attivi: {}", e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca tessere con abbonamenti attivi", e);
        }
    }
    
    @Override
    public List<Tessera> findTessereWithoutAbbonamenti() {
        try {
            String jpql = "SELECT t FROM Tessera t WHERE t.abbonamenti IS EMPTY";
            TypedQuery<Tessera> query = entityManager.createQuery(jpql, Tessera.class);
            
            List<Tessera> results = query.getResultList();
            logger.debug("Trovate {} tessere senza abbonamenti", results.size());
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca delle tessere senza abbonamenti: {}", e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca tessere senza abbonamenti", e);
        }
    }
    
    @Override
    public boolean existsByNumeroTessera(String numeroTessera) {
        try {
            String jpql = "SELECT COUNT(t) FROM Tessera t WHERE t.numeroTessera = :numeroTessera";
            TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
            query.setParameter("numeroTessera", numeroTessera);
            
            Long count = query.getSingleResult();
            boolean exists = count > 0;
            logger.debug("Verifica esistenza numero tessera '{}': {}", numeroTessera, exists);
            return exists;
        } catch (Exception e) {
            logger.error("Errore durante la verifica di esistenza del numero tessera '{}': {}", numeroTessera, e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    public long countActiveTessere() {
        try {
            String jpql = "SELECT COUNT(t) FROM Tessera t WHERE t.attiva = true";
            TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
            
            Long count = query.getSingleResult();
            logger.debug("Conteggio tessere attive: {}", count);
            return count;
        } catch (Exception e) {
            logger.error("Errore durante il conteggio delle tessere attive: {}", e.getMessage(), e);
            return 0;
        }
    }
    
    @Override
    public long countExpiredTessere() {
        try {
            String jpql = "SELECT COUNT(t) FROM Tessera t WHERE t.dataScadenza < CURRENT_DATE";
            TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
            
            Long count = query.getSingleResult();
            logger.debug("Conteggio tessere scadute: {}", count);
            return count;
        } catch (Exception e) {
            logger.error("Errore durante il conteggio delle tessere scadute: {}", e.getMessage(), e);
            return 0;
        }
    }
    
    @Override
    public long countTessereEmesseInPeriodo(LocalDate dataInizio, LocalDate dataFine) {
        try {
            String jpql = "SELECT COUNT(t) FROM Tessera t WHERE t.dataEmissione BETWEEN :dataInizio AND :dataFine";
            TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
            query.setParameter("dataInizio", dataInizio);
            query.setParameter("dataFine", dataFine);
            
            Long count = query.getSingleResult();
            logger.debug("Conteggio tessere emesse tra {} e {}: {}", dataInizio, dataFine, count);
            return count;
        } catch (Exception e) {
            logger.error("Errore durante il conteggio delle tessere emesse tra {} e {}: {}", 
                        dataInizio, dataFine, e.getMessage(), e);
            return 0;
        }
    }
    
    @Override
    public List<Tessera> findTessereNeedingRenewal(int giorni) {
        try {
            LocalDate dataLimite = LocalDate.now().plusDays(giorni);
            String jpql = "SELECT t FROM Tessera t WHERE t.attiva = true AND t.dataScadenza <= :dataLimite AND t.dataScadenza > CURRENT_DATE ORDER BY t.dataScadenza ASC";
            TypedQuery<Tessera> query = entityManager.createQuery(jpql, Tessera.class);
            query.setParameter("dataLimite", dataLimite);
            
            List<Tessera> results = query.getResultList();
            logger.debug("Trovate {} tessere che necessitano rinnovo entro {} giorni", results.size(), giorni);
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca delle tessere che necessitano rinnovo entro {} giorni: {}", 
                        giorni, e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca tessere per rinnovo", e);
        }
    }
}