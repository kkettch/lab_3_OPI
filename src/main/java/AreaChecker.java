public class AreaChecker {
    public static boolean isHit(Point point) {
        double x = point.getX();
        double y = point.getY();
        double r = point.getR();

        return (isCircleZone(x, y, r) || isTriangleZone(x, y, r) || isRectangleZone(x, y, r));
    }

    private static boolean isRectangleZone(double x, double y, double r) {
        return x >= -r && x <= 0 && y >= -r / 2 && y <= 0;
    }

    private static boolean isTriangleZone(double x, double y, double r) {
        return y <= x / 2 + r / 2 && y >= 0 && x <= 0;
    }

    private static boolean isCircleZone(double x, double y, double r) {
        return x * x + y * y <= (r / 2) * (r / 2) && x >= 0 && y <= 0;
    }
}
