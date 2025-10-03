package dao;

import entities.Biglietto;
import exceptions.InvalidDataException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.time.LocalDate;
import java.util.List;

public class BigliettoDAO {

    private final EntityManager entityManager;

    public BigliettoDAO(EntityManager entityManager, EntityManager entityManager1){
        this.entityManager = entityManager;
    }

    public void save(Biglietto newBiglietto) {
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            entityManager.persist(newBiglietto);
            transaction.commit();
          /*  System.out.println("Biglietto salvato correttamente - ID: " + newBiglietto.getId() +
                    " - Emesso presso ID Punto Emissione: " + newBiglietto.getPuntoEmissione().getIdPuntoEmissione() +
                    " (" + newBiglietto.getPuntoEmissione().getIndirizzo() + ")" +
                    " - Prezzo: €" + newBiglietto.getPrezzo());*/
        } catch (Exception e) {
            if(transaction.isActive()){
                transaction.rollback();
            }
            System.out.println("Errore salvataggio biglietto");
            e.printStackTrace();
        }
    }

    public Biglietto findById(long bigliettoid){
        return entityManager.find(Biglietto.class, bigliettoid);
    }

    public void validaBiglietto(long bigliettoid, long mezzoId) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Biglietto biglietto = findById(bigliettoid);

            if (biglietto.getDataValidazione() != null) {
                throw new InvalidDataException("Biglietto già validato in data: " + biglietto.getDataValidazione());
            }

            biglietto.setDataValidazione(LocalDate.now());
            entityManager.merge(biglietto);
            transaction.commit();
            System.out.println("Biglietto validato con successo");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Errore durante la validazione: " + e.getMessage());
        }
    }

    public void deleteWithId(long bigliettoid){
        Biglietto found = this.findById(bigliettoid);
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(found);
        transaction.commit();
        System.out.println(found + " Rimosso correttamente");
    }

    public void update(Biglietto biglietto) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(biglietto);
            transaction.commit();
            System.out.println("Biglietto aggiornato con successo");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Errore durante l'aggiornamento");
        }
    }

    public long countBigliettiByPuntoEmissioneAndPeriodo(Long puntoEmissioneId, LocalDate dataInizio, LocalDate dataFine) {
        return entityManager.createQuery(
                        "SELECT COUNT(b) FROM Biglietto b " +
                                "JOIN b.puntoEmissione pe " +
                                "WHERE pe.id = :puntoId " +
                                "AND b.dataEmissione BETWEEN :dataInizio AND :dataFine", Long.class)
                .setParameter("puntoId", puntoEmissioneId)
                .setParameter("dataInizio", dataInizio)
                .setParameter("dataFine", dataFine)
                .getSingleResult();
    }
    public List<Biglietto> findBigliettoByPuntoEmissioneAndPeriodo(Long puntoEmissioneId, LocalDate dataInizio, LocalDate dataFine) {
        return entityManager.createQuery(
                        "SELECT b FROM Biglietto b " +
                                "JOIN b.puntoEmissione pe " +
                                "WHERE pe.id = :puntoId " +
                                "AND b.dataEmissione BETWEEN :dataInizio AND :dataFine", Biglietto.class)
                .setParameter("puntoId", puntoEmissioneId)
                .setParameter("dataInizio", dataInizio)
                .setParameter("dataFine", dataFine)
                .getResultList();
    }
    public List<Biglietto> findBigliettoByPeriodo(LocalDate dataInizio, LocalDate dataFine){
        return entityManager.createQuery(
                "SELECT b FROM Biglietto b WHERE b.dataEmissione BETWEEN :dataInizio AND :dataFine", Biglietto.class)
                .setParameter("dataInizio", dataInizio)
                .setParameter("dataFine", dataFine)
                .getResultList();
    }


    public long countBigliettiByPeriodo(LocalDate dataInizio, LocalDate dataFine) {
        return entityManager.createQuery(
                        "SELECT COUNT(b) FROM Biglietto b WHERE b.dataEmissione BETWEEN :dataInizio AND :dataFine", Long.class)
                .setParameter("dataInizio", dataInizio)
                .setParameter("dataFine", dataFine)
                .getSingleResult();
    }

    public long countBigliettiVidimatiByMezzo(Long mezzoId, LocalDate dataInizio, LocalDate dataFine) {
        return entityManager.createQuery(
                        "SELECT COUNT(b) FROM Biglietto b " +
                                "JOIN b.mezzoValidante mv " +
                                "WHERE mv.id = :mezzoId " +
                                "AND b.dataValidazione IS NOT NULL " +
                                "AND b.dataValidazione BETWEEN :dataInizio AND :dataFine", Long.class)
                .setParameter("mezzoId", mezzoId)
                .setParameter("dataInizio", dataInizio)
                .setParameter("dataFine", dataFine)
                .getSingleResult();
    }
    public List<Biglietto> trovaBigliettiVidimatiByMezzo(Long mezzoId, LocalDate dataInizio, LocalDate dataFine) {
        return entityManager.createQuery(
                        "SELECT b FROM Biglietto b " +
                                "JOIN b.mezzoValidante mv " +
                                "WHERE mv.id = :mezzoId " +
                                "AND b.dataValidazione IS NOT NULL " +
                                "AND b.dataValidazione BETWEEN :dataInizio AND :dataFine", Biglietto.class)
                .setParameter("mezzoId", mezzoId)
                .setParameter("dataInizio", dataInizio)
                .setParameter("dataFine", dataFine)
                .getResultList();
    }

    public long countBigliettiVidimatiByPeriodo(LocalDate dataInizio, LocalDate dataFine) {
        return entityManager.createQuery(
                        "SELECT COUNT(b) FROM Biglietto b WHERE b.dataValidazione IS NOT NULL " +
                                "AND b.dataValidazione BETWEEN :dataInizio AND :dataFine", Long.class)
                .setParameter("dataInizio", dataInizio)
                .setParameter("dataFine", dataFine)
                .getSingleResult();
    }

    public List<Biglietto> countBigliettiVidimatiTotal(LocalDate dataInizio, LocalDate dataFine) {
        return entityManager.createQuery(
                        "SELECT b FROM Biglietto b WHERE b.dataValidazione IS NOT NULL " +
                                "AND b.dataValidazione BETWEEN :dataInizio AND :dataFine", Biglietto.class)
                .setParameter("dataInizio", dataInizio)
                .setParameter("dataFine", dataFine)
                .getResultList();
    }
}