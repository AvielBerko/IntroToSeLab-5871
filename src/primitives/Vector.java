package primitives;

import java.util.Objects;

import static primitives.Point3D.ZERO;
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
        Point3D head = new Point3D(x, y, z);

        if (head.equals(ZERO)) {
            throw new IllegalArgumentException("Vector cannot be a zero vector");
        }

        _head = head;
    }

    /**
     * Creates a vector by a given double coordinates.
     * @param x The X axis of the end point.
     * @param y The Y axis of the end point.
     * @param z The Z axis of the end point.
     * @exception IllegalArgumentException When the given coordinates are all 0.
     */
    public Vector(double x, double y, double z) {
        Point3D head = new Point3D(x, y, z);

        if (head.equals(ZERO)) {
            throw new IllegalArgumentException("Vector cannot be a zero vector");
        }

        _head = head;
    }

    /**
     * Returns the end point of the vector.
     *
     * @return A shallow copy of the head vector.
     */
    public Point3D getHead() {
        return _head;
    }

    /**
     * Returns the vector's x coordinate
     */
    public double getX() {
        return _head.getX();
    }

    /**
     * Returns the vector's y coordinate
     */
    public double getY() {
        return _head.getY();
    }

    /**
     * Returns the vector's z coordinate
     */
    public double getZ() {
        return _head.getZ();
    }

    /**
     * Rotates the vector around the x axis
     * @param alpha the amount to rotate in degrees
     * @return the current vector
     */
    public Vector rotateX(double alpha) {
        double radianAlpha = alpha * Math.PI / 180;


        double x = _head.getX();
        double y = _head.getY() * Math.cos(radianAlpha) - _head.getZ() * Math.sin(radianAlpha);
        double z = _head.getY() * Math.sin(radianAlpha) + _head.getZ() * Math.cos(radianAlpha);

        _head = new Point3D(x, y, z);
        return this;
    }


    /**
     * Rotates the vector around the y axis
     * @param alpha the amount to rotate in degrees
     * @return the current vector
     */
    public Vector rotateY(double alpha) {
        double radianAlpha = alpha * Math.PI / 180;

        double x = _head.getX() * Math.cos(radianAlpha) + _head.getZ() * Math.sin(radianAlpha);
        double y = _head.getY();
        double z = -_head.getX() * Math.sin(radianAlpha) + _head.getZ() * Math.cos(radianAlpha);

        _head = new Point3D(x, y, z);
        return this;
    }


    /**
     * Rotates the vector around the z axis
     * @param alpha the amount to rotate in degrees
     * @return the current vector
     */
    public Vector rotateZ(double alpha) {
        double radianAlpha = alpha * Math.PI / 180;

        double x = _head.getX() * Math.cos(radianAlpha) - _head.getY() * Math.sin(radianAlpha);
        double y = _head.getX() * Math.sin(radianAlpha) + _head.getY() * Math.cos(radianAlpha);
        double z = _head.getZ();

        _head = new Point3D(x, y, z);
        return this;
    }

    /**
     * Performs vector addition between the current vector and a given vector.
     *
     * @param other The other vector to perform the addition with.
     * @return A new vector of the result.
     */
    public Vector add(Vector other) {
        Point3D result =_head.add(other);
        // Handles the case of zero vector
        if(ZERO.equals(result)){
            throw new IllegalArgumentException("Resulting a ZERO Vector");
        }

        return new Vector(result);
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
        // Handles the case of zero vector
        if (isZero(scalar)) {
            throw new IllegalArgumentException("Resulting a ZERO Vector");
        }

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

        return x * x + y * y + z * z;
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
        double len = length();
        double x = _head._x._coord;
        double y = _head._y._coord;
        double z = _head._z._coord;

        Point3D temp = new Point3D(x / len, y / len, z / len);
        // Handles the case of zero vector
        if (ZERO.equals(temp)) {
            throw new ArithmeticException("Resulting a ZERO Vector");
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
        double len = length();

        double x = _head._x._coord;
        double y = _head._y._coord;
        double z = _head._z._coord;

        return new Vector(x / len, y / len, z / len);
    }


    /*************** Admin *****************/
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
