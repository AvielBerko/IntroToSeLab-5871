package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for {@link primitives.Point3D} class.
 */
class Point3DTest {

    /**
     * Test method for {@link primitives.Point3D#add(primitives.Vector)}.
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        Point3D p = new Point3D(1, 2, 3);
        Vector v = new Vector(-1, -2, -3);

        // test for a proper result
        assertEquals(Point3D.ZERO, p.add(v), "add() wrong result");
    }

    /**
     * Test method for {@link primitives.Point3D#subtract(primitives.Point3D)}.
     */
    @Test
    void testSubtract() {
        Point3D p1 = new Point3D(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        Point3D p2 = new Point3D(2, 3, 4);
        Vector v = new Vector(1, 1, 1);

        // test for a proper result
        assertEquals(v, p2.subtract(p1), "subtract() wrong result");

        // =============== Boundary Values Tests ==================
        // test for a zero vector when subtracting with itself
        try {
            p1.subtract(p1);
            fail("subtract() subtraction of itself doesn't throw an exception.");
        } catch(Exception err) {
            // expected result: zero vector.
        }
    }

    /**
     * Test method for {@link primitives.Point3D#distanceSquared(primitives.Point3D)}.
     */
    @Test
    void testDistanceSquared() {
        Point3D p1 = new Point3D(2, 3, 4);

        // ============ Equivalence Partitions Tests ==============
        Point3D p2 = new Point3D(-2.5, 2, 0);

        // test for a proper result
        assertTrue(isZero(p1.distanceSquared(p2) - 37.25d), "distanceSquared() wrong result");

        // =============== Boundary Values Tests ==================
        // test distance to itself is zero
        assertTrue(isZero(p1.distanceSquared(p1)), "distanceSquared() distance to itself is not zero");
    }

    /**
     * Test method for {@link primitives.Point3D#distance(primitives.Point3D)}.
     */
    @Test
    void testDistance() {
        Point3D p1 = new Point3D(2, 3, 4);

        // ============ Equivalence Partitions Tests ==============
        Point3D p2 = new Point3D(2, 3, 0);

        // test for a proper result
        assertTrue(isZero(p1.distance(p2) - 4d), "distance() wrong result");

        // =============== Boundary Values Tests ==================
        // test distance to itself is zero
        assertTrue(isZero(p1.distance(p1)), "distance() distance to itself is not zero");
    }
}