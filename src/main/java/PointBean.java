import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.inject.Model;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс предназначен для управления операциями и состоянием, связанными с точками
 * @author maria
 */

@Setter
@Getter
@Named("pointBean")
@Model
@SessionScoped
public class PointBean implements Serializable {
    private Point point;
    private final PointsBDManager pointsBDManager;
    private List<Point> pointsList;
    private int timezone;
    private Point lastPoint;
    private String pointsJson;

    public PointBean() {
        point = new Point();
        pointsBDManager = new PointsBDManager();
        pointsList = Collections.synchronizedList(pointsBDManager.getFromBD());
    }

    /**
     * Добавлние точки
     */
    public synchronized void add(){
        System.out.println("add");
        long timer = System.nanoTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String currentTime = formatter.format(LocalDateTime.now().minusMinutes(getTimezone()));

        point.setStatus(AreaChecker.isHit(point));
        point.setTime(currentTime);
        point.setScriptTime((long) ((System.nanoTime() - timer) * 0.001));

        pointsList.add(point);
        pointsBDManager.addToBD(point);

        lastPoint = point;
        point = new Point();
        point.setX(lastPoint.getX());
        point.setY(lastPoint.getY());
        point.setR(lastPoint.getR());

    }

    /**
     * Удаление всех точек
     */
    public String clear(){
        System.out.println("clear");
        pointsList = Collections.synchronizedList(new LinkedList<>());
        pointsBDManager.clearBD();
        return null;
    }

    /**
     * Получение листа со значениями точек
     */
    public synchronized List<Point> getPointsList() {
        return pointsList;
    }
    public String getPointsJson() {
        return pointsList.stream()
                .map(Point::toJSON)
                .toList()
                .toString();
    }

}

