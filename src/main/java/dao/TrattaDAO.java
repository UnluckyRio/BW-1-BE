package dao;

import entities.Tratta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class TrattaDAO {

    private final EntityManager em;

    public TrattaDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Tratta newTratta) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(newTratta);
        transaction.commit();
 /*       System.out.println("Nuova Tratta " +
                newTratta.getId() +
                " effettuata dal Mezzo " +
                newTratta.getMezzo() +
                " con Partenza: " +
                newTratta.getPartenza() +
                " ed Arrivo: " +
                newTratta.getArrivo() +
                ". \n Il tempo Previsto della tratta è di " +
                newTratta.getTempoPrevisto());*/
    }

    public Tratta findPathById(long id) {
        return em.find(Tratta.class, id);
    }

    public void update(Tratta tratta) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(tratta);
        transaction.commit();
        System.out.println("Tratta aggiornata");
    }

    public void delete(long id) {
        Tratta found = findPathById(id);
        if (found != null) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.remove(found);
            transaction.commit();
            System.out.println("La tratta " + id + " è stata eliminata con successo");
        }
    }
    public List<Long> getMezziIdPerTratta(String partenza, String arrivo) {
        return em.createQuery(
                        "SELECT t.mezzo.id FROM Tratta t WHERE t.partenza = :partenza AND t.arrivo = :arrivo",
                        Long.class)
                .setParameter("partenza", partenza)
                .setParameter("arrivo", arrivo)
                .getResultList();
    }
    public long contaPercorrenzePerTratta(String partenza, String arrivo) {
        return em.createQuery(
                        "SELECT COUNT(t) FROM Tratta t WHERE t.partenza = :partenza AND t.arrivo = :arrivo", Long.class)
                .setParameter("partenza", partenza)
                .setParameter("arrivo", arrivo)
                .getSingleResult();
    }
}