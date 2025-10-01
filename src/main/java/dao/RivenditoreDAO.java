package dao;

import entities.Rivenditore;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class RivenditoreDAO {
    private final EntityManager em;

    public RivenditoreDAO(EntityManager em){
        this.em=em;
    }
    public void save(Rivenditore newrivenditore){
        EntityTransaction transaction=em.getTransaction();
        try{
            transaction.begin();
            em.persist(newrivenditore);
            transaction.commit();
            System.out.println("Rivenditore" + newrivenditore + "ha venduto il bilgietto");
        }catch (Exception e){
            if(transaction.isActive()){
                transaction.rollback();
            }
            System.out.println("Errore durante la vendita");
            e.printStackTrace();
        }
    }
}
