package mbeans;

import bd.PointsBDManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import point.Point;

import java.io.Serializable;
import java.util.List;

@Named
@ApplicationScoped
public class Area implements Serializable, AreaMBean{

    private final PointsBDManager insertRepository = new PointsBDManager();

    @Override
    public double getAreaSize() {
        float r = 1;
        List<Point> points = insertRepository.getLastPoints(1);
        if (!points.isEmpty()) {
            r = points.get(0).getR();
        }
        return (r * r + Math.pow(r / 2, 2) / 2 + (Math.PI * Math.pow(r / 2, 2) / 4));
    }
}
