package dao;

import entities.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class UtenteDAO {

    private final EntityManager em;

    public UtenteDAO(EntityManager em) {
        this.em = em;
    }

    public void create(Utente utente) {
        EntityTransaction transition = em.getTransaction();
        try {
            transition.begin();
            em.persist(utente);
            transition.commit();
        } catch (Exception e) {
            if (transition.isActive()) transition.rollback();
            throw e;
        }
    }

    public Utente findById(long id) {
        return em.find(Utente.class, id);
    }

    public List<Utente> findAll() {
        return em.createQuery("SELECT u FROM Utente u", Utente.class).getResultList();
    }

    public void update(Utente utente) {
        EntityTransaction transition = em.getTransaction();
        try {
            transition.begin();
            em.merge(utente);
            transition.commit();
        } catch (Exception e) {
            if (transition.isActive()) transition.rollback();
            throw e;
        }
    }

    public void delete(Utente utente) {
        EntityTransaction transition = em.getTransaction();
        try {
            transition.begin();
            em.remove(em.contains(utente) ? utente : em.merge(utente));
            transition.commit();
        } catch (Exception e) {
            if (transition.isActive()) transition.rollback();
            throw e;
        }
    }
}
