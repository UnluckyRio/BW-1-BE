package dao;

import entities.Tratta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.time.LocalDate;
import java.util.List;

public class TrattaDAO {

    private final EntityManager em;

    public TrattaDAO(EntityManager em) {
        this.em = em;
    }

    public void save (Tratta newTratta) {
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(newTratta);
            transaction.commit();
            System.out.println("Nuova Tratta " +
                    newTratta.getId() +
                    " effettuata dal Mezzo " +
                    newTratta.getMezzo() +
                    " con Partenza: " +
                    newTratta.getPartenza() +
                    " ed Arrivo: " +
                    newTratta.getArrivo() +
                    ". \n Il tempo Previsto della tratta è di " +
                    newTratta.getTempoPrevisto());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Tratta findPathById (long id) {
        return em.find(Tratta.class, id);
    }

    public void update(Tratta tratta) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(tratta);
            transaction.commit();
            System.out.println("Tratta aggiornata");
        } catch (Exception ex) {
            if(transaction.isActive()) transaction.rollback();
            System.out.println(ex.getMessage());
        }
    }

    public void delete(long id) {
        Tratta found = findPathById(id);
        if(found != null) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.remove(found);
            transaction.commit();
            System.out.println("La tratta " + id + " è stata eliminata con successo");
        }
    }

   /* public List<Tratta> findAll() {
        return em.createQuery("SELECT t FROM Tratta t", Tratta.class).getResultList();
    }*/
}
