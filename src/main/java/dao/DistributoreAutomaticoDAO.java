package dao;

import entities.DistributoreAutomatico;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class DistributoreAutomaticoDAO {

    private final EntityManager em;

    public DistributoreAutomaticoDAO(EntityManager em) {
        this.em = em;
    }

    public void save(DistributoreAutomatico newDistributore) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(newDistributore);
        transaction.commit();
   /*     System.out.println("Distributore Automatico salvato con successo - ID Punto Emissione: " +
                newDistributore.getIdPuntoEmissione() + " - Indirizzo: " + newDistributore.getIndirizzo());*/
    }

    public DistributoreAutomatico findById(long id) {
        return em.find(DistributoreAutomatico.class, id);
    }

    public void update(DistributoreAutomatico distributore) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(distributore);
        transaction.commit();
        System.out.println("Distributore aggiornato con successo");
    }

    public void delete(long id) {
        DistributoreAutomatico found = findById(id);
        if (found != null) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.remove(found);
            transaction.commit();
            System.out.println("Distributore eliminato con successo");
        }
    }
}