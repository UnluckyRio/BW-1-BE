package dao;

import entities.Tessera;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class TesseraDAO {

    private final EntityManager em;

    public TesseraDAO(EntityManager em) {
        this.em = em;
    }

    public void create(Tessera tessera) {
        EntityTransaction transition = em.getTransaction();
        try {
            transition.begin();
            em.persist(tessera);
            transition.commit();
        } catch (Exception e) {
            if (transition.isActive()) transition.rollback();
            throw e;
        }
    }

    public Tessera findById(long id) {
        return em.find(Tessera.class, id);
    }

    public Tessera findByUtenteId(long utenteId) {
        return em.createQuery("SELECT t FROM Tessera t WHERE t.utente.id = :utenteId", Tessera.class)
                .setParameter("utenteId", utenteId)
                .getSingleResult();
    }

    public List<Tessera> findAll() {
        return em.createQuery("SELECT t FROM Tessera t", Tessera.class).getResultList();
    }

    public void update(Tessera tessera) {
        EntityTransaction transition = em.getTransaction();
        try {
            transition.begin();
            em.merge(tessera);
            transition.commit();
        } catch (Exception e) {
            if (transition.isActive()) transition.rollback();
            throw e;
        }
    }

    public void delete(Tessera tessera) {
        EntityTransaction transition = em.getTransaction();
        try {
            transition.begin();
            em.remove(em.contains(tessera) ? tessera : em.merge(tessera));
            transition.commit();
        } catch (Exception e) {
            if (transition.isActive()) transition.rollback();
            throw e;
        }
    }
}

