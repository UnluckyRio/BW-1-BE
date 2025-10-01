package dao;

import entities.PercorrenzaMedia;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class PercorrenzaMediaDAO {
    private final EntityManager em;

    public PercorrenzaMediaDAO(EntityManager em) {
        this.em = em;
    }

    // Salvataggio

    public void save (PercorrenzaMedia newPercorrenzaMedia) {
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(newPercorrenzaMedia);
            transaction.commit();
            System.out.println("La percorrenza media");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Ricerca

    public PercorrenzaMedia findAvaragePathById (long id) {
        return em.find(PercorrenzaMedia.class, id);
    }
}
