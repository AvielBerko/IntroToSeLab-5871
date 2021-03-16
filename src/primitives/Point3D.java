package primitives;

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
