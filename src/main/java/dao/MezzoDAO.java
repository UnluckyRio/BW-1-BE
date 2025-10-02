package dao;

import entities.Mezzo;
import enums.StatoMezzo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.time.LocalDate;
import java.util.List;

public class MezzoDAO {

    private final EntityManager em;

    public MezzoDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Mezzo newMezzo) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(newMezzo);
            transaction.commit();
            System.out.println("Mezzo " + newMezzo + " salvato con successo");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Errore durante il salvataggio del mezzo");
            e.printStackTrace();
        }
    }

    public Mezzo findById(long id) {
        return em.find(Mezzo.class, id);
    }

    public void update(Mezzo mezzo) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(mezzo);
            transaction.commit();
            System.out.println("Mezzo aggiornato con successo");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Errore durante l'aggiornamento del mezzo");
            e.printStackTrace();
        }
    }

    public void delete(long id) {
        Mezzo found = findById(id);
        if (found != null) {
            EntityTransaction transaction = em.getTransaction();
            try {
                transaction.begin();
                em.remove(found);
                transaction.commit();
                System.out.println("Mezzo eliminato con successo");
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                System.out.println("Errore durante l'eliminazione del mezzo");
                e.printStackTrace();
            }
        }
    }

    public List<Mezzo> findByStato(StatoMezzo stato) {
        return em.createQuery("SELECT m FROM Mezzo m WHERE m.statoMezzo = :stato", Mezzo.class)
                .setParameter("stato", stato)
                .getResultList();
    }

    public boolean isInManutenzione(long mezzoId, LocalDate data) {
        Long count = em.createQuery(
                        "SELECT COUNT(man) FROM Manutenzione man WHERE man.mezzo.id = :mezzoId " +
                                "AND :data BETWEEN man.dataInizio AND man.dataFine", Long.class)
                .setParameter("mezzoId", mezzoId)
                .setParameter("data", data)
                .getSingleResult();
        return count > 0;
    }
}
