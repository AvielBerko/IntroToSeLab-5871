package primitives;

import java.util.Objects;

/**
 * A vector - A fundamental object in geometry with direction and size,
 * defined by the end point (when the starting point is the axis's origin).
 */
public class Vector {
    private Point3D _head;

    /**
     * Creates a vector by a given head.
     * @param head The end point of the vector.
     */
    public Vector(Point3D head) {
        this(head.getX(), head.getY(), head.getZ());
    }

    /**
     * Creates a vector by a given coordinates.
     * @param x The X axis of the end point.
     * @param y The Y axis of the end point.
     * @param z The Z axis of the end point.
     */
    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        //this(x.coord, y.coord, z.coord);

        // For performance improvement.
        _head = new Point3D(x, y, z);

        if (_head.equals(Point3D.ZERO)) {
            throw new IllegalArgumentException("Vector cannot be a zero vector");
        }
    }

    /**
     * Creates a vector by a given double coordinates.
     * @param x The X axis of the end point.
     * @param y The Y axis of the end point.
     * @param z The Z axis of the end point.
     */
    public Vector(double x, double y, double z) {
        _head = new Point3D(x, y, z);

        if (_head.equals(Point3D.ZERO)) {
            throw new IllegalArgumentException("Vector cannot be a zero vector");
        }
    }

    /**
     * Returns the end point of the vector.
     *
     * @return A shallow copy of the head vector.
     */
    public Point3D getHead() {
        // return new Point3D(_head.getX(), _head.getY(), _head.getZ());

        // For performance improvement.
        return _head;
    }

    /**
     * Performs vector addition between the current vector and a given vector.
     *
     * @param other The other vector to perform the addition with.
     * @return A new vector of the result.
     */
    public Vector add(Vector other) {
        return new Vector(_head.add(other));
    }

    /**
     * Performs vector subtraction between the current vector and a given vector.
     *
     * @param other The other vector to perform the subtraction with.
     * @return A new vector of the result.
     */
    public Vector subtract(Vector other) {
        return _head.subtract(other._head);
    }

    /**
     * Performs a multiplication between the current vector and a given scalar.
     *
     * @param scalar The scalar to perform the multiplication with.
     * @return A new vector of the result.
     */
    public Vector scale(double scalar) {
        double x = _head.getX().coord;
        double y = _head.getY().coord;
        double z = _head.getZ().coord;

        return new Vector(
                x * scalar,
                y * scalar,
                z * scalar
        );
    }

    /**
     * Performs a cross product between the current vector and a given vector.
     *
     * @param other The other vector to perform the cross product with.
     * @return A new vector of the result.
     */
    public Vector crossProduct(Vector other) {
        double x1 = _head.getX().coord;
        double y1 = _head.getY().coord;
        double z1 = _head.getZ().coord;

        double x2 = other._head.getX().coord;
        double y2 = other._head.getY().coord;
        double z2 = other._head.getZ().coord;

        return new Vector(y1 * z2 - z1 * y2,
                          z1 * x2 - x1 * z2,
                          x1 * y2 - y1 * x2
        );
    }

    /**
     * Performs a dot product between the current vector and a given scalar.
     *
     * @param other The other vector to perform the dot product with.
     * @return The scalar of the result.
     */
    public double dotProduct(Vector other) {
        double x1 = _head.getX().coord;
        double y1 = _head.getY().coord;
        double z1 = _head.getZ().coord;

        double x2 = other._head.getX().coord;
        double y2 = other._head.getY().coord;
        double z2 = other._head.getZ().coord;

        return x1 * x2 +
               y1 * y2 +
               z1 * z2;
    }

    /**
     * Calculates the squared length of the vector.
     *
     * @return The squared length of the vector.
     */
    public double lengthSquared() {
        return _head.distanceSquared(Point3D.ZERO);
    }

    /**
     * Calculates the length of the vector.
     *
     * @return The length of the vector.
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Normalize the current vector.
     *
     * @return The current vector.
     */
    public Vector normalize() {
        double len = 1 / length();

        double x = _head.getX().coord;
        double y = _head.getY().coord;
        double z = _head.getZ().coord;

        _head = new Point3D(x * len, y * len, z * len);
        return this;
    }

    /**
     * Normalize a copy of the current vector.
     *
     * @return The copy of the vector.
     */
    public Vector normalized() {
        double len = 1 / length();

        double x = _head.getX().coord;
        double y = _head.getY().coord;
        double z = _head.getZ().coord;

        return new Vector(x * len, y * len, z * len);
    }

    @Override
    public String toString() {
        return "Vector" + _head;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Objects.equals(_head, vector._head);
    }
}
