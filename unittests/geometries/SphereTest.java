package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link geometries.Sphere} class.
 */
class SphereTest {

    /**
     * Test method for {@link geometries.Sphere#Sphere(double, primitives.Point3D)}.
     */
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for a proper result.
        try {
            new Sphere(2.5d, new Point3D(1, 0, 1));
        } catch (IllegalArgumentException err) {
            fail("Failed construct a correct sphere");
        }

        // =============== Boundary Values Tests ==================
        // TC02: Test when the radius is 0.1
        try {
            new Sphere(0.1d, new Point3D(1, 0, 1));
        } catch (IllegalArgumentException err) {
            fail("Failed construct a correct sphere when the radius is 0.1");
        }

        // TC03: Test when the radius is 0
        try {
            new Sphere(0, new Point3D(1, 0, 1));
            fail("Constructed a sphere while the radius is 0");
        } catch (IllegalArgumentException ignored) {}

        // TC04: Test when the radius is -0.1
        try {
            new Sphere(-0.1, new Point3D(1, 0, 1));
            fail("Constructed a sphere while the radius is -0.1");
        } catch (IllegalArgumentException ignored) {}
    }

    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point3D)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Sphere sp = new Sphere(1d, new Point3D(1, 1, 0));

        assertEquals(new Vector(0, -1, 0), sp.getNormal(new Point3D(1, 0, 0)), "Bad normal to sphere");
    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(1d, new Point3D(1, 0, 0));
        List<Point3D> result;

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))), "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
        Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
        result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        result = sphere.findIntersections(new Ray(new Point3D(1, 0.5, 0.5),
            new Vector(1, -0.5, -0.5)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new Point3D(2, 0, 0), result.get(0), "Ray starts inside the sphere");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point3D(2, 2, 2), new Vector(1, 2, 3))), "Ray starts after the sphere");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(0, -1, 1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new Point3D(1, 0, 1), result.get(0), "Ray starts at sphere and goes inside");

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(0, 1, -1))), "Ray starts at sphere and goes outside");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 0, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(List.of(Point3D.ZERO, new Point3D(2, 0, 0)), result, "Ray starts before the sphere and it's line goes through the center");

        // TC14: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(0, -1, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new Point3D(1, -1, 0), result.get(0), "Ray starts at sphere and goes inside. The ray's line goes through the center");

        // TC15: Ray starts inside (1 points)
        result = sphere.findIntersections(new Ray(new Point3D(1, 0.5, 0), new Vector(0, -1, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new Point3D(1, -1, 0), result.get(0), "Ray starts inside sphere and it's line goes through the center");

        // TC16: Ray starts at the center (1 points)
        result = sphere.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(0, 1, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new Point3D(1, 1, 0), result.get(0), "Ray starts at the sphere's center");

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(0, 1, 0))), "Ray starts at sphere and goes outside. The ray's line goes through the center");

        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point3D(1, 1.5, 0), new Vector(0, 1, 0))), "Ray starts after sphere and goes outside. The ray's line goes through the center");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point3D(0, 1, 0), new Vector(1, 0, 0))), "Ray starts before the sphere's tangent point");

        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(1, 0, 0))), "Ray starts at the sphere's tangent point");

        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point3D(2, 1, 0), new Vector(1, 0, 0))), "Ray starts after the sphere's tangent point");

        // **** Group: Special cases
        // TC19: Ray's line is outside the sphere. The ray is orthogonal to the ray that goes through the center line (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point3D(1, 1.5, 0), new Vector(1, 0, 0))), "Ray's line is outside the sphere. The ray is orthogonal to the ray that goes through the center line");
    }
}