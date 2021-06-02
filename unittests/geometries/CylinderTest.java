package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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

    /**
     * Test method for {@link geometries.Cylinder#findIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections() {
        Cylinder cylinder = new Cylinder(new Ray(new Point3D(2,0,0), new Vector(0,0,1)), 1d, 2d);

        // ============ Equivalence Partitions Tests ==============

        //TC01 ray is outside and parallel to the cylinder's ray

        List<Point3D> result = cylinder.findIntersections(new Ray(new Point3D(5,0,0), new Vector(0,0,1)));
        assertNull(result, "Wrong number of points");


        //TC02 ray starts inside and parallel to the cylinder's ray

        result = cylinder.findIntersections(new Ray(new Point3D(2.5,0,1), new Vector(0,0,1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point3D(2.5,0,2)), result, "Bad intersection point");

        //TC03 ray starts outside and parallel to the cylinder's ray and crosses the cylinder

        result = cylinder.findIntersections(new Ray(new Point3D(2.5,0,-1), new Vector(0,0,1)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(List.of(new Point3D(2.5, 0, 0), new Point3D(2.5,0,2)), result, "Bad intersection point");

        //TC04 ray starts from outside and crosses the cylinder

        result = cylinder.findIntersections(new Ray(new Point3D(-2,0,0), new Vector(1,0,0)));
        assertNull(result, "Wrong number of points");

        //TC05 ray starts from inside and crosses the cylinder

        result = cylinder.findIntersections(new Ray(new Point3D(1.5,0,0.5), new Vector(1,0,0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point3D(3,0,0.5)), result, "Bad intersection points");

        //TC06 ray starts from outside the cylinder and doesn't cross the cylinder

        result = cylinder.findIntersections(new Ray(new Point3D(5,0,0), new Vector(1,0,0)));
        assertNull(result, "Wrong number of points");

        // =============== Boundary Values Tests ==================

        //TC07 ray is on the surface of the cylinder (not bases)

        result = cylinder.findIntersections(new Ray(new Point3D(3,0,0), new Vector(0,0,1)));
        assertNull(result, "Wrong number of points");

        //TC08 ray is on the base of the cylinder and crosses 2 times

        result = cylinder.findIntersections(new Ray(new Point3D(-1,0,0), new Vector(1,0,0)));
        assertNull(result, "Wrong number of points");
        //assertEquals(List.of(new Point3D(1,0,0), new Point3D(3,0,0)), result, "Bad intersection points");

        //TC08 ray is in center of the cylinder

        result = cylinder.findIntersections(new Ray(new Point3D(2,0,0), new Vector(0,0,1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point3D(2,0,2)), result, "Bad intersection points");

        //TC09 ray is perpendicular to cylinder's ray and starts from outside the tube

        result = cylinder.findIntersections(new Ray(new Point3D(-2,0,0.5), new Vector(1,0,0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(List.of(new Point3D(1,0,0.5), new Point3D(3,0,0.5)), result, "Bad intersection points");

        //TC09 ray is perpendicular to cylinder's ray and starts from inside cylinder (not center)

        result = cylinder.findIntersections(new Ray(new Point3D(1.5,0,0.5), new Vector(1,0,0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point3D(3,0,0.5)), result, "Bad intersection points");

        //TC10 ray is perpendicular to cylinder's ray and starts from the center of cylinder

        result = cylinder.findIntersections(new Ray(new Point3D(2,0,0.5), new Vector(1,0,0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point3D(3,0,0.5)), result, "Bad intersection points");

        //TC11 ray is perpendicular to cylinder's ray and starts from the surface of cylinder to inside

        result = cylinder.findIntersections(new Ray(new Point3D(1,0,0.5), new Vector(1,0,0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point3D(3,0,0.5)), result, "Bad intersection points");

        //TC12 ray is perpendicular to cylinder's ray and starts from the surface of cylinder to outside

        result = cylinder.findIntersections(new Ray(new Point3D(3,0,0), new Vector(1,0,0)));
        assertNull(result, "Wrong number of points");

        //TC13 ray starts from the surface to outside

        result = cylinder.findIntersections(new Ray(new Point3D(3,0,0), new Vector(1,1,1)));
        assertNull(result, "Wrong number of points");

        //TC14 ray starts from the surface to inside

        result = cylinder.findIntersections(new Ray(new Point3D(3,0,-0.5), new Vector(-1,0,1)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(List.of(new Point3D(1, 0, 1.5), new Point3D(2.5,0,0)), result, "Bad intersection point");

        //TC15 ray starts from the center

        result = cylinder.findIntersections(new Ray(new Point3D(2,0,0), new Vector(1,0,1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point3D(3,0,1)), result, "Bad intersection point");

        //TC16 prolongation of ray crosses cylinder

        result = cylinder.findIntersections(new Ray(new Point3D(3,0,0), new Vector(1,0,0)));
        assertNull(result, "Wrong number of points");

        //TC17 ray starts from the surface to outside

        result = cylinder.findIntersections(new Ray(new Point3D(3,0,0), new Vector(1,1,1)));
        assertNull(result, "Wrong number of points");

        //TC18 ray starts from the surface to inside

        result = cylinder.findIntersections(new Ray(new Point3D(3,0,0.5), new Vector(-1,0,0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point3D(1,0,0.5)), result, "Bad intersection point");

        //TC19 ray starts from the center

        result = cylinder.findIntersections(new Ray(new Point3D(2,0,0), new Vector(1,0,1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point3D(3,0,1)), result, "Bad intersection point");

        //TC20 prolongation of ray crosses cylinder

        result = cylinder.findIntersections(new Ray(new Point3D(3,0,0), new Vector(1,0,0)));
        assertNull(result, "Wrong number of points");

        //TC21 ray is on the surface starts before cylinder

        result = cylinder.findIntersections(new Ray(new Point3D(3,0,-1), new Vector(0,0,1)));
        assertNull(result, "Wrong number of points");

        //TC22 ray is on the surface starts at bottom's base

        result = cylinder.findIntersections(new Ray(new Point3D(3,0,0), new Vector(0,0,1)));
        assertNull(result, "Wrong number of points");

        //TC23 ray is on the surface starts on the surface

        result = cylinder.findIntersections(new Ray(new Point3D(3,0,1), new Vector(0,0,1)));
        assertNull(result, "Wrong number of points");

        //TC24 ray is on the surface starts at top's base

        result = cylinder.findIntersections(new Ray(new Point3D(3,0,2), new Vector(0,0,1)));
        assertNull(result, "Wrong number of points");
    }
}