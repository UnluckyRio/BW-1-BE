package dao;

import entities.Abbonamento;
import entities.Tessera;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import java.time.LocalDate;
import java.util.List;

public class TesseraDAO {

    private final EntityManager em;

    public TesseraDAO(EntityManager em) {
        this.em = em;
    }

    public void create(Tessera tessera) {
        EntityTransaction transition = em.getTransaction();
        transition.begin();
        em.persist(tessera);
        transition.commit();
    }

    public Tessera findById(long id) {
        return em.find(Tessera.class, id);
    }

    public Tessera findByUtenteId(long utenteId) {
        return em.createQuery("SELECT t FROM Tessera t WHERE t.utente.id = :utenteId", Tessera.class)
                .setParameter("utenteId", utenteId)
                .getSingleResult();
    }

    public List<Tessera> findAll() {
        return em.createQuery("SELECT t FROM Tessera t", Tessera.class)
                .getResultList();
    }

    public void update(Tessera tessera) {
        EntityTransaction transition = em.getTransaction();
        transition.begin();
        em.merge(tessera);
        transition.commit();
    }

    public void delete(Tessera tessera) {
        EntityTransaction transition = em.getTransaction();
        transition.begin();
        em.remove(em.contains(tessera) ? tessera : em.merge(tessera));
        transition.commit();
    }

    public boolean verificaValiditaAbbonamento(long tesseraId) {
        Long count = em.createQuery(
                        "SELECT COUNT(a) FROM Abbonamento a WHERE a.tessera.id = :tesseraId " +
                                "AND :oggi BETWEEN a.datainiziovalidita AND a.datafinevalidita", Long.class)
                .setParameter("tesseraId", tesseraId)
                .setParameter("oggi", LocalDate.now())
                .getSingleResult();
        return count > 0;
    }

    public Abbonamento findAbbonamentoValidoByTessera(long tesseraId) {
        try {
            return em.createQuery(
                            "SELECT a FROM Abbonamento a WHERE a.tessera.id = :tesseraId " +
                                    "AND :oggi BETWEEN a.datainiziovalidita AND a.datafinevalidita", Abbonamento.class)
                    .setParameter("tesseraId", tesseraId)
                    .setParameter("oggi", LocalDate.now())
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
