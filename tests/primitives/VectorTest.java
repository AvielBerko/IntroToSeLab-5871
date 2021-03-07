package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest {

    Vector _a = new Vector(3d, -3d, 3d);
    Vector _aSame = new Vector(3d, -3d, 3d);
    Vector _b = new Vector(-2.75d, 7d, -0.3d);

    @Test
    void add() {
        assertEquals(new Vector(0.25d, 4d, 2.7d), _a.add(_b));
    }

    @Test
    void subtract() {
        assertEquals(new Vector(5.75d, -10d, 3.3d), _a.subtract(_b));
    }

    @Test
    void scale() {
        assertEquals(new Vector(-1d, 1d, -1d), _a.scale(-1d / 3d));
    }

    @Test
    void crossProduct() {
        assertEquals(new Vector(-20.1d, -7.35, 12.75), _a.crossProduct(_b));
    }

    @Test
    void dotProduct() {
        assertEquals(-30.15d, _a.dotProduct(_b));
    }

    @Test
    void lengthSquared() {
        assertEquals(27d, _a.lengthSquared());
    }

    @Test
    void length() {
        assertEquals(5.196d, _a.length(), 0.01);
    }

    @Test
    void normalize() {
        Vector b = new Vector(_b.getHead()).normalize();
        assertEquals(1d, b.lengthSquared(), 0.01);
    }

    @Test
    void normalized() {
        assertEquals(1d, _a.normalized().lengthSquared(), 0.01);
    }

    @Test
    void testEqualsSameObject() {
        assertEquals(_a, _a);
    }

    @Test
    void testEqualsNull() {
        assertNotEquals(null, _a);
    }

    @Test
    void testEqualsDifferentClass() {
        assertFalse(_a.equals(new Coordinate(5)));
    }

    @Test
    void testEqualsWhenNotEquals() {
        assertNotEquals(_b, _a);
    }

    @Test
    void testEqualsWhenEquals() {
        assertEquals(_aSame, _a);
    }
}