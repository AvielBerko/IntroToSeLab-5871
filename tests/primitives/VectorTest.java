package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest {

    Vector _a = new Vector(3d, -3d, 3d);
    Vector _aSame = new Vector(3d, -3d, 3d);
    Vector _b = new Vector(-2.75d, 7d, -0.3d);

    @Test
    void add() {
    }

    @Test
    void subtract() {
    }

    @Test
    void scale() {
    }

    @Test
    void crossProduct() {
    }

    @Test
    void dotProduct() {
    }

    @Test
    void lengthSquared() {
    }

    @Test
    void length() {
    }

    @Test
    void normalize() {
        Vector unit = new Vector(-0.365d, -0.93d, 0.399d);
        Vector b = new Vector(_b._head).normalize();
        assertEquals(b, unit);
    }

    @Test
    void normalized() {
        Vector unit = new Vector(0.802d, -0.535d, 0.267d);
        assertEquals(_a.normalized(), unit);
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
    void testEqualsDifferentClass() { assertFalse(_a.equals(new Coordinate(5))); }

    @Test
    void testEqualsWhenNotEquals() {
        assertNotEquals(_a, _b);
    }

    @Test
    void testEqualsWhenEquals() { assertEquals(_a, _aSame); }
}