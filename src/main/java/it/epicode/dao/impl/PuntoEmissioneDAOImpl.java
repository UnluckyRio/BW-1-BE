package it.epicode.dao.impl;

import it.epicode.dao.PuntoEmissioneDAO;
import it.epicode.entity.PuntoEmissione;
import it.epicode.entity.Biglietto;
import it.epicode.entity.Abbonamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementazione del DAO per l'entità PuntoEmissione.
 * Fornisce tutte le operazioni CRUD e query specifiche per i punti di emissione.
 */
public class PuntoEmissioneDAOImpl extends GenericDAOImpl<PuntoEmissione, Long> implements PuntoEmissioneDAO {
    
    private static final Logger logger = LoggerFactory.getLogger(PuntoEmissioneDAOImpl.class);
    
    /**
     * Costruttore che inizializza il DAO con l'EntityManager
     * @param entityManager L'EntityManager per le operazioni di persistenza
     */
    public PuntoEmissioneDAOImpl(EntityManager entityManager) {
        super(entityManager, PuntoEmissione.class);
    }
    
    @Override
    public Optional<PuntoEmissione> findByNome(String nome) {
        try {
            String jpql = "SELECT p FROM PuntoEmissione p WHERE p.nome = :nome";
            TypedQuery<PuntoEmissione> query = entityManager.createQuery(jpql, PuntoEmissione.class);
            query.setParameter("nome", nome);
            
            List<PuntoEmissione> results = query.getResultList();
            Optional<PuntoEmissione> result = results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
            
            logger.debug("Ricerca punto emissione per nome '{}': {}", nome, result.isPresent() ? "trovato" : "non trovato");
            return result;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca del punto emissione per nome '{}': {}", nome, e.getMessage(), e);
            return Optional.empty();
        }
    }
    
    @Override
    public List<PuntoEmissione> findByTipo(String tipo) {
        try {
            String jpql = "SELECT p FROM PuntoEmissione p WHERE p.tipo = :tipo ORDER BY p.nome";
            TypedQuery<PuntoEmissione> query = entityManager.createQuery(jpql, PuntoEmissione.class);
            query.setParameter("tipo", tipo);
            
            List<PuntoEmissione> results = query.getResultList();
            logger.debug("Trovati {} punti emissione di tipo '{}'", results.size(), tipo);
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca dei punti emissione per tipo '{}': {}", tipo, e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca punti emissione per tipo", e);
        }
    }
    
    @Override
    public List<PuntoEmissione> findByIndirizzoContaining(String indirizzo) {
        try {
            String jpql = "SELECT p FROM PuntoEmissione p WHERE LOWER(p.indirizzo) LIKE LOWER(:indirizzo) ORDER BY p.nome";
            TypedQuery<PuntoEmissione> query = entityManager.createQuery(jpql, PuntoEmissione.class);
            query.setParameter("indirizzo", "%" + indirizzo + "%");
            
            List<PuntoEmissione> results = query.getResultList();
            logger.debug("Trovati {} punti emissione con indirizzo contenente '{}'", results.size(), indirizzo);
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca dei punti emissione per indirizzo '{}': {}", indirizzo, e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca punti emissione per indirizzo", e);
        }
    }
    
    @Override
    public List<PuntoEmissione> findActivePuntiEmissione() {
        try {
            String jpql = "SELECT p FROM PuntoEmissione p WHERE p.attivo = true ORDER BY p.nome";
            TypedQuery<PuntoEmissione> query = entityManager.createQuery(jpql, PuntoEmissione.class);
            
            List<PuntoEmissione> results = query.getResultList();
            logger.debug("Trovati {} punti emissione attivi", results.size());
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca dei punti emissione attivi: {}", e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca punti emissione attivi", e);
        }
    }
    
    @Override
    public List<PuntoEmissione> findInactivePuntiEmissione() {
        try {
            String jpql = "SELECT p FROM PuntoEmissione p WHERE p.attivo = false ORDER BY p.nome";
            TypedQuery<PuntoEmissione> query = entityManager.createQuery(jpql, PuntoEmissione.class);
            
            List<PuntoEmissione> results = query.getResultList();
            logger.debug("Trovati {} punti emissione non attivi", results.size());
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca dei punti emissione non attivi: {}", e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca punti emissione non attivi", e);
        }
    }
    
    @Override
    public List<PuntoEmissione> findPuntiEmissioneCreatedBetween(LocalDateTime dataInizio, LocalDateTime dataFine) {
        try {
            String jpql = "SELECT p FROM PuntoEmissione p WHERE p.createdAt BETWEEN :dataInizio AND :dataFine ORDER BY p.createdAt DESC";
            TypedQuery<PuntoEmissione> query = entityManager.createQuery(jpql, PuntoEmissione.class);
            query.setParameter("dataInizio", dataInizio);
            query.setParameter("dataFine", dataFine);
            
            List<PuntoEmissione> results = query.getResultList();
            logger.debug("Trovati {} punti emissione creati tra {} e {}", results.size(), dataInizio, dataFine);
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca dei punti emissione creati tra {} e {}: {}", 
                        dataInizio, dataFine, e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca punti emissione per periodo creazione", e);
        }
    }
    
    @Override
    public List<PuntoEmissione> findPuntiEmissioneWithBigliettiInPeriodo(LocalDate dataInizio, LocalDate dataFine) {
        try {
            String jpql = "SELECT DISTINCT p FROM PuntoEmissione p JOIN p.biglietti b WHERE b.dataEmissione BETWEEN :dataInizio AND :dataFine ORDER BY p.nome";
            TypedQuery<PuntoEmissione> query = entityManager.createQuery(jpql, PuntoEmissione.class);
            query.setParameter("dataInizio", dataInizio);
            query.setParameter("dataFine", dataFine);
            
            List<PuntoEmissione> results = query.getResultList();
            logger.debug("Trovati {} punti emissione con biglietti emessi tra {} e {}", results.size(), dataInizio, dataFine);
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca dei punti emissione con biglietti tra {} e {}: {}", 
                        dataInizio, dataFine, e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca punti emissione con biglietti", e);
        }
    }
    
    @Override
    public List<PuntoEmissione> findPuntiEmissioneWithAbbonamentiInPeriodo(LocalDate dataInizio, LocalDate dataFine) {
        try {
            String jpql = "SELECT DISTINCT p FROM PuntoEmissione p JOIN p.abbonamenti a WHERE a.dataEmissione BETWEEN :dataInizio AND :dataFine ORDER BY p.nome";
            TypedQuery<PuntoEmissione> query = entityManager.createQuery(jpql, PuntoEmissione.class);
            query.setParameter("dataInizio", dataInizio);
            query.setParameter("dataFine", dataFine);
            
            List<PuntoEmissione> results = query.getResultList();
            logger.debug("Trovati {} punti emissione con abbonamenti emessi tra {} e {}", results.size(), dataInizio, dataFine);
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca dei punti emissione con abbonamenti tra {} e {}: {}", 
                        dataInizio, dataFine, e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca punti emissione con abbonamenti", e);
        }
    }
    
    @Override
    public List<PuntoEmissione> findPuntiEmissioneWithoutBiglietti() {
        try {
            String jpql = "SELECT p FROM PuntoEmissione p WHERE p.biglietti IS EMPTY ORDER BY p.nome";
            TypedQuery<PuntoEmissione> query = entityManager.createQuery(jpql, PuntoEmissione.class);
            
            List<PuntoEmissione> results = query.getResultList();
            logger.debug("Trovati {} punti emissione senza biglietti", results.size());
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca dei punti emissione senza biglietti: {}", e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca punti emissione senza biglietti", e);
        }
    }
    
    @Override
    public List<PuntoEmissione> findPuntiEmissioneWithoutAbbonamenti() {
        try {
            String jpql = "SELECT p FROM PuntoEmissione p WHERE p.abbonamenti IS EMPTY ORDER BY p.nome";
            TypedQuery<PuntoEmissione> query = entityManager.createQuery(jpql, PuntoEmissione.class);
            
            List<PuntoEmissione> results = query.getResultList();
            logger.debug("Trovati {} punti emissione senza abbonamenti", results.size());
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca dei punti emissione senza abbonamenti: {}", e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca punti emissione senza abbonamenti", e);
        }
    }
    
    @Override
    public long countBigliettiByPuntoEmissione(PuntoEmissione puntoEmissione) {
        try {
            String jpql = "SELECT COUNT(b) FROM Biglietto b WHERE b.puntoEmissione = :puntoEmissione";
            TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
            query.setParameter("puntoEmissione", puntoEmissione);
            
            Long count = query.getSingleResult();
            logger.debug("Conteggio biglietti per punto emissione {}: {}", puntoEmissione.getId(), count);
            return count;
        } catch (Exception e) {
            logger.error("Errore durante il conteggio biglietti per punto emissione {}: {}", 
                        puntoEmissione.getId(), e.getMessage(), e);
            return 0;
        }
    }
    
    @Override
    public long countAbbonamentiByPuntoEmissione(PuntoEmissione puntoEmissione) {
        try {
            String jpql = "SELECT COUNT(a) FROM Abbonamento a WHERE a.puntoEmissione = :puntoEmissione";
            TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
            query.setParameter("puntoEmissione", puntoEmissione);
            
            Long count = query.getSingleResult();
            logger.debug("Conteggio abbonamenti per punto emissione {}: {}", puntoEmissione.getId(), count);
            return count;
        } catch (Exception e) {
            logger.error("Errore durante il conteggio abbonamenti per punto emissione {}: {}", 
                        puntoEmissione.getId(), e.getMessage(), e);
            return 0;
        }
    }
    
    @Override
    public long countBigliettiByPuntoEmissioneInPeriodo(PuntoEmissione puntoEmissione, LocalDate dataInizio, LocalDate dataFine) {
        try {
            String jpql = "SELECT COUNT(b) FROM Biglietto b WHERE b.puntoEmissione = :puntoEmissione AND b.dataEmissione BETWEEN :dataInizio AND :dataFine";
            TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
            query.setParameter("puntoEmissione", puntoEmissione);
            query.setParameter("dataInizio", dataInizio);
            query.setParameter("dataFine", dataFine);
            
            Long count = query.getSingleResult();
            logger.debug("Conteggio biglietti per punto emissione {} tra {} e {}: {}", 
                        puntoEmissione.getId(), dataInizio, dataFine, count);
            return count;
        } catch (Exception e) {
            logger.error("Errore durante il conteggio biglietti per punto emissione {} tra {} e {}: {}", 
                        puntoEmissione.getId(), dataInizio, dataFine, e.getMessage(), e);
            return 0;
        }
    }
    
    @Override
    public long countAbbonamentiByPuntoEmissioneInPeriodo(PuntoEmissione puntoEmissione, LocalDate dataInizio, LocalDate dataFine) {
        try {
            String jpql = "SELECT COUNT(a) FROM Abbonamento a WHERE a.puntoEmissione = :puntoEmissione AND a.dataEmissione BETWEEN :dataInizio AND :dataFine";
            TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
            query.setParameter("puntoEmissione", puntoEmissione);
            query.setParameter("dataInizio", dataInizio);
            query.setParameter("dataFine", dataFine);
            
            Long count = query.getSingleResult();
            logger.debug("Conteggio abbonamenti per punto emissione {} tra {} e {}: {}", 
                        puntoEmissione.getId(), dataInizio, dataFine, count);
            return count;
        } catch (Exception e) {
            logger.error("Errore durante il conteggio abbonamenti per punto emissione {} tra {} e {}: {}", 
                        puntoEmissione.getId(), dataInizio, dataFine, e.getMessage(), e);
            return 0;
        }
    }
    
    @Override
    public boolean existsByNome(String nome) {
        try {
            String jpql = "SELECT COUNT(p) FROM PuntoEmissione p WHERE p.nome = :nome";
            TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
            query.setParameter("nome", nome);
            
            Long count = query.getSingleResult();
            boolean exists = count > 0;
            logger.debug("Verifica esistenza nome punto emissione '{}': {}", nome, exists);
            return exists;
        } catch (Exception e) {
            logger.error("Errore durante la verifica di esistenza del nome punto emissione '{}': {}", nome, e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    public long countActivePuntiEmissione() {
        try {
            String jpql = "SELECT COUNT(p) FROM PuntoEmissione p WHERE p.attivo = true";
            TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
            
            Long count = query.getSingleResult();
            logger.debug("Conteggio punti emissione attivi: {}", count);
            return count;
        } catch (Exception e) {
            logger.error("Errore durante il conteggio dei punti emissione attivi: {}", e.getMessage(), e);
            return 0;
        }
    }
    
    @Override
    public long countByTipo(String tipo) {
        try {
            String jpql = "SELECT COUNT(p) FROM PuntoEmissione p WHERE p.tipo = :tipo";
            TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
            query.setParameter("tipo", tipo);
            
            Long count = query.getSingleResult();
            logger.debug("Conteggio punti emissione di tipo '{}': {}", tipo, count);
            return count;
        } catch (Exception e) {
            logger.error("Errore durante il conteggio dei punti emissione per tipo '{}': {}", tipo, e.getMessage(), e);
            return 0;
        }
    }
    
    @Override
    public List<PuntoEmissione> findMostProductivePuntiEmissione(LocalDate dataInizio, LocalDate dataFine, int limit) {
        try {
            String jpql = "SELECT p, (COUNT(b) + COUNT(a)) as totale " +
                         "FROM PuntoEmissione p " +
                         "LEFT JOIN p.biglietti b ON b.dataEmissione BETWEEN :dataInizio AND :dataFine " +
                         "LEFT JOIN p.abbonamenti a ON a.dataEmissione BETWEEN :dataInizio AND :dataFine " +
                         "GROUP BY p " +
                         "ORDER BY totale DESC";
            
            TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
            query.setParameter("dataInizio", dataInizio);
            query.setParameter("dataFine", dataFine);
            query.setMaxResults(limit);
            
            List<Object[]> results = query.getResultList();
            List<PuntoEmissione> puntiEmissione = results.stream()
                    .map(result -> (PuntoEmissione) result[0])
                    .toList();
            
            logger.debug("Trovati {} punti emissione più produttivi tra {} e {}", puntiEmissione.size(), dataInizio, dataFine);
            return puntiEmissione;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca dei punti emissione più produttivi tra {} e {}: {}", 
                        dataInizio, dataFine, e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca punti emissione più produttivi", e);
        }
    }
    
    @Override
    public List<Biglietto> findBigliettiByPuntoEmissione(PuntoEmissione puntoEmissione) {
        try {
            String jpql = "SELECT b FROM Biglietto b WHERE b.puntoEmissione = :puntoEmissione ORDER BY b.dataEmissione DESC";
            TypedQuery<Biglietto> query = entityManager.createQuery(jpql, Biglietto.class);
            query.setParameter("puntoEmissione", puntoEmissione);
            
            List<Biglietto> results = query.getResultList();
            logger.debug("Trovati {} biglietti per punto emissione {}", results.size(), puntoEmissione.getId());
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca dei biglietti per punto emissione {}: {}", 
                        puntoEmissione.getId(), e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca biglietti per punto emissione", e);
        }
    }
    
    @Override
    public List<Abbonamento> findAbbonamentiByPuntoEmissione(PuntoEmissione puntoEmissione) {
        try {
            String jpql = "SELECT a FROM Abbonamento a WHERE a.puntoEmissione = :puntoEmissione ORDER BY a.dataEmissione DESC";
            TypedQuery<Abbonamento> query = entityManager.createQuery(jpql, Abbonamento.class);
            query.setParameter("puntoEmissione", puntoEmissione);
            
            List<Abbonamento> results = query.getResultList();
            logger.debug("Trovati {} abbonamenti per punto emissione {}", results.size(), puntoEmissione.getId());
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca degli abbonamenti per punto emissione {}: {}", 
                        puntoEmissione.getId(), e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca abbonamenti per punto emissione", e);
        }
    }
}