package dao;

import entities.Mezzo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class MezzoDAO {
    private final EntityManager em;

    public MezzoDAO(EntityManager em) {
        this.em = em;
    }

    // Metodo per salvare un nuovo mezzo
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

    // Metodo per trovare un mezzo per ID
    public Mezzo findById(long id) {
        return em.find(Mezzo.class, id);
    }
}
