package dao;

import entities.Biglietto;
import exceptions.InvalidDataException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.time.LocalDate;

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
            System.out.println("Biglietto salvato correttamente" + newBiglietto);
        } catch (Exception e) {
            if(transaction.isActive()){
                transaction.rollback();
            }
            System.out.println("Errore salvataggio biglietto");
            e.printStackTrace();
        }
    }

    public Biglietto findById(long bigliettoid){
        try {
            Biglietto trovato = entityManager.find(Biglietto.class, bigliettoid);
            return trovato;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

    public void delete(Biglietto biglietto) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.remove(biglietto);
            transaction.commit();
            System.out.println("Biglietto eliminato con successo");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Errore durante l'eliminazione");
        }
    }
    public void deletewithId(long bigliettoid){

        try {


            Biglietto found = this.findById(bigliettoid);

            EntityTransaction transaction = entityManager.getTransaction();

            transaction.begin();

            entityManager.remove(found);

            transaction.commit();

            System.out.println(found + "Rimosso correttamente");
        } catch (Exception e){
            throw new RuntimeException(e);

        }


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
    EntityManager em;
    public long countBigliettiByPuntoEmissioneAndPeriodo(Long puntoEmissioneId, LocalDate dataInizio, LocalDate dataFine) {

        return em.createQuery(
                        "SELECT COUNT(b) FROM Biglietto b WHERE " +
                                "(b.distributore.idPuntoEmissione = :puntoId OR b.rivenditore.idPuntoEmissione = :puntoId) " +
                                "AND b.dataEmissione BETWEEN :dataInizio AND :dataFine", Long.class)
                .setParameter("puntoId", puntoEmissioneId)
                .setParameter("dataInizio", dataInizio)
                .setParameter("dataFine", dataFine)
                .getSingleResult();
    }
    public long countBigliettiByPeriodo(LocalDate dataInizio, LocalDate dataFine) {
        return em.createQuery(
                        "SELECT COUNT(b) FROM Biglietto b WHERE b.dataEmissione BETWEEN :dataInizio AND :dataFine", Long.class)
                .setParameter("dataInizio", dataInizio)
                .setParameter("dataFine", dataFine)
                .getSingleResult();
    }

    public long countBigliettiVidimatiByMezzo(Long mezzoId, LocalDate dataInizio, LocalDate dataFine) {
        return em.createQuery(
                        "SELECT COUNT(b) FROM Biglietto b WHERE b.mezzoValidante.id = :mezzoId " +
                                "AND b.validazione = true AND b.dataValidazione BETWEEN :dataInizio AND :dataFine", Long.class)
                .setParameter("mezzoId", mezzoId)
                .setParameter("dataInizio", dataInizio)
                .setParameter("dataFine", dataFine)
                .getSingleResult();
    }

    public long countBigliettiVidimatiByPeriodo(LocalDate dataInizio, LocalDate dataFine) {
        return em.createQuery(
                        "SELECT COUNT(b) FROM Biglietto b WHERE b.validazione = true " +
                                "AND b.dataValidazione BETWEEN :dataInizio AND :dataFine", Long.class)
                .setParameter("dataInizio", dataInizio)
                .setParameter("dataFine", dataFine)
                .getSingleResult();
    }
}
