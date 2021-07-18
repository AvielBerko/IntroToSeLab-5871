package primitives;

import java.util.Arrays;
import java.util.Objects;

/**
 * AABB - Bounding box.
 * Surrounding an object (or a set of objects).
 * Finds intersections faster, but has more false positive values.
 * Used to make the rendering of an image faster.
 */
public class BoundingBox {
    /**
     * The bounding box is constructed by 2 points - min and max.
     * The line between them sets the diagonal of the box
     */
    private final Point3D _min, _max;

    /**
     * Bounding Box constructor, builds the BB by 2 points
     * @param min The min point
     * @param max The max point
     */
    public BoundingBox(Point3D min, Point3D max) {
        // If the values are not consistent
        if (min.getX() > max.getX() ||
            min.getY() > max.getY() ||
            min.getZ() > max.getZ()) {
            throw new IllegalArgumentException("min and max are not edge values");
        }

        _min = min;
        _max = max;
    }

    /**
     * Gets the min point of the bounding box
     * @return The min point
     */
    public Point3D getMin() {
        return _min;
    }
    /**
     * Gets the max point of the bounding box
     * @return The max point
     */
    public Point3D getMax() {
        return _max;
    }

    /**
     * Gets the center point of the bounding box
     * @return The center point
     */
    public Point3D getCenter() {
        return Point3D.getCenter(_min, _max);
    }

    /**
     * Returns true if the given ray is intersecting with the bounding box
     * and false otherwise.
     * @param ray The ray to check intersections with the bounding box
     * @return Whether the ray is intersecting with the bounding box or not
     */
    public boolean isIntersecting(Ray ray) {
        Point3D origin = ray.getP0();
        Vector dir = ray.getDir();

        T tx = calculateTa(_min.getX(), _max.getX(), origin.getX(), dir.getX());
        T ty = calculateTa(_min.getY(), _max.getY(), origin.getY(), dir.getY());
        T tz = calculateTa(_min.getZ(), _max.getZ(), origin.getZ(), dir.getZ());

        return tx.min <= ty.max && tx.min <= tz.max &&
               ty.min <= tx.max && ty.min <= tz.max &&
               tz.min <= tx.max && tz.min <= ty.max;
    }

    /**
     * Source: Fundamentals of Computer Graphics Fourth Edition, Page 299.
     * Calculates the min and max value for a given axis
     * @param aMin The a axis's min point value
     * @param aMax The a axis's max point value
     * @param aOrigin The a axis's rays origin point value
     * @param aDir The a axis's rays direction value
     * @return The (min, max) value of the intersection
     */
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

    /**
     * Helper class that saves 2 double values of min and max
     */
    private static class T {
        public double min, max;
    }

    /**
     * Surrounds all given bounding boxes with a bounding box
     * @param boundingBoxes The list of bounding boxes to surround
     * @return The surrounding bounding box
     */
    public static BoundingBox surround(BoundingBox... boundingBoxes) {
        // If the list is empty
        if (boundingBoxes.length == 0) {
            return null;
        }

        // Removes from the list all null bounding boxes
        BoundingBox[] notNull =
                Arrays.stream(boundingBoxes)
                .filter(Objects::nonNull)
                .toArray(BoundingBox[]::new);

        // Makes sure that not all bounding boxes are null
        if (notNull.length == 0) {
            return null;
        }

        // Generates the surround bounding box
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
