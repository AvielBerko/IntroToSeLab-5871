package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link geometries.Triangle} class.
 */
class TriangleTest {

    /**
     * Test method for {@link geometries.Triangle#Triangle(primitives.Point3D, primitives.Point3D, primitives.Point3D)}.
     */
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for a proper result.
        try {
            new Triangle(
                    new Point3D(0, 0, 1),
                    new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0)
            );
        } catch (IllegalArgumentException err) {
            fail("Failed construct a correct triangle");
        }

        // =============== Boundary Values Tests ==================
        // TC02: Test when point a is equals to point b.
        try {
            new Triangle(
                    new Point3D(1, 0, 0),
                    new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0)
            );
            fail("Constructed a triangle while point a and b are equals");
        } catch (IllegalArgumentException err) {}

        // TC03: Test when point a is equals to point c.
        try {
            new Triangle(
                    new Point3D(0, 0, 1),
                    new Point3D(1, 0, 0),
                    new Point3D(0, 0, 1)
            );
            fail("Constructed a triangle while point a and c are equals");
        } catch (IllegalArgumentException err) {}

        // TC04: Test when point b is equals to point c.
        try {
            new Triangle(
                    new Point3D(0, 0, 1),
                    new Point3D(0, 1, 0),
                    new Point3D(0, 1, 0)
            );
            fail("Constructed a triangle while point b and c are equals");
        } catch (IllegalArgumentException err) {}
    }

    /**
     * Test method for {@link geometries.Triangle#getNormal(primitives.Point3D)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Triangle pl = new Triangle(
                new Point3D(0, 0, 1),
                new Point3D(1, 0, 0),
                new Point3D(0, 1, 0)
        );

        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals(new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)), "Bad normal to triangle");
    }
}