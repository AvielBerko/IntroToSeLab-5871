package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link geometries.Plane} class.
 */
class PlaneTest {

    /**
     * Test method for {@link geometries.Plane#Plane(primitives.Point3D, primitives.Point3D, primitives.Point3D)}.
     */
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for a proper result.
        try {
            new Plane(
                    new Point3D(0, 0, 1),
                    new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0)
            );
        } catch (IllegalArgumentException err) {
            fail("Failed construct a correct plane");
        }

        // =============== Boundary Values Tests ==================
        // TC02: Test when point a is equals to point b.
        try {
            new Plane(
                    new Point3D(1, 0, 0),
                    new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0)
            );
            fail("Constructed a plane while point a and b are equals");
        } catch (IllegalArgumentException ignored) {}

        // TC03: Test when point a is equals to point c.
        try {
            new Plane(
                    new Point3D(0, 0, 1),
                    new Point3D(1, 0, 0),
                    new Point3D(0, 0, 1)
            );
            fail("Constructed a plane while point a and c are equals");
        } catch (IllegalArgumentException ignored) {}

        // TC04: Test when point b is equals to point c.
        try {
            new Plane(
                    new Point3D(0, 0, 1),
                    new Point3D(0, 1, 0),
                    new Point3D(0, 1, 0)
            );
            fail("Constructed a plane while point b and c are equals");
        } catch (IllegalArgumentException ignored) {}
    }

    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point3D)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Plane pl = new Plane(
                new Point3D(0, 0, 1),
                new Point3D(1, 0, 0),
                new Point3D(0, 1, 0)
        );

        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals(new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)), "Bad normal to plane");
    }

    /**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections() {
        Plane plane = new Plane(
                new Point3D(1, 0, 0),
                new Point3D(0, 1, 0),
                new Point3D(0, 0, 1)
        );
        List<Point3D> result;

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray intersects the plane (1 points)
        result = plane.findIntersections(new Ray(new Point3D(0, 1, 1), new Vector(0, 0, -1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new Point3D(0, 1, 0), result.get(0), "Ray intersects the plane");

        // TC02: Ray doesn't intersects the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point3D(0, 1, 1), new Vector(0, 0, 1))), "Ray doesn't intersects the plane");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray is parallel to the plane (all tests 0 points)
        // TC03: Ray is included in the plane
        assertNull(plane.findIntersections(new Ray(new Point3D(0, 0, 1), new Vector(-1, -1, 2))), "Ray is included in the plane");

        // TC04: Ray isn't included in the plane
        assertNull(plane.findIntersections(new Ray(new Point3D(0, 0, 2), new Vector(-1, -1, 2))), "Ray is parallel to the plane and not included in the plane");

        // **** Group: Ray is orthogonal to the plane
        // TC05: Ray starts before the plane (1 points)
        result = plane.findIntersections(new Ray(new Point3D(-1, -1, -1), new Vector(1, 1, 1)));
        assertEquals(1, result.size(), "Wrong number of points");
        double third = 1d / 3;
        assertEquals(new Point3D(third, third, third), result.get(0), "Ray starts before the plane and is orthogonal to the plane");

        // TC06: Ray starts inside the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point3D(0, 0, 1), new Vector(1, 1, 1))), "Ray starts inside the plane and is orthogonal to the plane");

        // TC07: Ray starts after the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point3D(2, 2, 2), new Vector(1, 1, 1))), "Ray starts after the plane and is orthogonal to the plane");

        // **** Group: Special cases
        // TC08: Ray begins in the plane's referenced point (0 points)
        assertNull(plane.findIntersections(new Ray(plane.getPoint(), new Vector(1, 0, 0))), "Ray begins in the plane's referenced point");
    }
}