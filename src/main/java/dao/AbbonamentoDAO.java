package dao;

import entities.Abbonamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.time.LocalDate;

public class AbbonamentoDAO {

    private final EntityManager entityManager;

    public AbbonamentoDAO(EntityManager entityManager, EntityManager entityManager1){
        this.entityManager = entityManager;
    }

    public void save(Abbonamento newAbbonamento) {
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            entityManager.persist(newAbbonamento);
            transaction.commit();
            System.out.println("Abbonamento salvato correttamente" + newAbbonamento);
        } catch (Exception e) {
            if(transaction.isActive()){
                transaction.rollback();
            }
            System.out.println("Errore salvataggio abbonamento");
            e.printStackTrace();
        }
    }

    public Abbonamento findById(long Abbonamentoid){
        try {
            Abbonamento trovato = entityManager.find(Abbonamento.class, Abbonamentoid);
            return trovato;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Abbonamento abbonamento) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.remove(abbonamento);
            transaction.commit();
            System.out.println("Abbonamento eliminato con successo");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Errore durante l'eliminazione");
        }
    }
    public void deletewithId(long abbonamentoid){

        try {


            Abbonamento found = this.findById(abbonamentoid);

            EntityTransaction transaction = entityManager.getTransaction();

            transaction.begin();

            entityManager.remove(found);

            transaction.commit();

            System.out.println(found + "Rimosso correttamente");
        } catch (Exception e){
            throw new RuntimeException(e);

        }


    }
    public void update(Abbonamento abbonamento) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(abbonamento);
            transaction.commit();
            System.out.println("Abbonamento aggiornato con successo");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Errore durante l'aggiornamento");
        }
    }
    EntityManager em;
    public long countAbbonamentiByPuntoEmissioneAndPeriodo(Long puntoEmissioneId, LocalDate dataInizio, LocalDate dataFine) {
        return em.createQuery(
                        "SELECT COUNT(a) FROM Abbonamento a WHERE " +
                                "(a.distributore.idPuntoEmissione = :puntoId OR a.rivenditore.idPuntoEmissione = :puntoId) " +
                                "AND a.dataEmissione BETWEEN :dataInizio AND :dataFine", Long.class)
                .setParameter("puntoId", puntoEmissioneId)
                .setParameter("dataInizio", dataInizio)
                .setParameter("dataFine", dataFine)
                .getSingleResult();
    }



    

    public long countAbbonamentiByPeriodo(LocalDate dataInizio, LocalDate dataFine) {
        return em.createQuery(
                        "SELECT COUNT(a) FROM Abbonamento a WHERE a.dataEmissione BETWEEN :dataInizio AND :dataFine", Long.class)
                .setParameter("dataInizio", dataInizio)
                .setParameter("dataFine", dataFine)
                .getSingleResult();
    }

}
