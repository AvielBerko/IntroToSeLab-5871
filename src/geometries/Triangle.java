package geometries;

import primitives.Point3D;

public class Triangle extends Polygon {
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
