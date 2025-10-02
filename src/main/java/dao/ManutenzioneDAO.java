package dao;

import entities.Manutenzione;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ManutenzioneDAO {
    private final EntityManager em;
    public ManutenzioneDAO(EntityManager em) {
        this.em =em;
    }

    // Salvataggio

    public void save (Manutenzione newManutenzione){
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(newManutenzione);
            transaction.commit();
            System.out.println("Nuova Manutenzione ID: " +
                    newManutenzione.getIdManutenzione() +
                    ", del Mezzo " +
                    newManutenzione.getMezzo() +
                    " creata.");
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    // Ricerca

    public Manutenzione findMaintenanceById (long idManutenzione) {
        return em.find(Manutenzione.class, idManutenzione);
    }
}
