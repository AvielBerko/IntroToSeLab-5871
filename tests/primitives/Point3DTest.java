package primitives;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Point3DTest {

    Point3D _a = new Point3D(1.1d, 2d, 3d);
    Point3D _aSame = new Point3D(1.1d, 2d, 3000000000000000000000000000000000000001d);
    Point3D _b = new Point3D(17.123d, -2d, 0.5d);

    @Test
    void add() {
    }

    @Test
    void subtract() {
    }

    @Test
    void distanceSquared() {
        double distanceSquared = _a.distanceSquared(_b);
        Assertions.assertEquals(distanceSquared, 278.987, 0.01);
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