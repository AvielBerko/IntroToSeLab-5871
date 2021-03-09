package geometries;

import primitives.Point3D;

/**
 * Triangle class represents two-dimensional triangle in 3D Cartesian coordinate
 * system
 */
public class Triangle extends Polygon {
    /**
     * Creates a new triangle from a given vertices of the triangle.
     * @param a A point on the plane.
     * @param b A point on the plane.
     * @param c A point on the plane.
     * @exception IllegalArgumentException When two of the given vertices are equals.
     */
    public Triangle(Point3D a, Point3D b, Point3D c) {
        super(a, b, c);
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "a=" + _vertices.get(0) +
                ", b=" + _vertices.get(1) +
                ", c=" + _vertices.get(2) +
               '}';
    }
}
