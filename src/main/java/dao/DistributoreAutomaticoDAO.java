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
        try {
            transaction.begin();
            em.persist(newDistributore);
            transaction.commit();
            System.out.println("Distributore Automatico salvato con successo - ID Punto Emissione: " +
                    newDistributore.getIdPuntoEmissione() + " - Indirizzo: " + newDistributore.getIndirizzo());
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Errore durante il salvataggio del distributore");
            e.printStackTrace();
        }
    }

    public DistributoreAutomatico findById(long id) {
        return em.find(DistributoreAutomatico.class, id);
    }

    public void update(DistributoreAutomatico distributore) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(distributore);
            transaction.commit();
            System.out.println("Distributore aggiornato con successo");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Errore durante l'aggiornamento");
            e.printStackTrace();
        }
    }

    public void delete(long id) {
        DistributoreAutomatico found = findById(id);
        if (found != null) {
            EntityTransaction transaction = em.getTransaction();
            try {
                transaction.begin();
                em.remove(found);
                transaction.commit();
                System.out.println("Distributore eliminato con successo");
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                System.out.println("Errore durante l'eliminazione");
                e.printStackTrace();
            }
        }
    }
}
