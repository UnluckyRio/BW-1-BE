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
         /*   System.out.println("Abbonamento salvato correttamente - ID: " + newAbbonamento.getId() +
                    " - Tipo: " + newAbbonamento.getTipoAbbonamento() +
                    " - Emesso presso ID Punto Emissione: " + newAbbonamento.getPuntoEmissione().getIdPuntoEmissione() +
                    " (" + newAbbonamento.getPuntoEmissione().getIndirizzo() + ")" +
                    " - Tessera ID: " + newAbbonamento.getTessera().getId() +
                    " - Prezzo: €" + newAbbonamento.getPrezzo());*/
        } catch (Exception e) {
            if(transaction.isActive()){
                transaction.rollback();
            }
            System.out.println("Errore salvataggio abbonamento");
        }
    }

    public Abbonamento findById(long Abbonamentoid){
        return entityManager.find(Abbonamento.class, Abbonamentoid);
    }

    public void deleteWithId(long abbonamentoid){
        Abbonamento found = this.findById(abbonamentoid);
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(found);
        transaction.commit();
        System.out.println(found + " Rimosso correttamente");
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

    public long countAbbonamentiByPuntoEmissioneAndPeriodo(Long puntoEmissioneId, LocalDate dataInizio, LocalDate dataFine) {
        return entityManager.createQuery(
                        "SELECT COUNT(a) FROM Abbonamento a " +
                                "JOIN a.puntoEmissione pe " +
                                "WHERE pe.id = :puntoId " +
                                "AND a.dataEmissione BETWEEN :dataInizio AND :dataFine", Long.class)
                .setParameter("puntoId", puntoEmissioneId)
                .setParameter("dataInizio", dataInizio)
                .setParameter("dataFine", dataFine)
                .getSingleResult();
    }

    public long countAbbonamentiByPeriodo(LocalDate dataInizio, LocalDate dataFine) {
        return entityManager.createQuery(
                        "SELECT COUNT(a) FROM Abbonamento a WHERE a.dataEmissione BETWEEN :dataInizio AND :dataFine", Long.class)
                .setParameter("dataInizio", dataInizio)
                .setParameter("dataFine", dataFine)
                .getSingleResult();
    }
}