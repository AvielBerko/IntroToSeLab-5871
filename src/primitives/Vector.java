package primitives;

import java.util.Objects;

public class Vector {
    final Point3D _head;

    public Vector(Point3D head) {
        this(head._x, head._y, head._z);
    }

    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        this(x.coord, y.coord, z.coord);
    }

    public Vector(double x, double y, double z) {
        _head = new Point3D(x, y, z);
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

    @Override
    public int hashCode() {
        return Objects.hash(_head);
    }
}
