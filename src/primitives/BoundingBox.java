package primitives;

import geometries.Intersectable;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

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

    public Point3D getMin() {
        return _min;
    }

    public Point3D getMax() {
        return _max;
    }

    public Point3D getCenter() {
        return Point3D.getCenter(_min, _max);
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

        return isIntersecting /* && inDistance*/;
    }

    private T calculateTa(double aMin, double aMax, double aOrigin, double aDir) {
        T ta = new T();
        double a = 1 / aDir;

        if (a >= 0) {
            ta.min = (aMin - aOrigin) * a;
            ta.max = (aMax - aOrigin) * a;
        } else {
            ta.min = (aMax - aOrigin) * a;
            ta.max = (aMin - aOrigin) * a;
        }

        return ta;
    }

    private static class T {
        public double min, max;
    }

    public static BoundingBox surround(BoundingBox... boundingBoxes) {
        if (boundingBoxes.length == 0) {
            return null;
        }

        BoundingBox[] notNull =
                Arrays.stream(boundingBoxes)
                .filter(Objects::nonNull)
                .toArray(BoundingBox[]::new);
        // Makes sure that not all bounding boxes are null
        if (notNull.length == 0) {
            return null;
        }

        return new BoundingBox(
                new Point3D(
                        Point3D.getMinByAxis(Axis.X,
                                Arrays.stream(notNull)
                                        .map(BoundingBox::getMin)
                                       .toArray(Point3D[]::new)),
                        Point3D.getMinByAxis(Axis.Y,
                                Arrays.stream(notNull)
                                        .map(BoundingBox::getMin)
                                       .toArray(Point3D[]::new)),
                        Point3D.getMinByAxis(Axis.Z,
                                Arrays.stream(notNull)
                                        .map(BoundingBox::getMin)
                                       .toArray(Point3D[]::new))
                ),
                new Point3D(
                        Point3D.getMaxByAxis(Axis.X,
                                Arrays.stream(notNull)
                                        .map(BoundingBox::getMax)
                                       .toArray(Point3D[]::new)),
                        Point3D.getMaxByAxis(Axis.Y,
                                Arrays.stream(notNull)
                                        .map(BoundingBox::getMax)
                                       .toArray(Point3D[]::new)),
                        Point3D.getMaxByAxis(Axis.Z,
                                Arrays.stream(notNull)
                                        .map(BoundingBox::getMax)
                                       .toArray(Point3D[]::new))
                )
        );
    }
}
