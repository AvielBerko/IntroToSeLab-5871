package primitives;

import java.util.Arrays;
import java.util.function.Function;

/**
 * A 3D point in space. The represented point is using 3 coordinate.
 */
public class Point3D {
    /**
     * A point with the coordinates (0, 0, 0)
     */
    public static final Point3D ZERO = new Point3D(0d, 0d, 0d);

    final Coordinate _x;
    final Coordinate _y;
    final Coordinate _z;

    /**
     * Creates a new point with a given coordinates.
     *
     * @param x the coordinate for the X axis.
     * @param y the coordinate for the Y axis.
     * @param z the coordinate for the Z axis.
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        _x = x;
        _y = y;
        _z = z;
    }

    /**
     * Main constructor.
     * Creates a new point from a given coordinates.
     *
     * @param x the coordinate for the X axis.
     * @param y the coordinate for the Y axis.
     * @param z the coordinate for the Z axis.
     */
    public Point3D(double x, double y, double z) {
        _x = new Coordinate(x);
        _y = new Coordinate(y);
        _z = new Coordinate(z);
    }

    /**
     * Returns the X axis coordinate.
     *
     * @return A shallow copy of the X axis coordinate.
     */
    public double getX() {
        return _x._coord;
    }

    /**
     * Returns the Y axis coordinate.
     *
     * @return A shallow copy of the Y axis coordinate.
     */
    public double getY() {
        return _y._coord;
    }

    /**
     * Returns the Z axis coordinate.
     *
     * @return A shallow copy of the Z axis coordinate.
     */
    public double getZ() {
        return _z._coord;
    }

    public double get(Axis axis) {
        return switch (axis) {
            case X -> _x._coord;
            case Y -> _y._coord;
            case Z -> _z._coord;
        };
    }

    /**
     * Adds a vector to the current point.
     *
     * @param vector The vector to add.
     * @return The created point with the addition.
     */
    public Point3D add(Vector vector) {
        Point3D vectorHead = vector.getHead();
        return new Point3D(
                _x._coord + vectorHead._x._coord,
                _y._coord + vectorHead._y._coord,
                _z._coord + vectorHead._z._coord
        );
    }

    /**
     * Vector subtraction - Creates a vector from the given point to the current point.
     *
     * @param point The origin point of the vector
     * @return The created vector from the given point to the current point.
     */
    public Vector subtract(Point3D point) {
        return new Vector(
                _x._coord - point._x._coord,
                _y._coord - point._y._coord,
                _z._coord - point._z._coord
        );
    }

    /**
     * Calculates the squared distance between the current point and the given point.
     *
     * @param point The point to calculate the distance to.
     * @return The calculated squared distance.
     */
    public double distanceSquared(Point3D point) {
        double x = _x._coord - point._x._coord;
        double y = _y._coord - point._y._coord;
        double z = _z._coord - point._z._coord;

        return x * x + y * y + z * z;
    }

    /**
     * Calculates the distance between the current point and the given point.
     *
     * @param point The point to calculate the distance to.
     * @return The calculated distance.
     */
    public double distance(Point3D point) {
        return Math.sqrt(distanceSquared(point));
    }

    /**
     * Finds the point with the minimum value in a given axis and returns it's coordinate on that axis
     * @param axis The axis to find the minimum value in
     * @param points The list of point to find a minimum from
     * @return The value of the coordinate with the minimum value in the given axis
     */
    public static double getMinByAxis(Axis axis, Point3D... points) {
        if (points.length == 0) {
            return Double.NaN;
        }

        return Arrays.stream(points)
                .map(point -> point.get(axis))
                .min(Double::compare)
                .get();
    }

    /**
     * Finds the point with the maximum value in a given axis and returns it's coordinate on that axis
     * @param axis The axis to find the maximum value in
     * @param points The list of point to find a maximum from
     * @return The value of the coordinate with the maximum value in the given axis
     */
    public static double getMaxByAxis(Axis axis, Point3D... points) {
        if (points.length == 0) {
            return Double.NaN;
        }

        return Arrays.stream(points)
                .map(point -> point.get(axis))
                .max(Double::compare)
                .get();
    }

    /**
     * Gets a center point between 2 given points
     * @param a The first point
     * @param b The second point
     * @return The middle point between the 2 given points
     */
    public static Point3D getCenter(Point3D a, Point3D b) {
        double x = (a.getX() + b.getX()) / 2;
        double y = (a.getY() + b.getY()) / 2;
        double z = (a.getZ() + b.getZ()) / 2;
        return new Point3D(x, y, z);
    }

    @Override
    public String toString() {
        return "(" + _x + ", " + _y + ", " + _z + ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return _x.equals(point3D._x) && _y.equals(point3D._y) && _z.equals(point3D._z);
    }
}
