package dao;

import entities.DistributoreAutomatico;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class DistributoreAutomaticoDAO {
    private final EntityManager em;


    public DistributoreAutomaticoDAO(EntityManager em) {
        this.em = em;
    }

    public void save(DistributoreAutomatico newdistributoreAutomatico){
        EntityTransaction transaction=em.getTransaction();
        try{
            transaction.begin();
            em.persist(newdistributoreAutomatico);
            transaction.commit();
            System.out.println("Erogazione" + newdistributoreAutomatico + "avvenuta con successo");
        }catch (Exception e){
            if(transaction.isActive()){
                transaction.rollback();
            }
            System.out.println("Errore erogazione");
            e.printStackTrace();
        }

    }
}
