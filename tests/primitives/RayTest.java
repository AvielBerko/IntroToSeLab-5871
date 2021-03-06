package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    Ray _a = new Ray(new Point3D(3d, 5d, 2d), new Vector(2d, 1d, 3d));
    Ray _aSame = new Ray(new Point3D(3d, 5d, 2d), new Vector(2d, 1d, 3d));
    Ray _b = new Ray(new Point3D(-1d, 5d, 36.5d), new Vector(Point3D.ZERO));

    @Test
    void testEqualsSameObject() { assertEquals(_a, _a); }

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
    void testEqualsWhenEquals() { assertEquals(_a, _aSame); }
}