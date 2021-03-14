package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link geometries.Sphere} class.
 */
class SphereTest {

    /**
     * Test method for {@link geometries.Sphere#Sphere(primitives.Point3D, double)}.
     */
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for a proper result.
        try {
            new Sphere(new Point3D(1, 0, 1), 2.5d);
        } catch (IllegalArgumentException err) {
            fail("Failed construct a correct sphere");
        }

        // =============== Boundary Values Tests ==================
        // TC02: Test when the radius is 0.1
        try {
            new Sphere(new Point3D(1, 0, 1), 0.1d);
        } catch (IllegalArgumentException err) {
            fail("Failed construct a correct sphere when the radius is 0.1");
        }

        // TC03: Test when the radius is 0
        try {
            new Sphere(new Point3D(1, 0, 1), 0);
            fail("Constructed a sphere while the radius is 0");
        } catch (IllegalArgumentException err) {}

        // TC04: Test when the radius is -0.1
        try {
            new Sphere(new Point3D(1, 0, 1), -0.1);
            fail("Constructed a sphere while the radius is -0.1");
        } catch (IllegalArgumentException err) {}
    }

    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point3D)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Sphere sp = new Sphere(new Point3D(1, 1, 0), 1d);

        assertEquals(new Vector(0, -1, 0), sp.getNormal(new Point3D(1, 0, 0)), "Bad normal to sphere");
    }
}