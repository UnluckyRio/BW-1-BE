package dao;

import entities.Tratta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class TrattaDAO {
    private final EntityManager em;

    public TrattaDAO(EntityManager em) {
        this.em = em;
    }

    // Salvataggio

    public void save (Tratta newTratta) {
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(newTratta);
            transaction.commit();
            System.out.println("Nuova Tratta " +
                    newTratta.getId() +
                    " effettuata dal Mezzo " +
                    newTratta.getMezzo() +
                    " con Partenza: " +
                    newTratta.getPartenza() +
                    " ed Arrivo: " +
                    newTratta.getArrivo() +
                    ". \n Il tempo Previsto della tratta è di " +
                    newTratta.getTempoPrevisto() +
                    " ed il tempo Effettivo di percorrenza è stato " +
                    newTratta.getTempoEffettivo());

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Ricerca

    public Tratta findPathById (long id) {
        return em.find(Tratta.class, id);
    }
}
