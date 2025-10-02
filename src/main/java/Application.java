import dao.*;
import entities.*;
import enums.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


public class Application {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("bw1be");
    private static EntityManager em = emf.createEntityManager();

}
