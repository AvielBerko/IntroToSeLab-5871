package primitives;

public class Vector {
    Point3D _head;

    public Vector(Point3D head) {
        this(head._x, head._y, head._z);
    }

    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        this(x.coord, y.coord, z.coord);
    }

    public Vector(double x, double y, double z) {
        _head = new Point3D(x, y, z);
    }

    public Vector add(Vector other) {
        return new Vector(_head._x.coord + other._head._x.coord,
                          _head._y.coord + other._head._y.coord,
                          _head._z.coord + other._head._z.coord);
    }

    public Vector subtract(Vector other) {
        return new Vector(_head._x.coord - other._head._x.coord,
                _head._y.coord - other._head._y.coord,
                _head._z.coord - other._head._z.coord);
    }

    public Vector scale (double scalar) {
        return new Vector(_head._x.coord * scalar, _head._y.coord * scalar, _head._z.coord * scalar);
    }

    public Vector crossProduct(Vector other) {
        return new Vector(_head._y.coord * other._head._z.coord - _head._z.coord * other._head._y.coord,
                          _head._x.coord * other._head._z.coord - _head._z.coord * other._head._x.coord,
                          _head._x.coord * other._head._y.coord - _head._y.coord * other._head._x.coord);
    }

    public double dotProduct(Vector other) {
        return _head._x.coord * other._head._x.coord +
                _head._y.coord * other._head._y.coord +
                _head._z.coord * other._head._z.coord;
    }

    public double lengthSquared() {
        return _head.distanceSquared(Point3D.ZERO);
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public Vector normalize() {
        double len = 1 / length();
        _head = new Point3D(_head._x.coord * len, _head._y.coord * len, _head._z.coord * len);
        return this;
    }

    public Vector normalized() {
        double len = 1 / length();
        return new Vector(new Point3D(_head._x.coord * len, _head._y.coord * len, _head._z.coord * len);)
    }


}
