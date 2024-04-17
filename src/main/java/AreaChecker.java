/**
 * Класс предназначен для проверки попадания точки в область
 * @author maria
 */

public class AreaChecker {

    /**
     * Метод для проверки на попадание в область
     */
    public static boolean isHit(double x, double y, double r) {
//        double x = point.getX();
//        double y = point.getY();
//        double r = point.getR();


        return (isCircleZone(x, y, r) || isTriangleZone(x, y, r) || isRectangleZone(x, y, r));
    }

    /**
     * Произошло ли попадание в прямоугольника
     */
    private static boolean isRectangleZone(double x, double y, double r) {
        return x >= -r && x <= 0 && y >= -r / 2 && y <= 0;

    }

    /**
     * Произошло ли попадание в треугольник
     */
    private static boolean isTriangleZone(double x, double y, double r) {
        return y <= x / 2 + r / 2 && y >= 0 && x <= 0;
    }

    /**
     * Произошло ли попадание в круг
     */
    private static boolean isCircleZone(double x, double y, double r) {
        return x * x + y * y <= (r / 2) * (r / 2) && x >= 0 && y <= 0;
    }
}
