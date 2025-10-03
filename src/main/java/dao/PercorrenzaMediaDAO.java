package dao;

import entities.PercorrenzaMedia;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.time.LocalTime;
import java.util.List;

public class PercorrenzaMediaDAO {

    private final EntityManager em;

    public PercorrenzaMediaDAO(EntityManager em) {
        this.em = em;
    }

    public void save(PercorrenzaMedia newPercorrenzaMedia) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(newPercorrenzaMedia);
        transaction.commit();
       /* System.out.println("La percorrenza media salvata con successo");*/
    }

    public PercorrenzaMedia findAvaragePathById(long id) {
        return em.find(PercorrenzaMedia.class, id);
    }

    public void update(PercorrenzaMedia percorrenza) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(percorrenza);
        transaction.commit();
        System.out.println("Percorrenza media aggiornata");
    }

    public void delete(long id) {
        PercorrenzaMedia found = findAvaragePathById(id);
        if (found != null) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.remove(found);
            transaction.commit();
            System.out.println("La percorrenza media " + id + " è stata eliminata con successo");
        }
    }

    public List<PercorrenzaMedia> getAllPercorrenzeMedie() {
        return em.createQuery("SELECT pm FROM PercorrenzaMedia pm", PercorrenzaMedia.class)
                .getResultList();
    }

    public LocalTime calcolaTempoMedioEffettivo(Long trattaId) {
        List<PercorrenzaMedia> percorrenze = em.createQuery(
                        "SELECT pm FROM PercorrenzaMedia pm WHERE pm.tratta.id = :trattaId", PercorrenzaMedia.class)
                .setParameter("trattaId", trattaId)
                .getResultList();

        if (percorrenze.isEmpty()) {
            return null;
        }

        long totalSecondi = 0;
        for (PercorrenzaMedia pm : percorrenze) {
            totalSecondi += pm.getTempoEffettivo().toSecondOfDay();
        }

        long averageSeconds = totalSecondi / percorrenze.size();
        return LocalTime.ofSecondOfDay(averageSeconds);
    }
}