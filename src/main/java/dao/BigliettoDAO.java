package dao;

import entities.Biglietto;
import exceptions.InvalidDataException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.Transaction;

public class BigliettoDAO {


    private final EntityManager entityManager;


    public BigliettoDAO(EntityManager entityManager, EntityManager entityManager1){

        this.entityManager = entityManager;
    }


    public void save(Biglietto newBiglietto) {
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();

            entityManager.persist(newBiglietto);

        transaction.commit();

        System.out.println("Biglietto salvato correttamente" + newBiglietto);


        } catch (Exception e) {if(transaction.isActive()){
            transaction.rollback();
        }
            System.out.println("Errore salvataggio biglietto");
            e.printStackTrace();
        }

    }



    public Biglietto findById(long bigliettoid){
     try {


         Biglietto trovato = entityManager.find(Biglietto.class, bigliettoid);

         return trovato;
     } catch (Exception e) {
         throw new RuntimeException(e);
     }
    }




}
