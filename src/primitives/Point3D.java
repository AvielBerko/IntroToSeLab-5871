package primitives;

import jdk.jshell.spi.ExecutionControl;

import java.util.Objects;

public class Point3D {
    final Coordinate _x;
    final Coordinate _y;
    final Coordinate _z;

    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        this(x.coord, y.coord, z.coord);
    }

    public Point3D(double x, double y, double z) {
        _x = new Coordinate(x);
        _y = new Coordinate(y);
        _z = new Coordinate(z);
    }

    /*
    public Point3D add(Vector vector) {
    }

    public Vector subtract(Point3D point) {
    }
     */

    public double distanceSquared(Point3D point) {
        double x = _x.coord - point._x.coord;
        double y = _y.coord - point._y.coord;
        double z = _z.coord - point._z.coord;

        return x*x + y*y + z*z;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(_x, _y, _z);
    }
}
