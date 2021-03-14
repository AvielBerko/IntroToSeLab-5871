package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link geometries.Tube} class.
 */
class TubeTest {

    /**
     * Test method for {@link geometries.Tube#Tube(primitives.Ray, double)}.
     */
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for a proper result.
        try {
            new Tube(new Ray(new Point3D(1, -1.5, 0), new Vector(0, 2, 1)), 2);
        } catch (IllegalArgumentException err) {
            fail("Failed construct a correct tube");
        }

        // =============== Boundary Values Tests ==================
        // TC02: Test when the radius is 0.1
        try {
            new Tube(new Ray(new Point3D(1, -1.5, 0), new Vector(0, 2, 1)), 0.1);
        } catch (IllegalArgumentException err) {
            fail("Failed construct a correct tube when the radius is 0.1");
        }

        // TC03: Test when the radius is 0
        try {
            new Tube(new Ray(new Point3D(1, -1.5, 0), new Vector(0, 2, 1)), 0);
            fail("Constructed a tube while the radius is 0");
        } catch (IllegalArgumentException ignored) {}

        // TC04: Test when the radius is -0.1
        try {
            new Tube(new Ray(new Point3D(1, -1.5, 0), new Vector(0, 2, 1)), -0.1);
            fail("Constructed a tube while the radius is -0.1");
        } catch (IllegalArgumentException ignored) {}
    }

    /**
     * Test method for {@link geometries.Tube#getNormal(primitives.Point3D)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Tube tb = new Tube(new Ray(new Point3D(1, 1, 0), new Vector(0, 0, 1)), 1d);

        assertEquals(new Vector(0, -1, 0), tb.getNormal(new Point3D(1, 0, 2)), "Bad normal to tube");
    }
}