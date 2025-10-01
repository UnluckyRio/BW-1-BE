package dao;

import entities.Abbonamento;
import entities.Biglietto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class AbbonamentoDAO {


    private final EntityManager entityManager;


    public AbbonamentoDAO(EntityManager entityManager, EntityManager entityManager1){

        this.entityManager = entityManager;
    }


    public void save(Abbonamento newAbbonamento) {
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();

            entityManager.persist(newAbbonamento);

            transaction.commit();

            System.out.println("Biglietto salvato correttamente" + newAbbonamento);


        } catch (Exception e) {if(transaction.isActive()){
            transaction.rollback();
        }
            System.out.println("Errore salvataggio biglietto");
            e.printStackTrace();
        }

    }



    public Abbonamento findById(long Abbonamentoid){
        try {


            Abbonamento trovato = entityManager.find(Abbonamento.class, Abbonamentoid);

            return trovato;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




}
