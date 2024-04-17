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
        getFromBD();
    }

    /**
     * Соединение с базой данных
     */
    private void connection(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        entityManager = entityManagerFactory.createEntityManager();
        transaction=entityManager.getTransaction();
    }

    /**
     * Получение точек из БД
     */
    public List getFromBD() {
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

    /**
     * Сохранение точки в БД
     */
    public void addToBD(Point point){
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

    /**
     * Удаление всех точек из БД
     */
    public void clearBD() {
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
