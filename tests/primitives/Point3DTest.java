package primitives;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Point3DTest {

    Point3D _a = new Point3D(1.1d, 2d, 3d);
    Point3D _aSame = new Point3D(1.1d, 2d, 3d);
    Point3D _b = new Point3D(17.123d, -2d, 0.5d);
    Vector _vec = new Vector(3d, 2.2d, 48d);

    @Test
    void add() {
        Point3D addition = _a.add(_vec);
        Point3D result = new Point3D(3.1d, 4.2d, 51d);
        assertEquals(addition, result);
    }

    @Test
    void subtract() {
        Vector subtraction = _a.subtract(_b);
        Vector result = new Vector(16.023d, -4d, -2.5d);
    }

    @Test
    void distanceSquared() {
        double distanceSquared = _a.distanceSquared(_b);
        assertEquals(distanceSquared, 278.987, 0.01);
    }

    @Test
    void distance() {
        double distance = _a.distance(_b);
        Assertions.assertEquals(distance, 16.703, 0.01);
    }

    @Test
    void testEqualsSameObject() {
        assertEquals(_a, _a);
    }

    @Test
    void testEqualsNull() {
        assertNotEquals(_a, null);
    }

    @Test
    void testEqualsDifferentClass() {
        assertFalse(_a.equals(new Coordinate(5)));
    }

    @Test
    void testEqualsWhenNotEquals() {
        assertNotEquals(_a, _b);
    }

    @Test
    void testEqualsWhenEquals() {
        assertEquals(_a, _aSame);
    }
}