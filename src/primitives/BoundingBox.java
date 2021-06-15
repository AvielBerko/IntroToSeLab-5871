package primitives;

public class BoundingBox {
    private final Point3D _min, _max;

    public BoundingBox(Point3D min, Point3D max) {
        if (min.getX() > max.getX() ||
            min.getY() > max.getY() ||
            min.getZ() > max.getZ()) {
            throw new IllegalArgumentException("min and max are not edge values");
        }

        _min = min;
        _max = max;
    }

    public boolean isIntersecting(Ray ray, double maxDistance) {
        Point3D origin = ray.getP0();
        Vector dir = ray.getDir();

        T tx = calculateTa(_min.getX(), _max.getX(), origin.getX(), dir.getX());
        T ty = calculateTa(_min.getY(), _max.getY(), origin.getY(), dir.getY());
        T tz = calculateTa(_min.getZ(), _max.getZ(), origin.getZ(), dir.getZ());

        boolean isIntersecting = tx.min <= ty.max && tx.min <= tz.max &&
                                 ty.min <= tx.max && ty.min <= tz.max &&
                                 tz.min <= tx.max && tz.min <= ty.max;

        double minSquaredDistance = tx.min * tx.min + ty.min * ty.min + tz.min * tz.min;
        boolean inDistance = maxDistance * maxDistance >= minSquaredDistance;

        return isIntersecting && inDistance;
    }

    private T calculateTa(double aMin, double aMax, double aOrigin, double aDir) {
        T ta = new T();
        double a = 1 / aDir;

        if (a >= 0) {
            ta.min = (aMin - aOrigin) / a;
            ta.max = (aMax - aOrigin) / a;
        } else {
            ta.min = (aMax - aOrigin) / a;
            ta.max = (aMin - aOrigin) / a;
        }

        return ta;
    }

    private static class T {
        public double min, max;
    }
}
