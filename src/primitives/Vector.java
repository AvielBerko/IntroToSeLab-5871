package primitives;

import java.util.Objects;

import static primitives.Point3D.ZERO;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * A vector - A fundamental object in geometry with direction and size,
 * defined by the end point (when the starting point is the axis's origin).
 */
public class Vector {
     Point3D _head;

    /** primary constructor
     * Creates a vector by a given head.
     * @param head The end point of the vector.
     * @exception IllegalArgumentException When the given point is 0.
     */
    public Vector(Point3D head) {
        // this(head._x, head._y._coord, head._z._coord);

        // For performance improvement.
        if (head.equals(ZERO)) {
            throw new IllegalArgumentException("Vector cannot be a zero vector");
        }

        _head = head;
    }

    /**
     * Creates a vector by a given coordinates.
     * @param x The X axis of the end point.
     * @param y The Y axis of the end point.
     * @param z The Z axis of the end point.
     * @exception IllegalArgumentException When the given coordinates are all 0.
     */
    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        // this(new Point3D(x,y,x));

        // For performance improvement.
        Point3D head = new Point3D(x, y, z);

        if (head.equals(ZERO)) {
            throw new IllegalArgumentException("Vector cannot be a zero vector");
        }
        
        _head=head;
    }

    /**
     * Creates a vector by a given double coordinates.
     * @param x The X axis of the end point.
     * @param y The Y axis of the end point.
     * @param z The Z axis of the end point.
     * @exception IllegalArgumentException When the given coordinates are all 0.
     */
    public Vector(double x, double y, double z) {
        //this(new Point3D(x,y,z));
        Point3D head = new Point3D(x, y, z);

        if (head.equals(ZERO)) {
            throw new IllegalArgumentException("Vector cannot be a zero vector");
        }

        _head=head;

    }

    /**
     * Returns the end point of the vector.
     *
     * @return A shallow copy of the head vector.
     */
    public Point3D getHead() {
        // return new Point3D(_head._x, _head._y._coord, _head._z._coord);

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
        Point3D point3D =_head.add(other);
        if(ZERO.equals(point3D)){
            throw new IllegalArgumentException("resulting ZERO Point");
        }
        return new Vector(point3D);
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
        double x = _head._x._coord;
        double y = _head._y._coord;
        double z = _head._z._coord;

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
        double x1 = _head._x._coord;
        double y1 = _head._y._coord;
        double z1 = _head._z._coord;

        double x2 = other._head._x._coord;
        double y2 = other._head._y._coord;
        double z2 = other._head._z._coord;

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
        double x1 = _head._x._coord;
        double y1 = _head._y._coord;
        double z1 = _head._z._coord;

        double x2 = other._head._x._coord;
        double y2 = other._head._y._coord;
        double z2 = other._head._z._coord;

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
        double x = _head._x._coord;
        double y = _head._y._coord;
        double z = _head._z._coord;

        return x*x +y*y +z*z;
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
//        double len = alignZero(1 / length());
//
//        if(isZero(len)){
//            throw new ArithmeticException("hyhuihuihuih");
//        }
//
//        double x = _head._x._coord;
//        double y = _head._y._coord;
//        double z = _head._z._coord;
//        _head = new Point3D(x * len, y * len, z * len);

        double len = length();
        double x = _head._x._coord;
        double y = _head._y._coord;
        double z = _head._z._coord;
        Point3D temp = new Point3D(x / len, y / len, z / len);
        if (ZERO.equals(temp)) {
            throw new ArithmeticException("");
        }
        _head = temp;
        return this;
    }

    /**
     * Normalize a copy of the current vector.
     *
     * @return The copy of the vector.
     */
    public Vector normalized() {
        double len = 1 / length();

        double x = _head._x._coord;
        double y = _head._y._coord;
        double z = _head._z._coord;

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
