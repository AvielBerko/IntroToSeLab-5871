package primitives;

import java.util.Objects;

/**
 * A 3D point in space. The represented point is using 3 coordinate.
 */
public class Point3D {
    /**
     * A point with the coordinates (0, 0, 0)
     */
    public static final Point3D ZERO = new Point3D(0d, 0d, 0d);

    private final Coordinate _x;
    private final Coordinate _y;
    private final Coordinate _z;

    /**
     * Creates a new point with a given coordinates.
     *
     * @param x the coordinate for the X axis.
     * @param y the coordinate for the Y axis.
     * @param z the coordinate for the Z axis.
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        // this(x.coord, y.coord, z.coord);

        // For performance improvement.
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
        // return new Coordinate(_x);

        // For performance improvement.
        return _x.coord;
    }

    /**
     * Returns the Y axis coordinate.
     *
     * @return A shallow copy of the Y axis coordinate.
     */
    public double getY() {
        // return new Coordinate(_y);

        // For performance improvement.
        return _y.coord;
    }

    /**
     * Returns the Z axis coordinate.
     *
     * @return A shallow copy of the Z axis coordinate.
     */
    public double getZ() {
        // return new Coordinate(_z);

        // For performance improvement.
        return _z.coord;
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
                _x.coord + vectorHead._x.coord,
                _y.coord + vectorHead._y.coord,
                _z.coord + vectorHead._z.coord
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
                _x.coord - point._x.coord,
                _y.coord - point._y.coord,
                _z.coord - point._z.coord
        );
    }

    /**
     * Calculates the squared distance between the current point and the given point.
     * @param point The point to calculate the distance to.
     * @return The calculated squared distance.
     */
    public double distanceSquared(Point3D point) {
        double x = _x.coord - point._x.coord;
        double y = _y.coord - point._y.coord;
        double z = _z.coord - point._z.coord;

        return x*x + y*y + z*z;
    }

    /**
     * Calculates the distance between the current point and the given point.
     * @param point The point to calculate the distance to.
     * @return The calculated distance.
     */
    public double distance(Point3D point) {
        return Math.sqrt(distanceSquared(point));
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
        return Objects.equals(_x, point3D._x) && Objects.equals(_y, point3D._y) && Objects.equals(_z, point3D._z);
    }
}
