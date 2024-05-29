package bd;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import point.Point;

/**
 * Класс предназначен для работы с БД
 * @author maria
 */

@Named
@ApplicationScoped
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

    /**
     * Получение всех точек из БД
     *
     * @return список всех точек
     */
    public List<Point> getAllPoints() {
        try {
            transaction.begin();
            List<Point> points = entityManager.createQuery("SELECT p FROM Point p", Point.class).getResultList();
            transaction.commit();
            return points;
        } catch (RuntimeException exception) {
            System.out.println("error:" + exception.getMessage());
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw exception;
        }
    }

    /**
     * Получение последних number точек из БД
     *
     * @param number количество точек
     * @return список последних точек
     */
    public List<Point> getLastPoints(int number) {
        try {
            transaction.begin();
            List<Point> points = entityManager.createQuery("SELECT p FROM Point p ORDER BY p.id DESC", Point.class)
                    .setMaxResults(number)
                    .getResultList();
            transaction.commit();
            return points;
        } catch (RuntimeException exception) {
            System.out.println("error:" + exception.getMessage());
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw exception;
        }
    }

    /**
     * Получение неудачных точек из БД
     *
     * @return список неудачных точек
     */
    public List<Point> getFailedPoints() {
        try {
            transaction.begin();
            List<Point> failedPoints = entityManager.createQuery("SELECT p FROM Point p WHERE p.status = :status", Point.class)
                    .setParameter("status", false)
                    .getResultList();
            transaction.commit();
            return failedPoints;
        } catch (RuntimeException exception) {
            System.out.println("error:" + exception.getMessage());
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw exception;
        }
    }

}
