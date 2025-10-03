package dao;

import entities.Rivenditore;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class RivenditoreDAO {

    private final EntityManager em;

    public RivenditoreDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Rivenditore newRivenditore) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(newRivenditore);
        transaction.commit();
  /*      System.out.println("Rivenditore salvato con successo - ID Punto Emissione: " +
                newRivenditore.getIdPuntoEmissione() + " - Nome: " + newRivenditore.getNomeRivenditore() +
                " - Indirizzo: " + newRivenditore.getIndirizzo());*/
    }

    public Rivenditore findById(long id) {
        return em.find(Rivenditore.class, id);
    }

    public void update(Rivenditore rivenditore) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(rivenditore);
        transaction.commit();
        System.out.println("Rivenditore aggiornato con successo");
    }

    public void delete(long id) {
        Rivenditore found = findById(id);
        if (found != null) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.remove(found);
            transaction.commit();
            System.out.println("Rivenditore eliminato con successo");
        }
    }
}