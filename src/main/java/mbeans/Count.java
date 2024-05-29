package mbeans;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import java.io.Serializable;
import java.util.List;

import bd.PointsBDManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import point.Point;

@Named
@ApplicationScoped
public class Count extends NotificationBroadcasterSupport implements CountMBean, Serializable {

    private final PointsBDManager pointsBDManager = new PointsBDManager();
    private long sequenceNumber = 0;

    // Возвращает общее количество точек
    @Override
    public long getNumberOfAllPoints() {
        List<Point> points = pointsBDManager.getAllPoints();
        if (isLastNPointsFailed(3))
            sendNotification(new Notification("Три промаха подряд", this.getClass().getName(), sequenceNumber++, "Было совершено три промаха подряд"));
        return points.size();
    }

    // Возвращает количество промахов
    @Override
    public long getNumberOfFailedPoints() {
        List<Point> failedPoints = pointsBDManager.getFailedPoints();
        return failedPoints.size();
    }

    // Проверяет, были ли последние N точек промахами
    @Override
    public boolean isLastNPointsFailed(int n) {
        List<Point> lastPoints = pointsBDManager.getLastPoints(n);
        for (Point point : lastPoints) {
            if (point.getStatus().equals("Hit!")) { // Assuming isStatus() returns true for "Hit!" and false for "Miss!"
                return false;
            }
        }
        return true;
    }
}
