package dao;

import entities.Manutenzione;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class ManutenzioneDAO {

    private final EntityManager em;

    public ManutenzioneDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Manutenzione newManutenzione) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(newManutenzione);
        transaction.commit();
     /*   System.out.println("Nuova Manutenzione ID: " +
                newManutenzione.getIdManutenzione() +
                ", del Mezzo " +
                newManutenzione.getMezzo() +
                " creata.");*/
    }

    public Manutenzione findMaintenanceById(long idManutenzione) {
        return em.find(Manutenzione.class, idManutenzione);
    }

    public void update(Manutenzione manutenzione) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(manutenzione);
        transaction.commit();
        System.out.println("Manutenzione aggiornata");
    }

    public void delete(long id) {
        Manutenzione found = findMaintenanceById(id);
        if (found != null) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.remove(found);
            transaction.commit();
            System.out.println("La manutenzione " + id + " è stata eliminata con successo");
        }
    }

    public List<Manutenzione> findByMezzo(long mezzoId) {
        return em.createQuery(
                        "SELECT m FROM Manutenzione m WHERE m.mezzo.id = :mezzoId ORDER BY m.dataInizio DESC", Manutenzione.class)
                .setParameter("mezzoId", mezzoId)
                .getResultList();
    }
}