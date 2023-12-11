import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

/**
 * Класс предназначен для работы с БД
 * @author maria
 */

@Named
@SessionScoped
public class PointsBDManager implements Serializable {

    private EntityManager entityManager;
    private EntityTransaction transaction;
    private static final String PERSISTENCE_UNIT_NAME = "WebLab";

    public PointsBDManager()
    {
        connection();
        loadPoints();
    }
    private void connection(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        entityManager = entityManagerFactory.createEntityManager();
        transaction=entityManager.getTransaction();
    }
    public List loadPoints() {
        try {
            transaction.begin();
            Query query = entityManager.createQuery("select points from Point points");
            transaction.commit();
            return query.getResultList();
        } catch (RuntimeException exception) {
            System.out.println("error:" + exception.getMessage());
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw exception;
        }
    }

    public void savePoint(Point point){
        try {
            transaction.begin();
            entityManager.persist(point);
            transaction.commit();
        }catch (RuntimeException exception) {
            System.out.println("error:" + exception.getMessage());
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public void clearPoints() {
        try {
            transaction.begin();
            Query query = entityManager.createQuery("delete from Point");
            query.executeUpdate();
            transaction.commit();
        }catch (RuntimeException exception){
            System.out.println("error:" + exception.getMessage());
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }
}
