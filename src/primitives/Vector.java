package primitives;

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
}
