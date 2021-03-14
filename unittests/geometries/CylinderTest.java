package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link geometries.Cylinder} class.
 */
class CylinderTest {

    /**
     * Test method for {@link geometries.Cylinder#Cylinder(primitives.Ray, double, double)}.
     */
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for a proper result.
        try {
            new Cylinder(new Ray(new Point3D(1, -1.5, 0), new Vector(0, 2, 1)), 2, 3);
        } catch (IllegalArgumentException err) {
            fail("Failed construct a correct cylinder");
        }

        // =============== Boundary Values Tests ==================
        // TC02: Test when the radius is 0.1
        try {
            new Cylinder(new Ray(new Point3D(1, -1.5, 0), new Vector(0, 2, 1)), 0.1, 1);
        } catch (IllegalArgumentException err) {
            fail("Failed construct a correct cylinder when the radius is 0.1");
        }

        // TC03: Test when the radius is 0
        try {
            new Cylinder(new Ray(new Point3D(1, -1.5, 0), new Vector(0, 2, 1)), 0, 1);
            fail("Constructed a cylinder while the radius is 0");
        } catch (IllegalArgumentException ignored) {}

        // TC04: Test when the radius is -0.1
        try {
            new Cylinder(new Ray(new Point3D(1, -1.5, 0), new Vector(0, 2, 1)), -0.1, 1);
            fail("Constructed a cylinder while the radius is -0.1");
        } catch (IllegalArgumentException ignored) {}

        // TC02: Test when the height is 0.1
        try {
            new Cylinder(new Ray(new Point3D(1, -1.5, 0), new Vector(0, 2, 1)), 1, 0.1);
        } catch (IllegalArgumentException err) {
            fail("Failed construct a correct cylinder when the height is 0.1");
        }

        // TC03: Test when the height is 0
        try {
            new Cylinder(new Ray(new Point3D(1, -1.5, 0), new Vector(0, 2, 1)), 1, 0);
            fail("Constructed a cylinder while the height is 0");
        } catch (IllegalArgumentException ignored) {}

        // TC04: Test when the height is -0.1
        try {
            new Cylinder(new Ray(new Point3D(1, -1.5, 0), new Vector(0, 2, 1)), 1, -0.1);
            fail("Constructed a cylinder while the height is -0.1");
        } catch (IllegalArgumentException ignored) {}
    }

    /**
     * Test method for {@link geometries.Cylinder#getNormal(primitives.Point3D)}.
     */
    @Test
    void testGetNormal() {
        Cylinder c = new Cylinder(new Ray(new Point3D(1, 1, 0), new Vector(0, 0, 1)), 1d, 3d);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test with point on the side of the cylinder
        assertEquals(new Vector(0, -1, 0), c.getNormal(new Point3D(1, 0, 1)), "Bad normal to the side of the cylinder");

        // TC02: Test with point on the top of the cylinder
        assertEquals(new Vector(0, 0, 1), c.getNormal(new Point3D(1, 1, 3)), "Bad normal to the top of the cylinder");

        // TC03: Test with point on the bottom of the cylinder
        assertEquals(new Vector(0, 0, -1), c.getNormal(new Point3D(1, 1, 0)), "Bad normal to the bottom of the cylinder");

        // =============== Boundary Values Tests ==================
        // TC04: Test with point on the top-edge of the cylinder
        assertEquals(new Vector(0, 0, 1), c.getNormal(new Point3D(1, 0, 3)), "Bad normal to the top-edge of the cylinder");

        // TC05: Test with point on the bottom-edge of the cylinder
        assertEquals(new Vector(0, 0, -1), c.getNormal(new Point3D(0, 1, 0)), "Bad normal to the bottom-edge of the cylinder");
    }
}