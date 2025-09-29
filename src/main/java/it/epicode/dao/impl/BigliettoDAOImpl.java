package it.epicode.dao.impl;

import it.epicode.dao.BigliettoDAO;
import it.epicode.entity.Biglietto;
import it.epicode.entity.PuntoEmissione;
import it.epicode.entity.Vidimazione;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Implementazione del DAO per l'entità Biglietto.
 * Fornisce tutte le operazioni CRUD e query specifiche per i biglietti.
 */
public class BigliettoDAOImpl extends GenericDAOImpl<Biglietto, Long> implements BigliettoDAO {
    
    private static final Logger logger = LoggerFactory.getLogger(BigliettoDAOImpl.class);
    
    /**
     * Costruttore che inizializza il DAO con l'EntityManager
     * @param entityManager L'EntityManager per le operazioni di persistenza
     */
    public BigliettoDAOImpl(EntityManager entityManager) {
        super(entityManager, Biglietto.class);
    }
    
    @Override
    public Optional<Biglietto> findByCodiceBiglietto(String codiceBiglietto) {
        try {
            String jpql = "SELECT b FROM Biglietto b WHERE b.codiceBiglietto = :codiceBiglietto";
            TypedQuery<Biglietto> query = entityManager.createQuery(jpql, Biglietto.class);
            query.setParameter("codiceBiglietto", codiceBiglietto);
            
            List<Biglietto> results = query.getResultList();
            Optional<Biglietto> result = results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
            
            logger.debug("Ricerca biglietto per codice '{}': {}", codiceBiglietto, result.isPresent() ? "trovato" : "non trovato");
            return result;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca del biglietto per codice '{}': {}", codiceBiglietto, e.getMessage(), e);
            return Optional.empty();
        }
    }
    
    @Override
    public List<Biglietto> findByDataEmissione(LocalDate dataEmissione) {
        try {
            String jpql = "SELECT b FROM Biglietto b WHERE b.dataEmissione = :dataEmissione ORDER BY b.createdAt DESC";
            TypedQuery<Biglietto> query = entityManager.createQuery(jpql, Biglietto.class);
            query.setParameter("dataEmissione", dataEmissione);
            
            List<Biglietto> results = query.getResultList();
            logger.debug("Trovati {} biglietti emessi il {}", results.size(), dataEmissione);
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca dei biglietti per data emissione {}: {}", dataEmissione, e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca biglietti per data emissione", e);
        }
    }
    
    @Override
    public List<Biglietto> findByDataEmissioneBetween(LocalDate dataInizio, LocalDate dataFine) {
        try {
            String jpql = "SELECT b FROM Biglietto b WHERE b.dataEmissione BETWEEN :dataInizio AND :dataFine ORDER BY b.dataEmissione DESC";
            TypedQuery<Biglietto> query = entityManager.createQuery(jpql, Biglietto.class);
            query.setParameter("dataInizio", dataInizio);
            query.setParameter("dataFine", dataFine);
            
            List<Biglietto> results = query.getResultList();
            logger.debug("Trovati {} biglietti emessi tra {} e {}", results.size(), dataInizio, dataFine);
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca dei biglietti emessi tra {} e {}: {}", 
                        dataInizio, dataFine, e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca biglietti per periodo emissione", e);
        }
    }
    
    @Override
    public List<Biglietto> findByDataScadenza(LocalDate dataScadenza) {
        try {
            String jpql = "SELECT b FROM Biglietto b WHERE b.dataScadenza = :dataScadenza ORDER BY b.createdAt DESC";
            TypedQuery<Biglietto> query = entityManager.createQuery(jpql, Biglietto.class);
            query.setParameter("dataScadenza", dataScadenza);
            
            List<Biglietto> results = query.getResultList();
            logger.debug("Trovati {} biglietti che scadono il {}", results.size(), dataScadenza);
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca dei biglietti per data scadenza {}: {}", dataScadenza, e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca biglietti per data scadenza", e);
        }
    }
    
    @Override
    public List<Biglietto> findByDataScadenzaBefore(LocalDate dataScadenza) {
        try {
            String jpql = "SELECT b FROM Biglietto b WHERE b.dataScadenza <= :dataScadenza ORDER BY b.dataScadenza ASC";
            TypedQuery<Biglietto> query = entityManager.createQuery(jpql, Biglietto.class);
            query.setParameter("dataScadenza", dataScadenza);
            
            List<Biglietto> results = query.getResultList();
            logger.debug("Trovati {} biglietti che scadono entro {}", results.size(), dataScadenza);
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca dei biglietti che scadono entro {}: {}", dataScadenza, e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca biglietti per scadenza", e);
        }
    }
    
    @Override
    public List<Biglietto> findByPrezzo(BigDecimal prezzo) {
        try {
            String jpql = "SELECT b FROM Biglietto b WHERE b.prezzo = :prezzo ORDER BY b.dataEmissione DESC";
            TypedQuery<Biglietto> query = entityManager.createQuery(jpql, Biglietto.class);
            query.setParameter("prezzo", prezzo);
            
            List<Biglietto> results = query.getResultList();
            logger.debug("Trovati {} biglietti con prezzo {}", results.size(), prezzo);
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca dei biglietti per prezzo {}: {}", prezzo, e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca biglietti per prezzo", e);
        }
    }
    
    @Override
    public List<Biglietto> findByPrezzoBetween(BigDecimal prezzoMin, BigDecimal prezzoMax) {
        try {
            String jpql = "SELECT b FROM Biglietto b WHERE b.prezzo BETWEEN :prezzoMin AND :prezzoMax ORDER BY b.prezzo ASC";
            TypedQuery<Biglietto> query = entityManager.createQuery(jpql, Biglietto.class);
            query.setParameter("prezzoMin", prezzoMin);
            query.setParameter("prezzoMax", prezzoMax);
            
            List<Biglietto> results = query.getResultList();
            logger.debug("Trovati {} biglietti con prezzo tra {} e {}", results.size(), prezzoMin, prezzoMax);
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca dei biglietti per range prezzo {} - {}: {}", 
                        prezzoMin, prezzoMax, e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca biglietti per range prezzo", e);
        }
    }
    
    @Override
    public List<Biglietto> findValidatedBiglietti() {
        try {
            String jpql = "SELECT b FROM Biglietto b WHERE b.validato = true ORDER BY b.dataValidazione DESC";
            TypedQuery<Biglietto> query = entityManager.createQuery(jpql, Biglietto.class);
            
            List<Biglietto> results = query.getResultList();
            logger.debug("Trovati {} biglietti validati", results.size());
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca dei biglietti validati: {}", e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca biglietti validati", e);
        }
    }
    
    @Override
    public List<Biglietto> findNonValidatedBiglietti() {
        try {
            String jpql = "SELECT b FROM Biglietto b WHERE b.validato = false ORDER BY b.dataEmissione DESC";
            TypedQuery<Biglietto> query = entityManager.createQuery(jpql, Biglietto.class);
            
            List<Biglietto> results = query.getResultList();
            logger.debug("Trovati {} biglietti non validati", results.size());
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca dei biglietti non validati: {}", e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca biglietti non validati", e);
        }
    }
    
    @Override
    public List<Biglietto> findByDataValidazione(LocalDate dataValidazione) {
        try {
            String jpql = "SELECT b FROM Biglietto b WHERE b.dataValidazione = :dataValidazione ORDER BY b.createdAt DESC";
            TypedQuery<Biglietto> query = entityManager.createQuery(jpql, Biglietto.class);
            query.setParameter("dataValidazione", dataValidazione);
            
            List<Biglietto> results = query.getResultList();
            logger.debug("Trovati {} biglietti validati il {}", results.size(), dataValidazione);
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca dei biglietti per data validazione {}: {}", dataValidazione, e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca biglietti per data validazione", e);
        }
    }
    
    @Override
    public List<Biglietto> findByDataValidazioneBetween(LocalDate dataInizio, LocalDate dataFine) {
        try {
            String jpql = "SELECT b FROM Biglietto b WHERE b.dataValidazione BETWEEN :dataInizio AND :dataFine ORDER BY b.dataValidazione DESC";
            TypedQuery<Biglietto> query = entityManager.createQuery(jpql, Biglietto.class);
            query.setParameter("dataInizio", dataInizio);
            query.setParameter("dataFine", dataFine);
            
            List<Biglietto> results = query.getResultList();
            logger.debug("Trovati {} biglietti validati tra {} e {}", results.size(), dataInizio, dataFine);
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca dei biglietti validati tra {} e {}: {}", 
                        dataInizio, dataFine, e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca biglietti per periodo validazione", e);
        }
    }
    
    @Override
    public List<Biglietto> findByPuntoEmissione(PuntoEmissione puntoEmissione) {
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
    public List<Biglietto> findExpiredBiglietti() {
        try {
            String jpql = "SELECT b FROM Biglietto b WHERE b.dataScadenza < CURRENT_DATE ORDER BY b.dataScadenza DESC";
            TypedQuery<Biglietto> query = entityManager.createQuery(jpql, Biglietto.class);
            
            List<Biglietto> results = query.getResultList();
            logger.debug("Trovati {} biglietti scaduti", results.size());
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca dei biglietti scaduti: {}", e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca biglietti scaduti", e);
        }
    }
    
    @Override
    public List<Biglietto> findValidBiglietti() {
        try {
            String jpql = "SELECT b FROM Biglietto b WHERE b.dataScadenza > CURRENT_DATE AND b.validato = false ORDER BY b.dataScadenza ASC";
            TypedQuery<Biglietto> query = entityManager.createQuery(jpql, Biglietto.class);
            
            List<Biglietto> results = query.getResultList();
            logger.debug("Trovati {} biglietti validi", results.size());
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca dei biglietti validi: {}", e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca biglietti validi", e);
        }
    }
    
    @Override
    public List<Biglietto> findBigliettiExpiringToday() {
        try {
            String jpql = "SELECT b FROM Biglietto b WHERE b.dataScadenza = CURRENT_DATE ORDER BY b.createdAt DESC";
            TypedQuery<Biglietto> query = entityManager.createQuery(jpql, Biglietto.class);
            
            List<Biglietto> results = query.getResultList();
            logger.debug("Trovati {} biglietti che scadono oggi", results.size());
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca dei biglietti che scadono oggi: {}", e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca biglietti in scadenza oggi", e);
        }
    }
    
    @Override
    public List<Biglietto> findBigliettiExpiringWithinDays(int giorni) {
        try {
            LocalDate dataLimite = LocalDate.now().plusDays(giorni);
            String jpql = "SELECT b FROM Biglietto b WHERE b.dataScadenza <= :dataLimite AND b.dataScadenza > CURRENT_DATE ORDER BY b.dataScadenza ASC";
            TypedQuery<Biglietto> query = entityManager.createQuery(jpql, Biglietto.class);
            query.setParameter("dataLimite", dataLimite);
            
            List<Biglietto> results = query.getResultList();
            logger.debug("Trovati {} biglietti che scadono entro {} giorni", results.size(), giorni);
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca dei biglietti che scadono entro {} giorni: {}", 
                        giorni, e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca biglietti in scadenza", e);
        }
    }
    
    @Override
    public List<Biglietto> findBigliettiWithoutVidimazioni() {
        try {
            String jpql = "SELECT b FROM Biglietto b WHERE b.vidimazioni IS EMPTY ORDER BY b.dataEmissione DESC";
            TypedQuery<Biglietto> query = entityManager.createQuery(jpql, Biglietto.class);
            
            List<Biglietto> results = query.getResultList();
            logger.debug("Trovati {} biglietti senza vidimazioni", results.size());
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca dei biglietti senza vidimazioni: {}", e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca biglietti senza vidimazioni", e);
        }
    }
    
    @Override
    public List<Biglietto> findBigliettiWithVidimazioni() {
        try {
            String jpql = "SELECT DISTINCT b FROM Biglietto b WHERE b.vidimazioni IS NOT EMPTY ORDER BY b.dataEmissione DESC";
            TypedQuery<Biglietto> query = entityManager.createQuery(jpql, Biglietto.class);
            
            List<Biglietto> results = query.getResultList();
            logger.debug("Trovati {} biglietti con vidimazioni", results.size());
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca dei biglietti con vidimazioni: {}", e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca biglietti con vidimazioni", e);
        }
    }
    
    @Override
    public boolean existsByCodiceBiglietto(String codiceBiglietto) {
        try {
            String jpql = "SELECT COUNT(b) FROM Biglietto b WHERE b.codiceBiglietto = :codiceBiglietto";
            TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
            query.setParameter("codiceBiglietto", codiceBiglietto);
            
            Long count = query.getSingleResult();
            boolean exists = count > 0;
            logger.debug("Verifica esistenza codice biglietto '{}': {}", codiceBiglietto, exists);
            return exists;
        } catch (Exception e) {
            logger.error("Errore durante la verifica di esistenza del codice biglietto '{}': {}", codiceBiglietto, e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    public long countByDataEmissione(LocalDate dataEmissione) {
        try {
            String jpql = "SELECT COUNT(b) FROM Biglietto b WHERE b.dataEmissione = :dataEmissione";
            TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
            query.setParameter("dataEmissione", dataEmissione);
            
            Long count = query.getSingleResult();
            logger.debug("Conteggio biglietti emessi il {}: {}", dataEmissione, count);
            return count;
        } catch (Exception e) {
            logger.error("Errore durante il conteggio dei biglietti per data emissione {}: {}", dataEmissione, e.getMessage(), e);
            return 0;
        }
    }
    
    @Override
    public long countByDataEmissioneBetween(LocalDate dataInizio, LocalDate dataFine) {
        try {
            String jpql = "SELECT COUNT(b) FROM Biglietto b WHERE b.dataEmissione BETWEEN :dataInizio AND :dataFine";
            TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
            query.setParameter("dataInizio", dataInizio);
            query.setParameter("dataFine", dataFine);
            
            Long count = query.getSingleResult();
            logger.debug("Conteggio biglietti emessi tra {} e {}: {}", dataInizio, dataFine, count);
            return count;
        } catch (Exception e) {
            logger.error("Errore durante il conteggio dei biglietti emessi tra {} e {}: {}", 
                        dataInizio, dataFine, e.getMessage(), e);
            return 0;
        }
    }
    
    @Override
    public long countValidatedBiglietti() {
        try {
            String jpql = "SELECT COUNT(b) FROM Biglietto b WHERE b.validato = true";
            TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
            
            Long count = query.getSingleResult();
            logger.debug("Conteggio biglietti validati: {}", count);
            return count;
        } catch (Exception e) {
            logger.error("Errore durante il conteggio dei biglietti validati: {}", e.getMessage(), e);
            return 0;
        }
    }
    
    @Override
    public long countNonValidatedBiglietti() {
        try {
            String jpql = "SELECT COUNT(b) FROM Biglietto b WHERE b.validato = false";
            TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
            
            Long count = query.getSingleResult();
            logger.debug("Conteggio biglietti non validati: {}", count);
            return count;
        } catch (Exception e) {
            logger.error("Errore durante il conteggio dei biglietti non validati: {}", e.getMessage(), e);
            return 0;
        }
    }
    
    @Override
    public long countExpiredBiglietti() {
        try {
            String jpql = "SELECT COUNT(b) FROM Biglietto b WHERE b.dataScadenza < CURRENT_DATE";
            TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
            
            Long count = query.getSingleResult();
            logger.debug("Conteggio biglietti scaduti: {}", count);
            return count;
        } catch (Exception e) {
            logger.error("Errore durante il conteggio dei biglietti scaduti: {}", e.getMessage(), e);
            return 0;
        }
    }
    
    @Override
    public long countValidBiglietti() {
        try {
            String jpql = "SELECT COUNT(b) FROM Biglietto b WHERE b.dataScadenza > CURRENT_DATE AND b.validato = false";
            TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
            
            Long count = query.getSingleResult();
            logger.debug("Conteggio biglietti validi: {}", count);
            return count;
        } catch (Exception e) {
            logger.error("Errore durante il conteggio dei biglietti validi: {}", e.getMessage(), e);
            return 0;
        }
    }
    
    @Override
    public long countByPuntoEmissione(PuntoEmissione puntoEmissione) {
        try {
            String jpql = "SELECT COUNT(b) FROM Biglietto b WHERE b.puntoEmissione = :puntoEmissione";
            TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
            query.setParameter("puntoEmissione", puntoEmissione);
            
            Long count = query.getSingleResult();
            logger.debug("Conteggio biglietti per punto emissione {}: {}", puntoEmissione.getId(), count);
            return count;
        } catch (Exception e) {
            logger.error("Errore durante il conteggio dei biglietti per punto emissione {}: {}", 
                        puntoEmissione.getId(), e.getMessage(), e);
            return 0;
        }
    }
    
    @Override
    public BigDecimal calculateTotalRevenueInPeriod(LocalDate dataInizio, LocalDate dataFine) {
        try {
            String jpql = "SELECT COALESCE(SUM(b.prezzo), 0) FROM Biglietto b WHERE b.dataEmissione BETWEEN :dataInizio AND :dataFine";
            TypedQuery<BigDecimal> query = entityManager.createQuery(jpql, BigDecimal.class);
            query.setParameter("dataInizio", dataInizio);
            query.setParameter("dataFine", dataFine);
            
            BigDecimal revenue = query.getSingleResult();
            logger.debug("Ricavo totale biglietti tra {} e {}: {}", dataInizio, dataFine, revenue);
            return revenue != null ? revenue : BigDecimal.ZERO;
        } catch (Exception e) {
            logger.error("Errore durante il calcolo del ricavo totale tra {} e {}: {}", 
                        dataInizio, dataFine, e.getMessage(), e);
            return BigDecimal.ZERO;
        }
    }
    
    @Override
    public BigDecimal calculateValidatedRevenueInPeriod(LocalDate dataInizio, LocalDate dataFine) {
        try {
            String jpql = "SELECT COALESCE(SUM(b.prezzo), 0) FROM Biglietto b WHERE b.validato = true AND b.dataValidazione BETWEEN :dataInizio AND :dataFine";
            TypedQuery<BigDecimal> query = entityManager.createQuery(jpql, BigDecimal.class);
            query.setParameter("dataInizio", dataInizio);
            query.setParameter("dataFine", dataFine);
            
            BigDecimal revenue = query.getSingleResult();
            logger.debug("Ricavo biglietti validati tra {} e {}: {}", dataInizio, dataFine, revenue);
            return revenue != null ? revenue : BigDecimal.ZERO;
        } catch (Exception e) {
            logger.error("Errore durante il calcolo del ricavo biglietti validati tra {} e {}: {}", 
                        dataInizio, dataFine, e.getMessage(), e);
            return BigDecimal.ZERO;
        }
    }
    
    @Override
    public List<Vidimazione> findVidimazioniByBiglietto(Biglietto biglietto) {
        try {
            String jpql = "SELECT v FROM Vidimazione v WHERE v.biglietto = :biglietto ORDER BY v.dataVidimazione DESC";
            TypedQuery<Vidimazione> query = entityManager.createQuery(jpql, Vidimazione.class);
            query.setParameter("biglietto", biglietto);
            
            List<Vidimazione> results = query.getResultList();
            logger.debug("Trovate {} vidimazioni per biglietto {}", results.size(), biglietto.getId());
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca delle vidimazioni per biglietto {}: {}", 
                        biglietto.getId(), e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca vidimazioni per biglietto", e);
        }
    }
    
    @Override
    public List<Object[]> findMostSoldBigliettiByPrice(int limit) {
        try {
            String jpql = "SELECT b.prezzo, COUNT(b) as vendite FROM Biglietto b GROUP BY b.prezzo ORDER BY vendite DESC";
            TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
            query.setMaxResults(limit);
            
            List<Object[]> results = query.getResultList();
            logger.debug("Trovati {} prezzi di biglietti più venduti", results.size());
            return results;
        } catch (Exception e) {
            logger.error("Errore durante la ricerca dei biglietti più venduti per prezzo: {}", e.getMessage(), e);
            throw new RuntimeException("Errore durante la ricerca biglietti più venduti", e);
        }
    }
}