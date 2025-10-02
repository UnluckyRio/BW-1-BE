package dao;

import entities.PercorrenzaMedia;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;


public class PercorrenzaMediaDAO {

    private final EntityManager em;

    public PercorrenzaMediaDAO(EntityManager em) {
        this.em = em;
    }

    public void save (PercorrenzaMedia newPercorrenzaMedia) {
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(newPercorrenzaMedia);
            transaction.commit();
            System.out.println("La percorrenza media salvata con successo");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public PercorrenzaMedia findAvaragePathById (long id) {
        return em.find(PercorrenzaMedia.class, id);
    }

    public void update(PercorrenzaMedia percorrenza) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(percorrenza);
            transaction.commit();
            System.out.println("Percorrenza media aggiornata");
        } catch (Exception ex) {
            if(transaction.isActive()) transaction.rollback();
            System.out.println(ex.getMessage());
        }
    }

    public void delete(long id) {
        PercorrenzaMedia found = findAvaragePathById(id);
        if(found != null) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.remove(found);
            transaction.commit();
            System.out.println("La percorrenza media " + id + " è stata eliminata con successo");
        }
    }

    /*public List<PercorrenzaMedia> findAll() {
        return em.createQuery("SELECT p FROM PercorrenzaMedia p", PercorrenzaMedia.class).getResultList();
    }*/
}
